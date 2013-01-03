/*
 * Created on Oct 31, 2006
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2006-2013 the original author or authors.
 */
package org.fest.reflect.method;

import static org.fest.reflect.util.Accessibles.makeAccessible;
import static org.fest.reflect.util.Accessibles.setAccessibleIgnoringExceptions;
import static org.fest.reflect.util.Throwables.targetOf;
import static org.fest.reflect.util.Types.castSafely;
import static org.fest.util.Arrays.format;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Preconditions.checkNotNullOrEmpty;
import static org.fest.util.Strings.quote;

import java.lang.reflect.Method;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.reflect.exception.ReflectionError;

/**
 * Invokes a method using
 * <a href="http://docs.oracle.com/javase/tutorial/reflect/index.html" target="_blank">Java Reflection</a>.
 *
 * @param <T> the return type of the method to invoke.
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class MethodInvoker<T> {
  private final Class<T> returnType;
  private final Object target;
  private final Method method;

  MethodInvoker(@Nonnull String methodName, @Nonnull Class<T> returnType, @Nonnull Class<?>[] parameterTypes,
      @Nonnull Object target) {
    this.returnType = checkNotNull(returnType);
    this.target = checkNotNull(target);
    this.method = findMethodInClassHierarchy(checkNotNullOrEmpty(methodName), checkNotNull(parameterTypes));
  }

  private @Nonnull Method findMethodInClassHierarchy(@Nonnull String methodName, @Nonnull Class<?>[] parameterTypes) {
    Method method = null;
    Class<?> targetType = target instanceof Class<?> ? (Class<?>) target : target.getClass();
    Class<?> type = targetType;
    while (type != null) {
      method = findMethod(methodName, type, parameterTypes);
      if (method != null) {
        break;
      }
      type = type.getSuperclass();
    }
    if (method == null) {
      String format = "Unable to find method: %s in: %s with parameter type(s) %s";
      throw new ReflectionError(String.format(format, quote(methodName), targetType.getName(), format(parameterTypes)));
    }
    return method;
  }

  private static @Nullable Method findMethod(
      @Nonnull String methodName, @Nonnull Class<?> type, @Nonnull Class<?>[] parameterTypes) {
    try {
      return type.getDeclaredMethod(methodName, parameterTypes);
    } catch (SecurityException e) {
      return null;
    } catch (NoSuchMethodException e) {
      return null;
    }
  }

  /**
   * <p>
   * Invokes a method.
   * </p>
   *
   * <p>
   * Examples demonstrating usage of the fluent interface:
   *
   * <pre>
   * // Equivalent to invoking the method 'person.setName("Luke")'
   * {@link org.fest.reflect.core.Reflection#method(String) method}("setName").{@link org.fest.reflect.method.MethodName#withParameterTypes(Class...) withParameterTypes}(String.class)
   *                  .{@link org.fest.reflect.method.ParameterTypes#in(Object) in}(person)
   *                  .{@link org.fest.reflect.method.MethodInvoker#invoke(Object...) invoke}("Luke");
   *
   * // Equivalent to invoking the method 'jedi.getPowers()'
   * List&lt;String&gt; powers = {@link org.fest.reflect.core.Reflection#method(String) method}("getPowers").{@link org.fest.reflect.method.MethodName#withReturnType(org.fest.reflect.reference.TypeRef) withReturnType}(new {@link org.fest.reflect.reference.TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {})
   *                                          .{@link org.fest.reflect.method.ReturnTypeRef#in(Object) in}(person)
   *                                          .{@link org.fest.reflect.method.MethodInvoker#invoke(Object...) invoke}();
   *
   * // Equivalent to invoking the static method 'Jedi.setCommonPower("Jump")'
   * {@link org.fest.reflect.core.Reflection#method(String) method}("setCommonPower").{@link org.fest.reflect.method.MethodName#withParameterTypes(Class...) withParameterTypes}(String.class)
   *                         .{@link org.fest.reflect.method.ParameterTypes#in(Object) in}(Jedi.class)
   *                         .{@link org.fest.reflect.method.MethodInvoker#invoke(Object...) invoke}("Jump");
   *
   * // Equivalent to invoking the static method 'Jedi.addPadawan()'
   * {@link org.fest.reflect.core.Reflection#method(String) method}("addPadawan").{@link org.fest.reflect.method.MethodName#in(Object) in}(Jedi.class).{@link org.fest.reflect.method.MethodInvoker#invoke(Object...) invoke}();
   * </pre>
   * </p>
   *
   * @param args the arguments to use to call the method managed by this class.
   * @return the result of the method call.
   * @throws ReflectionError if the method cannot be invoked.
   */
  public @Nullable T invoke(@Nonnull Object... args) {
    checkNotNull(args);
    Method method = target();
    boolean accessible = method.isAccessible();
    try {
      makeAccessible(method);
      Object returnValue = method.invoke(target, args);
      return castSafely(returnValue, checkNotNull(returnType));
    } catch (Throwable t) {
      Throwable cause = targetOf(t);
      if (cause instanceof RuntimeException) {
        throw (RuntimeException) cause;
      }
      String format = "Unable to invoke method %s with arguments %s";
      throw new ReflectionError(String.format(format, quote(method.getName()), format(args)));
    } finally {
      setAccessibleIgnoringExceptions(method, accessible);
    }
  }

  /**
   * @return the underlying method to invoke via Java Reflection.
   */
  public @Nonnull Method target() {
    return checkNotNull(method);
  }
}
