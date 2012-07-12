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

import org.fest.reflect.reference.TypeRef;

/**
 * Understands the parameter types of the static method to invoke.
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
 * @param <T> the generic type of the static method's return type.
 * 
 * @author Alex Ruiz
 */
public final class StaticMethodParameterTypes<T> {

  static <T> StaticMethodParameterTypes<T> newParameterTypes(String name, Class<?>[] parameterTypes) {
    if (parameterTypes == null)
      throw new NullPointerException("The array of parameter types for the static method to access should not be null");
    return new StaticMethodParameterTypes<T>(name, parameterTypes);
  }

  private final String name;
  private final Class<?>[] parameterTypes;

  private StaticMethodParameterTypes(String name, Class<?>[] parameterTypes) {
    this.name = name;
    this.parameterTypes = parameterTypes;
  }

  /**
   * Creates a new method invoker.
   * @param target the class containing the static method to invoke.
   * @return the created method invoker.
   * @throws NullPointerException if the given target is <code>null</code>.
   */
  public Invoker<T> in(Class<?> target) {
    return newInvoker(name, target, parameterTypes);
  }
}