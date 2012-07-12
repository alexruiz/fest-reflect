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

/**
 * Understands the parameter types of the method to invoke.
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
 * </pre>
 * </p>
 * 
 * @param <T> the generic type of the method's return type.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class MethodParameterTypes<T> {

  private final String name;
  private final Class<?>[] parameterTypes;

  static <T> MethodParameterTypes<T> newParameterTypes(String name, Class<?>[] parameterTypes) {
    if (parameterTypes == null)
      throw new NullPointerException("The array of parameter types for the method to access should not be null");
    return new MethodParameterTypes<T>(parameterTypes, name);
  }

  private MethodParameterTypes(Class<?>[] parameterTypes, String name) {
    this.name = name;
    this.parameterTypes = parameterTypes;
  }

  /**
   * Creates a new method invoker.
   * @param target the object containing the method to invoke.
   * @return the created method invoker.
   * @throws NullPointerException if the given target is <code>null</code>.
   */
  public Invoker<T> in(Object target) {
    return newInvoker(name, target, parameterTypes);
  }
}