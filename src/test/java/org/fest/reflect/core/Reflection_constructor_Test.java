/*
 * Created on May 17, 2007
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;

import org.fest.reflect.exception.ReflectionError;
import org.fest.test.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link Reflection#constructor()}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class Reflection_constructor_Test {
  @Rule
  public ExpectedException thrown = none();

  @Test
  public void should_create_new_instance_with_default_constructor() {
    Person person = Reflection.constructor().in(Person.class).newInstance();
    assertNotNull(person);
  }

  @Test
  public void should_create_new_instance_using_given_constructor_parameters() {
    Person person =
        Reflection.constructor().withParameterTypes(String.class).in(Person.class).newInstance("Yoda");
    assertNotNull(person);
    assertEquals("Yoda", person.name);
  }

  @Test
  public void should_return_real_constructor() {
    Constructor<Person> constructor = Reflection.constructor().withParameterTypes(String.class).in(Person.class).constructor();
    assertNotNull(constructor);
    Class<?>[] parameterTypes = constructor.getParameterTypes();
    assertEquals(1, parameterTypes.length);
    assertEquals(String.class, parameterTypes[0]);
  }

  @Test
  public void should_throw_error_if_constructor_was_not_found() {
    String msg = "Failed to find constructor in type " + getClass().getName() + "$Person with parameter types [float]";
    thrown.expect(ReflectionError.class, msg);
    Reflection.constructor().withParameterTypes(float.class).in(Person.class);
  }

  @Test
  public void should_throw_error_if_instance_was_not_created() {
    thrown.expect(IllegalArgumentException.class);
    Reflection.constructor().withParameterTypes(String.class).in(Person.class).newInstance(8);
  }

  @Test
  public void should_rethrow_RuntimeException_thrown_by_constructor() {
    thrown.expect(IllegalArgumentException.class, "The name of a person cannot be a number");
    Reflection.constructor().withParameterTypes(int.class).in(Person.class).newInstance(8);
  }

  @Test
  public void should_wrap_with_a_ReflectionError_the_checked_Exception_thrown_by_constructor() {
    try {
      Reflection.constructor().withParameterTypes(Person.class).in(Person.class).newInstance(new Person());
      fail();
    } catch (ReflectionError e) {
      Throwable cause = e.getCause();
      assertEquals("A person cannot be created from another person", cause.getMessage());
    }
  }

  static class Person {
    String name;

    public Person() {}

    public Person(String name) {
      this.name = name;}

    public Person(int name) {
      throw new IllegalArgumentException("The name of a person cannot be a number");
    }

    public Person(Person person) throws Exception {
      throw new Exception("A person cannot be created from another person");
    }
  }
}

