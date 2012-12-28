/*
 * Created on Jan 25, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.reflect.method;

import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Preconditions.checkNotNullOrEmpty;

import javax.annotation.Nonnull;

import org.fest.reflect.reference.TypeRef;

/**
 * Holds the return type reference of the method to invoke, preserving generic types that otherwise would be lost due to
 * erasure.
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
 * List&lt;String&gt; powers = {@link org.fest.reflect.core.Reflection#method(String) method}("getPowers").{@link org.fest.reflect.method.MethodName#withReturnType(TypeRef) withReturnType}(new {@link TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {})
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
 * @param <T> the return type of the method to invoke.
 * @author Alex Ruiz
 * @since 1.1
 */
public class ReturnTypeRef<T> {
  private final String methodName;
  private final TypeRef<T> value;

  ReturnTypeRef(@Nonnull String methodName, @Nonnull TypeRef<T> type) {
    this.methodName = checkNotNullOrEmpty(methodName);
    this.value = checkNotNull(type);
  }

  /**
   * Specifies the parameter types of the method to invoke.
   * 
   * <p>
   * <strong>Note:</strong> Invocation of this method is optional if the method to invoke does not take any arguments.
   * 
   * @param parameterTypes the parameter types of the method to invoke.
   * @return the created parameter types holder.
   * @throws NullPointerException if the array of parameter types is {@code null}.
   */
  public @Nonnull ParameterTypes<T> withParameterTypes(@Nonnull Class<?>... parameterTypes) {
    return new ParameterTypes<T>(checkNotNullOrEmpty(methodName), value.rawType(), parameterTypes);
  }

  /**
   * Creates a new invoker for a method that does not take any parameters.
   * 
   * @param target the object containing the method to invoke.
   * @return the created method invoker.
   * @throws NullPointerException if the given target is {@code null}.
   */
  public @Nonnull MethodInvoker<T> in(Object target) {
    return new MethodInvoker<T>(checkNotNullOrEmpty(methodName), value.rawType(), new Class<?>[0], checkNotNull(target));
  }
}