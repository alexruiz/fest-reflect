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
 * <p>
 * Provides a fluent interface for invoking constructors via
 * <a href="http://docs.oracle.com/javase/tutorial/reflect/index.html" target="_blank">Java Reflection</a>.
 * </p>
 *
 * <p>
 * Examples:
 *
 * <pre>
 * // import static {@link org.fest.reflect.core.Reflection#constructor() org.fest.reflect.core.Reflection.constructor};
 *
 * // Equivalent to 'Person p = new Person()'
 * Person p = {@link org.fest.reflect.core.Reflection#constructor() constructor}().{@link org.fest.reflect.constructor.TargetType#in in}(Person.class).{@link org.fest.reflect.constructor.ConstructorInvoker#newInstance newInstance}();
 * 
 * // Equivalent to 'Person p = new Person("Yoda")'
 * Person p = {@link org.fest.reflect.core.Reflection#constructor() constructor}().{@link org.fest.reflect.constructor.TargetType#withParameterTypes(Class...) withParameterTypes}(String.class).{@link org.fest.reflect.constructor.ParameterTypes#in(Class) in}(Person.class).{@link org.fest.reflect.constructor.ConstructorInvoker#newInstance newInstance}("Yoda");
 * </pre>
 * </p>
 */
package org.fest.reflect.constructor;