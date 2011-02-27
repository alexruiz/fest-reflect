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
package org.fest.reflect.method;

import java.lang.reflect.Method;

import org.fest.reflect.exception.ReflectionError;

/**
 * Invokes a method.
 * @param <T> the return type of the method to invoke.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public interface Invoker<T> {

  /**
   * Invokes the method.
   * @param args the arguments to pass.
   * @return the value returned by the invoked method.
   * @throws ReflectionError if the method could not be invoked.
   */
  T invoke(Object...args);

  /**
   * Returns the underlying <code>{@link Method}</code>.
   * @return the underlying method.
   */
  Method info();
}
