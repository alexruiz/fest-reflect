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
 * Holds the parameter types of the method to invoke.
 *
 * @param <T> the return type of the method to invoke.
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class ParameterTypes<T> {
  private final String methodName;
  private final Class<T> returnType;
  private final Class<?>[] value;

  ParameterTypes(@Nonnull String methodName, @Nonnull Class<T> returnType, @Nonnull Class<?>[] parameterTypes) {
    this.methodName = checkNotNullOrEmpty(methodName);
    this.returnType = checkNotNull(returnType);
    this.value = checkNotNull(parameterTypes);
  }

  /**
   * Creates a new method invoker.
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
   * @param target the object containing the method to invoke.
   * @return the created method invoker.
   * @throws NullPointerException if the given target is {@code null}.
   */
  public @Nonnull MethodInvoker<T> in(@Nonnull Object target) {
    return new MethodInvoker<T>(checkNotNullOrEmpty(methodName), checkNotNull(returnType), checkNotNull(value), target);
  }
}