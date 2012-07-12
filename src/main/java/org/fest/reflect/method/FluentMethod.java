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
package org.fest.reflect.method;

import static org.fest.reflect.util.Accessibles.*;
import static org.fest.reflect.util.Casting.cast;
import static org.fest.reflect.util.Throwables.targetOf;
import static org.fest.util.Arrays.copyOf;
import static org.fest.util.ToString.toStringOf;

import java.lang.reflect.Method;

import org.fest.reflect.exception.ReflectionError;
import org.fest.reflect.reference.TypeRef;

/**
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class FluentMethod<T> implements Name<T>, ParameterTypes<T>, Target<T>, Invoker<T> {

  private static final Class<?>[] NO_PARAMETERS = new Class<?>[0];

  private final Class<T> returnType;
  private String name;
  private Object target;
  private Class<?>[] parameterTypes;
  private Method method;

  FluentMethod(Class<T> returnType) {
    if (returnType == null) throw new NullPointerException("The return type of the method to access should not be null");
    this.returnType = returnType;
  }

  FluentMethod(TypeRef<T> returnTypeRef) {
    if (returnTypeRef == null)
      throw new NullPointerException("The reference to the return type of the method to access should not be null");
    returnType = returnTypeRef.rawType();
  }

  /** {@inheritDoc} */
  public ParameterTypes<T> withName(String name) {
    if (name == null) throw new NullPointerException("The name of the method to access should not be null");
    if (name.length() == 0) throw new IllegalArgumentException("The name of the method to access should not be empty");
    this.name = name;
    return this;
  }

  /** {@inheritDoc} */
  public Target<T> withParameterTypes(Class<?>... types) {
    if (types == null) throw new NullPointerException("The array of parameter types should not be null");
    return updateParameterTypes(copyOf(types, types.length));
  }

  /** {@inheritDoc} */
  public Target<T> withNoParameters() {
    return updateParameterTypes(NO_PARAMETERS);
  }

  private Target<T> updateParameterTypes(Class<?>[] types) {
    parameterTypes = types;
    return this;
  }

  /** {@inheritDoc} */
  public Invoker<T> in(Object target) {
    if (target == null) throw new NullPointerException("The target object should not be null");
    return updateTarget(target);
  }

  /** {@inheritDoc} */
  public Invoker<T> in(Class<?> target) {
    if (target == null) throw new NullPointerException("The target type should not be null");
    return updateTarget(target);
  }

  private Invoker<T> updateTarget(Object target) {
    this.target = target;
    findMethodInTypeHierarchy(targetType());
    return this;
  }

  private Class<?> targetType() {
    if (target instanceof Class<?>) return (Class<?>) target;
    return target.getClass();
  }

  private void findMethodInTypeHierarchy(Class<?> targetType) {
    Class<?> current = targetType;
    while (current != null) {
      method = findMethodIn(current);
      if (method != null) return;
      current = current.getSuperclass();
    }
    throw cannotFindMethod(targetType);
  }

  private Method findMethodIn(Class<?> targetType) {
    try {
      return targetType.getDeclaredMethod(name, parameterTypes);
    } catch (SecurityException e) {
      return null;
    } catch (NoSuchMethodException e) {
      return null;
    }
  }

  private ReflectionError cannotFindMethod(Class<?> targetType) {
    String format = "Unable to find method '%s' in %s with parameter type(s) %s";
    String message = String.format(format, name, targetType.getName(), toStringOf(parameterTypes));
    return new ReflectionError(message);
  }

  /** {@inheritDoc} */
  public T invoke(Object... args) {
    boolean accessible = method.isAccessible();
    try {
      makeAccessible(method);
      return cast(method.invoke(target, args), returnType);
    } catch (Throwable t) {
      Throwable cause = targetOf(t);
      if (cause instanceof RuntimeException) throw (RuntimeException) cause;
      throw cannotInvokeMethod(cause, args);
    } finally {
      setAccessibleIgnoringExceptions(method, accessible);
    }
  }

  private ReflectionError cannotInvokeMethod(Throwable cause, Object... args) {
    String message = String.format("Unable to invoke method '%s' with arguments %s", name, toStringOf(args));
    throw new ReflectionError(message, cause);
  }

  /** {@inheritDoc} */
  public Method info() {
    return method;
  }
}
