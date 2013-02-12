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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.reflect.core;

import static org.fest.test.ExpectedException.none;
import static org.fest.util.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.lang.reflect.Field;
import java.util.List;

import org.fest.reflect.exception.ReflectionError;
import org.fest.reflect.reference.TypeRef;
import org.fest.test.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link Reflection#field(String)}.
 * 
 * @author Alex Ruiz
 */
public class Reflection_field_Test {
  @Rule
  public ExpectedException thrown = none();

  @Test
  public void should_get_field_value() {
    Person person = new Person();
    person.name = "Yoda";
    String name = Reflection.field("name").ofType(String.class).in(person).get();
    assertEquals("Yoda", name);
  }

  @Test
  public void should_set_field_value() {
    Person person = new Person();
    Reflection.field("name").ofType(String.class).in(person).set("Leia");
    assertEquals("Leia", person.name);
  }

  @Test
  public void should_return_real_field() {
    Person person = new Person();
    Field field = Reflection.field("name").ofType(String.class).in(person).target();
    assertEquals("name", field.getName());
    assertEquals(String.class, field.getType());
  }

  @Test
  public void should_throw_error_if_wrong_field_type_was_specified() {
    Person person = new Person();
    String msg = "Expecting type of field 'name' in " + getClass().getName()
        + "$Person to be <java.lang.Integer> but was <java.lang.String>";
    thrown.expect(ReflectionError.class, msg);
    Reflection.field("name").ofType(Integer.class).in(person).get();
  }

  @Test
  public void should_throw_error_if_field_name_is_invalid() {
    Person person = new Person();
    String msg = "Failed to find field 'age' in " + getClass().getName() + "$Person";
    thrown.expect(ReflectionError.class, msg);
    Reflection.field("age").ofType(Integer.class).in(person).get();
  }

  @Test
  public void should_get_field_in_super_type() {
    Jedi jedi = new Jedi();
    jedi.name = "Yoda";
    String name = Reflection.field("name").ofType(String.class).in(jedi).get();
    assertEquals("Yoda", name);
  }

  @Test
  public void should_use_TypeRef_to_access_field() {
    Jedi jedi = new Jedi();
    List<String> powers = Reflection.field("powers").ofType(new TypeRef<List<String>>() {}).in(jedi).get();
    assertSame(jedi.powers, powers);
  }

  @Test
  public void should_access_static_field() {
    List<Person> persons = Reflection.field("elements").ofType(new TypeRef<List<Person>>() {}).in(Persons.class).get();
    assertSame(Persons.elements, persons);
  }

  private static class Persons {
    static List<Person> elements = newArrayList();
  }

  private static class Person {
    String name;
  }

  private static class Jedi extends Person {
    List<String> powers = newArrayList();
  }
}
