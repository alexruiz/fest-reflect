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
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.reflect.constructor;

import static org.fest.reflect.test.ExpectedException.none;
import static org.junit.Assert.*;

import java.lang.reflect.Constructor;

import org.fest.reflect.exception.ReflectionError;
import org.fest.reflect.test.*;
import org.junit.*;

/**
 * Tests for <code>{@link Constructors}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class Constructors_Test {

  @Rule public ExpectedException thrown = none();

  @Test public void should_throw_error_if_type_is_null() {
    thrown.expectNullPointerException("The given type should not be null");
    Constructors.constructorIn(null);
  }

  @Test public void should_create_new_instance_with_default_constructor() {
    Person person = Constructors.constructorIn(Person.class).withNoParameters().newInstance();
    assertNotNull(person);
    assertNull(person.getName());
  }

  @Test public void should_create_new_instance_using_given_constructor_parameters() {
    Person person = Constructors.constructorIn(Person.class).withParameterTypes(String.class).newInstance("Yoda");
    assertNotNull(person);
    assertEquals("Yoda", person.getName());
  }

  @Test public void should_return_real_constructor() {
    Constructor<Person> constructor = Constructors.constructorIn(Person.class).withParameterTypes(String.class).info();
    assertNotNull(constructor);
    Class<?>[] parameterTypes = constructor.getParameterTypes();
    assertEquals(1, parameterTypes.length);
    assertEquals(String.class, parameterTypes[0]);
  }

  @Test public void should_throw_error_if_parameter_type_array_is_null() {
    thrown.expectNullPointerException("The array of parameter types should not be null");
    Constructors.constructorIn(Person.class).withParameterTypes((Class<?>[]) null).info();
  }

  @Test public void should_throw_error_if_constructor_was_not_found() {
    thrown.expect(ReflectionError.class);
    Class<Integer> illegalType = Integer.class;
    Constructors.constructorIn(Person.class).withParameterTypes(illegalType).newInstance();
  }

  @Test public void should_throw_error_if_instance_was_not_created() {
    thrown.expect(IllegalArgumentException.class);
    Constructors.constructorIn(Person.class).withParameterTypes(String.class).newInstance(8);
  }

  @Test public void should_rethrow_RuntimeException_thrown_by_constructor() {
    thrown.expectIllegalArgumentException("The name of a person cannot be a number");
    Constructors.constructorIn(Person.class).withParameterTypes(int.class).newInstance(8);
  }

  @Test public void should_wrap_with_a_ReflectionError_the_checked_Exception_thrown_by_constructor() {
    try {
      Constructors.constructorIn(Person.class).withParameterTypes(Person.class).newInstance(new Person());
      fail("Expecting an ReflectionError");
    } catch (ReflectionError e) {
      Throwable cause = e.getCause();
      assertTrue(cause instanceof Exception);
      assertEquals("A person cannot be created from another person", cause.getMessage());
    }
  }
}
