/*
 * Created on Feb 5, 2008
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

import static org.fest.reflect.field.StaticFieldType.newFieldType;
import static org.fest.reflect.field.StaticFieldTypeRef.newFieldTypeRef;
import static org.fest.util.Strings.isEmpty;

import org.fest.reflect.reference.TypeRef;

/**
 * Understands the name of a static field to access using Java Reflection.
 * <p>
 * The following is an example of proper usage of this class:
 * 
 * <pre>
 *   // Retrieves the value of the static field "count"
 *   int count = {@link org.fest.reflect.core.Reflection#staticField(String) staticField}("count").{@link StaticFieldName#ofType(Class) ofType}(int.class).{@link StaticFieldType#in(Class) in}(Person.class).{@link Invoker#get() get}();
 *
 *   // Sets the value of the static field "count" to 3
 *   {@link org.fest.reflect.core.Reflection#staticField(String) staticField}("count").{@link StaticFieldName#ofType(Class) ofType}(int.class).{@link StaticFieldType#in(Class) in}(Person.class).{@link Invoker#set(Object) set}(3);
 *
 *   // Retrieves the value of the static field "commonPowers"
 *   List&lt;String&gt; commmonPowers = {@link org.fest.reflect.core.Reflection#staticField(String) staticField}("commonPowers").{@link #ofType(TypeRef) ofType}(new {@link TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link StaticFieldTypeRef#in(Class) in}(Jedi.class).{@link Invoker#get() get}();
 *
 *   // Sets the value of the static field "commonPowers"
 *   List&lt;String&gt; commonPowers = new ArrayList&lt;String&gt;();
 *   commonPowers.add("jump");
 *   {@link org.fest.reflect.core.Reflection#staticField(String) staticField}("commonPowers").{@link #ofType(TypeRef) ofType}(new {@link TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link StaticFieldTypeRef#in(Class) in}(Jedi.class).{@link Invoker#set(Object) set}(commonPowers);
 * </pre>
 * </p>
 * 
 * @author Alex Ruiz
 */
public final class StaticFieldName {

  /**
   * Creates a new <code>{@link StaticFieldName}</code>: the starting point of the fluent interface for accessing static fields
   * using Java Reflection.
   * @param name the name of the field to access using Java Reflection.
   * @return the created <code>StaticFieldName</code>.
   * @throws NullPointerException if the given name is <code>null</code>.
   * @throws IllegalArgumentException if the given name is empty.
   */
  public static StaticFieldName beginStaticFieldAccess(String name) {
    validateIsNotNullOrEmpty(name);
    return new StaticFieldName(name);
  }

  private static void validateIsNotNullOrEmpty(String name) {
    if (name == null) throw new NullPointerException("The name of the static field to access should not be null");
    if (isEmpty(name)) throw new IllegalArgumentException("The name of the static field to access should not be empty");
  }

  private final String name;

  private StaticFieldName(String name) {
    this.name = name;
  }

  /**
   * Sets the type of the field to access.
   * @param <T> the generic type of the field type.
   * @param type the type of the field to access.
   * @return a recipient for the field type.
   * @throws NullPointerException if the given type is <code>null</code>.
   */
  public <T> StaticFieldType<T> ofType(Class<T> type) {
    return newFieldType(name, type);
  }

  /**
   * Sets the type reference of the field to access. This method reduces casting when the type of the field to access uses
   * generics.
   * <p>
   * For example:
   * 
   * <pre>
   *   List&lt;String&gt; commmonPowers = {@link org.fest.reflect.core.Reflection#staticField(String) staticField}("commonPowers").{@link #ofType(TypeRef) ofType}(new {@link TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link StaticFieldTypeRef#in(Class) in}(Jedi.class).{@link Invoker#get() get}();
   * </pre>
   * </p>
   * @param <T> the generic type of the field type.
   * @param type the type of the field to access.
   * @return a recipient for the field type.
   * @throws NullPointerException if the given type reference is <code>null</code>.
   */
  public <T> StaticFieldTypeRef<T> ofType(TypeRef<T> type) {
    return newFieldTypeRef(name, type);
  }
}