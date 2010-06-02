package org.fest.reflect.util;

import static org.fest.reflect.util.PropertiesUtils.isNestedProperty;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PropertiesUtils_isNestedProperty_Test {

  @Test
  public void should_pass_if_property_is_a_nested_property() {
    assertTrue(isNestedProperty("person.name"));
    assertTrue(isNestedProperty("adress.street.name"));
  }

  @Test
  public void should_fail_if_property_is_not_a_nested_property() {
    assertFalse(isNestedProperty("person"));
    assertFalse(isNestedProperty(".name"));
    assertFalse(isNestedProperty("person."));
    assertFalse(isNestedProperty("person.name."));
    assertFalse(isNestedProperty(".person.name"));
    assertFalse(isNestedProperty("."));
    assertFalse(isNestedProperty(""));
  }

  @Test(expected = NullPointerException.class)
  public void should_fail_if_property_is_null() {
    isNestedProperty(null);
  }
}
