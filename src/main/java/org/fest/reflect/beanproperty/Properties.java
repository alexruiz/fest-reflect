/*
 * Created on Feb 25, 2011
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
 * Copyright @2011 the original author or authors.
 */
package org.fest.reflect.beanproperty;

import org.fest.reflect.reference.TypeRef;

/**
 * Implements a fluent interface for accessing JavaBeans properties.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class Properties {

  /**
   * Sets the type of the property to access.
   * @param <T> the generic type of the property to access.
   * @param type the type of the property to access.
   * @return the next object in the fluent interface.
   * @throws NullPointerException if the given type is {@code null}.
   */
  public static <T> Name<T> propertyOfType(Class<T> type) {
    return new FluentProperty<T>(type);
  }

  /**
   * Sets the type of the property to access.
   * @param <T> the generic type of the property to access.
   * @param typeRef a reference to the type of the property to access. Used to overcome type erasure in generics.
   * @return the next object in the fluent interface.
   * @throws NullPointerException if the given type reference is {@code null}.
   */
  public static <T> Name<T> propertyOfType(TypeRef<T> typeRef) {
    return new FluentProperty<T>(typeRef);
  }

  private Properties() {}
}
