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

import org.fest.util.InternalApi;

/**
 * <p>
 * Starting point of the fluent interface for invoking constructors via
 * <a href="http://docs.oracle.com/javase/tutorial/reflect/index.html" target="_blank">Java Reflection</a>.
 * </p>
 *
 * <p>
 * <strong>Note:</strong> Do <em>not</em> instantiate this class directly. Instead, invoke
 * {@link org.fest.reflect.core.Reflection#constructor()}.
 * </p>
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class TargetType {
  /**
   * <p>
   * Creates a new {@link TargetType}.
   * </p>
   *
   * <p>
   * <strong>Note:</strong> Do <em>not</em> invoke this constructor directly. Instead, invoke
   * {@link org.fest.reflect.core.Reflection#constructor()}.
   */
  @InternalApi
  public TargetType() {}

  /**
   * <p>
   * Creates a new invoker for a type's default constructor.
   * </p>
   *
   * <p>
   * Examples demonstrating usage of the fluent interface:
   *
   * <pre>
   * // import static {@link org.fest.reflect.core.Reflection#constructor() org.fest.reflect.core.Reflection.constructor};
   *
   * // Equivalent to invoking 'new Person()'
   * Person p = {@link org.fest.reflect.core.Reflection#constructor() constructor}().{@link TargetType#in in}(Person.class).{@link ConstructorInvoker#newInstance newInstance}();
   * 
   * // Equivalent to invoking 'new Person("Yoda")'
   * Person p = {@link org.fest.reflect.core.Reflection#constructor() constructor}().{@link TargetType#withParameterTypes(Class...) withParameterTypes}(String.class).{@link ParameterTypes#in(Class) in}(Person.class).{@link ConstructorInvoker#newInstance newInstance}("Yoda");
   * </pre>
   * </p>
   *
   * @param target the type of the object to create by invoking a constructor.
   * @return the created constructor invoker.
   * @throws NullPointerException if the given type is {@code null}.
   */
  public @Nonnull <T> ConstructorInvoker<T> in(@Nonnull Class<T> target) {
    return new ConstructorInvoker<T>(target);
  }

  /**
   * <p>
   * Specifies the parameter types for the constructor to invoke.
   * </p>
   *
   * <p>
   * <strong>Note:</strong> Invocation of this method is optional if the constructor to invoke is the default
   * constructor.
   *
   * <p>
   * Examples demonstrating usage of the fluent interface:
   * 
   * <pre>
   * // import static {@link org.fest.reflect.core.Reflection#constructor() org.fest.reflect.core.Reflection.constructor};
   *
   * // Equivalent to invoking 'new Person()'
   * Person p = {@link org.fest.reflect.core.Reflection#constructor() constructor}().{@link TargetType#in in}(Person.class).{@link ConstructorInvoker#newInstance newInstance}();
   * 
   * // Equivalent to invoking 'new Person("Yoda")'
   * Person p = {@link org.fest.reflect.core.Reflection#constructor() constructor}().{@link TargetType#withParameterTypes(Class...) withParameterTypes}(String.class).{@link ParameterTypes#in(Class) in}(Person.class).{@link ConstructorInvoker#newInstance newInstance}("Yoda");
   * </pre>
   * </p>
   *
   * @param parameterTypes the types of the parameters to pass to the constructor.
   * @return the created parameter type holder.
   * @throws NullPointerException if the given array is {@code null}.
   */
  public @Nonnull ParameterTypes withParameterTypes(@Nonnull Class<?>... parameterTypes) {
    return new ParameterTypes(parameterTypes);
  }
}