/*
 * Created on Nov 23, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.reflect.beanproperty;

import static org.fest.reflect.util.Types.castSafely;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Preconditions.checkNotNullOrEmpty;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.reflect.exception.ReflectionError;

/**
 * Accesses a property from a JavaBean via Bean Introspection.
 *
 * @param <T> the type for the property to access.
 * @author Alex Ruiz
 * @since 1.2
 */
public final class PropertyAccessor<T> {
  private final Class<T> propertyType;
  private final Object target;
  private final PropertyDescriptor descriptor;

  PropertyAccessor(@Nonnull String propertyName, @Nonnull Class<T> propertyType, @Nonnull Object target) {
    this.propertyType = checkNotNull(propertyType);
    this.target = checkNotNull(target);
    this.descriptor = findPropertyDescriptor(checkNotNullOrEmpty(propertyName));
  }

  private PropertyDescriptor findPropertyDescriptor(@Nonnull String propertyName) {
    BeanInfo beanInfo = null;
    Class<?> targetType = target.getClass();
    try {
      beanInfo = Introspector.getBeanInfo(targetType);
    } catch (Throwable t) {
      String format = "Failed to get BeanInfo for type %s";
      throw new ReflectionError(String.format(format, targetType.getName()), t);
    }
    PropertyDescriptor found = null;
    for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
      if (propertyName.equals(descriptor.getName())) {
        found = descriptor;
        break;
      }
    }
    if (found != null) {
      Class<?> actualType = found.getPropertyType();
      if (!propertyType.isAssignableFrom(actualType)) {
        String format = "Expecting type of property '%s' in %s to be <%s> but was <%s>";
        String msg =
            String.format(format, propertyName, targetType.getName(), propertyType.getName(), actualType.getName());
        throw new ReflectionError(msg);
      }
      return found;
    }
    String msg = String.format("Failed to find property '%s' in %s", propertyName, targetType.getName());
    throw new ReflectionError(msg);
  }

  /**
   * Sets a value of the JavaBeans property specified in this fluent interface.
   *
   * <p>
   * Examples demonstrating usage of the fluent interface:
   *
   * <pre>
   * // Retrieves the value of the property "name"
   * String name = {@link org.fest.reflect.core.Reflection#property(String) property}("name").{@link PropertyName#ofType(Class) ofType}(String.class).{@link PropertyType#in(Object) in}(person).{@link PropertyAccessor#get() get}();
   * 
   * // Sets the value of the property "name" to "Yoda"
   * {@link org.fest.reflect.core.Reflection#property(String) property}("name").{@link PropertyName#ofType(Class) ofType}(String.class).{@link PropertyType#in(Object) in}(person).{@link PropertyAccessor#set(Object) set}("Yoda");
   * 
   * // Retrieves the value of the property "powers"
   * List&lt;String&gt; powers = {@link org.fest.reflect.core.Reflection#property(String) property}("powers").{@link PropertyName#ofType(org.fest.reflect.reference.TypeRef) ofType}(new {@link org.fest.reflect.reference.TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link PropertyTypeRef#in(Object) in}(jedi).{@link PropertyAccessor#get() get}();
   *
   * // Sets the value of the property "powers"
   * List&lt;String&gt; powers = new ArrayList&lt;String&gt;();
   * powers.add("heal");
   * {@link org.fest.reflect.core.Reflection#property(String) property}("powers").{@link PropertyName#ofType(org.fest.reflect.reference.TypeRef) ofType}(new {@link org.fest.reflect.reference.TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link PropertyTypeRef#in(Object) in}(jedi).{@link PropertyAccessor#set(Object) set}(powers);
   * </pre>
   * </p>
   *
   * @param value the value to set.
   * @throws ReflectionError if the given value cannot be set.
   */
  public void set(@Nullable T value) {
    try {
      descriptor.getWriteMethod().invoke(target, value);
    } catch (Exception e) {
      String format = "Failed to set value %s in property '%s'";
      String msg = String.format(format, String.valueOf(value), descriptor.getName());
      throw new ReflectionError(msg, e);
    }
  }

  /**
   * Retrieves the value of the JavaBeans property in this fluent interface.
   *
   * <p>
   * Examples demonstrating usage of the fluent interface:
   *
   * <pre>
   * // Retrieves the value of the property "name"
   * String name = {@link org.fest.reflect.core.Reflection#property(String) property}("name").{@link PropertyName#ofType(Class) ofType}(String.class).{@link PropertyType#in(Object) in}(person).{@link PropertyAccessor#get() get}();
   * 
   * // Sets the value of the property "name" to "Yoda"
   * {@link org.fest.reflect.core.Reflection#property(String) property}("name").{@link PropertyName#ofType(Class) ofType}(String.class).{@link PropertyType#in(Object) in}(person).{@link PropertyAccessor#set(Object) set}("Yoda");
   * 
   * // Retrieves the value of the property "powers"
   * List&lt;String&gt; powers = {@link org.fest.reflect.core.Reflection#property(String) property}("powers").{@link PropertyName#ofType(org.fest.reflect.reference.TypeRef) ofType}(new {@link org.fest.reflect.reference.TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link PropertyTypeRef#in(Object) in}(jedi).{@link PropertyAccessor#get() get}();
   *
   * // Sets the value of the property "powers"
   * List&lt;String&gt; powers = new ArrayList&lt;String&gt;();
   * powers.add("heal");
   * {@link org.fest.reflect.core.Reflection#property(String) property}("powers").{@link PropertyName#ofType(org.fest.reflect.reference.TypeRef) ofType}(new {@link org.fest.reflect.reference.TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link PropertyTypeRef#in(Object) in}(jedi).{@link PropertyAccessor#set(Object) set}(powers);
   * </pre>
   * </p>
   *
   * @return the value of the JavaBeans property in this fluent interface.
   * @throws ReflectionError if the value of the property cannot be retrieved.
   */
  public @Nullable T get() {
    try {
      Object value = descriptor.getReadMethod().invoke(target);
      return castSafely(value, checkNotNull(propertyType));
    } catch (Throwable t) {
      String msg = String.format("Failed to get the value of property '%s'", descriptor.getName());
      throw new ReflectionError(msg, t);
    }
  }

  /**
   * @return the underlying JavaBeans property to invoke via Bean Introspection.
   */
  public @Nonnull PropertyDescriptor descriptor() {
    return checkNotNull(descriptor);
  }
}