/*
 * Created on Jan 28, 2009
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

import static org.fest.util.Strings.quote;

/**
 * Understands loading a class dynamically using a specific <code>{@link ClassLoader}</code>.
 * 
 * @author Alex Ruiz
 * 
 * @since 1.1
 */
public final class TypeLoader {

  static TypeLoader newLoader(String name, ClassLoader classLoader) {
    if (classLoader == null) throw new NullPointerException("The given class loader should not be null");
    return new TypeLoader(name, classLoader);
  }

  private final String name;
  private final ClassLoader classLoader;

  private TypeLoader(String name, ClassLoader classLoader) {
    this.name = name;
    this.classLoader = classLoader;
  }

  /**
   * Loads the class with the name specified in this type, using this class' <code>ClassLoader</code>.
   * <p>
   * Example:
   * 
   * <pre>
   * Class&lt;?&gt; type = {@link org.fest.reflect.core.Reflection#type(String) type}("org.republic.Jedi").{@link Type#withClassLoader(ClassLoader) withClassLoader}(myClassLoader).{@link TypeLoader#load() load}();
   * </pre>
   * </p>
   * @return the loaded class.
   * @throws ReflectionError wrapping any error that occurred during class loading.
   */
  public Class<?> load() {
    try {
      return loadType();
    } catch (Exception e) {
      throw new ReflectionError(unableToLoadClassMessage(null), e);
    }
  }

  /**
   * Loads the class with the name specified in this type, as the given type, using this class' <code>ClassLoader</code>.
   * <p>
   * The following example shows how to use this method. Let's assume that we have the class <code>Jedi</code> that extends the
   * class <code>Person</code>:
   * 
   * <pre>
   * Class&lt;Person&gt; type = {@link org.fest.reflect.core.Reflection#type(String) type}("org.republic.Jedi").{@link Type#withClassLoader(ClassLoader) withClassLoader}(myClassLoader).{@link TypeLoader#loadAs(Class) loadAs}(Person.class);
   * </pre>
   * </p>
   * @param type the given type.
   * @param <T> the generic type of the type.
   * @return the loaded class.
   * @throws NullPointerException if the given type is <code>null</code>.
   * @throws ReflectionError wrapping any error that occurred during class loading.
   */
  public <T> Class<? extends T> loadAs(Class<T> type) {
    if (type == null) throw new NullPointerException("The given type should not be null");
    try {
      return loadType().asSubclass(type);
    } catch (Exception e) {
      throw new ReflectionError(unableToLoadClassMessage(type), e);
    }
  }

  private String unableToLoadClassMessage(Class<?> asType) {
    StringBuilder msg = new StringBuilder();
    msg.append("Unable to load class ").append(quote(name));
    if (asType != null) msg.append(" as ").append(asType.getName());
    msg.append(" using class loader ").append(classLoader);
    return msg.toString();
  }

  private Class<?> loadType() throws ClassNotFoundException {
    return classLoader.loadClass(name);
  }
}
