/*
 * Created on Oct 31, 2006
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
 * Copyright @2006-2013 the original author or authors.
 */
package org.fest.reflect.constructor;

import static org.fest.reflect.util.Accessibles.makeAccessible;
import static org.fest.reflect.util.Accessibles.setAccessibleIgnoringExceptions;
import static org.fest.reflect.util.Throwables.targetOf;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.ToString.toStringOf;

import java.lang.reflect.Constructor;

import javax.annotation.Nonnull;

import org.fest.reflect.exception.ReflectionError;

/**
 * Invokes a constructor via
 * <a href="http://docs.oracle.com/javase/tutorial/reflect/index.html" target="_blank">Java Reflection</a>.
 *
 * @param <T> the type in which the constructor is declared.
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class ConstructorInvoker<T> {
  private final Constructor<T> constructor;

  ConstructorInvoker(@Nonnull Class<T> target, @Nonnull Class<?>... parameterTypes) {
    checkNotNull(target);
    checkNotNull(parameterTypes);
    try {
      this.constructor = target.getDeclaredConstructor(parameterTypes);
    } catch (Throwable t) {
      String format = "Failed to find constructor in type %s with parameter types %s";
      String msg = String.format(format, target.getName(), toStringOf(parameterTypes));
      throw new ReflectionError(msg);
    }
  }

  /**
   * <p>
   * Invokes the constructor of the specified type with the given arguments.
   * </p>
   *
   * <p>
   * Examples demonstrating usage of the fluent interface:
   * 
   * <pre>
   * // import static {@link org.fest.reflect.core.Reflection#constructor() org.fest.reflect.core.Reflection.constructor};
   *
   * // Equivalent to 'Person p = new Person()'
   * Person p = {@link org.fest.reflect.core.Reflection#constructor() constructor}().{@link org.fest.reflect.constructor.TargetType#in in}(Person.class).{@link org.fest.reflect.constructor.ConstructorInvoker#newInstance newInstance}();
   * 
   * // Equivalent to 'Person p = new Person("Yoda")'
   * Person p = {@link org.fest.reflect.core.Reflection#constructor() constructor}().{@link org.fest.reflect.constructor.TargetType#withParameterTypes(Class...) withParameterTypes}(String.class).{@link org.fest.reflect.constructor.ParameterTypes#in(Class) in}(Person.class).{@link org.fest.reflect.constructor.ConstructorInvoker#newInstance newInstance}("Yoda");
   * </pre>
   * </p>
   * 
   * @param args the arguments to pass to the constructor (can be zero or more).
   * @return the created instance of {@code T}.
   * @throws ReflectionError if a new instance cannot be created.
   */
  public T newInstance(@Nonnull Object... args) {
    Constructor<T> c = constructor;
    boolean accessible = constructor.isAccessible();
    try {
      makeAccessible(c);
      T newInstance = c.newInstance(args);
      return newInstance;
    } catch (Throwable t) {
      Throwable cause = targetOf(t);
      if (cause instanceof RuntimeException) {
        throw (RuntimeException) cause;
      }
      throw new ReflectionError("Unable to create a new object from the enclosed constructor", cause);
    } finally {
      setAccessibleIgnoringExceptions(c, accessible);
    }
  }

  /**
   * @return the underlying constructor to invoke.
   */
  public @Nonnull Constructor<T> constructor() {
    return constructor;
  }
}
