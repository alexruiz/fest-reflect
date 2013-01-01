/*
 * Created on Jan 1, 2013
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
 * Fluent interfaces that provide a more readable, easier to write and type safer way to use Java Reflection and
 * Beans Introspection.
 *
 * <p>
 * Examples:
 *
 * <pre>
 * // Loads the class 'org.republic.Jedi'
 * Class&lt;?&gt; jediType = {@link org.fest.reflect.core.Reflection#type(String) type}("org.republic.Jedi").{@link org.fest.reflect.type.Type#load() load}();
 *
 * // Loads the class 'org.republic.Jedi' as 'org.republic.Person' (Jedi extends Person)
 * Class&lt;Person&gt; jediType = {@link org.fest.reflect.core.Reflection#type(String) type}("org.republic.Jedi").{@link org.fest.reflect.type.Type#loadAs(Class) loadAs}(Person.class);
 *
 * // Loads the class 'org.republic.Jedi' using a custom class loader
 * Class&lt;?&gt; jediType = {@link org.fest.reflect.core.Reflection#type(String) type}("org.republic.Jedi").{@link org.fest.reflect.type.Type#withClassLoader(ClassLoader) withClassLoader}(myClassLoader).{@link org.fest.reflect.type.TypeLoader#load() load}();
 *
 * // Gets the inner class 'Master' in the declaring class 'Jedi':
 * Class&lt;?&gt; masterClass = {@link org.fest.reflect.core.Reflection#innerClass(String) innerClass}("Master").{@link org.fest.reflect.innerclass.InnerClassName#in(Class) in}(Jedi.class).{@link org.fest.reflect.innerclass.InnerClassFinder#get() get}();
 *
 * // Equivalent to invoking 'new Person()'
 * Person p = {@link org.fest.reflect.core.Reflection#constructor() constructor}().{@link org.fest.reflect.constructor.TargetType#in in}(Person.class).{@link org.fest.reflect.constructor.ConstructorInvoker#newInstance(Object...) newInstance}();
 *
 * // Equivalent to invoking 'new Person("Yoda")'
 * Person p = {@link org.fest.reflect.core.Reflection#constructor() constructor}().{@link org.fest.reflect.constructor.TargetType#withParameterTypes(Class...) withParameterTypes}(String.class).{@link org.fest.reflect.constructor.ParameterTypes#in(Class) in}(Person.class).{@link org.fest.reflect.constructor.ConstructorInvoker#newInstance(Object...) newInstance}("Yoda");
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
 * // Equivalent to invoking the method 'person.setName("Luke")'
 * {@link org.fest.reflect.core.Reflection#method(String) method}("setName").{@link org.fest.reflect.method.MethodName#withParameterTypes(Class...) withParameterTypes}(String.class)
 *                  .{@link org.fest.reflect.method.ParameterTypes#in(Object) in}(person)
 *                  .{@link org.fest.reflect.method.MethodInvoker#invoke(Object...) invoke}("Luke");
 *
 * // Equivalent to invoking the method 'jedi.getPowers()'
 * List&lt;String&gt; powers = {@link org.fest.reflect.core.Reflection#method(String) method}("getPowers").{@link org.fest.reflect.method.MethodName#withReturnType(org.fest.reflect.reference.TypeRef) withReturnType}(new {@link org.fest.reflect.reference.TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {})
 *                                          .{@link org.fest.reflect.method.ReturnTypeRef#in(Object) in}(person)
 *                                          .{@link org.fest.reflect.method.MethodInvoker#invoke(Object...) invoke}();
 *
 * // Retrieves the value of the static field "count" in Person.class
 * int count = {@link org.fest.reflect.core.Reflection#field(String) field}("count").{@link org.fest.reflect.field.FieldName#ofType(Class) ofType}(int.class).{@link org.fest.reflect.field.FieldType#in(Object) in}(Person.class).{@link org.fest.reflect.field.FieldAccessor#get() get}();
 *
 * // Sets the value of the static field "count" to 3 in Person.class
 * {@link org.fest.reflect.core.Reflection#field(String) field}("count").{@link org.fest.reflect.field.FieldName#ofType(Class) ofType}(int.class).{@link org.fest.reflect.field.FieldType#in(Object) in}(Person.class).{@link org.fest.reflect.field.FieldAccessor#set(Object) set}(3);
 *
 * // Retrieves the value of the static field "commonPowers" in Jedi.class
 * List&lt;String&gt; commmonPowers = {@link org.fest.reflect.core.Reflection#field(String) field}("commonPowers").{@link org.fest.reflect.field.FieldName#ofType(org.fest.reflect.reference.TypeRef) ofType}(new {@link org.fest.reflect.reference.TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link org.fest.reflect.field.FieldTypeRef#in(Object) in}(Jedi.class).{@link org.fest.reflect.field.FieldAccessor#get() get}();
 *
 * // Equivalent to invoking the method 'person.concentrate()'
 * {@link org.fest.reflect.core.Reflection#method(String) method}("concentrate").{@link org.fest.reflect.method.MethodName#in(Object) in}(person).{@link org.fest.reflect.method.MethodInvoker#invoke(Object...) invoke}();
 *
 * // Equivalent to invoking the method 'person.getName()'
 * String name = {@link org.fest.reflect.core.Reflection#method(String) method}("getName").{@link org.fest.reflect.method.MethodName#withReturnType(Class) withReturnType}(String.class)
 *                                .{@link org.fest.reflect.method.ReturnType#in(Object) in}(person)
 *                                .{@link org.fest.reflect.method.MethodInvoker#invoke(Object...) invoke}();
 *
 * // Equivalent to invoking the static method 'Jedi.setCommonPower("Jump")'
 * {@link org.fest.reflect.core.Reflection#method(String) method}("setCommonPower").{@link org.fest.reflect.method.MethodName#withParameterTypes(Class...) withParameterTypes}(String.class)
 *                         .{@link org.fest.reflect.method.ParameterTypes#in(Object) in}(Jedi.class)
 *                         .{@link org.fest.reflect.method.MethodInvoker#invoke(Object...) invoke}("Jump");
 *
 * // Equivalent to invoking the static method 'Jedi.addPadawan()'
 * {@link org.fest.reflect.core.Reflection#method(String) method}("addPadawan").{@link org.fest.reflect.method.MethodName#in(Object) in}(Jedi.class).{@link org.fest.reflect.method.MethodInvoker#invoke(Object...) invoke}();
 *
 * // Equivalent to invoking the static method 'Jedi.commonPowerCount()'
 * String name = {@link org.fest.reflect.core.Reflection#method(String) method}("commonPowerCount").{@link org.fest.reflect.method.MethodName#withReturnType(Class) withReturnType}(String.class)
 *                                         .{@link org.fest.reflect.method.ReturnType#in(Object) in}(Jedi.class)
 *                                         .{@link org.fest.reflect.method.MethodInvoker#invoke(Object...) invoke}();
 *
 * // Equivalent to invoking the static method 'Jedi.getCommonPowers()'
 * List&lt;String&gt; powers = {@link org.fest.reflect.core.Reflection#method(String) method}("getCommonPowers").{@link org.fest.reflect.method.MethodName#withReturnType(org.fest.reflect.reference.TypeRef) withReturnType}(new {@link org.fest.reflect.reference.TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {})
 *                                                .{@link org.fest.reflect.method.ReturnTypeRef#in(Object) in}(Jedi.class)
 *                                                .{@link org.fest.reflect.method.MethodInvoker#invoke(Object...) invoke}();
 *
 * // Retrieves the value of the property "name"
 * String name = {@link org.fest.reflect.core.Reflection#property(String) property}("name").{@link org.fest.reflect.beanproperty.PropertyName#ofType(Class) ofType}(String.class).{@link org.fest.reflect.beanproperty.PropertyType#in(Object) in}(person).{@link org.fest.reflect.beanproperty.PropertyAccessor#get() get}();
 *
 * // Sets the value of the property "name" to "Yoda"
 * {@link org.fest.reflect.core.Reflection#property(String) property}("name").{@link org.fest.reflect.beanproperty.PropertyName#ofType(Class) ofType}(String.class).{@link org.fest.reflect.beanproperty.PropertyType#in(Object) in}(person).{@link org.fest.reflect.beanproperty.PropertyAccessor#set(Object) set}("Yoda");
 * </pre>
 * </p>
 */
package org.fest.reflect.core;
