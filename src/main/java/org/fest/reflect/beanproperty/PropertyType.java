/*
 * Created on Nov 23, 2009
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
package org.fest.reflect.beanproperty;

import static org.fest.reflect.beanproperty.Invoker.newInvoker;

import org.fest.reflect.exception.ReflectionError;

/**
 * Understands the type of a property to access using Bean Instrospection.
 * <p>
 * The following is an example of proper usage of this class:
 * 
 * <pre>
 *   // Retrieves the value of the property "name"
 *   String name = {@link org.fest.reflect.core.Reflection#property(String) property}("name").{@link PropertyName#ofType(Class) ofType}(String.class).{@link PropertyType#in(Object) in}(person).{@link Invoker#get() get}();
 *
 *   // Sets the value of the property "name" to "Yoda"
 *   {@link org.fest.reflect.core.Reflection#property(String) property}("name").{@link PropertyName#ofType(Class) ofType}(String.class).{@link PropertyType#in(Object) in}(person).{@link Invoker#set(Object) set}("Yoda");
 * </pre>
 * </p>
 * 
 * @param <T> the generic type of the property.
 * 
 * @author Alex Ruiz
 * 
 * @since 1.2
 */
public class PropertyType<T> {

  static <T> PropertyType<T> newPropertyType(String name, Class<T> type) {
    if (type == null) throw new NullPointerException("The type of the property to access should not be null");
    return new PropertyType<T>(name, type);
  }

  private final String name;
  private final Class<T> type;

  private PropertyType(String name, Class<T> type) {
    this.name = name;
    this.type = type;
  }

  /**
   * Returns a new property invoker. A property invoker is capable of accessing (read/write) the underlying property.
   * @param target the object containing the property of interest.
   * @return the created property invoker.
   * @throws NullPointerException if the given target is <code>null</code>.
   * @throws ReflectionError if a property with a matching name and type cannot be found.
   */
  public Invoker<T> in(Object target) {
    if (target == null) throw new NullPointerException("Target should not be null");
    return newInvoker(name, type, target);
  }
}