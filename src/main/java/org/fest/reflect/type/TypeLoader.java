/*
 * Created on Jan 28, 2009
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
package org.fest.reflect.type;

import static org.fest.util.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import org.fest.reflect.exception.ReflectionError;

/**
 * Loads a class dynamically using a specific {@code ClassLoader}.
 *
 * <p>
 * Examples demonstrating usage of the fluent interface:
 *
 * <pre>
 * // Loads the class 'org.republic.Jedi'
 * Class&lt;?&gt; jediType = {@link org.fest.reflect.core.Reflection#type(String) type}("org.republic.Jedi").{@link Type#load() load}();
 *
 * // Loads the class 'org.republic.Jedi' as 'org.republic.Person' (Jedi extends Person)
 * Class&lt;Person&gt; jediType = {@link org.fest.reflect.core.Reflection#type(String) type}("org.republic.Jedi").{@link Type#loadAs(Class) loadAs}(Person.class);
 *
 * // Loads the class 'org.republic.Jedi' using a custom class loader
 * Class&lt;?&gt; jediType = {@link org.fest.reflect.core.Reflection#type(String) type}("org.republic.Jedi").{@link Type#withClassLoader(ClassLoader) withClassLoader}(myClassLoader).{@link org.fest.reflect.type.TypeLoader#load() load}();
 * </pre>
 * </p>
 *
 * @author Alex Ruiz
 * @since 1.1
 */
public final class TypeLoader {
  private final String name;
  private final ClassLoader classLoader;

  TypeLoader(@Nonnull String name, @Nonnull ClassLoader classLoader) {
    this.name = name;
    this.classLoader = checkNotNull(classLoader);
  }

  /**
   * <p>
   * Loads the class with the name specified in this type, using {@code this} class' {@code ClassLoader}.
   * </p>
   *
   * <p>
   * Examples demonstrating usage of the fluent interface:
   *
   * <pre>
   * // import static  {@link org.fest.reflect.core.Reflection#type(String) org.fest.reflect.core.Reflection.type};
   * 
   * // Loads the class 'org.republic.Jedi'
   * Class&lt;?&gt; jediType = {@link org.fest.reflect.core.Reflection#type(String) type}("org.republic.Jedi").{@link org.fest.reflect.type.Type#load() load}();
   *
   * // Loads the class 'org.republic.Jedi' as 'org.republic.Person' (Jedi extends Person)
   * Class&lt;Person&gt; jediType = {@link org.fest.reflect.core.Reflection#type(String) type}("org.republic.Jedi").{@link org.fest.reflect.type.Type#loadAs(Class) loadAs}(Person.class);
   *
   * // Loads the class 'org.republic.Jedi' using a custom class loader
   * Class&lt;?&gt; jediType = {@link org.fest.reflect.core.Reflection#type(String) type}("org.republic.Jedi").{@link org.fest.reflect.type.Type#withClassLoader(ClassLoader) withClassLoader}(myClassLoader).{@link org.fest.reflect.type.TypeLoader#load() load}();
   * </pre>
   * </p>
   * 
   * @return the loaded class.
   * @throws ReflectionError wrapping any error that occurred during class loading.
   */
  public Class<?> load() {
    try {
      return loadType();
    } catch (Throwable t) {
      String format = "Unable to load class '%s' using ClassLoader %s";
      throw new ReflectionError(String.format(format, name, classLoader), t);
    }
  }

  /**
   * <p>
   * Loads the class as the given super-type.
   * </p>
   *
   * <p>
   * Examples demonstrating usage of the fluent interface:
   *
   * <pre>
   * // import static  {@link org.fest.reflect.core.Reflection#type(String) org.fest.reflect.core.Reflection.type};
   * 
   * // Loads the class 'org.republic.Jedi'
   * Class&lt;?&gt; jediType = {@link org.fest.reflect.core.Reflection#type(String) type}("org.republic.Jedi").{@link org.fest.reflect.type.Type#load() load}();
   *
   * // Loads the class 'org.republic.Jedi' as 'org.republic.Person' (Jedi extends Person)
   * Class&lt;Person&gt; jediType = {@link org.fest.reflect.core.Reflection#type(String) type}("org.republic.Jedi").{@link org.fest.reflect.type.Type#loadAs(Class) loadAs}(Person.class);
   *
   * // Loads the class 'org.republic.Jedi' using a custom class loader
   * Class&lt;?&gt; jediType = {@link org.fest.reflect.core.Reflection#type(String) type}("org.republic.Jedi").{@link org.fest.reflect.type.Type#withClassLoader(ClassLoader) withClassLoader}(myClassLoader).{@link org.fest.reflect.type.TypeLoader#load() load}();
   * </pre>
   * </p>
   *
   * @param superType the given super-type.
   * @return the loaded class.
   * @throws NullPointerException if the given super-type is {@code null}.
   * @throws ReflectionError wrapping any error that occurred during class loading.
   */
  public @Nonnull <T> Class<? extends T> loadAs(@Nonnull Class<T> superType) {
    checkNotNull(superType);
    try {
      return checkNotNull(loadType().asSubclass(superType));
    } catch (Throwable t) {
      String format = "Unable to load class '%s' as %s using ClassLoader %s";
      throw new ReflectionError(String.format(format, name, superType.getName(), classLoader), t);
    }
  }

  private @Nonnull Class<?> loadType() throws ClassNotFoundException {
    return checkNotNull(classLoader.loadClass(name));
  }
}
