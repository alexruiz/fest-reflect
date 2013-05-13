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

import org.fest.reflect.reference.TypeRef;
import org.fest.util.InternalApi;

import javax.annotation.Nonnull;

import static org.fest.util.Preconditions.checkNotNullOrEmpty;

/**
 * Stores the name of the field to access via
 * <a href="http://docs.oracle.com/javase/tutorial/reflect/index.html" target="_blank">Java Reflection</a>.
 * <p/>
 * <strong>Note:</strong> To improve code readability, we recommend invoking
 * {@link org.fest.reflect.core.Reflection#field(String) Reflection.field(String)} instead of this class' constructor:
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
 *
 * @author Alex Ruiz
 */
public final class FieldName {
  private final String name;

  /**
   * Creates a new {@link FieldName}.
   * <p/>
   * <strong>Note:</strong> To improve code readability, we recommend invoking
   * {@link org.fest.reflect.core.Reflection#field(String) Reflection.field(String)} instead of this constructor:
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
   *
   * @param name the name of the field to invoke.
   * @throws NullPointerException     if the field name is {@code null}.
   * @throws IllegalArgumentException if the field name is empty.
   */
  @InternalApi
  public FieldName(@Nonnull String name) {
    this.name = checkNotNullOrEmpty(name);
  }

  /**
   * Specifies the type of the field to access.
   * <p/>
   * Examples:
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
   *
   * @param type the type of the field to access.
   * @return a holder for the field type.
   * @throws NullPointerException if the given type is {@code null}.
   */
  public @Nonnull <T> FieldType<T> ofType(@Nonnull Class<T> type) {
    return new FieldType<T>(name, type);
  }

  /**
   * Specifies the type of the field to access. This method uses {@link TypeRef} instead of {@link Class} to preserve
   * generic types that otherwise would be lost due to
   * <a href="http://docs.oracle.com/javase/tutorial/java/generics/erasure.html" target="_blank">type erasure</a>.
   * <p/>
   * Examples:
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
   *
   * @param type the type of the field to access.
   * @return a holder for the field type.
   * @throws NullPointerException if the given type is {@code null}.
   * @since 1.1
   */
  public @Nonnull <T> FieldTypeRef<T> ofType(@Nonnull TypeRef<T> type) {
    return new FieldTypeRef<T>(name, type);
  }
}