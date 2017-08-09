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

import org.fest.reflect.exception.ReflectionError;

import org.jetbrains.annotations.NotNull;

import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Preconditions.checkNotNullOrEmpty;

/**
 * Stores the type of the property to access via Bean Introspection.
 *
 * @param <T> the type of the property.
 * @author Alex Ruiz
 * @since 1.2
 */
public class PropertyType<T> {
  private final String propertyName;
  private final Class<T> value;

  PropertyType(@NotNull String propertyName, @NotNull Class<T> type) {
    this.propertyName = checkNotNullOrEmpty(propertyName);
    this.value = checkNotNull(type);
  }

  /**
   * Specifies the
   * <a href="http://docs.oracle.com/javase/tutorial/javabeans/index.html" target="_blank">JavaBean</a> to access the
   * property from.
   * <p/>
   * Examples:
   * <pre>
   * // import static {@link org.fest.reflect.core.Reflection#property(String) org.fest.reflect.core.Reflection.property};
   *
   * // Equivalent to "String name = person.getName()"
   * String name = {@link org.fest.reflect.core.Reflection#property(String) property}("name").{@link org.fest.reflect.beanproperty.PropertyName#ofType(Class) ofType}(String.class).{@link org.fest.reflect.beanproperty.PropertyType#in(Object) in}(person).{@link org.fest.reflect.beanproperty.PropertyAccessor#get() get}();
   *
   * // Equivalent to "person.setName("Yoda")"
   * {@link org.fest.reflect.core.Reflection#property(String) property}("name").{@link org.fest.reflect.beanproperty.PropertyName#ofType(Class) ofType}(String.class).{@link org.fest.reflect.beanproperty.PropertyType#in(Object) in}(person).{@link org.fest.reflect.beanproperty.PropertyAccessor#set(Object) set}("Yoda");
   *
   * // Equivalent to "List&lt;String&gt; powers = jedi.getPowers()"
   * List&lt;String&gt; powers = {@link org.fest.reflect.core.Reflection#property(String) property}("powers").{@link org.fest.reflect.beanproperty.PropertyName#ofType(org.fest.reflect.reference.TypeRef) ofType}(new {@link org.fest.reflect.reference.TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link org.fest.reflect.beanproperty.PropertyTypeRef#in(Object) in}(jedi).{@link org.fest.reflect.beanproperty.PropertyAccessor#get() get}();
   *
   * // Equivalent to "jedi.setPowers(powers)"
   * List&lt;String&gt; powers = new ArrayList&lt;String&gt;();
   * powers.add("heal");
   * {@link org.fest.reflect.core.Reflection#property(String) property}("powers").{@link org.fest.reflect.beanproperty.PropertyName#ofType(org.fest.reflect.reference.TypeRef) ofType}(new {@link org.fest.reflect.reference.TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link org.fest.reflect.beanproperty.PropertyTypeRef#in(Object) in}(jedi).{@link org.fest.reflect.beanproperty.PropertyAccessor#set(Object) set}(powers);
   * </pre>
   *
   * @param target the object containing the property to access.
   * @return the created property accessor.
   * @throws NullPointerException if the given target is {@code null}.
   * @throws ReflectionError      if a property with a matching name and type cannot be found.
   */
  public @NotNull PropertyAccessor<T> in(@NotNull Object target) {
    return new PropertyAccessor<T>(propertyName, value, target);
  }
}