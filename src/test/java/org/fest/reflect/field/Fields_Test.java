/*
 * Created on May 18, 2007
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.reflect.field;

import static org.fest.reflect.test.ExpectedException.none;
import static org.fest.util.Collections.list;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.List;

import org.fest.reflect.reference.TypeRef;
import org.fest.reflect.test.*;
import org.junit.*;

/**
 * Tests for <code>{@link Fields}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class Fields_Test {

  @Rule public ExpectedException thrown = none();

  private Person person;

  @Before public void setUp() {
    person = new Person("Luke");
  }

  @Test public void should_throw_error_if_field_type_is_null() {
    thrown.expectNullPointerException("The type of the field to access should not be null");
    Fields.fieldOfType((Class<?>) null);
  }

  @Test public void should_throw_error_if_field_name_is_null() {
    thrown.expectNullPointerException("The name of the field to access should not be null");
    Fields.fieldOfType(String.class).withName(null);
  }

  @Test public void should_throw_error_if_field_name_is_empty() {
    thrown.expectIllegalArgumentException("The name of the field to access should not be empty");
    Fields.fieldOfType(String.class).withName("");
  }

  @Test public void should_throw_error_if_target_is_null() {
    thrown.expectNullPointerException("The target object should not be null");
    Fields.fieldOfType(String.class).withName("name").in((Object) null);
  }

  @Test public void should_get_field_value() {
    String personName = Fields.fieldOfType(String.class).withName("name").in(person).get();
    assertEquals("Luke", personName);
  }

  @Test public void should_set_field_value() {
    Fields.fieldOfType(String.class).withName("name").in(person).set("Leia");
    assertEquals("Leia", person.getName());
  }

  @Test public void should_return_real_field() {
    Field field = Fields.fieldOfType(String.class).withName("name").in(person).info();
    assertNotNull(field);
    assertEquals("name", field.getName());
    assertEquals(String.class, field.getType());
  }

  @Test public void should_throw_error_if_wrong_field_type_was_specified() {
    String msg = "The type of the field 'name' in org.fest.reflect.test.Person should be <java.lang.Integer> but was <java.lang.String>";
    thrown.expectReflectionError(msg);
    Fields.fieldOfType(Integer.class).withName("name").in(person).get();
  }

  @Test public void should_throw_error_if_field_name_is_invalid() {
    thrown.expectReflectionError("Unable to find field 'age' in org.fest.reflect.test.Person");
    Fields.fieldOfType(Integer.class).withName("age").in(person);
  }

  @Test public void should_get_field_in_super_type() {
    Jedi jedi = new Jedi("Yoda");
    String jediName = Fields.fieldOfType(String.class).withName("name").in(jedi).get();
    assertEquals("Yoda", jediName);
  }

  @Test public void should_throw_error_if_TypeRef_is_null() {
    thrown.expectNullPointerException("The reference to the type of the field to access should not be null");
    Fields.fieldOfType((TypeRef<?>) null);
  }

  @Test public void should_use_TypeRef_to_read_field() {
    Jedi jedi = new Jedi("Yoda");
    jedi.addPower("heal");
    List<String> powers = Fields.fieldOfType(new TypeRef<List<String>>() {}).withName("powers").in(jedi).get();
    assertEquals(1, powers.size());
    assertEquals("heal", powers.get(0));
  }

  @Test public void should_use_TypeRef_to_write_field() {
    Jedi jedi = new Jedi("Yoda");
    List<String> powers = list("heal");
    Fields.fieldOfType(new TypeRef<List<String>>() {}).withName("powers").in(jedi).set(powers);
    assertEquals(1, jedi.powers().size());
    assertEquals("heal", jedi.powers().get(0));
  }

  @Test public void should_throw_error_if_target_type_is_null() {
    thrown.expectNullPointerException("The target type should not be null");
    Fields.fieldOfType(String.class).withName("name").in((Class<?>) null);
  }

  @Test
  public void should_get_static_field_value() {
    Person.setCount(6);
    int count = Fields.fieldOfType(int.class).withName("count").in(Person.class).get();
    assertEquals(6, count);
  }

  @Test
  public void should_set_static_field_value() {
    Fields.fieldOfType(int.class).withName("count").in(Person.class).set(8);
    assertEquals(8, Person.getCount());
  }
}
