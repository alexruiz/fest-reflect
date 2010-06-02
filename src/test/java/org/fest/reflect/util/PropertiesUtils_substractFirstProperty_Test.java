package org.fest.reflect.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PropertiesUtils_substractFirstProperty_Test {

  @Test
  public void should_pass_if_first_sub_property_is_removed_from_given_property() {
    assertEquals("name", PropertiesUtils.substractFirstSubProperty("person.name"));
    assertEquals("street.name", PropertiesUtils.substractFirstSubProperty("adress.street.name"));
  }

  @Test
  public void should_return_empty_string_if_not_a_nested_property() {
    assertEquals("", PropertiesUtils.substractFirstSubProperty("adress"));
  }

  @Test(expected = NullPointerException.class)
  public void should_fail_if_property_is_null() {
    PropertiesUtils.substractFirstSubProperty(null);
  }
}
