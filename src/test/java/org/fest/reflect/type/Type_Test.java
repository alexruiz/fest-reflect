/*
 * Created on Jan 23, 2009
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
package org.fest.reflect.type;

import static org.fest.reflect.util.ExpectedFailures.expectIllegalArgumentException;
import static org.fest.reflect.util.ExpectedFailures.expectNullPointerException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.fest.reflect.Jedi;
import org.fest.reflect.Person;
import org.fest.reflect.exception.ReflectionError;
import org.fest.test.CodeToTest;

/**
 * Tests for <code>{@link Type}</code>.
 * 
 * @author Alex Ruiz
 */
public class Type_Test {

  @Test
  public void should_throw_error_if__type_name_is_null() {
    expectNullPointerException("The name of the class to load should not be null").on(new CodeToTest() {
      public void run() {
        Type.newType(null);
      }
    });
  }

  @Test
  public void should_throw_error_if__type_name_is_empty() {
    expectIllegalArgumentException("The name of the class to load should not be empty").on(new CodeToTest() {
      public void run() {
        Type.newType("");
      }
    });
  }

  @Test
  public void should_throw_error_if_subtype_is_mull() {
    expectNullPointerException("The given type should not be null").on(new CodeToTest() {
      public void run() {
        Type.newType("hello").loadAs(null);
      }
    });
  }

  @Test
  public void should_load_class() {
    Class<Jedi> expected = Jedi.class;
    Class<?> type = Type.newType(expected.getName()).load();
    assertEquals(expected, type);
  }

  @Test
  public void should_load_class_with_given_ClassLoader() {
    Class<Jedi> expected = Jedi.class;
    Class<?> type = Type.newType(expected.getName()).withClassLoader(getClass().getClassLoader()).load();
    assertEquals(expected, type);
  }

  @Test
  public void should_throw_error_if_Classloader_is_null() {
    expectNullPointerException("The given class loader should not be null").on(new CodeToTest() {
      public void run() {
        Type.newType("hello").withClassLoader(null);
      }
    });
  }

  @Test
  public void should_wrap_any_Exception_thrown_when_loading_class() {
    try {
      Type.newType("org.fest.reflect.NonExistingType").load();
    } catch (ReflectionError expected) {
      assertTrue(expected.getMessage().contains("Unable to load class 'org.fest.reflect.NonExistingType' using class loader "));
      assertTrue(expected.getCause() instanceof ClassNotFoundException);
    }
  }

  @Test
  public void should_load_class_as_given_type() {
    Class<? extends Person> type = Type.newType(Jedi.class.getName()).loadAs(Person.class);
    assertEquals(Jedi.class, type);
  }

  @Test
  public void should_load_class_as_given_type_with_given_ClassLoader() {
    Class<? extends Person> type = Type.newType(Jedi.class.getName()).withClassLoader(getClass().getClassLoader())
        .loadAs(Person.class);
    assertEquals(Jedi.class, type);
  }

  @Test
  public void should_wrap_any_Exception_thrown_when_loading_class_as_given_type() {
    try {
      Type.newType("org.fest.reflect.NonExistingType").loadAs(Jedi.class);
    } catch (ReflectionError expected) {
      assertTrue(expected.getMessage().contains(
          "Unable to load class 'org.fest.reflect.NonExistingType' as org.fest.reflect.Jedi using class loader "));
      assertTrue(expected.getCause() instanceof ClassNotFoundException);
    }
  }
}
