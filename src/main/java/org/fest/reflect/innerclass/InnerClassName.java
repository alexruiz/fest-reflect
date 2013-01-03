/*
 * Created on Jan 25, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.reflect.innerclass;

import static org.fest.util.Preconditions.checkNotNullOrEmpty;

import javax.annotation.Nonnull;

import org.fest.util.InternalApi;

/**
 * Stores the name of the inner class to access.
 *
 * <p>
 * <strong>Note:</strong> To improve code readability, we recommend invoking
 * {@link org.fest.reflect.core.Reflection#innerClass(String) Reflection.innerClass(String)} instead of this class'
 * constructor:
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
 *
 * @author Alex Ruiz
 * @since 1.1
 */
public final class InnerClassName {
  private final String name;

  /**
   * <p>
   * Creates a new {@link InnerClassName}.
   * </p>
   *
   * <p>
   * <strong>Note:</strong> To improve code readability, we recommend invoking
   * {@link org.fest.reflect.core.Reflection#innerClass(String) Reflection.innerClass(String)} instead of this class'
   * constructor.
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
   *
   * @param name the name of the static inner class to obtain.
   * @throws NullPointerException if the given name is {@code null}.
   * @throws IllegalArgumentException if the given name is empty.
   */
  @InternalApi
  public InnerClassName(@Nonnull String name) {
    this.name = checkNotNullOrEmpty(name);
  }

  /**
   * <p>
   * Specifies the declaring class of the static inner class to obtain.
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
   *
   * @param declaringClass the declaring class.
   * @return an object responsible for finding a reference to a static inner class.
   * @throws NullPointerException if the given declaring class is {@code null}.
   */
  public @Nonnull InnerClassFinder in(@Nonnull Class<?> declaringClass) {
    return new InnerClassFinder(declaringClass, checkNotNullOrEmpty(name));
  }
}
