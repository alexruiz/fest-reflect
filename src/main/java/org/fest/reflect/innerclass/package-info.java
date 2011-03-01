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
 * Provides a &quot;fluent&quot; API for accessing static inner classes via Java Reflection.
 * </p>
 * <p>
 * Examples:
 * <pre>
 * // import static {@link org.fest.reflect.innerclass.StaticInnerClasses#staticInnerClass(String) org.fest.reflect.innerclass.StaticInnerClasses.staticInnerClass};
 *
 * // Gets the inner class 'Master' in the declaring class 'Jedi':
 * Class&lt;?&gt; masterClass = {@link org.fest.reflect.innerclass.StaticInnerClasses#staticInnerClass(String) staticInnerClass}("Master").{@link org.fest.reflect.innerclass.Finder#in(Class) in}(Jedi.class);
 * </pre>
 * </p>
 */
package org.fest.reflect.innerclass;

