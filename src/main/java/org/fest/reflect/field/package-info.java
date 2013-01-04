/*
 * Created on Jan 2, 2013
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
 * Copyright @2013 the original author or authors.
 */
/**
 * <p>
 * Provides a fluent interface for accessing fields via
 * <a href="http://docs.oracle.com/javase/tutorial/reflect/index.html" target="_blank">Java Reflection</a>.
 * </p>
 *
 * <p>
 * Examples:
 *
 * <pre>
 * // import static {@link org.fest.reflect.core.Reflection#field(String) org.fest.reflect.core.Reflection.field};
 *
 * // Retrieves the value of the field "name"
 * String name = {@link org.fest.reflect.core.Reflection#field(String) field}("name").{@link org.fest.reflect.field.FieldName#ofType(Class) ofType}(String.class).{@link org.fest.reflect.field.FieldType#in(Object) in}(person).{@link org.fest.reflect.field.FieldAccessor#get() get}();
 *
 * // Sets the value of the field "name" to "Yoda"
 * {@link org.fest.reflect.core.Reflection#field(String) field}("name").{@link org.fest.reflect.field.FieldName#ofType(Class) ofType}(String.class).{@link org.fest.reflect.field.FieldType#in(Object) in}(person).{@link org.fest.reflect.field.FieldAccessor#set(Object) set}("Yoda");
 *
 * // Retrieves the value of the field "powers"
 * List&lt;String&gt; powers = {@link org.fest.reflect.core.Reflection#field(String) field}("powers").{@link org.fest.reflect.field.FieldName#ofType(org.fest.reflect.reference.TypeRef) ofType}(new {@link org.fest.reflect.reference.TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link org.fest.reflect.field.FieldTypeRef#in(Object) in}(jedi).{@link org.fest.reflect.field.FieldAccessor#get() get}();
 *
 * // Sets the value of the field "powers"
 * List&lt;String&gt; powers = new ArrayList&lt;String&gt;();
 * powers.add("heal");
 * {@link org.fest.reflect.core.Reflection#field(String) field}("powers").{@link org.fest.reflect.field.FieldName#ofType(org.fest.reflect.reference.TypeRef) ofType}(new {@link org.fest.reflect.reference.TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link org.fest.reflect.field.FieldTypeRef#in(Object) in}(jedi).{@link org.fest.reflect.field.FieldAccessor#set(Object) set}(powers);
 *
 * // Retrieves the value of the static field "count" in Person.class
 * int count = {@link org.fest.reflect.core.Reflection#field(String) field}("count").{@link org.fest.reflect.field.FieldName#ofType(Class) ofType}(int.class).{@link org.fest.reflect.field.FieldType#in(Object) in}(Person.class).{@link org.fest.reflect.field.FieldAccessor#get() get}();
 * </pre>
 * </p>
 */
package org.fest.reflect.field;