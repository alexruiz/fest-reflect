/*
 * Created on Feb 26, 2011
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
package org.fest.reflect.field;

import org.fest.reflect.exception.ReflectionError;

/**
 * Holds the object containing the field to access.
 * @param <T> the type of the field to access.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public interface Target<T> {

  /**
   * Sets the object containing the field to access.
   * @param target the object containing the field to access.
   * @return the next object in the fluent interface.
   * @throws NullPointerException if the given target is {@code null}.
   * @throws ReflectionError if a matching field cannot be found on the given object.
   */
  Invoker<T> in(Object target);

  /**
   * Sets the type containing the static field to access.
   * @param target the type containing the static field to access.
   * @return the next object in the fluent interface.
   * @throws NullPointerException if the given target is {@code null}.
   * @throws ReflectionError if a matching static field cannot be found on the given type.
   */
  Invoker<T> in(Class<?> target);
}
