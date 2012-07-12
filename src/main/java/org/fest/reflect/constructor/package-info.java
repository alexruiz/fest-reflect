/*
 * Created on Feb 27, 2011
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
 * Copyright @2011 the original author or authors.
 */

/**
 * <p>
 * Provides a &quot;fluent&quot; API for invoking constructors via Java Reflection.
 * </p>
 * <p>
 * Examples:
 * 
 * <pre>
 *    // import static {@link org.fest.reflect.constructor.Constructors#constructorIn(Class) org.fest.reflect.constructor.Constructors.constructorIn};
 *
 *    // Equivalent to call 'new Person()'
 *    Person p = {@link org.fest.reflect.constructor.Constructors#constructorIn(Class) constructorIn}(Person.class).{@link org.fest.reflect.constructor.ParameterTypes#withNoParameters() withNoParameters}().{@link org.fest.reflect.constructor.Invoker#newInstance(Object...) newInstance}();
 *
 *    // Equivalent to call 'new Person("Yoda")'
 *    Person p = {@link org.fest.reflect.constructor.Constructors#constructorIn(Class) constructorIn}(Person.class).{@link org.fest.reflect.constructor.ParameterTypes#withParameterTypes(Class...) withParameterTypes}(String.class).{@link org.fest.reflect.constructor.Invoker#newInstance(Object...) newInstance}("Yoda");
 * </pre>
 * </p>
 */
package org.fest.reflect.constructor;

