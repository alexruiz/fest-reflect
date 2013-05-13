/*
 * Created on Oct 31, 2006
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

import org.fest.reflect.exception.ReflectionError;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;

import static org.fest.reflect.util.Accessibles.*;
import static org.fest.reflect.util.Types.castSafely;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Preconditions.checkNotNullOrEmpty;

/**
 * Accesses a field via
 * <a href="http://docs.oracle.com/javase/tutorial/reflect/index.html" target="_blank">Java Reflection</a>.
 *
 * @param <T> the type of the field to access.
 * @author Alex Ruiz
 */
public final class FieldAccessor<T> {
  private final Class<T> fieldType;
  private final Object target;
  private final Field field;
  private final boolean accessible;

  FieldAccessor(@Nonnull String fieldName, @Nonnull Class<T> fieldType, @Nonnull Object target) {
    this.fieldType = checkNotNull(fieldType);
    this.target = checkNotNull(target);
    field = findFieldInClassHierarchy(checkNotNullOrEmpty(fieldName));
    accessible = field.isAccessible();
  }

  private @Nonnull Field findFieldInClassHierarchy(@Nonnull String fieldName) {
    Field field = null;
    Class<?> originalType = target instanceof Class<?> ? (Class<?>) target : target.getClass();
    Class<?> targetType = originalType;
    while (targetType != null) {
      try {
        field = targetType.getDeclaredField(fieldName);
      } catch (NoSuchFieldException e) {
        targetType = targetType.getSuperclass();
        continue;
      }
      break;
    }
    if (field != null) {
      boolean isAccessible = field.isAccessible();
      try {
        makeAccessible(field);
        Class<?> actualType = field.getType();
        if (!fieldType.isAssignableFrom(actualType)) {
          String format = "Expecting type of field '%s' in %s to be <%s> but was <%s>";
          String msg =
              String.format(format, fieldName, originalType.getName(), fieldType.getName(), actualType.getName());
          throw new ReflectionError(msg);
        }
      } finally {
        setAccessibleIgnoringExceptions(field, isAccessible);
      }
      return field;
    }
    String format = "Failed to find field '%s' in %s";
    String msg = String.format(format, fieldName, originalType.getName());
    throw new ReflectionError(msg);
  }

  /**
   * Sets a value in the field specified in this fluent interface.
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
   *
   * @param value the value to set.
   * @throws ReflectionError if the given value cannot be set.
   */
  public void set(@Nullable T value) {
    Field f = checkNotNull(field);
    try {
      setAccessible(f, true);
      f.set(target, value);
    } catch (Throwable t) {
      String format = "Failed to set value %s in field '%s'";
      String msg = String.format(format, String.valueOf(value), f.getName());
      throw new ReflectionError(msg, t);
    } finally {
      setAccessibleIgnoringExceptions(f, accessible);
    }
  }

  /**
   * Retrieves the value of the field specified in this fluent interface.
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
   *
   * @return the value of the field in this fluent interface.
   * @throws ReflectionError if the value of the field cannot be retrieved.
   */
  public @Nullable T get() {
    Field f = checkNotNull(field);
    try {
      setAccessible(f, true);
      Object value = f.get(target);
      return castSafely(value, checkNotNull(fieldType));
    } catch (Throwable t) {
      String msg = String.format("Failed to get the value of field '%s'", f.getName());
      throw new ReflectionError(msg, t);
    } finally {
      setAccessibleIgnoringExceptions(f, accessible);
    }
  }

  /**
   * @return the underlying field to access.
   */
  public @Nonnull Field target() {
    return field;
  }
}
