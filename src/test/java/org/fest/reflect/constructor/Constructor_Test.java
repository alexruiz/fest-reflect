/*
 * Created on May 17, 2007
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
package org.fest.reflect.constructor;

import static org.fest.reflect.util.ExpectedFailures.expectIllegalArgumentException;
import static org.fest.reflect.util.ExpectedFailures.expectNullPointerException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;

import org.junit.Test;

import org.fest.reflect.Person;
import org.fest.reflect.exception.ReflectionError;
import org.fest.test.CodeToTest;

/**
 * Tests for the fluent interface for constructors.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class Constructor_Test {

  @Test
  public void should_create_new_instance_with_default_constructor() {
    Person person = TargetType.startConstructorAccess().in(Person.class).newInstance();
    assertNotNull(person);
    assertNull(person.getName());
  }

  @Test
  public void should_create_new_instance_using_given_constructor_parameters() {
    Person person = TargetType.startConstructorAccess().withParameterTypes(String.class).in(Person.class).newInstance("Yoda");
    assertNotNull(person);
    assertEquals("Yoda", person.getName());
  }

  @Test
  public void should_return_real_constructor() {
    Constructor<Person> constructor = TargetType.startConstructorAccess().withParameterTypes(String.class).in(Person.class)
        .info();
    assertNotNull(constructor);
    Class<?>[] parameterTypes = constructor.getParameterTypes();
    assertEquals(1, parameterTypes.length);
    assertEquals(String.class, parameterTypes[0]);
  }

  @Test
  public void should_throw_error_if_parameter_type_array_is_null() {
    expectNullPointerException("The array of parameter types should not be null").on(new CodeToTest() {
      public void run() {
        TargetType.startConstructorAccess().withParameterTypes((Class<?>[]) null).in(Person.class).info();
      }
    });
  }

  @Test(expected = ReflectionError.class)
  public void should_throw_error_if_constructor_was_not_found() {
    Class<Integer> illegalType = Integer.class;
    TargetType.startConstructorAccess().withParameterTypes(illegalType).in(Person.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_throw_error_if_instance_was_not_created() {
    int illegalArg = 8;
    TargetType.startConstructorAccess().withParameterTypes(String.class).in(Person.class).newInstance(illegalArg);
  }

  @Test
  public void should_rethrow_RuntimeException_thrown_by_constructor() {
    expectIllegalArgumentException("The name of a person cannot be a number").on(new CodeToTest() {
      public void run() {
        TargetType.startConstructorAccess().withParameterTypes(int.class).in(Person.class).newInstance(8);
      }
    });
  }

  @Test
  public void should_wrap_with_a_ReflectionError_the_checked_Exception_thrown_by_constructor() {
    try {
      TargetType.startConstructorAccess().withParameterTypes(Person.class).in(Person.class).newInstance(new Person());
      fail("Expecting an ReflectionError");
    } catch (ReflectionError e) {
      Throwable cause = e.getCause();
      assertTrue(cause instanceof Exception);
      assertEquals("A person cannot be created from another person", cause.getMessage());
    }
  }
}
