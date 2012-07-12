/*
 * Created on Feb 5, 2006
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

import org.fest.reflect.exception.ReflectionError;
import org.fest.reflect.reference.TypeRef;

/**
 * Understands the type of a static field to access using Java Reflection.
 * <p>
 * The following is an example of proper usage of this class:
 * 
 * <pre>
 *   // Retrieves the value of the static field "commonPowers"
 *   List&lt;String&gt; commmonPowers = {@link org.fest.reflect.core.Reflection#staticField(String) staticField}("commonPowers").{@link StaticFieldName#ofType(TypeRef) ofType}(new {@link TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link StaticFieldTypeRef#in(Class) in}(Jedi.class).{@link Invoker#get() get}();
 *
 *   // Sets the value of the static field "commonPowers"
 *   List&lt;String&gt; commonPowers = new ArrayList&lt;String&gt;();
 *   commonPowers.add("jump");
 *   {@link org.fest.reflect.core.Reflection#staticField(String) staticField}("commonPowers").{@link StaticFieldName#ofType(TypeRef) ofType}(new {@link TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link StaticFieldTypeRef#in(Class) in}(Jedi.class).{@link Invoker#set(Object) set}(commonPowers);
 * </pre>
 * </p>
 * 
 * @param <T> the generic type of the field.
 * 
 * @author Alex Ruiz
 * 
 * @since 1.1
 */
public class StaticFieldTypeRef<T> {

  static <T> StaticFieldTypeRef<T> newFieldTypeRef(String name, TypeRef<T> type) {
    if (type == null) throw new NullPointerException("The type reference of the static field to access should not be null");
    return new StaticFieldTypeRef<T>(name, type);
  }

  private final String name;
  private final TypeRef<T> type;

  private StaticFieldTypeRef(String name, TypeRef<T> type) {
    this.name = name;
    this.type = type;
  }

  /**
   * Returns a new field invoker. A field invoker is capable of accessing (read/write) the underlying field.
   * @param target the type containing the static field of interest.
   * @return the created field invoker.
   * @throws NullPointerException if the given target is <code>null</code>.
   * @throws ReflectionError if a static field with a matching name and type cannot be found.
   */
  public Invoker<T> in(Class<?> target) {
    return newInvoker(name, type, target);
  }
}