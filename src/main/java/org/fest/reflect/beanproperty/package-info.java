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
 * Provides a fluent interface for JavaBean property access via Bean Instrospection.
 *
 * <p>
 * Examples:
 *
 * <pre>
 * // Retrieves the value of the property "name"
 * String name = {@link org.fest.reflect.core.Reflection#property(String) property}("name").{@link org.fest.reflect.beanproperty.PropertyName#ofType(Class) ofType}(String.class).{@link org.fest.reflect.beanproperty.PropertyType#in(Object) in}(person).{@link org.fest.reflect.beanproperty.PropertyAccessor#get() get}();
 *
 * // Sets the value of the property "name" to "Yoda"
 * {@link org.fest.reflect.core.Reflection#property(String) property}("name").{@link org.fest.reflect.beanproperty.PropertyName#ofType(Class) ofType}(String.class).{@link org.fest.reflect.beanproperty.PropertyType#in(Object) in}(person).{@link org.fest.reflect.beanproperty.PropertyAccessor#set(Object) set}("Yoda");
 * 
 * // Retrieves the value of the property "powers"
 * List&lt;String&gt; powers = {@link org.fest.reflect.core.Reflection#property(String) property}("powers").{@link org.fest.reflect.beanproperty.PropertyName#ofType(org.fest.reflect.reference.TypeRef) ofType}(new {@link org.fest.reflect.reference.TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link org.fest.reflect.beanproperty.PropertyTypeRef#in(Object) in}(jedi).{@link org.fest.reflect.beanproperty.PropertyAccessor#get() get}();
 * 
 * // Sets the value of the property "powers"
 * List&lt;String&gt; powers = new ArrayList&lt;String&gt;();
 * powers.add("heal");
 * {@link org.fest.reflect.core.Reflection#property(String) property}("powers").{@link org.fest.reflect.beanproperty.PropertyName#ofType(org.fest.reflect.reference.TypeRef) ofType}(new {@link org.fest.reflect.reference.TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link org.fest.reflect.beanproperty.PropertyTypeRef#in(Object) in}(jedi).{@link org.fest.reflect.beanproperty.PropertyAccessor#set(Object) set}(powers);
 * </pre>
 * </p>
 */
package org.fest.reflect.beanproperty;
