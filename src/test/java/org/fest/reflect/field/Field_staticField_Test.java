/*
 * Created on Nov 23, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.reflect.field;

import static org.fest.reflect.util.ExpectedFailures.expectIllegalArgumentException;
import static org.fest.reflect.util.ExpectedFailures.expectNullPointerException;
import static org.fest.reflect.util.ExpectedFailures.expectReflectionError;
import static org.fest.util.Lists.newArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import org.fest.reflect.Jedi;
import org.fest.reflect.Person;
import org.fest.reflect.reference.TypeRef;
import org.fest.test.CodeToTest;

/**
 * Tests for the fluent interface for accessing static fields.
 * 
 * @author Alex Ruiz
 */
public class Field_staticField_Test {

  @Test
  public void should_throw_error_if_static_field_name_is_null() {
    expectNullPointerException("The name of the static field to access should not be null").on(new CodeToTest() {
      public void run() {
        StaticFieldName.beginStaticFieldAccess(null);
      }
    });
  }

  @Test
  public void should_throw_error_if_static_field_name_is_empty() {
    expectIllegalArgumentException("The name of the static field to access should not be empty").on(new CodeToTest() {
      public void run() {
        StaticFieldName.beginStaticFieldAccess("");
      }
    });
  }

  @Test
  public void should_throw_error_if_static_field_type_is_null() {
    expectNullPointerException("The type of the static field to access should not be null").on(new CodeToTest() {
      public void run() {
        StaticFieldName.beginStaticFieldAccess("name").ofType((Class<?>) null);
      }
    });
  }

  @Test
  public void should_throw_error_if_target_is_null() {
    expectNullPointerException("Target should not be null").on(new CodeToTest() {
      public void run() {
        StaticFieldName.beginStaticFieldAccess("age").ofType(int.class).in(null);
      }
    });
  }

  @Test
  public void should_get_static_field_value() {
    Person.setCount(6);
    int count = StaticFieldName.beginStaticFieldAccess("count").ofType(int.class).in(Person.class).get();
    assertEquals(6, count);
  }

  @Test
  public void should_set_static_field_value() {
    StaticFieldName.beginStaticFieldAccess("count").ofType(int.class).in(Person.class).set(8);
    assertEquals(8, Person.getCount());
  }

  @Test
  public void should_return_real_static_field() {
    java.lang.reflect.Field field = StaticFieldName.beginStaticFieldAccess("count").ofType(int.class).in(Person.class).info();
    assertNotNull(field);
    assertEquals("count", field.getName());
    assertEquals(int.class, field.getType());

  }

  @Test
  public void should_throw_error_if_wrong_static_field_type_was_specified() {
    String msg = "The type of the field 'count' in org.fest.reflect.Person should be <java.lang.Float> but was <int>";
    expectReflectionError(msg).on(new CodeToTest() {
      public void run() {
        StaticFieldName.beginStaticFieldAccess("count").ofType(Float.class).in(Person.class).get();
      }
    });
  }

  @Test
  public void should_throw_error_if_static_field_name_is_invalid() {
    expectReflectionError("Unable to find field 'age' in org.fest.reflect.Person").on(new CodeToTest() {
      public void run() {
        StaticFieldName.beginStaticFieldAccess("age").ofType(int.class).in(Person.class);
      }
    });
  }

  @Test
  public void should_get_static_field_in_super_type() {
    Person.setCount(8);
    int count = StaticFieldName.beginStaticFieldAccess("count").ofType(int.class).in(Person.class).get();
    assertEquals(8, count);
  }

  @Test
  public void should_throw_error_if_TypeRef_is_null() {
    String msg = "The type reference of the static field to access should not be null";
    expectNullPointerException(msg).on(new CodeToTest() {
      public void run() {
        StaticFieldName.beginStaticFieldAccess("name").ofType((TypeRef<?>) null);
      }
    });
  }

  @Test
  public void should_use_TypeRef_to_read_static_field() {
    Jedi.addCommonPower("jump");
    List<String> powers = StaticFieldName.beginStaticFieldAccess("commonPowers").ofType(new TypeRef<List<String>>() {})
        .in(Jedi.class).get();
    assertEquals(1, powers.size());
    assertEquals("jump", powers.get(0));
  }

  @Test
  public void should_use_TypeRef_to_write_static_field() {
    List<String> powers = newArrayList("jump");
    StaticFieldName.beginStaticFieldAccess("commonPowers").ofType(new TypeRef<List<String>>() {}).in(Jedi.class).set(powers);
    assertEquals(1, Jedi.commonPowers().size());
    assertEquals("jump", Jedi.commonPowers().get(0));
  }
}
