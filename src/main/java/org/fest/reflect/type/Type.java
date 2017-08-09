/*
 * Created on Jan 23, 2009
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

import org.fest.reflect.exception.ReflectionError;
import org.fest.util.InternalApi;

import org.jetbrains.annotations.NotNull;

import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Preconditions.checkNotNullOrEmpty;

/**
 * Stores the name of the type to load.
 * <p/>
 * <strong>Note:</strong> To improve code readability, we recommend invoking
 * {@link org.fest.reflect.core.Reflection#type(String) Reflection.type(String)} instead of this class' constructor:
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
 *
 * @author Alex Ruiz
 * @since 1.1
 */
public final class Type {
  private final String name;

  /**
   * Creates a new {@link Type}.
   * <p/>
   * <strong>Note:</strong> To improve code readability, we recommend invoking
   * {@link org.fest.reflect.core.Reflection#type(String) Reflection.type(String)} instead of this constructor:
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
   *
   * @param name the name of the class to load.
   * @throws NullPointerException     if the given name is {@code null}.
   * @throws IllegalArgumentException if the given name is empty.
   */
  @InternalApi
  public Type(@NotNull String name) {
    this.name = checkNotNullOrEmpty(name);
  }

  /**
   * Loads the class with the name specified in this type, using this class' {@code ClassLoader}.
   * <p/>
   * Examples:
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
   *
   * @return the loaded class.
   * @throws ReflectionError wrapping any error that occurred during class loading.
   */
  public Class<?> load() {
    return new TypeLoader(checkNotNullOrEmpty(name), thisClassLoader()).load();
  }

  /**
   * Loads the class as the given super-type, using {@code this} class' {@code ClassLoader}.
   * <p/>
   * Examples:
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
   *
   * @param superType the given super-type.
   * @return the loaded class.
   * @throws NullPointerException if the given super-type is {@code null}.
   * @throws ReflectionError      wrapping any error that occurred during class loading.
   */
  public <T> Class<? extends T> loadAs(@NotNull Class<T> superType) {
    return new TypeLoader(checkNotNullOrEmpty(name), thisClassLoader()).loadAs(superType);
  }

  private @NotNull ClassLoader thisClassLoader() {
    return checkNotNull(getClass().getClassLoader());
  }

  /**
   * Specifies the {@code ClassLoader} to use when loading the class.
   * <p/>
   * Examples:
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
   *
   * @param classLoader the given {@code ClassLoader}.
   * @return an object responsible of loading a class with the given {@code ClassLoader}.
   * @throws NullPointerException if the given {@code ClassLoader} is {@code null}.
   */
  public TypeLoader withClassLoader(@NotNull ClassLoader classLoader) {
    return new TypeLoader(checkNotNullOrEmpty(name), classLoader);
  }
}
