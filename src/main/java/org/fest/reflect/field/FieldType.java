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

import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Preconditions.checkNotNullOrEmpty;

import javax.annotation.Nonnull;

import org.fest.reflect.exception.ReflectionError;

/**
 * Stores the type of the field to access via
 * <a href="http://docs.oracle.com/javase/tutorial/reflect/index.html" target="_blank">Java Reflection</a>.
 *
 * @param <T> the type of the field.
 * @author Alex Ruiz
 */
public class FieldType<T> {
  private final String filedName;
  private final Class<T> value;

  FieldType(@Nonnull String fieldName, @Nonnull Class<T> type) {
    this.filedName = checkNotNullOrEmpty(fieldName);
    this.value = checkNotNull(type);
  }

  /**
   * <p>
   * Specifies the object or class containing the field to access.
   * </p>
   *
   * <p>
   * Examples demonstrating usage of the fluent interface:
   *
   * <pre>
   * // import static {@link org.fest.reflect.core.Reflection#field(String) org.fest.reflect.core.Reflection.field};
   *
   * // Retrieves the value of the field "name"
   * String name = {@link org.fest.reflect.core.Reflection#field(String) field}("name").{@link FieldName#ofType(Class) ofType}(String.class).{@link FieldType#in(Object) in}(person).{@link FieldAccessor#get() get}();
   *
   * // Sets the value of the field "name" to "Yoda"
   * {@link org.fest.reflect.core.Reflection#field(String) field}("name").{@link FieldName#ofType(Class) ofType}(String.class).{@link FieldType#in(Object) in}(person).{@link FieldAccessor#set(Object) set}("Yoda");
   *
   * // Retrieves the value of the field "powers"
   * List&lt;String&gt; powers = {@link org.fest.reflect.core.Reflection#field(String) field}("powers").{@link FieldName#ofType(org.fest.reflect.reference.TypeRef) ofType}(new {@link org.fest.reflect.reference.TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link FieldTypeRef#in(Object) in}(jedi).{@link FieldAccessor#get() get}();
   *
   * // Sets the value of the field "powers"
   * List&lt;String&gt; powers = new ArrayList&lt;String&gt;();
   * powers.add("heal");
   * {@link org.fest.reflect.core.Reflection#field(String) field}("powers").{@link FieldName#ofType(org.fest.reflect.reference.TypeRef) ofType}(new {@link org.fest.reflect.reference.TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link FieldTypeRef#in(Object) in}(jedi).{@link FieldAccessor#set(Object) set}(powers);
   *
   * // Retrieves the value of the static field "count" in Person.class
   * int count = {@link org.fest.reflect.core.Reflection#field(String) field}("count").{@link org.fest.reflect.field.FieldName#ofType(Class) ofType}(int.class).{@link org.fest.reflect.field.FieldType#in(Object) in}(Person.class).{@link org.fest.reflect.field.FieldAccessor#get() get}();
   * </pre>
   * </p>
   *
   * @param target the object containing the field to access. To access a static field, pass a class instead.
   * @return the created field accessor.
   * @throws NullPointerException if the given target is {@code null}.
   * @throws ReflectionError if a field with a matching name and type cannot be found.
   */
  public @Nonnull FieldAccessor<T> in(@Nonnull Object target) {
    return new FieldAccessor<T>(checkNotNullOrEmpty(filedName), checkNotNull(value), target);
  }
}