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
package org.fest.reflect.field;

import static org.fest.reflect.util.Accessibles.*;
import static org.fest.util.Strings.*;

import java.lang.reflect.Field;

import org.fest.reflect.exception.ReflectionError;
import org.fest.reflect.reference.TypeRef;

/**
 * Implements a fluent interface for accessing fields.
 * @param <T> the type of the field to access.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class Fields<T> implements Name<T>, Target<T>, Invoker<T> {

  /**
   * Sets the type of the field to access.
   * @param <T> the generic type of the field to access.
   * @param type the type of the field to access.
   * @return the next object in the fluent interface.
   * @throws NullPointerException if the given type is {@code null}.
   */
  public static <T> Name<T> fieldOfType(Class<T> type) {
    if (type == null) throw new NullPointerException("The type of the field to access should not be null");
    return new Fields<T>(type);
  }

  /**
   * Sets the type of the field to access.
   * @param <T> the generic type of the field to access.
   * @param typeRef a reference to the type of the property to access. Used to overcome type erasure in generics.
   * @return the next object in the fluent interface.
   * @throws NullPointerException if the given type reference is {@code null}.
   */
  public static <T> Name<T> fieldOfType(TypeRef<T> typeRef) {
    if (typeRef == null)
      throw new NullPointerException("The reference to the type of the field to access should not be null");
    return new Fields<T>(typeRef.rawType());
  }

  private final Class<T> type;
  private String name;
  private Object target;
  private Field field;

  private Fields(Class<T> type) {
    this.type = type;
  }

  /** {@inheritDoc} */
  public Target<T> withName(String name) {
    if (name == null) throw new NullPointerException("The name of the field to access should not be null");
    if (name.length() == 0)
      throw new IllegalArgumentException("The name of the field to access should not be empty");
    this.name = name;
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
    findField();
    return this;
  }

  private void findField() {
    findFieldInTypeHierarchy(targetType());
    checkRightType();
  }

  private Class<?> targetType() {
    if (target instanceof Class<?>) return (Class<?>)target;
    return target.getClass();
  }

  private void findFieldInTypeHierarchy(Class<?> targetType) {
    Class<?> current = targetType;
    while (current != null) {
      field = findFieldIn(current);
      if (field != null) return;
      current = current.getSuperclass();
    }
    throw cannotFindField(targetType);
  }

  private Field findFieldIn(Class<?> declaringType) {
    try {
      return declaringType.getDeclaredField(name);
    } catch (NoSuchFieldException e) {
      return null;
    }
  }

  private ReflectionError cannotFindField(Class<?> targetType) {
    String message = String.format("Unable to find field '%s' in %s", name, targetType.getName());
    return new ReflectionError(message);
  }

  private void checkRightType() {
    boolean isAccessible = field.isAccessible();
    try {
      makeAccessible(field);
      Class<?> actualType = field.getType();
      if (!type.isAssignableFrom(actualType)) throw wrongType(actualType);
    } finally {
      setAccessibleIgnoringExceptions(field, isAccessible);
    }
  }

  private ReflectionError wrongType(Class<?> actual) {
    String format = "The type of the field '%s' in %s should be <%s> but was <%s>";
    String message = String.format(format, name, target.getClass().getName(), type.getName(), actual.getName());
    throw new ReflectionError(message);
  }

  /** {@inheritDoc} */
  public T get() {
    boolean accessible = field.isAccessible();
    try {
      setAccessible(field, true);
      return type.cast(field.get(target));
    } catch (Exception e) {
      throw new ReflectionError(String.format("Unable to obtain the value in field '%s'" + field.getName()), e);
    } finally {
      setAccessibleIgnoringExceptions(field, accessible);
    }
  }

  /** {@inheritDoc} */
  public void set(T value) {
    boolean accessible = field.isAccessible();
    try {
      setAccessible(field, true);
      field.set(target, value);
    } catch (Exception e) {
      throw new ReflectionError(concat("Unable to update the value in field ", quote(field.getName())), e);
    } finally {
      setAccessibleIgnoringExceptions(field, accessible);
    }
  }

  /** {@inheritDoc} */
  public Field info() {
    return field;
  }
}
