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

import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Preconditions.checkNotNullOrEmpty;
import static org.fest.util.Strings.concat;

import javax.annotation.Nonnull;

import org.fest.reflect.exception.ReflectionError;

/**
 * Obtains a reference to a static inner class.
 *
 * <p>
 * <strong>Note:</strong> Do <em>not</em> instantiate this class directly. Instead, invoke
 * {@link org.fest.reflect.core.Reflection#innerClass(String)}.
 * </p>
 *
 * <p>
 * Let's assume we have the class {@code Jedi}, which contains two static inner classes: {@code Master} and
 * {@code Padawan}.
 *
 * <pre>
 * public class Jedi {
 *   public static class Master {
 *   }
 * 
 *   public static class Padawan {
 *   }
 * }
 * </pre>
 * 
 * The following example shows how to get a reference to the inner class {@code Master}:
 * 
 * <pre>
 * Class&lt;?&gt; masterClass = {@link org.fest.reflect.core.Reflection#innerClass(String) staticInnerClass}("Master").{@link org.fest.reflect.innerclass.InnerClassName#in(Class) in}(Jedi.class).{@link org.fest.reflect.innerclass.InnerClassFinder#get() get}();
 * </pre>
 * </p>
 *
 * @author Alex Ruiz
 * @since 1.1
 */
public class InnerClassFinder {
  private final Class<?> declaringClass;
  private final String innerClassName;

  InnerClassFinder(@Nonnull Class<?> declaringClass, @Nonnull String innerClassName) {
    this.declaringClass = checkNotNull(declaringClass);
    this.innerClassName = checkNotNullOrEmpty(innerClassName);
  }

  /**
   * @return a reference to the static inner class with the specified name in the specified declaring class.
   * @throws ReflectionError if the static inner class does not exist (since 1.2).
   */
  public @Nonnull Class<?> get() {
    String namespace = declaringClass.getName();
    String expectedInnerClassName = expectedInnerClassName(namespace);
    for (Class<?> innerClass : declaringClass.getDeclaredClasses()) {
      if (innerClass.getName().equals(expectedInnerClassName)) {
        return innerClass;
      }
    }
    String format = "Failed to find static inner class %s in %s";
    throw new ReflectionError(String.format(format, innerClassName, declaringClass.getName()));
  }

  private String expectedInnerClassName(String namespace) {
    return concat(namespace, "$", innerClassName);
  }
}
