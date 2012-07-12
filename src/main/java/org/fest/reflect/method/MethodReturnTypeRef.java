/*
 * Created on Jan 25, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.reflect.method;

import static org.fest.reflect.method.Invoker.newInvoker;
import static org.fest.reflect.method.MethodParameterTypes.newParameterTypes;

import org.fest.reflect.reference.TypeRef;

/**
 * Understands the return type reference of the method to invoke.
 * <p>
 * The following is an example of proper usage of this class:
 * 
 * <pre>
 *   // Equivalent to call 'jedi.getPowers()'
 *   List&lt;String&gt; powers = {@link org.fest.reflect.core.Reflection#method(String) method}("getPowers").{@link MethodName#withReturnType(TypeRef) withReturnType}(new {@link TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {})
 *                                            .{@link MethodReturnTypeRef#in(Object) in}(person)
 *                                            .{@link Invoker#invoke(Object...) invoke}();
 * </pre>
 * </p>
 * 
 * @param <T> the generic type of the method's return type reference.
 * 
 * @author Alex Ruiz
 * 
 * @since 1.1
 */
public class MethodReturnTypeRef<T> {

  static <T> MethodReturnTypeRef<T> newReturnTypeRef(String name, TypeRef<T> type) {
    if (type == null) throw new NullPointerException("The return type reference of the method to access should not be null");
    return new MethodReturnTypeRef<T>(name);
  }

  private final String name;

  private MethodReturnTypeRef(String name) {
    this.name = name;
  }

  /**
   * Creates a new method invoker.
   * @param target the object containing the method to invoke.
   * @return the created method invoker.
   * @throws NullPointerException if the given target is <code>null</code>.
   */
  public Invoker<T> in(Object target) {
    return newInvoker(name, target);
  }

  /**
   * Specifies the parameter types of the method to invoke. This method call is optional if the method to invoke does not take
   * arguments.
   * @param parameterTypes the parameter types of the method to invoke.
   * @return the created parameter types holder.
   * @throws NullPointerException if the array of parameter types is <code>null</code>.
   */
  public MethodParameterTypes<T> withParameterTypes(Class<?>... parameterTypes) {
    return newParameterTypes(name, parameterTypes);
  }
}