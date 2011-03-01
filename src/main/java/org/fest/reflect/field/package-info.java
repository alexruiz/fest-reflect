/*
 * Created on Feb 27, 2011
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
 * Copyright @2011 the original author or authors.
 */

/**
 * <p>
 * Provides a &quot;fluent&quot; API for field access via Java Reflection.
 * </p>
 * <p>
 * Examples:
 * <pre>
 * // import static {@link org.fest.reflect.field.Fields#fieldOfType(Class) org.fest.reflect.field.Fields#fieldOfType}.*;
 *
 * // Retrieves the value of the field "name"
 * String name = {@link org.fest.reflect.field.Fields#fieldOfType(Class) fieldOfType}(String.class).{@link org.fest.reflect.field.Name#withName(String) withName}("name").{@link org.fest.reflect.field.Target#in(Object) in}(person).{@link org.fest.reflect.field.Invoker#get() get}();
 *
 * // Sets the value of the field "name" to "Yoda"
 * {@link org.fest.reflect.field.Fields#fieldOfType(Class) fieldOfType}(String.class).{@link org.fest.reflect.field.Name#withName(String) withName}("name").{@link org.fest.reflect.field.Target#in(Object) in}(person).{@link org.fest.reflect.field.Invoker#set(Object) set}("Yoda");
 *
 * // Retrieves the value of the static field "count"
 * int count = {@link org.fest.reflect.field.Fields#fieldOfType(Class) fieldOfType}(int.class).{@link org.fest.reflect.field.Name#withName(String) withName}("count").{@link org.fest.reflect.field.Target#in(Class) in}(Person.class).{@link org.fest.reflect.field.Invoker#get() get}();
 *
 * // Sets the value of the static field "count" to 3
 * {@link org.fest.reflect.field.Fields#fieldOfType(Class) fieldOfType}(int.class).{@link org.fest.reflect.field.Name#withName(String) withName}("count").{@link org.fest.reflect.field.Target#in(Class) in}(Person.class).{@link org.fest.reflect.field.Invoker#set(Object) set}(3);
 * </pre>
 * </p>
 */
package org.fest.reflect.field;
