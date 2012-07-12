/*
 * Created on Aug 17, 2006
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
 * Copyright @2006-2009 the original author or authors.
 */
package org.fest.reflect.field;

import static org.fest.reflect.field.Invoker.newInvoker;

import java.util.List;

import org.fest.reflect.exception.ReflectionError;

/**
 * Understands the type of a field to access using Java Reflection.
 * <p>
 * The following is an example of proper usage of this class:
 * 
 * <pre>
 * // Retrieves the value of the field "name"
 * String name = {@link org.fest.reflect.core.Reflection#field(String) field}("name").{@link FieldName#ofType(Class) ofType}(String.class).{@link FieldType#in(Object) in}(person).{@link Invoker#get() get}();
 *
 * // Sets the value of the field "name" to "Yoda"
 * {@link org.fest.reflect.core.Reflection#field(String) field}("name").{@link FieldName#ofType(Class) ofType}(String.class).{@link FieldType#in(Object) in}(person).{@link Invoker#set(Object) set}("Yoda");
 * </pre>
 * </p>
 * 
 * @param <T> the generic type of the field.
 * 
 * @author Alex Ruiz
 * @author Ivan Hristov
 */
public class FieldType<T> {

  private final List<String> path;

  static <T> FieldType<T> newFieldType(String name, Class<T> type, List<String> path) {
    if (type == null) throw new NullPointerException("The type of the field to access should not be null");
    return new FieldType<T>(name, type, path);
  }

  private final String name;
  private final Class<T> type;

  private FieldType(String name, Class<T> type, List<String> path) {
    this.name = name;
    this.type = type;
    this.path = path;
  }

  /**
   * Returns a new field access invoker, capable of accessing (read/write) the underlying field.
   * @param target the object containing the field of interest.
   * @return the created field access invoker.
   * @throws NullPointerException if the given target is <code>null</code>.
   * @throws ReflectionError if a field with a matching name and type cannot be found.
   */
  public Invoker<T> in(Object target) {
    Object nestedTarget = null;

    for (String fieldName : path) {
      nestedTarget = Invoker.getNestedField(fieldName, nestedTarget == null ? target : nestedTarget);
    }

    return newInvoker(name, type, nestedTarget == null ? target : nestedTarget);
  }

}