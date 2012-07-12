/*
 * Created on Jan 25, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.reflect.innerclass;

import static org.fest.reflect.innerclass.Invoker.newInvoker;
import static org.fest.util.Strings.isEmpty;

/**
 * Understands the name of a static inner class.
 * <p>
 * Let's assume we have the class <code>Jedi</code>, which contains two static inner classes: <code>Master</code> and
 * <code>Padawan</code>.
 * 
 * <pre>
 * public class Jedi {
 *
 *   public static class Master {}
 *
 *   public static class Padawan {}
 * }
 * </pre>
 * </p>
 * <p>
 * The following example shows how to get a reference to the inner class <code>Master</code>:
 * 
 * <pre>
 * Class&lt;?&gt; masterClass = {@link org.fest.reflect.core.Reflection#staticInnerClass(String) staticInnerClass}("Master").{@link org.fest.reflect.innerclass.StaticInnerClassName#in(Class) in}(Jedi.class).{@link org.fest.reflect.innerclass.Invoker#get() get}();
 * </pre>
 * </p>
 * 
 * @author Alex Ruiz
 * 
 * @since 1.1
 */
public final class StaticInnerClassName {

  /**
   * Creates a new </code>{@link StaticInnerClassName}</code>.
   * @param name the name of the static inner class to obtain.
   * @return the created <code>StaticInnerClassName</code>.
   * @throws NullPointerException if the given name is <code>null</code>.
   * @throws IllegalArgumentException if the given name is empty.
   */
  public static StaticInnerClassName startStaticInnerClassAccess(String name) {
    validateIsNotNullOrEmpty(name);
    return new StaticInnerClassName(name);
  }

  private static void validateIsNotNullOrEmpty(String name) {
    if (name == null) throw new NullPointerException("The name of the static inner class to access should not be null");
    if (isEmpty(name)) throw new IllegalArgumentException("The name of the static inner class to access should not be empty");
  }

  private final String name;

  private StaticInnerClassName(String name) {
    this.name = name;
  }

  /**
   * Specifies the declaring class of the static inner class to obtain.
   * @param declaringClass the declaring class.
   * @return an object responsible for obtaining a reference to a static inner class.
   * @throws NullPointerException if the given declaring class is <code>null</code>.
   */
  public Invoker in(Class<?> declaringClass) {
    return newInvoker(declaringClass, name);
  }
}
