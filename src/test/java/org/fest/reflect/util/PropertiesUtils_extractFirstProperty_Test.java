package org.fest.reflect.util;

import static org.fest.reflect.util.PropertiesUtils.extractFirstSubProperty;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PropertiesUtils_extractFirstProperty_Test {

  @Test
  public void should_pass_if_first_sub_property_is_removed_from_given_property() {
    assertEquals("person", extractFirstSubProperty("person.name"));
    assertEquals("adress", extractFirstSubProperty("adress.street.name"));
  }

  @Test
  public void should_return_given_property_if_not_a_nested_property() {
    assertEquals("adress", extractFirstSubProperty("adress"));
    assertEquals("é'&(&-&", extractFirstSubProperty("é'&(&-&"));
  }

  @Test(expected = NullPointerException.class)
  public void should_fail_if_property_is_null() {
    extractFirstSubProperty(null);
  }
}
