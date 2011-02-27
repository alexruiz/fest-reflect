/*
 * Created on Feb 25, 2011
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
package org.fest.reflect.javabean;

import java.beans.PropertyDescriptor;

import org.fest.reflect.exception.ReflectionError;

/**
 * Invokes "getters" and "setters".
 * @param <T> the type of the property to access.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public interface Invoker<T> {

  /**
   * Invokes the "getter."
   * @return the value returned by the "getter."
   * @throws ReflectionError if the value of the property cannot be retrieved.
   */
  T get();

  /**
   * Invokes the "setter," passing the value to set.
   * @param value the value to set.
   * @throws ReflectionError if the given value cannot be set.
   */
  void set(T value);

  /**
   * Returns the underlying <code>{@link PropertyDescriptor}</code>.
   * @return the underlying property descriptor.
   */
  PropertyDescriptor info();
}
