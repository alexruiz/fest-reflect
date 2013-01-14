/*
 * Created on Aug 17, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.reflect.method;

import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Preconditions.checkNotNullOrEmpty;

import javax.annotation.Nonnull;

/**
 * Stores the return type of the method to invoke.
 *
 * @param <T> the return type of the method to invoke.
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ReturnType<T> {
  private final String methodName;
  private final Class<T> value;

  ReturnType(@Nonnull String methodName, @Nonnull Class<T> returnType) {
    this.methodName = checkNotNull(methodName);
    this.value = checkNotNull(returnType);
  }

  /**
   * <p>
   * Specifies the parameter types of the method to invoke.
   * </p>
   *
   * <p>
   * <strong>Note:</strong> Invocation of this method is optional if the method to invoke does not take any arguments.
   * </p>
   *
   * <p>
   * Examples demonstrating usage of the fluent interface:
   *
   * <pre>
   * // import static {@link org.fest.reflect.core.Reflection#method(String) org.fest.reflect.core.Reflection.method};
   *
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
   * @param parameterTypes the parameter types of the method to invoke.
   * @return a holder for the method's parameter types.
   * @throws NullPointerException if the array of parameter types is {@code null}.
   */
  public @Nonnull ParameterTypes<T> withParameterTypes(@Nonnull Class<?>... parameterTypes) {
    return new ParameterTypes<T>(checkNotNullOrEmpty(methodName), checkNotNull(value), parameterTypes);
  }

  /**
   * <p>
   * Specifies the object or class containing the method to invoke. The method to invoke does not take any parameters.
   * </p>
   *
   * <p>
   * Examples demonstrating usage of the fluent interface:
   *
   * <pre>
   * // import static {@link org.fest.reflect.core.Reflection#method(String) org.fest.reflect.core.Reflection.method};
   *
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
   * @param target the object containing the method to invoke. To invoke a static method, pass a class instead.
   * @return the created method invoker.
   * @throws NullPointerException if the given target is {@code null}.
   */
  public @Nonnull MethodInvoker<T> in(@Nonnull Object target) {
    return new MethodInvoker<T>(methodName, value, new Class<?>[0], target);
  }
}