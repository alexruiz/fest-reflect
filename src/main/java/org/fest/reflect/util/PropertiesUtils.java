package org.fest.reflect.util;

/**
 * 
 * Various utilities methods used in property introspection context.
 * 
 * @author Joel Costigliola
 * 
 */
public class PropertiesUtils {

  private static final String PROPERTY_SEPARATOR = ".";

  /**
   * Returns true if property is nested, false otherwise.
   * <p>
   * Examples :
   * 
   * <pre>
   * isNestedProperty("adress.street") : true
   * isNestedProperty("adress.street.name") : true
   * 
   * isNestedProperty("person") : false
   * isNestedProperty(".name") : false
   * isNestedProperty("person.") : false
   * isNestedProperty("person.name.") : false
   * isNestedProperty(".person.name") : false
   * isNestedProperty(".") : false
   * isNestedProperty("") : false
   * </pre>
   * 
   * @param property property name, <b>must not be null</b>
   * @return True if property is nested, false otherwise.
   * @throws NullPointerException if given property is null
   */
  public static boolean isNestedProperty(String property) {
    if (!property.contains(PROPERTY_SEPARATOR)) { return false; }
    // check that propertyName does not starts or ends with '.' to avoid "adress." and ".street" to be considered as
    // nested properties.
    if (property.startsWith(PROPERTY_SEPARATOR)) { return false; }
    if (property.endsWith(PROPERTY_SEPARATOR)) { return false; }
    return true;
  }

  /**
   * If nested property is <i>'adress.street.name'</i>, returns <i>'street.name'</i>.<br>
   * Returns an empty string if property is not a nested property.
   * 
   * @param nestedProperty nested property name, <b>must not be null</b>
   * @return The nested property without its first sub-property
   * @throws NullPointerException if given nestedProperty is null
   */
  public static String substractFirstSubProperty(String nestedProperty) {
    if (!isNestedProperty(nestedProperty)) { return ""; }
    return nestedProperty.substring(nestedProperty.indexOf(PROPERTY_SEPARATOR) + 1);
  }

  /**
   * If nested property is <i>'adress.street.name'</i>, returns <i>'adress'</i>.<br>
   * If property is a simple property, simply returns it.
   * 
   * @param nestedProperty nested property name, <b>must not be null</b>
   * @return The first sub-property of the given nested property name.
   * @throws NullPointerException if nestedProperty is null
   */
  public static String extractFirstSubProperty(String nestedProperty) {
    if (!isNestedProperty(nestedProperty)) { return nestedProperty; }
    return nestedProperty.substring(0, nestedProperty.indexOf(PROPERTY_SEPARATOR));
  }

}
