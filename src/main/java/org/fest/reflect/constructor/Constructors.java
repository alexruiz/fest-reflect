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
package org.fest.reflect.constructor;

import static org.fest.reflect.util.Accessibles.*;
import static org.fest.reflect.util.Throwables.targetOf;
import static org.fest.util.Arrays.copyOf;
import static org.fest.util.ToString.toStringOf;

import java.lang.reflect.Constructor;

import org.fest.reflect.exception.ReflectionError;

/**
 * Implements a fluent interface for invoking constructors.
 * @param <T> the class in which the constructor is declared.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class Constructors<T> implements ParameterTypes<T>, Invoker<T> {

  private static final Class<?>[] NO_PARAMETERS = new Class<?>[0];

  /**
   * Sets the class declaring the constructor.
   * @param <T> the generic type of the given class.
   * @param target the class declaring the constructor.
   * @return the next object in the fluent interface.
   */
  public static <T> ParameterTypes<T> constructorIn(Class<T> target) {
    // TODO check for null
    return new Constructors<T>(target);
  }

  private final Class<T> target;
  private Class<?>[] parameterTypes;
  private Constructor<T> constructor;

  private Constructors(Class<T> target) {
    this.target = target;
  }

  /** {@inheritDoc} */
  public Invoker<T> withParameterTypes(Class<?>... types) {
    if (types == null) throw new NullPointerException("The array of parameter types should not be null");
    return updateParameterTypes(copyOf(types, types.length));
  }

  /** {@inheritDoc} */
  public Invoker<T> withNoParameters() {
    return updateParameterTypes(NO_PARAMETERS);
  }

  private Invoker<T> updateParameterTypes(Class<?>[] types) {
    parameterTypes = types;
    findConstructor();
    return this;
  }

  private void findConstructor() {
    try {
      constructor = target.getDeclaredConstructor(parameterTypes);
    } catch (Exception e) {
      String format = "Unable to find constructor in type %s with parameter types %s";
      String message = String.format(format, target.getName(), toStringOf(parameterTypes));
      throw new ReflectionError(message, e);
    }
  }

  /** {@inheritDoc} */
  public T newInstance(Object... args) {
    boolean accessible = constructor.isAccessible();
    try {
      makeAccessible(constructor);
      return constructor.newInstance(args);
    } catch (Throwable t) {
      Throwable cause = targetOf(t);
      if (cause instanceof RuntimeException) throw (RuntimeException)cause;
      throw new ReflectionError("Unable to create a new object from the enclosed constructor", cause);
    } finally {
      setAccessibleIgnoringExceptions(constructor, accessible);
    }
  }

  /** {@inheritDoc} */
  public Constructor<T> info() {
    return constructor;
  }
}
