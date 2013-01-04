/*
 * Created on Jan 3, 2013
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
 * Provides a fluent interface for accessing static inner classes via
 * <a href="http://docs.oracle.com/javase/tutorial/reflect/index.html" target="_blank">Java Reflection</a>.
 * </p>
 *
 * <p>
 * Assuming we have the top-level class {@code Jedi} containing the static inner classes: {@code Master} and
 * {@code Padawan}:
 *
 * <pre>
 * public class Jedi {
 *   public static class Master {}
 * 
 *   public static class Padawan {}
 * }
 * </pre>
 *
 * The following example shows how to get a reference to the inner class {@code Master}:
 *
 * <pre>
 * // import static {@link org.fest.reflect.core.Reflection#innerClass(String) org.fest.reflect.core.Reflection.innerClass};
 *
 * Class&lt;?&gt; masterClass = {@link org.fest.reflect.core.Reflection#innerClass(String) innerClass}("Master").{@link org.fest.reflect.innerclass.InnerClassName#in(Class) in}(Jedi.class).{@link org.fest.reflect.innerclass.InnerClassFinder#get() get}();
 * </pre>
 * </p>
 */
package org.fest.reflect.innerclass;

