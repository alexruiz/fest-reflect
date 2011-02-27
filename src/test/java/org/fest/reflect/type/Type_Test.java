/*
 * Created on Jan 23, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.reflect.type;

import static org.fest.reflect.test.ExpectedException.none;
import static org.junit.Assert.*;

import org.fest.reflect.*;
import org.fest.reflect.exception.ReflectionError;
import org.fest.reflect.test.ExpectedException;
import org.junit.*;

/**
 * Tests for <code>{@link Type}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class Type_Test {

  @Rule public ExpectedException thrown = none();

  @Test public void should_throw_error_if__type_name_is_null() {
    thrown.expectNullPointerException("The name of the class to load should not be null");
    Type.newType(null);
  }

  @Test public void should_throw_error_if__type_name_is_empty() {
    thrown.expectIllegalArgumentException("The name of the class to load should not be empty");
    Type.newType("");
  }

  @Test public void should_throw_error_if_subtype_is_mull() {
    thrown.expectNullPointerException("The given type should not be null");
    Type.newType("hello").loadAs(null);
  }

  @Test public void should_load_class() {
    Class<Jedi> expected = Jedi.class;
    Class<?> type = Type.newType(expected.getName()).load();
    assertEquals(expected, type);
  }

  @Test public void should_load_class_with_given_ClassLoader() {
    Class<Jedi> expected = Jedi.class;
    Class<?> type = Type.newType(expected.getName()).withClassLoader(getClass().getClassLoader()).load();
    assertEquals(expected, type);
  }

  @Test public void should_throw_error_if_Classloader_is_null() {
    thrown.expectNullPointerException("The given class loader should not be null");
    Type.newType("hello").withClassLoader(null);
  }

  @Test public void should_wrap_any_Exception_thrown_when_loading_class() {
    try {
      Type.newType("org.fest.reflect.NonExistingType").load();
    } catch (ReflectionError expected) {
      assertTrue(expected.getMessage().contains(
          "Unable to load class 'org.fest.reflect.NonExistingType' using class loader "));
      assertTrue(expected.getCause() instanceof ClassNotFoundException);
    }
  }

  @Test public void should_load_class_as_given_type() {
    Class<? extends Person> type = Type.newType(Jedi.class.getName()).loadAs(Person.class);
    assertEquals(Jedi.class, type);
  }

  @Test public void should_load_class_as_given_type_with_given_ClassLoader() {
    Class<? extends Person> type = Type.newType(Jedi.class.getName()).withClassLoader(getClass().getClassLoader())
        .loadAs(Person.class);
    assertEquals(Jedi.class, type);
  }

  @Test public void should_wrap_any_Exception_thrown_when_loading_class_as_given_type() {
    try {
      Type.newType("org.fest.reflect.NonExistingType").loadAs(Jedi.class);
    } catch (ReflectionError expected) {
      assertTrue(expected.getMessage().contains(
          "Unable to load class 'org.fest.reflect.NonExistingType' as org.fest.reflect.Jedi using class loader "));
      assertTrue(expected.getCause() instanceof ClassNotFoundException);
    }
  }
}
