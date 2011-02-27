/*
 * Created on Feb 26, 2011
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
package org.fest.reflect.innerclass;

import org.fest.reflect.exception.ReflectionError;

/**
 * Finds a static inner class by name.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public interface Finder {

  /**
   * Finds the static inner class.
   * @param declaringType the class declaring the static inner class to find.
   * @return the found inner class.
   * @throws NullPointerException if the given type is {@code null}.
   * @throws ReflectionError if a static inner class with the specified name cannot be found in the given type.
   */
  Class<?> in(Class<?> declaringType);
}
