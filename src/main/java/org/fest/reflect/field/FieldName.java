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

import org.fest.reflect.reference.TypeRef;


/**
 * Understands the name of a field to access using Java Reflection.
 * <p>
 * The following is an example of proper usage of this class:
 * <pre>
 *   // Retrieves the value of the field "name"
 *   String name = {@link org.fest.reflect.core.Reflection#field(String) field}("name").{@link FieldName#ofType(Class) ofType}(String.class).{@link FieldType#in(Object) in}(person).{@link Invoker#get() get}();
 *   
 *   // Sets the value of the field "name" to "Yoda"
 *   {@link org.fest.reflect.core.Reflection#field(String) field}("name").{@link FieldName#ofType(Class) ofType}(String.class).{@link FieldType#in(Object) in}(person).{@link Invoker#set(Object) set}("Yoda");
 *
 *   // Retrieves the value of the field "powers"
 *   List&lt;String&gt; powers = {@link org.fest.reflect.core.Reflection#field(String) field}("powers").{@link #ofType(TypeRef) ofType}(new {@link TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link FieldTypeRef#in(Object) in}(jedi).{@link Invoker#get() get}();
 *   
 *   // Sets the value of the field "powers"
 *   List&lt;String&gt; powers = new ArrayList&lt;String&gt;();
 *   powers.add("heal");
 *   {@link org.fest.reflect.core.Reflection#field(String) field}("powers").{@link #ofType(TypeRef) ofType}(new {@link TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link FieldTypeRef#in(Object) in}(jedi).{@link Invoker#set(Object) set}(powers);
 * </pre>
 * </p>
 *
 * @author Alex Ruiz
 */
public final class FieldName extends NameTemplate {

  /**
   * Creates a new <code>{@link FieldName}</code>: the starting point of the fluent interface for accessing fields
   * using Java Reflection.
   * @param name the name of the field to access using Java Reflection.
   * @throws NullPointerException if the given name is <code>null</code>.
   * @throws IllegalArgumentException if the given name is empty.
   */
  public FieldName(String name) {
    super(name);
  }

  /**
   * Sets the type of the field to access.
   * @param <T> the generic type of the field type.
   * @param type the type of the field to access.
   * @return a recipient for the field type.
   * @throws NullPointerException if the given type is <code>null</code>.
   */
  public <T> FieldType<T> ofType(Class<T> type) {
    return new FieldType<T>(type, this);
  }
  
  /**
   * Sets the type reference of the field to access. This method reduces casting when the type of the field to access 
   * uses generics.
   * <p>
   * For example:
   * <pre>
   *   List&lt;String&gt; powers = {@link org.fest.reflect.core.Reflection#field(String) field}("powers").{@link #ofType(TypeRef) ofType}(new {@link TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link FieldTypeRef#in(Object) in}(jedi).{@link Invoker#get() get}();
   * </pre>
   * </p>
   * @param <T> the generic type of the field type.
   * @param type the type of the field to access.
   * @return a recipient for the field type.
   * @throws NullPointerException if the given type reference is <code>null</code>.
   * @since 1.1
   */
  public <T> FieldTypeRef<T> ofType(TypeRef<T> type) {
    return new FieldTypeRef<T>(type, this);
  }
}