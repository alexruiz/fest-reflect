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
package org.fest.reflect.javabean;

import static org.fest.reflect.test.ExpectedException.none;
import static org.fest.util.Collections.list;
import static org.junit.Assert.*;

import java.beans.PropertyDescriptor;
import java.util.List;

import org.fest.reflect.reference.TypeRef;
import org.fest.reflect.test.*;
import org.junit.*;

/**
 * Tests for <code>{@link Properties}</code>.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class Properties_Test {

  @Rule
  public ExpectedException thrown = none();

  private Person person;

  @Before
  public void setUp() {
    person = new Person("Luke");
  }

  @Test
  public void should_throw_error_if_property_type_is_null() {
    thrown.expectNullPointerException("The type of the property to access should not be null");
    Properties.propertyOfType((Class<?>) null);
  }

  @Test
  public void should_throw_error_if_property_name_is_null() {
    thrown.expectNullPointerException("The name of the property to access should not be null");
    Properties.propertyOfType(String.class).withName(null);
  }

  @Test
  public void should_throw_error_if_property_name_is_empty() {
    thrown.expectIllegalArgumentException("The name of the property to access should not be empty");
    Properties.propertyOfType(String.class).withName("");
  }

  @Test
  public void should_throw_error_if_target_is_null() {
    thrown.expectNullPointerException("The target object should not be null");
    Properties.propertyOfType(String.class).withName("name").in(null);
  }

  @Test
  public void should_get_property_value() {
    String personName = Properties.propertyOfType(String.class).withName("name").in(person).get();
    assertEquals("Luke", personName);
  }

  @Test
  public void should_get_property_value_defined_in_object_class() {
    Class<?> personClass = Properties.propertyOfType(Class.class).withName("class").in(person).get();
    assertEquals(Person.class, personClass);
  }

  @Test
  public void should_set_property_value() {
    Properties.propertyOfType(String.class).withName("name").in(person).set("Leia");
    assertEquals("Leia", person.getName());
  }

  @Test
  public void should_return_real_property() {
    PropertyDescriptor property = Properties.propertyOfType(String.class).withName("name").in(person).info();
    assertNotNull(property);
    assertEquals("name", property.getName());
    assertEquals(String.class, property.getPropertyType());
  }

  @Test
  public void should_throw_error_if_wrong_property_type_was_specified() {
    String message = "The type of the property 'name' in org.fest.reflect.test.Person should be <java.lang.Integer> but was <java.lang.String>";
    thrown.expectReflectionError(message);
    Properties.propertyOfType(Integer.class).withName("name").in(person).get();
  }

  @Test
  public void should_throw_error_if_property_name_is_invalid() {
    thrown.expectReflectionError("Unable to find property 'age' in org.fest.reflect.test.Person");
    Properties.propertyOfType(String.class).withName("age").in(person);
  }

  @Test
  public void should_get_property_in_super_type() {
    Jedi jedi = new Jedi("Yoda");
    String jediName = Properties.propertyOfType(String.class).withName("name").in(jedi).get();
    assertEquals("Yoda", jediName);
  }

  @Test
  public void should_throw_error_if_TypeRef_is_null() {
    thrown.expectNullPointerException("The reference to the type of the property to access should not be null");
    Properties.propertyOfType((TypeRef<?>) null);
  }

  @Test
  public void should_use_TypeRef_to_read_property() {
    Jedi jedi = new Jedi("Yoda");
    jedi.addPower("heal");
    List<String> powers = Properties.propertyOfType(new TypeRef<List<String>>() {}).withName("powers").in(jedi).get();
    assertEquals(1, powers.size());
    assertEquals("heal", powers.get(0));
  }

  @Test
  public void should_use_TypeRef_to_write_property() {
    Jedi jedi = new Jedi("Yoda");
    List<String> powers = list("heal");
    Properties.propertyOfType(new TypeRef<List<String>>() {}).withName("powers").in(jedi).set(powers);
    assertEquals(1, jedi.powers().size());
    assertEquals("heal", jedi.powers().get(0));
  }
}
