/*
 * Created on Oct 31, 2006
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 * 
 * Copyright @2006-2009 the original author or authors.
 */
package org.fest.reflect.method;

import static org.fest.reflect.util.Accessibles.makeAccessible;
import static org.fest.reflect.util.Accessibles.setAccessibleIgnoringExceptions;
import static org.fest.reflect.util.Throwables.targetOf;
import static org.fest.util.Arrays.format;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

import java.lang.reflect.Method;

import org.fest.reflect.exception.ReflectionError;

/**
 * Understands the use of reflection to access a method from an object.
 * <p>
 * 
 * <pre>
 *   // Equivalent to call 'person.setName("Luke")'
 *   {@link org.fest.reflect.core.Reflection#method(String) method}("setName").{@link MethodName#withParameterTypes(Class...) withParameterTypes}(String.class)
 *                    .{@link MethodParameterTypes#in(Object) in}(person)
 *                    .{@link Invoker#invoke(Object...) invoke}("Luke");
 *
 *   // Equivalent to call 'person.concentrate()'
 *   {@link org.fest.reflect.core.Reflection#method(String) method}("concentrate").{@link MethodName#in(Object) in}(person).{@link Invoker#invoke(Object...) invoke}();
 *
 *   // Equivalent to call 'person.getName()'
 *   String name = {@link org.fest.reflect.core.Reflection#method(String) method}("getName").{@link MethodName#withReturnType(Class) withReturnType}(String.class)
 *                                  .{@link MethodReturnType#in(Object) in}(person)
 *                                  .{@link Invoker#invoke(Object...) invoke}();
 * </pre>
 * </p>
 * 
 * @param <T> the return type of the method invocation.
 * 
 * @author Yvonne Wang
 */
public final class Invoker<T> {

  static <T> Invoker<T> newInvoker(String methodName, Object target, Class<?>... parameterTypes) {
    return createInvoker(methodName, target, parameterTypes);
  }

  private static <T> Invoker<T> createInvoker(String methodName, Object target, Class<?>... parameterTypes) {
    if (target == null) throw new NullPointerException("Target should not be null");
    Method method = lookupInClassHierarchy(methodName, typeOf(target), parameterTypes);
    return new Invoker<T>(target, method);
  }

  private static Class<?> typeOf(Object target) {
    if (target instanceof Class<?>) return (Class<?>) target;
    return target.getClass();
  }

  private static Method lookupInClassHierarchy(String methodName, Class<?> targetType, Class<?>[] parameterTypes) {
    Method method = null;
    Class<?> type = targetType;
    while (type != null) {
      method = method(methodName, type, parameterTypes);
      if (method != null) break;
      type = type.getSuperclass();
    }
    if (method == null)
      throw new ReflectionError(concat("Unable to find method ", quote(methodName), " in ", targetType.getName(),
          " with parameter type(s) ", format(parameterTypes)));
    return method;
  }

  private static Method method(String methodName, Class<?> type, Class<?>[] parameterTypes) {
    try {
      return type.getDeclaredMethod(methodName, parameterTypes);
    } catch (SecurityException e) {
      return null;
    } catch (NoSuchMethodException e) {
      return null;
    }
  }

  private final Object target;
  private final Method method;

  private Invoker(Object target, Method method) {
    this.target = target;
    this.method = method;
  }

  /**
   * Invokes the method managed by this class using the given arguments.
   * @param args the arguments to use to call the method managed by this class.
   * @return the result of the method call.
   * @throws ReflectionError if the method cannot be invoked.
   */
  @SuppressWarnings("unchecked")
  public T invoke(Object... args) {
    boolean accessible = method.isAccessible();
    try {
      makeAccessible(method);
      return (T) method.invoke(target, args);
    } catch (Throwable t) {
      Throwable cause = targetOf(t);
      if (cause instanceof RuntimeException) throw (RuntimeException) cause;
      throw cannotInvokeMethod(cause, args);
    } finally {
      setAccessibleIgnoringExceptions(method, accessible);
    }
  }

  private ReflectionError cannotInvokeMethod(Throwable cause, Object... args) {
    String message = concat("Unable to invoke method ", quote(method.getName()), " with arguments ", format(args));
    throw new ReflectionError(message, cause);
  }

  /**
   * Returns the "real" method managed by this class.
   * @return the "real" method managed by this class.
   */
  public java.lang.reflect.Method info() {
    return method;
  }
}
