/*
 * Created on Feb 26, 2011
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
package org.fest.reflect.innerclass;

import org.fest.reflect.exception.ReflectionError;

/**
 * Fluent interface for finding static inner classes.
 * <p>
 * Examples:
 * <pre>
 * // import static {@link org.fest.reflect.innerclass.StaticInnerClasses#staticInnerClass(String) org.fest.reflect.innerclass.StaticInnerClasses.staticInnerClass};
 *
 * // Gets the inner class 'Master' in the declaring class 'Jedi':
 * Class&lt;?&gt; masterClass = {@link org.fest.reflect.innerclass.StaticInnerClasses#staticInnerClass(String) staticInnerClass}("Master").{@link org.fest.reflect.innerclass.Finder#in(Class) in}(Jedi.class);
 * </pre>
 * </p>
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class StaticInnerClasses implements Finder {

  /**
   * Sets the name of the static inner class to find.
   * @param name the name of the static inner class.
   * @return the next object in the fluent interface.
   * @throws NullPointerException if the given name is {@code null}.
   * @throws IllegalArgumentException if the given name is empty.
   */
  public static Finder staticInnerClass(String name) {
    if (name == null) throw new NullPointerException("The name of the static inner class to access should not be null");
    if (name.length() == 0)
      throw new IllegalArgumentException("The name of the static inner class to access should not be empty");
    return new StaticInnerClasses(name);
  }

  private final String name;

  private StaticInnerClasses(String name) {
    this.name = name;
  }

  /** {@inheritDoc} */
  public Class<?> in(Class<?> declaringType) {
    if (declaringType == null) throw new NullPointerException("The declaring class should not be null");
    String namespace = declaringType.getName();
    for (Class<?> innerClass : declaringType.getDeclaredClasses())
      if (innerClass.getName().equals(expectedInnerClassName(namespace))) return innerClass;
    throw cannotFindInnerClassIn(declaringType);
  }

  private String expectedInnerClassName(String namespace) {
    return String.format("%s$%s", namespace, name);
  }

  private ReflectionError cannotFindInnerClassIn(Class<?> declaringType) {
    String message = String.format("The static inner class <%s> cannot be found in %s", name, declaringType.getName());
    return new ReflectionError(message);
  }
}
