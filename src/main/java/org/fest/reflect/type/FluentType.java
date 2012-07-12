/*
 * Created on Mar 3, 2011
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
 * Copyright @2011 the original author or authors.
 */
package org.fest.reflect.type;

import static org.fest.util.Strings.quote;

import org.fest.reflect.exception.ReflectionError;

/**
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class FluentType implements ClassLoaderRef, TypeLoader {

  private final String name;
  private ClassLoader classLoader;

  FluentType(String name) {
    if (name == null) throw new NullPointerException("The name of the class to load should not be null");
    if (name.length() == 0) throw new IllegalArgumentException("The name of the class to load should not be empty");
    this.name = name;
  }

  /** {@inheritDoc} */
  public TypeLoader withClassLoader(ClassLoader classLoader) {
    if (classLoader == null) throw new NullPointerException("The given ClassLoader should not be null");
    return updateClassLoader(classLoader);
  }

  /** {@inheritDoc} */
  public TypeLoader withDefaultClassLoader() {
    return updateClassLoader(getClass().getClassLoader());
  }

  private TypeLoader updateClassLoader(ClassLoader classLoader) {
    this.classLoader = classLoader;
    return this;
  }

  /** {@inheritDoc} */
  public Class<?> load() {
    try {
      return loadType();
    } catch (Throwable t) {
      throw new ReflectionError(unableToLoadClassMessage(null), t);
    }
  }

  /** {@inheritDoc} */
  public <T> Class<? extends T> loadAs(Class<T> type) {
    if (type == null) throw new NullPointerException("The type to load as should not be null");
    try {
      return loadType().asSubclass(type);
    } catch (Throwable t) {
      throw new ReflectionError(unableToLoadClassMessage(type), t);
    }
  }

  private Class<?> loadType() throws ClassNotFoundException {
    return classLoader.loadClass(name);
  }

  private String unableToLoadClassMessage(Class<?> asType) {
    StringBuilder msg = new StringBuilder();
    msg.append("Unable to load class ").append(quote(name));
    if (asType != null) msg.append(" as ").append(asType.getName());
    msg.append(" using class loader ").append(classLoader);
    return msg.toString();
  }
}
