/*
 * Created on Jan 23, 2009
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
package org.fest.reflect.type;

import org.fest.reflect.exception.ReflectionError;

import static org.fest.reflect.type.TypeLoader.newLoader;
import static org.fest.util.Strings.isEmpty;

/**
 * Understands loading a class dynamically.
 * <p>
 * The following is an example of proper usage of this class:
 * 
 * <pre>
 *   // Loads the class 'org.republic.Jedi'
 *   Class&lt;?&gt; jediType = {@link org.fest.reflect.core.Reflection#type(String) type}("org.republic.Jedi").{@link Type#load() load}();
 *
 *   // Loads the class 'org.republic.Jedi' as 'org.republic.Person' (Jedi extends Person)
 *   Class&lt;Person&gt; jediType = {@link org.fest.reflect.core.Reflection#type(String) type}("org.republic.Jedi").{@link Type#loadAs(Class) loadAs}(Person.class);
 *
 *   // Loads the class 'org.republic.Jedi' using a custom class loader
 *   Class&lt;?&gt; jediType = {@link org.fest.reflect.core.Reflection#type(String) type}("org.republic.Jedi").{@link Type#withClassLoader(ClassLoader) withClassLoader}(myClassLoader).{@link org.fest.reflect.type.TypeLoader#load() load}();
 * </pre>
 * </p>
 * 
 * @author Alex Ruiz
 * 
 * @since 1.1
 */
public final class Type {

  /**
   * Creates a new <code>{@link Type}</code>: the starting point of the fluent interface for loading classes dynamically.
   * @param name the name of the class to load.
   * @return the created <code>Type</code>.
   * @throws NullPointerException if the given name is <code>null</code>.
   * @throws IllegalArgumentException if the given name is empty.
   */
  public static Type newType(String name) {
    if (name == null) throw new NullPointerException("The name of the class to load should not be null");
    if (isEmpty(name)) throw new IllegalArgumentException("The name of the class to load should not be empty");
    return new Type(name);
  }

  private final String name;

  private Type(String name) {
    this.name = name;
  }

  /**
   * Loads the class with the name specified in this type, using this class' <code>ClassLoader</code>.
   * @return the loaded class.
   * @throws ReflectionError wrapping any error that occurred during class loading.
   */
  public Class<?> load() {
    return newLoader(name, thisClassLoader()).load();
  }

  /**
   * Loads the class with the name specified in this type, as the given type, using this class' <code>ClassLoader</code>.
   * <p>
   * The following example shows how to use this method. Let's assume that we have the class <code>Jedi</code> that extends the
   * class <code>Person</code>:
   * 
   * <pre>
   * Class&lt;Person&gt; type = {@link org.fest.reflect.core.Reflection#type(String) type}("org.republic.Jedi").{@link Type#loadAs(Class) loadAs}(Person.class);
   * </pre>
   * </p>
   * @param type the given type.
   * @param <T> the generic type of the type.
   * @return the loaded class.
   * @throws NullPointerException if the given type is <code>null</code>.
   * @throws ReflectionError wrapping any error that occurred during class loading.
   */
  public <T> Class<? extends T> loadAs(Class<T> type) {
    return newLoader(name, thisClassLoader()).loadAs(type);
  }

  private ClassLoader thisClassLoader() {
    return getClass().getClassLoader();
  }

  /**
   * Specifies the <code>{@link ClassLoader}</code> to use to load the class.
   * <p>
   * Example:
   * 
   * <pre>
   * Class&lt;?&gt; type = {@link org.fest.reflect.core.Reflection#type(String) type}("org.republic.Jedi").{@link Type#withClassLoader(ClassLoader) withClassLoader}(myClassLoader).{@link TypeLoader#load() load}();
   * </pre>
   * </p>
   * @param classLoader the given <code>ClassLoader</code>.
   * @return an object responsible of loading a class with the given <code>ClassLoader</code>.
   * @throws NullPointerException if the given <code>ClassLoader</code> is <code>null</code>.
   */
  public TypeLoader withClassLoader(ClassLoader classLoader) {
    return newLoader(name, classLoader);
  }
}
