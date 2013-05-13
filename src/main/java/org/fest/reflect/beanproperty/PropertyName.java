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

import org.fest.reflect.reference.TypeRef;
import org.fest.util.InternalApi;

import javax.annotation.Nonnull;

import static org.fest.util.Preconditions.checkNotNullOrEmpty;

/**
 * Stores the name of the
 * <a href="http://docs.oracle.com/javase/tutorial/javabeans/index.html" target="_blank">JavaBeans</a> property to
 * access.
 * <p/>
 * <strong>Note:</strong> To improve code readability, we recommend invoking the static method
 * {@link org.fest.reflect.core.Reflection#property(String) Reflection.property(String)} instead of this class'
 * constructor:
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
 * @author Alex Ruiz
 * @since 1.2
 */
public final class PropertyName {
  private final String name;

  /**
   * Creates a new {@link PropertyName}.
   * <p/>
   * <strong>Note:</strong> To improve code readability, we recommend invoking
   * {@link org.fest.reflect.core.Reflection#property(String) Reflection.property(String)} instead of this constructor:
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
   * @param name the name of the property to access.
   * @throws NullPointerException     if the property name is {@code null}.
   * @throws IllegalArgumentException if the property name is empty.
   */
  @InternalApi
  public PropertyName(@Nonnull String name) {
    this.name = checkNotNullOrEmpty(name);
  }

  /**
   * Specifies the data type of the
   * <a href="http://docs.oracle.com/javase/tutorial/javabeans/index.html" target="_blank">JavaBeans</a> property to
   * access.
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
   * @param type the type of the property to access.
   * @return a holder for the property type.
   * @throws NullPointerException if the given type is {@code null}.
   */
  public @Nonnull <T> PropertyType<T> ofType(@Nonnull Class<T> type) {
    return new PropertyType<T>(name, type);
  }

  /**
   * Specifies the data type of the
   * <a href="http://docs.oracle.com/javase/tutorial/javabeans/index.html" target="_blank">JavaBeans</a> property to
   * access. This method uses {@link TypeRef} instead of {@link Class} to preserve generic types that otherwise would be
   * lost due to
   * <a href="http://docs.oracle.com/javase/tutorial/java/generics/erasure.html" target="_blank">type erasure</a>.
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
   * @param type the type of the property to access.
   * @return a holder for the property type.
   * @throws NullPointerException if the given type reference is {@code null}.
   */
  public @Nonnull <T> PropertyTypeRef<T> ofType(@Nonnull TypeRef<T> type) {
    return new PropertyTypeRef<T>(name, type);
  }
}