/*
 * Created on Aug 17, 2006
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
package org.fest.reflect.constructor;

import static org.fest.reflect.constructor.Invoker.newInvoker;

/**
 * Understands the parameter types for the constructor to invoke.
 * <p>
 * The following is an example of proper usage of the classes in this package:
 * 
 * <pre>
 *   // Equivalent to call 'new Person()'
 *   Person p = {@link org.fest.reflect.core.Reflection#constructor() constructor}().{@link TargetType#in in}(Person.class).{@link Invoker#newInstance newInstance}();
 *
 *   // Equivalent to call 'new Person("Yoda")'
 *   Person p = {@link org.fest.reflect.core.Reflection#constructor() constructor}().{@link TargetType#withParameterTypes(Class...) withParameterTypes}(String.class).{@link ParameterTypes#in(Class) in}(Person.class).{@link Invoker#newInstance newInstance}("Yoda");
 * </pre>
 * </p>
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class ParameterTypes {

  static ParameterTypes newParameterTypes(Class<?>[] parameterTypes) {
    if (parameterTypes == null) throw new NullPointerException("The array of parameter types should not be null");
    return new ParameterTypes(parameterTypes);
  }

  private final Class<?>[] parameterTypes;

  private ParameterTypes(Class<?>[] parameterTypes) {
    this.parameterTypes = parameterTypes;
  }

  /**
   * Creates a new constructor invoker.
   * @param <T> the generic type of the class containing the constructor to invoke.
   * @param target the the type of object that the constructor invoker will create.
   * @return the created constructor invoker.
   */
  public <T> Invoker<T> in(Class<T> target) {
    return newInvoker(target, parameterTypes);
  }
}