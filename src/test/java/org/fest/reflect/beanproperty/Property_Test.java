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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.util.ExpectedFailures.*;
import static org.fest.util.Collections.list;

import java.beans.PropertyDescriptor;
import java.util.List;

import org.fest.reflect.Jedi;
import org.fest.reflect.Person;
import org.fest.reflect.reference.TypeRef;
import org.fest.test.CodeToTest;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the fluent interface for accessing properties.
 *
 * @author Alex Ruiz
 */
public class Property_Test {

  private Person person;

  @Before
  public void setUp() {
    person = new Person("Luke");
  }

  @Test
  public void should_throw_error_if_property_name_is_null() {
    expectNullPointerException("The name of the property to access should not be null").on(new CodeToTest() {
      public void run() {
        PropertyName.startPropertyAccess(null);
      }
    });
  }

  @Test
  public void should_throw_error_if_property_name_is_empty() {
    expectIllegalArgumentException("The name of the property to access should not be empty").on(new CodeToTest() {
      public void run() {
        PropertyName.startPropertyAccess("");
      }
    });
  }

  @Test
  public void should_throw_error_if_property_type_is_null() {
    expectNullPointerException("The type of the property to access should not be null").on(new CodeToTest() {
      public void run() {
        PropertyName.startPropertyAccess("name").ofType((Class<?>)null);
      }
    });
  }

  @Test
  public void should_throw_error_if_target_is_null() {
    expectNullPointerException("Target should not be null").on(new CodeToTest() {
      public void run() {
        PropertyName.startPropertyAccess("name").ofType(String.class).in(null);
      }
    });
  }

  @Test
  public void should_get_property_value() {
    String personName = PropertyName.startPropertyAccess("name").ofType(String.class).in(person).get();
    assertThat(personName).isEqualTo("Luke");
  }

  @Test
  public void should_set_property_value() {
    PropertyName.startPropertyAccess("name").ofType(String.class).in(person).set("Leia");
    assertThat(person.getName()).isEqualTo("Leia");
  }

  @Test
  public void should_return_real_property() {
    PropertyDescriptor property = PropertyName.startPropertyAccess("name").ofType(String.class).in(person).info();
    assertThat(property).isNotNull();
    assertThat(property.getName()).isEqualTo("name");
    assertThat(property.getPropertyType()).isEqualTo(String.class);
  }

  @Test
  public void should_throw_error_if_wrong_property_type_was_specified() {
    String message =
      "The type of the property 'name' in org.fest.reflect.Person should be <java.lang.Integer> but was <java.lang.String>";
    expectReflectionError(message).on(new CodeToTest() {
      public void run()  {
        PropertyName.startPropertyAccess("name").ofType(Integer.class).in(person).get();
      }
    });
  }

  @Test
  public void should_throw_error_if_property_name_is_invalid() {
    expectReflectionError("Unable to find property 'age' in org.fest.reflect.Person").on(new CodeToTest() {
      public void run()  {
        PropertyName.startPropertyAccess("age").ofType(Integer.class).in(person);
      }
    });
  }

  @Test
  public void should_get_property_in_super_type() {
    Jedi jedi = new Jedi("Yoda");
    String jediName = PropertyName.startPropertyAccess("name").ofType(String.class).in(jedi).get();
    assertThat(jediName).isEqualTo("Yoda");
  }

  @Test
  public void should_throw_error_if_TypeRef_is_null() {
    expectNullPointerException("The type reference of the property to access should not be null").on(new CodeToTest() {
      public void run() {
        PropertyName.startPropertyAccess("name").ofType((TypeRef<?>)null);
      }
    });
  }

  @Test
  public void should_use_TypeRef_to_read_property() {
    Jedi jedi = new Jedi("Yoda");
    jedi.addPower("heal");
    List<String> powers = PropertyName.startPropertyAccess("powers").ofType(new TypeRef<List<String>>() {}).in(jedi).get();
    assertThat(powers).containsOnly("heal");
  }

  @Test
  public void should_use_TypeRef_to_write_property() {
    Jedi jedi = new Jedi("Yoda");
    List<String> powers = list("heal");
    PropertyName.startPropertyAccess("powers").ofType(new TypeRef<List<String>>() {}).in(jedi).set(powers);
    assertThat(jedi.powers()).containsOnly("heal");
  }
}
