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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.reflect.type;

import org.fest.reflect.exception.ReflectionError;
import org.fest.test.ExpectedException;
import org.fest.util.Preconditions;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.fest.test.ExpectedException.none;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link Type}.
 *
 * @author Alex Ruiz
 */
public class Type_Test {
  @Rule
  public ExpectedException thrown = none();

  @Test
  public void should_load_class() {
    Class<?> type = new Type(Preconditions.class.getName()).load();
    assertEquals(Preconditions.class, type);
  }

  @Test
  public void should_load_class_with_given_ClassLoader() {
    ClassLoader classLoader = getClass().getClassLoader();
    Class<?> type = new Type(Preconditions.class.getName()).withClassLoader(classLoader).load();
    assertEquals(Preconditions.class, type);
  }

  @Test
  public void should_throw_error_if_Classloader_is_null() {
    thrown.expect(NullPointerException.class);
    new Type("java.lang.String").withClassLoader(null);
  }

  @Test
  public void should_wrap_any_Exception_thrown_when_loading_class() {
    try {
      new Type("org.fest.reflect.NonExistingType").load();
    } catch (ReflectionError expected) {
      assertTrue(expected.getMessage().contains(
          "Unable to load class 'org.fest.reflect.NonExistingType' using ClassLoader "));
      assertTrue(expected.getCause() instanceof ClassNotFoundException);
    }
  }

  @Test
  public void should_load_class_as_given_type() {
    Class<? extends Number> type = new Type(Integer.class.getName()).loadAs(Number.class);
    assertEquals(Integer.class, type);
  }

  @Test
  public void should_load_class_as_given_type_with_given_ClassLoader() {
    ClassLoader classLoader = getClass().getClassLoader();
    Class<? extends Number> type = new Type(Integer.class.getName()).withClassLoader(classLoader).loadAs(Number.class);
    assertEquals(Integer.class, type);
  }

  @Test
  public void should_wrap_any_Exception_thrown_when_loading_class_as_given_type() {
    try {
      new Type("org.fest.reflect.NonExistingType").loadAs(List.class);
    } catch (ReflectionError expected) {
      assertTrue(expected.getMessage().contains(
          "Unable to load class 'org.fest.reflect.NonExistingType' as java.util.List using ClassLoader "));
      assertTrue(expected.getCause() instanceof ClassNotFoundException);
    }
  }
}
