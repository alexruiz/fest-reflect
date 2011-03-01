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
 * Provides a &quot;fluent&quot; API for property access via the Bean Instrospection API.
 * </p>
 * <p>
 * Examples:
 * <pre>
 * // import static {@link org.fest.reflect.javabean.Properties#propertyOfType(Class) org.fest.reflect.javabean.Properties#propertyOfType};
 *
 * // Retrieves the value of the property "name"
 * String name = {@link org.fest.reflect.javabean.Properties#propertyOfType(Class) propertyOfType}(String.class).{@link org.fest.reflect.javabean.Name#withName(String) withName}("name").{@link org.fest.reflect.javabean.Target#in(Object) in}(person).{@link org.fest.reflect.javabean.Invoker#get() get}();
 *
 * // Sets the value of the property "name" to "Yoda"
 * {@link org.fest.reflect.javabean.Properties#propertyOfType(Class) propertyOfType}(String.class).{@link org.fest.reflect.javabean.Name#withName(String) withName}("name").{@link org.fest.reflect.javabean.Target#in(Object) in}(person).{@link org.fest.reflect.javabean.Invoker#set(Object) set}("Yoda");
 * </pre>
 * </p>
 */
package org.fest.reflect.javabean;

