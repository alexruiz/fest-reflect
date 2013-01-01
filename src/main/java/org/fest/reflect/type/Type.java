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

import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Preconditions.checkNotNullOrEmpty;

import javax.annotation.Nonnull;

import org.fest.reflect.exception.ReflectionError;
import org.fest.util.InternalApi;

/**
 * Loads classes dynamically.
 *
 * <p>
 * <strong>Note:</strong> Do <em>not</em> instantiate this class directly. Instead, invoke
 * {@link org.fest.reflect.core.Reflection#type(String)}.
 * </p>
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
public final class Type {
  private final String name;

  /**
   * Starting point of the fluent interface for loading classes dynamically.
   * 
   * <p>
   * <strong>Note:</strong> Do <em>not</em> invoke this constructor directly. Instead, invoke
   * {@link org.fest.reflect.core.Reflection#type(String)}.
   * 
   * @param name the name of the class to load.
   * @throws NullPointerException if the given name is {@code null}.
   * @throws IllegalArgumentException if the given name is empty.
   */
  @InternalApi
  public Type(@Nonnull String name) {
    this.name = checkNotNullOrEmpty(name);
  }

  /**
   * Loads the class with the name specified in this type, using this class' {@code ClassLoader}.
   * 
   * @return the loaded class.
   * @throws ReflectionError wrapping any error that occurred during class loading.
   */
  public Class<?> load() {
    return new TypeLoader(checkNotNullOrEmpty(name), thisClassLoader()).load();
  }

  /**
   * Loads the class with the name specified in this type, as the given type, using this class' {@code ClassLoader}.
   * <p>
   * The following example shows how to use this method. Let's assume that we have the class {{@code Jedi} that extends
   * the class {@code Person}:
   * 
   * <pre>
   * Class&lt;Person&gt; type = {@link org.fest.reflect.core.Reflection#type(String) type}("org.republic.Jedi").{@link Type#loadAs(Class) loadAs}(Person.class);
   * </pre>
   * 
   * </p>
   * 
   * @param type the given type.
   * @return the loaded class.
   * @throws NullPointerException if the given type is {@code null}.
   * @throws ReflectionError wrapping any error that occurred during class loading.
   */
  public <T> Class<? extends T> loadAs(@Nonnull Class<T> type) {
    return new TypeLoader(checkNotNullOrEmpty(name), thisClassLoader()).loadAs(type);
  }

  private @Nonnull ClassLoader thisClassLoader() {
    return checkNotNull(getClass().getClassLoader());
  }

  /**
   * Specifies the {@link ClassLoader} to use to load the class.
   * <p>
   * Example:
   * 
   * <pre>
   * Class&lt;?&gt; type = {@link org.fest.reflect.core.Reflection#type(String) type}("org.republic.Jedi").{@link Type#withClassLoader(ClassLoader) withClassLoader}(myClassLoader).{@link TypeLoader#load() load}();
   * </pre>
   * 
   * </p>
   * 
   * @param classLoader the given {@code ClassLoader}.
   * @return an object responsible of loading a class with the given {@code ClassLoader}.
   * @throws NullPointerException if the given {@code ClassLoader} is {@code null}.
   */
  public TypeLoader withClassLoader(@Nonnull ClassLoader classLoader) {
    return new TypeLoader(checkNotNullOrEmpty(name), classLoader);
  }
}
