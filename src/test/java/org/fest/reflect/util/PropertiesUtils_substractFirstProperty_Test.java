package org.fest.reflect.util;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import org.fest.reflect.util.PropertiesUtils;

public class PropertiesUtils_substractFirstProperty_Test {

  @Test
  public void should_pass_if_first_sub_property_is_removed_from_given_property() {
    assertThat(PropertiesUtils.substractFirstSubProperty("person.name")).isEqualTo("name");
    assertThat(PropertiesUtils.substractFirstSubProperty("adress.street.name")).isEqualTo("street.name");
  }

  @Test
  public void should_return_empty_string_if_not_a_nested_property() {
    assertThat(PropertiesUtils.substractFirstSubProperty("adress")).isEqualTo("");
  }

  @Test(expected = NullPointerException.class)
  public void should_fail_if_property_is_null() {
    PropertiesUtils.substractFirstSubProperty(null);
  }
}
