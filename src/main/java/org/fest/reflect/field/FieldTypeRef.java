/*
 * Created on Jan 24, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.reflect.field;

import static org.fest.reflect.field.Invoker.newInvoker;

import java.util.List;

import org.fest.reflect.exception.ReflectionError;
import org.fest.reflect.reference.TypeRef;

/**
 * Understands the type of a field to access using Java Reflection. This implementation supports Java generics.
 * <p>
 * The following is an example of proper usage of this class:
 * 
 * <pre>
 *   // Retrieves the value of the field "powers"
 *   List&lt;String&gt; powers = {@link org.fest.reflect.core.Reflection#field(String) field}("powers").{@link FieldName#ofType(TypeRef) ofType}(new {@link TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link #in(Object) in}(jedi).{@link Invoker#get() get}();
 *
 *   // Sets the value of the field "powers"
 *   List&lt;String&gt; powers = new ArrayList&lt;String&gt;();
 *   powers.add("heal");
 *   {@link org.fest.reflect.core.Reflection#field(String) field}("powers").{@link FieldName#ofType(TypeRef) ofType}(new {@link TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link #in(Object) in}(jedi).{@link Invoker#set(Object) set}(powers);
 * </pre>
 * </p>
 * 
 * @param <T> the generic type of the field.
 * 
 * @author Alex Ruiz
 * @author Ivan Hristov
 * 
 * @since 1.1
 */
public class FieldTypeRef<T> {

  static <T> FieldTypeRef<T> newFieldTypeRef(String name, TypeRef<T> type, List<String> path) {
    if (type == null) throw new NullPointerException("The type reference of the field to access should not be null");
    return new FieldTypeRef<T>(name, type, path);
  }

  private final String name;
  private final TypeRef<T> type;
  private final List<String> path;

  private FieldTypeRef(String name, TypeRef<T> type, List<String> path) {
    this.name = name;
    this.type = type;
    this.path = path;
  }

  /**
   * Returns a new field invoker. A field invoker is capable of accessing (read/write) the underlying field.
   * @param target the object containing the field of interest.
   * @return the created field invoker.
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