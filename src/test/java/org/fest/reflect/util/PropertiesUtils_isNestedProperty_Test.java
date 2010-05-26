package org.fest.reflect.util;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.util.PropertiesUtils.isNestedProperty;

import org.junit.Test;

public class PropertiesUtils_isNestedProperty_Test {

  @Test
  public void should_pass_if_property_is_a_nested_property() {
    assertThat(isNestedProperty("person.name")).isTrue();
    assertThat(isNestedProperty("adress.street.name")).isTrue();
  }

  @Test
  public void should_fail_if_property_is_not_a_nested_property() {
    assertThat(isNestedProperty("person")).isFalse();
    assertThat(isNestedProperty(".name")).isFalse();
    assertThat(isNestedProperty("person.")).isFalse();
    assertThat(isNestedProperty("person.name.")).isFalse();
    assertThat(isNestedProperty(".person.name")).isFalse();
    assertThat(isNestedProperty(".")).isFalse();
    assertThat(isNestedProperty("")).isFalse();
  }

  @Test(expected = NullPointerException.class)
  public void should_fail_if_property_is_null() {
    isNestedProperty(null);
  }
}
