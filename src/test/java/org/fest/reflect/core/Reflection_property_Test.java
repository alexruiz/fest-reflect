/*
 * Created on Nov 23, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.reflect.core;

import static org.fest.test.ExpectedException.none;
import static org.junit.Assert.assertEquals;

import java.beans.PropertyDescriptor;

import org.fest.reflect.exception.ReflectionError;
import org.fest.reflect.reference.TypeRef;
import org.fest.test.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link Reflection#property(String)}.
 * 
 * @author Alex Ruiz
 */
public class Reflection_property_Test {
  @Rule
  public ExpectedException thrown = none();

  @Test
  public void should_get_property_value() {
    Person person = new Person();
    person.setName("Yoda");
    String name = Reflection.property("name").ofType(String.class).in(person).get();
    assertEquals("Yoda", name);
  }

  @Test
  public void should_get_property_value_defined_in_object_class() {
    Person person = new Person();
    Class<?> personClass = Reflection.property("class").ofType(Class.class).in(person).get();
    assertEquals(Person.class, personClass);
  }

  @Test
  public void should_set_property_value() {
    Person person = new Person();
    Reflection.property("name").ofType(String.class).in(person).set("Yoda");
    assertEquals("Yoda", person.getName());
  }

  @Test
  public void should_return_property_descriptor() {
    Person person = new Person();
    PropertyDescriptor property = Reflection.property("name").ofType(String.class).in(person).descriptor();
    assertEquals("name", property.getName());
    assertEquals(String.class, property.getPropertyType());
  }

  @Test
  public void should_throw_error_if_wrong_property_type_was_specified() {
    Person person = new Person();
    String message = "Expecting type of property 'name' to be <java.lang.Integer> but was <java.lang.String>";
    thrown.expect(ReflectionError.class, message);
    Reflection.property("name").ofType(Integer.class).in(person).get();
  }

  @Test
  public void should_throw_error_if_property_name_is_invalid() {
    Person person = new Person();
    String message = "Failed to find property 'age' in " + getClass().getName() + "$Person";
    thrown.expect(ReflectionError.class, message);
    Reflection.property("age").ofType(Integer.class).in(person);
  }

  @Test
  public void should_use_TypeRef_to_read_property() {
    Person person = new Person();
    Class<Person> personClass = Reflection.property("class").ofType(new TypeRef<Class<Person>>() {
    }).in(person).get();
    assertEquals(Person.class, personClass);
  }

  @Test
  public void should_use_TypeRef_to_write_property() {
    Person person = new Person();
    Reflection.property("name").ofType(new TypeRef<String>() {}).in(person).set("Yoda");
    assertEquals("Yoda", person.getName());
  }

  private static class Person {
    private String name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }
}
