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

import static org.fest.util.Strings.concat;

import org.fest.reflect.exception.ReflectionError;

/**
 * Understands how to obtain a reference to a static inner class.
 * 
 * @author Alex Ruiz
 * 
 * @since 1.1
 */
public class Invoker {

  static Invoker newInvoker(Class<?> declaringClass, String innerClassName) {
    if (declaringClass == null) throw new NullPointerException("The declaring class should not be null");
    return new Invoker(declaringClass, innerClassName);
  }

  private final Class<?> declaringClass;
  private final String innerClassName;

  private Invoker(Class<?> declaringClass, String innerClassName) {
    this.declaringClass = declaringClass;
    this.innerClassName = innerClassName;
  }

  /**
   * Returns a reference to the static inner class with the specified name in the specified declaring class.
   * @return a reference to the static inner class with the specified name in the specified declaring class.
   * @throws ReflectionError if the static inner class does not exist (since 1.2).
   */
  public Class<?> get() {
    String namespace = declaringClass.getName();
    for (Class<?> innerClass : declaringClass.getDeclaredClasses())
      if (innerClass.getName().equals(expectedInnerClassName(namespace))) return innerClass;
    throw new ReflectionError(concat("The static inner class <", innerClassName, "> cannot be found in ",
        declaringClass.getName()));
  }

  private String expectedInnerClassName(String namespace) {
    return concat(namespace, "$", innerClassName);
  }
}
