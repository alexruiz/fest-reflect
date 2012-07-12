/*
 * Created on Feb 20, 2008
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
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.reflect.method;

import static org.fest.reflect.method.Invoker.newInvoker;
import static org.fest.reflect.method.StaticMethodParameterTypes.newParameterTypes;
import static org.fest.reflect.method.StaticMethodReturnType.newReturnType;
import static org.fest.reflect.method.StaticMethodReturnTypeRef.newReturnTypeRef;
import static org.fest.util.Strings.isEmpty;

import org.fest.reflect.reference.TypeRef;

/**
 * Understands the name of a static method to invoke using Java Reflection.
 * <p>
 * The following is an example of proper usage of this class:
 * 
 * <pre>
 *   // Equivalent to call 'Jedi.setCommonPower("Jump")'
 *   {@link org.fest.reflect.core.Reflection#staticMethod(String) staticMethod}("setCommonPower").{@link StaticMethodName#withParameterTypes(Class...) withParameterTypes}(String.class)
 *                                 .{@link StaticMethodParameterTypes#in(Class) in}(Jedi.class)
 *                                 .{@link Invoker#invoke(Object...) invoke}("Jump");
 *
 *   // Equivalent to call 'Jedi.addPadawan()'
 *   {@link org.fest.reflect.core.Reflection#staticMethod(String) staticMethod}("addPadawan").{@link StaticMethodName#in(Class) in}(Jedi.class).{@link Invoker#invoke(Object...) invoke}();
 *
 *   // Equivalent to call 'Jedi.commonPowerCount()'
 *   String name = {@link org.fest.reflect.core.Reflection#staticMethod(String) staticMethod}("commonPowerCount").{@link StaticMethodName#withReturnType(Class) withReturnType}(String.class)
 *                                                 .{@link StaticMethodReturnType#in(Class) in}(Jedi.class)
 *                                                 .{@link Invoker#invoke(Object...) invoke}();
 *
 *   // Equivalent to call 'Jedi.getCommonPowers()'
 *   List&lt;String&gt; powers = {@link org.fest.reflect.core.Reflection#staticMethod(String) staticMethod}("getCommonPowers").{@link StaticMethodName#withReturnType(TypeRef) withReturnType}(new {@link TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {})
 *                                                        .{@link StaticMethodReturnTypeRef#in(Class) in}(Jedi.class)
 *                                                        .{@link Invoker#invoke(Object...) invoke}();
 * </pre>
 * </p>
 * 
 * @author Alex Ruiz
 */
public final class StaticMethodName {

  /**
   * Creates a new </code>{@link StaticMethodName}</code>: the starting point of the fluent interface for accessing static methods
   * using Java Reflection.
   * @param name the name of the method to access using Java Reflection.
   * @return the created <code>StaticMethodName</code>.
   * @throws NullPointerException if the given name is <code>null</code>.
   * @throws IllegalArgumentException if the given name is empty.
   */
  public static StaticMethodName startStaticMethodAccess(String name) {
    validateIsNotNullOrEmpty(name);
    return new StaticMethodName(name);
  }

  private static void validateIsNotNullOrEmpty(String name) {
    if (name == null) throw new NullPointerException("The name of the static method to access should not be null");
    if (isEmpty(name)) throw new IllegalArgumentException("The name of the static method to access should not be empty");
  }

  private final String name;

  private StaticMethodName(String name) {
    this.name = name;
  }

  /**
   * Specifies the return type of the static method to invoke. This method call is optional if the return type of the method to
   * invoke is <code>void</code>.
   * @param <T> the generic type of the method's return type.
   * @param type the return type of the method to invoke.
   * @return the created return type holder.
   * @throws NullPointerException if the given type is <code>null</code>.
   */
  public <T> StaticMethodReturnType<T> withReturnType(Class<T> type) {
    return newReturnType(name, type);
  }

  /**
   * Specifies the return type reference of the static method to invoke. This method call is optional if the return type of the
   * method to invoke is <code>void</code>.
   * @param <T> the generic type of the method's return type.
   * @param type the return type reference of the method to invoke.
   * @return the created return type holder.
   * @throws NullPointerException if the given type reference is <code>null</code>.
   */
  public <T> StaticMethodReturnTypeRef<T> withReturnType(TypeRef<T> type) {
    return newReturnTypeRef(name, type);
  }

  /**
   * Specifies the parameter types of the static method to invoke. This method call is optional if the method to invoke does not
   * take arguments.
   * @param parameterTypes the parameter types of the method to invoke.
   * @return the created parameter types holder.
   * @throws NullPointerException if the array of parameter types is <code>null</code>.
   */
  public StaticMethodParameterTypes<Void> withParameterTypes(Class<?>... parameterTypes) {
    return newParameterTypes(name, parameterTypes);
  }

  /**
   * Creates a new invoker for a static method that takes no parameters and return value <code>void</code>.
   * @param target the object containing the method to invoke.
   * @return the created method invoker.
   * @throws NullPointerException if the given target is <code>null</code>.
   */
  public Invoker<Void> in(Class<?> target) {
    return newInvoker(name, target);
  }
}
