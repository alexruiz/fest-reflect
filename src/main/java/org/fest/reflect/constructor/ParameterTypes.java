/*
 * Created on Aug 17, 2006
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
 * Copyright @2006-2013 the original author or authors.
 */
package org.fest.reflect.constructor;

import javax.annotation.Nonnull;

import static org.fest.util.Preconditions.checkNotNull;

/**
 * Stores the parameter types for the constructor to invoke.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class ParameterTypes {
  private final Class<?>[] parameterTypes;

  ParameterTypes(@Nonnull Class<?>[] parameterTypes) {
    this.parameterTypes = checkNotNull(parameterTypes);
  }

  /**
   * Indicates the data type of the object to create.
   * <p/>
   * Examples:
   * <pre>
   * // import static {@link org.fest.reflect.core.Reflection#constructor() org.fest.reflect.core.Reflection.constructor};
   *
   * // Equivalent to 'Person p = new Person()'
   * Person p = {@link org.fest.reflect.core.Reflection#constructor() constructor}().{@link org.fest.reflect.constructor.TargetType#in in}(Person.class).{@link org.fest.reflect.constructor.ConstructorInvoker#newInstance newInstance}();
   *
   * // Equivalent to 'Person p = new Person("Yoda")'
   * Person p = {@link org.fest.reflect.core.Reflection#constructor() constructor}().{@link org.fest.reflect.constructor.TargetType#withParameterTypes(Class...) withParameterTypes}(String.class).{@link org.fest.reflect.constructor.ParameterTypes#in(Class) in}(Person.class).{@link org.fest.reflect.constructor.ConstructorInvoker#newInstance newInstance}("Yoda");
   * </pre>
   *
   * @param target the type of the object to create by invoking a constructor.
   * @return the created constructor invoker.
   * @throws NullPointerException if the given type is {@code null}.
   */
  public @Nonnull <T> ConstructorInvoker<T> in(@Nonnull Class<T> target) {
    return new ConstructorInvoker<T>(target, parameterTypes);
  }
}