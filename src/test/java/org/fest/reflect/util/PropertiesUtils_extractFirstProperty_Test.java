package org.fest.reflect.util;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import org.fest.reflect.util.PropertiesUtils;

public class PropertiesUtils_extractFirstProperty_Test {

  @Test
  public void should_pass_if_first_sub_property_is_removed_from_given_property() {
    assertThat(PropertiesUtils.extractFirstSubProperty("person.name")).isEqualTo("person");
    assertThat(PropertiesUtils.extractFirstSubProperty("adress.street.name")).isEqualTo("adress");
  }

  @Test
  public void should_return_given_property_if_not_a_nested_property() {
    assertThat(PropertiesUtils.extractFirstSubProperty("adress")).isEqualTo("adress");
    assertThat(PropertiesUtils.extractFirstSubProperty("é'&(&-&")).isEqualTo("é'&(&-&");
  }

  @Test(expected = NullPointerException.class)
  public void should_fail_if_property_is_null() {
    PropertiesUtils.extractFirstSubProperty(null);
  }
}
