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
 * Copyright @2006-2009 the original author or authors.
 */
package org.fest.reflect.field;

import org.fest.reflect.exception.ReflectionError;

/**
 * Understands the type of a field to access using Java Reflection.
 * <p>
 * The following is an example of proper usage of this class:
 * <pre>
 *   // Retrieves the value of the field "name"
 *   String name = {@link org.fest.reflect.core.Reflection#field(String) field}("name").{@link FieldName#ofType(Class) ofType}(String.class).{@link FieldType#in(Object) in}(person).{@link Invoker#get() get}();
 *   
 *   // Sets the value of the field "name" to "Yoda"
 *   {@link org.fest.reflect.core.Reflection#field(String) field}("name").{@link FieldName#ofType(Class) ofType}(String.class).{@link FieldType#in(Object) in}(person).{@link Invoker#set(Object) set}("Yoda");
 * </pre>
 * </p>
 *
 * @param <T> the generic type of the field. 
 *
 * @author Alex Ruiz
 */
public class FieldType<T> extends TypeTemplate<T> {

  FieldType(Class<T> type, FieldName fieldName) {
    super(type, fieldName);
  }

  /**
   * Returns a new field invoker. A field invoker is capable of accessing (read/write) the underlying field.
   * @param target the object containing the field of interest.
   * @return the created field invoker.
   * @throws NullPointerException if the given target is <code>null</code>.
   * @throws ReflectionError if a field with a matching name and type cannot be found.
   */
  public Invoker<T> in(Object target) {
    if (target == null) throw new NullPointerException("Target should not be null");
    return fieldInvoker(target, target.getClass());
  }
}