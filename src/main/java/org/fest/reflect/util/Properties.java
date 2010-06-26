/*
 * Created on May 26, 2010
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
 * Copyright @2010 the original author or authors.
 */
package org.fest.reflect.util;

/**
 * Various utilities methods used in property introspection context.
 *
 * @author Joel Costigliola
 *
 * @since 1.2.1
 */
public class Properties {

  private static final String SEPARATOR = ".";

  /**
   * Returns <code>true</code> if property is nested, <code>false</code> otherwise.
   * <p>
   * Examples:
   * <pre>
   * isNestedProperty("address.street"); // true
   * isNestedProperty("address.street.name"); // true
   *
   * isNestedProperty("person"); // false
   * isNestedProperty(".name"); // false
   * isNestedProperty("person."); // false
   * isNestedProperty("person.name."); // false
   * isNestedProperty(".person.name"); // false
   * isNestedProperty("."); // false
   * isNestedProperty(""); // false
   * </pre>
   * @param propertyName the given property name.
   * @return <code>true</code> if property is nested, <code>false</code> otherwise.
   * @throws NullPointerException if given property name is <code>null</code>.
   */
  public static boolean isNestedProperty(String propertyName) {
    if (propertyName == null) throw new NullPointerException("The property name to verify should not be null");
    return propertyName.contains(SEPARATOR) && !propertyName.startsWith(SEPARATOR) && !propertyName.endsWith(SEPARATOR);
  }

  /**
   * Removes the first property from the given property name only if the given property name belongs to a nested
   * property. For example, given the nested property "address.street.name", this method will return "street.name". This
   * method returns an empty {@code String} if the given property name does not belong to a nested property.
   * @param propertyName the given property name.
   * @return the given property name without its first property, if the property name belongs to a nested property;
   * otherwise, it will return an empty {@code String}.
   * @throws NullPointerException if given property name is <code>null</code>.
   */
  public static String removeFirstPropertyIfNested(String propertyName) {
    if (!isNestedProperty(propertyName)) return "";
    return propertyName.substring(propertyName.indexOf(SEPARATOR) + 1);
  }

  /**
   * Returns the first property from the given property name only if the given property name belongs to a nested
   * property. For example, given the nested property "address.street.name", this method will return "address". This
   * method returns the given property name unchanged if it does not belong to a nested property.
   * @param propertyName the given property name.
   * @return the first property from the given property name, if the property name belongs to a nested property;
   * otherwise, it will return the given property name unchanged.
   * @throws NullPointerException if given property name is <code>null</code>.
   */
  public static String firstPropertyIfNested(String propertyName) {
    if (!isNestedProperty(propertyName)) return propertyName;
    return propertyName.substring(0, propertyName.indexOf(SEPARATOR));
  }
}
