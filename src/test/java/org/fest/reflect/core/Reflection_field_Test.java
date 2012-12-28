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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests for {@link Reflection#field(String)}.
 * 
 * @author Alex Ruiz
 */
public class Reflection_field_Test {
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
    java.lang.reflect.Field field = Reflection.field("name").ofType(String.class).in(person).field();
    assertEquals("name", field.getName());
    assertEquals(String.class, field.getType());
  }
  //
  //  @Test
  //  public void should_throw_error_if_wrong_field_type_was_specified() {
  //    String msg = "The type of the field 'name' in org.fest.reflect.Person should be <java.lang.Integer> but was <java.lang.String>";
  //    expectReflectionError(msg).on(new CodeToTest() {
  //      public void run() {
  //        FieldName.beginFieldAccess("name").ofType(Integer.class).in(person).get();
  //      }
  //    });
  //  }
  //
  //  @Test
  //  public void should_throw_error_if_field_name_is_invalid() {
  //    expectReflectionError("Unable to find field 'age' in org.fest.reflect.Person").on(new CodeToTest() {
  //      public void run() {
  //        FieldName.beginFieldAccess("age").ofType(Integer.class).in(person);
  //      }
  //    });
  //  }
  //
  //  @Test
  //  public void should_get_field_in_super_type() {
  //    Jedi jedi = new Jedi("Yoda");
  //    String jediName = FieldName.beginFieldAccess("name").ofType(String.class).in(jedi).get();
  //    assertEquals("Yoda", jediName);
  //  }
  //
  //  @Test
  //  public void should_use_TypeRef_to_read_field() {
  //    Jedi jedi = new Jedi("Yoda");
  //    jedi.addPower("heal");
  //    List<String> powers = FieldName.beginFieldAccess("powers").ofType(new TypeRef<List<String>>() {
  //    }).in(jedi).get();
  //    assertEquals(1, powers.size());
  //    assertEquals("heal", powers.get(0));
  //  }
  //
  //  @Test
  //  public void should_use_TypeRef_to_write_field() {
  //    Jedi jedi = new Jedi("Yoda");
  //    List<String> powers = list("heal");
  //    FieldName.beginFieldAccess("powers").ofType(new TypeRef<List<String>>() {
  //    }).in(jedi).set(powers);
  //    assertEquals(1, jedi.powers().size());
  //    assertEquals("heal", jedi.powers().get(0));
  //  }

  private static class Person {
    String name;
  }
}
