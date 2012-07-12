/*
 * Created on Feb 25, 2011
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
package org.fest.reflect.constructor;

import java.lang.reflect.Constructor;

import org.fest.reflect.exception.ReflectionError;

/**
 * Invokes a constructor.
 * @param <T> the class in which the constructor is declared.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public interface Invoker<T> {

  /**
   * Creates a new instance of {code T} by calling a constructor with the given arguments.
   * @param args the arguments to pass to the constructor (can be zero or more).
   * @return the created instance of {code T}.
   * @throws ReflectionError if a new instance cannot be created.
   */
  T newInstance(Object... args);

  /**
   * Returns the underlying <code>{@link Constructor}</code>.
   * @return the underlying constructor.
   */
  Constructor<T> info();
}
