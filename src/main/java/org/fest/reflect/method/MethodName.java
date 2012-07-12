/*
 * Created on Aug 17, 2007
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
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.reflect.method;

import static org.fest.reflect.method.Invoker.newInvoker;
import static org.fest.reflect.method.MethodParameterTypes.newParameterTypes;
import static org.fest.reflect.method.MethodReturnType.newReturnType;
import static org.fest.reflect.method.MethodReturnTypeRef.newReturnTypeRef;
import static org.fest.util.Strings.isEmpty;

import org.fest.reflect.reference.TypeRef;

/**
 * Understands the name of a method to invoke using Java Reflection.
 * <p>
 * The following is an example of proper usage of this class:
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
 *
 *   // Equivalent to call 'jedi.getPowers()'
 *   List&lt;String&gt; powers = {@link org.fest.reflect.core.Reflection#method(String) method}("getPowers").{@link MethodName#withReturnType(TypeRef) withReturnType}(new {@link TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {})
 *                                            .{@link MethodReturnTypeRef#in(Object) in}(person)
 *                                            .{@link Invoker#invoke(Object...) invoke}();
 * </pre>
 * </p>
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class MethodName {

  /**
   * Creates a new <code>{@link MethodName}</code>: the starting point of the fluent interface for accessing methods using Java
   * Reflection.
   * @param name the name of the method to invoke using Java Reflection.
   * @return the created <code>MethodName</code>.
   * @throws NullPointerException if the given name is <code>null</code>.
   * @throws IllegalArgumentException if the given name is empty.
   */
  public static MethodName startMethodAccess(String name) {
    validateIsNotNullOrEmpty(name);
    return new MethodName(name);
  }

  private static void validateIsNotNullOrEmpty(String name) {
    if (name == null) throw new NullPointerException("The name of the method to access should not be null");
    if (isEmpty(name)) throw new IllegalArgumentException("The name of the method to access should not be empty");
  }

  private final String name;

  private MethodName(String name) {
    this.name = name;
  }

  /**
   * Specifies the return type of the method to invoke. This method call is optional if the return type of the method to invoke is
   * <code>void</code>.
   * @param <T> the generic type of the method's return type.
   * @param type the return type of the method to invoke.
   * @return the created return type holder.
   * @throws NullPointerException if the given type is <code>null</code>.
   */
  public <T> MethodReturnType<T> withReturnType(Class<T> type) {
    return newReturnType(name, type);
  }

  /**
   * Specifies the return type reference of the method to invoke. This method call is optional if the return type of the method to
   * invoke is <code>void</code>.
   * @param <T> the generic type of the method's return type.
   * @param type the return type reference of the method to invoke.
   * @return the created return type holder.
   * @throws NullPointerException if the given type reference is <code>null</code>.
   * @since 1.1
   */
  public <T> MethodReturnTypeRef<T> withReturnType(TypeRef<T> type) {
    return newReturnTypeRef(name, type);
  }

  /**
   * Specifies the parameter types of the method to invoke. This method call is optional if the method to invoke does not take
   * arguments.
   * @param parameterTypes the parameter types of the method to invoke.
   * @return the created parameter types holder.
   * @throws NullPointerException if the array of parameter types is <code>null</code>.
   */
  public MethodParameterTypes<Void> withParameterTypes(Class<?>... parameterTypes) {
    return newParameterTypes(name, parameterTypes);
  }

  /**
   * Creates a new invoker for a method that takes no parameters and return value <code>void</code>.
   * @param target the object containing the method to invoke.
   * @return the created method invoker.
   * @throws NullPointerException if the given target is <code>null</code>.
   */
  public Invoker<Void> in(Object target) {
    return newInvoker(name, target);
  }
}