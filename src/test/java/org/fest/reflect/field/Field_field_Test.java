/*
 * Created on May 18, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 * 
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.reflect.field;

import static org.fest.reflect.util.ExpectedFailures.expectIllegalArgumentException;
import static org.fest.reflect.util.ExpectedFailures.expectNullPointerException;
import static org.fest.reflect.util.ExpectedFailures.expectReflectionError;
import static org.fest.util.Lists.newArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.fest.reflect.Jedi;
import org.fest.reflect.Person;
import org.fest.reflect.reference.TypeRef;
import org.fest.test.CodeToTest;

/**
 * Tests for the fluent interface for accessing fields.
 * 
 * @author Alex Ruiz
 */
public class Field_field_Test {

  private Person person;

  @Before
  public void setUp() {
    person = new Person("Luke");
  }

  @Test
  public void should_throw_error_if_field_name_is_null() {
    expectNullPointerException("The name of the field to access should not be null").on(new CodeToTest() {
      public void run() {
        FieldName.beginFieldAccess(null);
      }
    });
  }

  @Test
  public void should_throw_error_if_field_name_is_empty() {
    expectIllegalArgumentException("The name of the field to access should not be empty").on(new CodeToTest() {
      public void run() {
        FieldName.beginFieldAccess("");
      }
    });
  }

  @Test
  public void should_throw_error_if_field_type_is_null() {
    expectNullPointerException("The type of the field to access should not be null").on(new CodeToTest() {
      public void run() {
        FieldName.beginFieldAccess("name").ofType((Class<?>) null);
      }
    });
  }

  @Test
  public void should_throw_error_if_target_is_null() {
    expectNullPointerException("Target should not be null").on(new CodeToTest() {
      public void run() {
        FieldName.beginFieldAccess("name").ofType(String.class).in(null);
      }
    });
  }

  @Test
  public void should_get_field_value() {
    String personName = FieldName.beginFieldAccess("name").ofType(String.class).in(person).get();
    assertEquals("Luke", personName);
  }

  @Test
  public void should_set_field_value() {
    FieldName.beginFieldAccess("name").ofType(String.class).in(person).set("Leia");
    assertEquals("Leia", person.getName());
  }

  @Test
  public void should_return_real_field() {
    java.lang.reflect.Field field = FieldName.beginFieldAccess("name").ofType(String.class).in(person).info();
    assertNotNull(field);
    assertEquals("name", field.getName());
    assertEquals(String.class, field.getType());
  }

  @Test
  public void should_throw_error_if_wrong_field_type_was_specified() {
    String msg = "The type of the field 'name' in org.fest.reflect.Person should be <java.lang.Integer> but was <java.lang.String>";
    expectReflectionError(msg).on(new CodeToTest() {
      public void run() {
        FieldName.beginFieldAccess("name").ofType(Integer.class).in(person).get();
      }
    });
  }

  @Test
  public void should_throw_error_if_field_name_is_invalid() {
    expectReflectionError("Unable to find field 'age' in org.fest.reflect.Person").on(new CodeToTest() {
      public void run() {
        FieldName.beginFieldAccess("age").ofType(Integer.class).in(person);
      }
    });
  }

  @Test
  public void should_get_field_in_super_type() {
    Jedi jedi = new Jedi("Yoda");
    String jediName = FieldName.beginFieldAccess("name").ofType(String.class).in(jedi).get();
    assertEquals("Yoda", jediName);
  }

  @Test
  public void should_throw_error_if_TypeRef_is_null() {
    expectNullPointerException("The type reference of the field to access should not be null").on(new CodeToTest() {
      public void run() {
        FieldName.beginFieldAccess("name").ofType((TypeRef<?>) null);
      }
    });
  }

  @Test
  public void should_use_TypeRef_to_read_field() {
    Jedi jedi = new Jedi("Yoda");
    jedi.addPower("heal");
    List<String> powers = FieldName.beginFieldAccess("powers").ofType(new TypeRef<List<String>>() {}).in(jedi).get();
    assertEquals(1, powers.size());
    assertEquals("heal", powers.get(0));
  }

  @Test
  public void should_use_TypeRef_to_write_field() {
    Jedi jedi = new Jedi("Yoda");
    List<String> powers = newArrayList("heal");
    FieldName.beginFieldAccess("powers").ofType(new TypeRef<List<String>>() {}).in(jedi).set(powers);
    assertEquals(1, jedi.powers().size());
    assertEquals("heal", jedi.powers().get(0));
  }
}
