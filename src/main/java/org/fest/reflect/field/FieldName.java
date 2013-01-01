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
package org.fest.reflect.field;

import static org.fest.util.Preconditions.checkNotNullOrEmpty;

import javax.annotation.Nonnull;

import org.fest.reflect.reference.TypeRef;
import org.fest.util.InternalApi;

/**
 * Starting point of the fluent interface for accessing fields via Java Reflection.
 *
 * <p>
 * <strong>Note:</strong> Do <em>not</em> instantiate this class directly. Instead, invoke
 * {@link org.fest.reflect.core.Reflection#field(String)}.
 * </p>
 *
 * @author Alex Ruiz
 */
public final class FieldName {
  private final String name;

  /**
   * Creates a new {@link FieldName}.
   * 
   * <p>
   * <strong>Note:</strong> Do <em>not</em> invoke this constructor directly. Instead, invoke
   * {@link org.fest.reflect.core.Reflection#field(String)}.
   * 
   * @param name the name of the field to invoke.
   * @throws NullPointerException if the field name is {@code null}.
   * @throws IllegalArgumentException if the field name is empty.
   */
  @InternalApi
  public FieldName(@Nonnull String name) {
    this.name = checkNotNullOrEmpty(name);
  }

  /**
   * Specifies the type of the field to access.
   *
   * <p>
   * Examples demonstrating usage of the fluent interface:
   *
   * <pre>
   * // Retrieves the value of the field "name"
   * String name = {@link org.fest.reflect.core.Reflection#field(String) field}("name").{@link FieldName#ofType(Class) ofType}(String.class).{@link FieldType#in(Object) in}(person).{@link FieldAccessor#get() get}();
   *
   * // Sets the value of the field "name" to "Yoda"
   * {@link org.fest.reflect.core.Reflection#field(String) field}("name").{@link FieldName#ofType(Class) ofType}(String.class).{@link FieldType#in(Object) in}(person).{@link FieldAccessor#set(Object) set}("Yoda");
   *
   * // Retrieves the value of the field "powers"
   * List&lt;String&gt; powers = {@link org.fest.reflect.core.Reflection#field(String) field}("powers").{@link FieldName#ofType(TypeRef) ofType}(new {@link TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link FieldTypeRef#in(Object) in}(jedi).{@link FieldAccessor#get() get}();
   *
   * // Sets the value of the field "powers"
   * List&lt;String&gt; powers = new ArrayList&lt;String&gt;();
   * powers.add("heal");
   * {@link org.fest.reflect.core.Reflection#field(String) field}("powers").{@link FieldName#ofType(TypeRef) ofType}(new {@link TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link FieldTypeRef#in(Object) in}(jedi).{@link FieldAccessor#set(Object) set}(powers);
   *
   * // Retrieves the value of the static field "count" in Person.class
   * int count = {@link org.fest.reflect.core.Reflection#field(String) field}("count").{@link org.fest.reflect.field.FieldName#ofType(Class) ofType}(int.class).{@link org.fest.reflect.field.FieldType#in(Object) in}(Person.class).{@link org.fest.reflect.field.FieldAccessor#get() get}();
   * </pre>
   * </p>
   *
   * @param type the type of the field to access.
   * @return a holder for the field type.
   * @throws NullPointerException if the given type is {@code null}.
   */
  public @Nonnull <T> FieldType<T> ofType(@Nonnull Class<T> type) {
    return new FieldType<T>(checkNotNullOrEmpty(name), type);
  }

  /**
   * Specifies the type of the field to access. This method uses {@link TypeRef} instead of {@link Class} to preserve
   * generic types that otherwise would be lost due to erasure.
   *
   * <p>
   * Examples demonstrating usage of the fluent interface:
   *
   * <pre>
   * // Retrieves the value of the field "name"
   * String name = {@link org.fest.reflect.core.Reflection#field(String) field}("name").{@link FieldName#ofType(Class) ofType}(String.class).{@link FieldType#in(Object) in}(person).{@link FieldAccessor#get() get}();
   *
   * // Sets the value of the field "name" to "Yoda"
   * {@link org.fest.reflect.core.Reflection#field(String) field}("name").{@link FieldName#ofType(Class) ofType}(String.class).{@link FieldType#in(Object) in}(person).{@link FieldAccessor#set(Object) set}("Yoda");
   *
   * // Retrieves the value of the field "powers"
   * List&lt;String&gt; powers = {@link org.fest.reflect.core.Reflection#field(String) field}("powers").{@link FieldName#ofType(TypeRef) ofType}(new {@link TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link FieldTypeRef#in(Object) in}(jedi).{@link FieldAccessor#get() get}();
   *
   * // Sets the value of the field "powers"
   * List&lt;String&gt; powers = new ArrayList&lt;String&gt;();
   * powers.add("heal");
   * {@link org.fest.reflect.core.Reflection#field(String) field}("powers").{@link FieldName#ofType(TypeRef) ofType}(new {@link TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link FieldTypeRef#in(Object) in}(jedi).{@link FieldAccessor#set(Object) set}(powers);
   *
   * // Retrieves the value of the static field "count" in Person.class
   * int count = {@link org.fest.reflect.core.Reflection#field(String) field}("count").{@link org.fest.reflect.field.FieldName#ofType(Class) ofType}(int.class).{@link org.fest.reflect.field.FieldType#in(Object) in}(Person.class).{@link org.fest.reflect.field.FieldAccessor#get() get}();
   * </pre>
   * </p>
   *
   * @param type the type of the field to access.
   * @return a holder for the field type.
   * @throws NullPointerException if the given type is {@code null}.
   * @since 1.1
   */
  public @Nonnull <T> FieldTypeRef<T> ofType(@Nonnull TypeRef<T> type) {
    return new FieldTypeRef<T>(checkNotNullOrEmpty(name), type);
  }
}