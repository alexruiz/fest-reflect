/*
 * Created on Oct 31, 2006
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
 * Copyright @2006-2009 the original author or authors.
 */
package org.fest.reflect.constructor;

import static org.fest.reflect.util.Accessibles.makeAccessible;
import static org.fest.reflect.util.Accessibles.setAccessibleIgnoringExceptions;
import static org.fest.reflect.util.Throwables.targetOf;
import static org.fest.util.Strings.concat;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import org.fest.reflect.exception.ReflectionError;

/**
 * Understands the invocation of a constructor via Java Reflection.
 * @param <T> the class in which the constructor is declared.
 *          <p>
 *          The following is an example of proper usage of the classes in this package:
 * 
 * <pre>
 *   // Equivalent to call 'new Person()'
 *   Person p = {@link org.fest.reflect.core.Reflection#constructor() constructor}().{@link TargetType#in in}(Person.class).{@link #newInstance newInstance}();
 *
 *   // Equivalent to call 'new Person("Yoda")'
 *   Person p = {@link org.fest.reflect.core.Reflection#constructor() constructor}().{@link TargetType#withParameterTypes(Class...) withParameterTypes}(String.class).{@link ParameterTypes#in(Class) in}(Person.class).{@link #newInstance newInstance}("Yoda");
 * </pre>
 *          </p>
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class Invoker<T> {

  public static <T> Invoker<T> newInvoker(Class<T> target, Class<?>... parameterTypes) {
    Constructor<T> constructor = constructor(target, parameterTypes);
    return new Invoker<T>(constructor);
  }

  private static <T> Constructor<T> constructor(Class<T> target, Class<?>... parameterTypes) {
    try {
      return target.getDeclaredConstructor(parameterTypes);
    } catch (Exception e) {
      throw new ReflectionError(concat("Unable to find constructor in type ", target.getName(), " with parameter types ",
          Arrays.toString(parameterTypes)), e);
    }
  }

  private final Constructor<T> constructor;

  private Invoker(Constructor<T> constructor) {
    this.constructor = constructor;
  }

  /**
   * Creates a new instance of <code>T</code> by calling a constructor with the given arguments.
   * @param args the arguments to pass to the constructor (can be zero or more).
   * @return the created instance of <code>T</code>.
   * @throws ReflectionError if a new instance cannot be created.
   */
  public T newInstance(Object... args) {
    boolean accessible = constructor.isAccessible();
    try {
      makeAccessible(constructor);
      T newInstance = constructor.newInstance(args);
      return newInstance;
    } catch (Throwable t) {
      Throwable cause = targetOf(t);
      if (cause instanceof RuntimeException) throw (RuntimeException) cause;
      throw new ReflectionError("Unable to create a new object from the enclosed constructor", cause);
    } finally {
      setAccessibleIgnoringExceptions(constructor, accessible);
    }
  }

  /**
   * Returns the "real" constructor managed by this class.
   * @return the "real" constructor managed by this class.
   */
  public Constructor<T> info() {
    return constructor;
  }
}
