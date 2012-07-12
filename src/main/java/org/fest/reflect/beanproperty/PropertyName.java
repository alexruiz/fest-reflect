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

import static org.fest.reflect.beanproperty.PropertyType.newPropertyType;
import static org.fest.reflect.beanproperty.PropertyTypeRef.newPropertyTypeRef;
import static org.fest.util.Strings.isEmpty;

import org.fest.reflect.reference.TypeRef;

/**
 * Understands the name of a property to access using Bean Introspection.
 * <p>
 * The following is an example of proper usage of this class:
 * 
 * <pre>
 *   // Retrieves the value of the property "name"
 *   String name = {@link org.fest.reflect.core.Reflection#property(String) property}("name").{@link PropertyName#ofType(Class) ofType}(String.class).{@link PropertyType#in(Object) in}(person).{@link Invoker#get() get}();
 *
 *   // Sets the value of the property "name" to "Yoda"
 *   {@link org.fest.reflect.core.Reflection#property(String) property}("name").{@link PropertyName#ofType(Class) ofType}(String.class).{@link PropertyType#in(Object) in}(person).{@link Invoker#set(Object) set}("Yoda");
 *
 *   // Retrieves the value of the property "powers"
 *   List&lt;String&gt; powers = {@link org.fest.reflect.core.Reflection#property(String) property}("powers").{@link #ofType(TypeRef) ofType}(new {@link TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link PropertyTypeRef#in(Object) in}(jedi).{@link Invoker#get() get}();
 *
 *   // Sets the value of the property "powers"
 *   List&lt;String&gt; powers = new ArrayList&lt;String&gt;();
 *   powers.add("heal");
 *   {@link org.fest.reflect.core.Reflection#property(String) property}("powers").{@link #ofType(TypeRef) ofType}(new {@link TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link PropertyTypeRef#in(Object) in}(jedi).{@link Invoker#set(Object) set}(powers);
 * </pre>
 * </p>
 * 
 * @author Alex Ruiz
 * 
 * @since 1.2
 */
public final class PropertyName {

  /**
   * Creates a new <code>{@link PropertyName}</code>: the starting point of the fluent interface for accessing properties using
   * Bean Introspection.
   * @param name the name of the property to access using Bean Introspection.
   * @return the created <code>PropertyName</code>.
   * @throws NullPointerException if the given name is <code>null</code>.
   * @throws IllegalArgumentException if the given name is empty.
   */
  public static PropertyName startPropertyAccess(String name) {
    validateIsNotNullOrEmpty(name);
    return new PropertyName(name);
  }

  private static void validateIsNotNullOrEmpty(String name) {
    if (name == null) throw new NullPointerException("The name of the property to access should not be null");
    if (isEmpty(name)) throw new IllegalArgumentException("The name of the property to access should not be empty");
  }

  private final String name;

  private PropertyName(String name) {
    this.name = name;
  }

  /**
   * Sets the type of the property to access.
   * @param <T> the generic type of the property type.
   * @param type the type of the property to access.
   * @return a recipient for the property type.
   * @throws NullPointerException if the given type is <code>null</code>.
   */
  public <T> PropertyType<T> ofType(Class<T> type) {
    return newPropertyType(name, type);
  }

  /**
   * Sets the type reference of the property to access. This method reduces casting when the type of the property to access uses
   * generics.
   * <p>
   * For example:
   * 
   * <pre>
   *   List&lt;String&gt; powers = {@link org.fest.reflect.core.Reflection#property(String) property}("powers").{@link #ofType(TypeRef) ofType}(new {@link TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link PropertyTypeRef#in(Object) in}(jedi).{@link Invoker#get() get}();
   * </pre>
   * </p>
   * @param <T> the generic type of the property type.
   * @param type the type of the property to access.
   * @return a recipient for the property type.
   * @throws NullPointerException if the given type reference is <code>null</code>.
   */
  public <T> PropertyTypeRef<T> ofType(TypeRef<T> type) {
    return newPropertyTypeRef(name, type);
  }
}