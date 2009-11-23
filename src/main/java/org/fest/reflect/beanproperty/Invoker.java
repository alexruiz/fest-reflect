/*
 * Created on Nov 23, 2009
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2009 the original author or authors.
 */
package org.fest.reflect.beanproperty;

import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

import java.beans.*;

import org.fest.reflect.exception.ReflectionError;
import org.fest.reflect.field.StaticFieldName;
import org.fest.reflect.field.StaticFieldType;
import org.fest.reflect.reference.TypeRef;

/**
 * Understands the use of instrospection to access a property from a JavaBean.
 * <p>
 * The following is an example of proper usage of this class:
 * <pre>
 *   // Retrieves the value of the property "name"
 *   String name = {@link org.fest.reflect.core.Reflection#property(String) property}("name").{@link PropertyName#ofType(Class) ofType}(String.class).{@link PropertyType#in(Object) in}(person).{@link Invoker#get() get}();
 *
 *   // Sets the value of the property "name" to "Yoda"
 *   {@link org.fest.reflect.core.Reflection#property(String) property}("name").{@link PropertyName#ofType(Class) ofType}(String.class).{@link PropertyType#in(Object) in}(person).{@link Invoker#set(Object) set}("Yoda");
 *
 *   // Retrieves the value of the static property "count"
 *   int count = {@link org.fest.reflect.core.Reflection#staticField(String) staticField}("count").{@link StaticFieldName#ofType(Class) ofType}(int.class).{@link StaticFieldType#in(Class) in}(Person.class).{@link Invoker#get() get}();
 *
 *   // Sets the value of the static property "count" to 3
 *   {@link org.fest.reflect.core.Reflection#staticField(String) property}("count").{@link StaticFieldName#ofType(Class) ofType}(int.class).{@link StaticFieldType#in(Class) in}(Person.class).{@link Invoker#set(Object) set}(3);
 * </pre>
 * </p>
 *
 * @param <T> the declared type for the property to access.
 *
 * @author Alex Ruiz
 *
 * @since 1.2
 */
public final class Invoker<T> {

  private final String propertyName;
  private final Object target;
  private final PropertyDescriptor descriptor;

  Invoker(String propertyName, Object target) {
    this.propertyName = propertyName;
    this.target = target;
    descriptor = descriptorForProperty();
  }

  private PropertyDescriptor descriptorForProperty() {
    BeanInfo beanInfo = null;
    Class<?> type = target.getClass();
    try {
      beanInfo = Introspector.getBeanInfo(type, Object.class);
    } catch (Exception e) {
      throw new ReflectionError(concat("Unable to get BeanInfo for type ", type.getName()), e);
    }
    for (PropertyDescriptor d : beanInfo.getPropertyDescriptors())
      if (propertyName.equals(d.getName())) return d;
    throw new ReflectionError(concat("Unable to find property ", quote(propertyName), " in " , type.getName()));
  }

  void verifyCorrectType(TypeRef<?> expectedType) {
    verifyCorrectType(expectedType.rawType());
  }

  void verifyCorrectType(Class<?> expectedType) {
    Class<?> actualType = descriptor.getPropertyType();
    if (!expectedType.isAssignableFrom(actualType)) throw incorrectPropertyType(actualType, expectedType);
  }

  private ReflectionError incorrectPropertyType(Class<?> actualType, Class<?> expectedType) {
    String typeName = target.getClass().getName();
    String msg = concat("The type of the property ", quote(propertyName), " in ", typeName, " should be <",
        expectedType.getName(), "> but was <", actualType.getName(), ">");
    throw new ReflectionError(msg);
  }

  /**
   * Sets a value in the property managed by this class.
   * @param value the value to set.
   * @throws ReflectionError if the given value cannot be set.
   */
  public void set(T value) {
    try {
      descriptor.getWriteMethod().invoke(target, value);
    } catch (Exception e) {
      throw new ReflectionError(concat("Unable to update the value in property ", quote(propertyName)), e);
    }
  }

  /**
   * Returns the value of the property managed by this class.
   * @return the value of the property managed by this class.
   * @throws ReflectionError if the value of the property cannot be retrieved.
   */
  @SuppressWarnings("unchecked") public T get() {
    try {
      return (T) descriptor.getReadMethod().invoke(target);
    } catch (Exception e) {
      throw new ReflectionError(concat("Unable to obtain the value in property " + quote(propertyName)), e);
    }
  }

  /**
   * Returns the "real" property managed by this class.
   * @return the "real" property managed by this class.
   */
  public PropertyDescriptor info() {
    return descriptor;
  }
}
