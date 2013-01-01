/*
 * Created on Jan 24, 2009
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
package org.fest.reflect.field;

import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Preconditions.checkNotNullOrEmpty;

import javax.annotation.Nonnull;

import org.fest.reflect.exception.ReflectionError;
import org.fest.reflect.reference.TypeRef;

/**
 * Holds the type of the field to access via Java Reflection, preserving generic types that otherwise would be lost due
 * to erasure.
 * 
 * @param <T> the type of the field.
 * @author Alex Ruiz
 * @since 1.1
 */
public class FieldTypeRef<T> {
  private final String filedName;
  private final TypeRef<T> value;

  FieldTypeRef(@Nonnull String fieldName, @Nonnull TypeRef<T> type) {
    this.filedName = checkNotNullOrEmpty(fieldName);
    this.value = checkNotNull(type);
  }

  /**
   * Creates a new field accessor.
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
   * @param target the object containing the field to access.
   * @return the created field accessor.
   * @throws NullPointerException if the given target is {@code null}.
   * @throws ReflectionError if a field with a matching name and type cannot be found.
   */
  public @Nonnull FieldAccessor<T> in(@Nonnull Object target) {
    return new FieldAccessor<T>(checkNotNullOrEmpty(filedName), value.rawType(), target);
  }
}