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

import org.fest.reflect.exception.ReflectionError;

/**
 * Loads a type.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public interface TypeLoader {

  /**
   * Loads the type.
   * @return the loaded type.
   * @throws ReflectionError wrapping any error that occurred during class loading.
   */
  Class<?> load();

  /**
   * Loads the type, as the given sub-type.
   * @param <T> the generic type of the sub-type.
   * @param subType the sub-type.
   * @return the loaded type.
   * @throws NullPointerException if the given sub-type is {@code null}.
   * @throws ReflectionError wrapping any error that occurred during class loading.
   */
  <T> Class<? extends T> loadAs(Class<T> subType);
}
