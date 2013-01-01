/*
 * Created on Nov 23, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.reflect.beanproperty;

import static org.fest.util.Preconditions.checkNotNullOrEmpty;

import javax.annotation.Nonnull;

import org.fest.reflect.reference.TypeRef;
import org.fest.util.InternalApi;

/**
 * Starting point of the fluent interface for accessing JavaBeans properties via Bean Introspection.
 *
 * <p>
 * <strong>Note:</strong> Do <em>not</em> instantiate this class directly. Instead, invoke
 * {@link org.fest.reflect.core.Reflection#property(String)}.
 * </p>
 *
 * @author Alex Ruiz
 * @since 1.2
 */
public final class PropertyName {
  private final String name;

  /**
   * Creates a new {@link PropertyName}.
   * 
   * <p>
   * <strong>Note:</strong> Do <em>not</em> invoke this constructor directly. Instead, invoke
   * {@link org.fest.reflect.core.Reflection#property(String)}.
   * 
   * @param name the name of the property to access.
   * @throws NullPointerException if the property name is {@code null}.
   * @throws IllegalArgumentException if the property name is empty.
   */
  @InternalApi
  public PropertyName(@Nonnull String name) {
    this.name = checkNotNullOrEmpty(name);
  }

  /**
   * Specifies the type of the property to access.
   *
   * <p>
   * Examples demonstrating usage of the fluent interface:
   *
   * <pre>
   * // Retrieves the value of the property "name"
   * String name = {@link org.fest.reflect.core.Reflection#property(String) property}("name").{@link PropertyName#ofType(Class) ofType}(String.class).{@link PropertyType#in(Object) in}(person).{@link PropertyAccessor#get() get}();
   * 
   * // Sets the value of the property "name" to "Yoda"
   * {@link org.fest.reflect.core.Reflection#property(String) property}("name").{@link PropertyName#ofType(Class) ofType}(String.class).{@link PropertyType#in(Object) in}(person).{@link PropertyAccessor#set(Object) set}("Yoda");
   * 
   * // Retrieves the value of the property "powers"
   * List&lt;String&gt; powers = {@link org.fest.reflect.core.Reflection#property(String) property}("powers").{@link PropertyName#ofType(org.fest.reflect.reference.TypeRef) ofType}(new {@link org.fest.reflect.reference.TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link PropertyTypeRef#in(Object) in}(jedi).{@link PropertyAccessor#get() get}();
   *
   * // Sets the value of the property "powers"
   * List&lt;String&gt; powers = new ArrayList&lt;String&gt;();
   * powers.add("heal");
   * {@link org.fest.reflect.core.Reflection#property(String) property}("powers").{@link PropertyName#ofType(org.fest.reflect.reference.TypeRef) ofType}(new {@link org.fest.reflect.reference.TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link PropertyTypeRef#in(Object) in}(jedi).{@link PropertyAccessor#set(Object) set}(powers);
   * </pre>
   * </p>
   *
   * @param type the type of the property to access.
   * @return a holder for the property type.
   * @throws NullPointerException if the given type is {@code null}.
   */
  public @Nonnull <T> PropertyType<T> ofType(@Nonnull Class<T> type) {
    return new PropertyType<T>(checkNotNullOrEmpty(name), type);
  }

  /**
   * Specifies the type of the property to access. This method uses {@link TypeRef} instead of {@link Class} to preserve
   * generic types that otherwise would be lost due to erasure.
   *
   * <p>
   * Examples demonstrating usage of the fluent interface:
   *
   * <pre>
   * // Retrieves the value of the property "name"
   * String name = {@link org.fest.reflect.core.Reflection#property(String) property}("name").{@link PropertyName#ofType(Class) ofType}(String.class).{@link PropertyType#in(Object) in}(person).{@link PropertyAccessor#get() get}();
   * 
   * // Sets the value of the property "name" to "Yoda"
   * {@link org.fest.reflect.core.Reflection#property(String) property}("name").{@link PropertyName#ofType(Class) ofType}(String.class).{@link PropertyType#in(Object) in}(person).{@link PropertyAccessor#set(Object) set}("Yoda");
   * 
   * // Retrieves the value of the property "powers"
   * List&lt;String&gt; powers = {@link org.fest.reflect.core.Reflection#property(String) property}("powers").{@link PropertyName#ofType(org.fest.reflect.reference.TypeRef) ofType}(new {@link org.fest.reflect.reference.TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link PropertyTypeRef#in(Object) in}(jedi).{@link PropertyAccessor#get() get}();
   *
   * // Sets the value of the property "powers"
   * List&lt;String&gt; powers = new ArrayList&lt;String&gt;();
   * powers.add("heal");
   * {@link org.fest.reflect.core.Reflection#property(String) property}("powers").{@link PropertyName#ofType(org.fest.reflect.reference.TypeRef) ofType}(new {@link org.fest.reflect.reference.TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link PropertyTypeRef#in(Object) in}(jedi).{@link PropertyAccessor#set(Object) set}(powers);
   * </pre>
   * </p>
   *
   * @param type the type of the property to access.
   * @return a holder for the property type.
   * @throws NullPointerException if the given type reference is {@code null}.
   */
  public @Nonnull <T> PropertyTypeRef<T> ofType(@Nonnull TypeRef<T> type) {
    return new PropertyTypeRef<T>(checkNotNullOrEmpty(name), type);
  }
}