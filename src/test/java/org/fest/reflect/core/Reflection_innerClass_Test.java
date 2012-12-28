/*
 * Created on Jan 25, 2009
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

import org.fest.reflect.exception.ReflectionError;
import org.fest.test.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link Reflection#innerClass(String)}.
 * 
 * @author Alex Ruiz
 */
public class Reflection_innerClass_Test {
  @Rule
  public ExpectedException thrown = none();

  @Test
  public void should_see_static_inner_class() {
    Class<?> innerClass = Reflection.innerClass("PrivateInnerClass").in(OuterClass.class).get();
    String expectedName = getClass().getName() + "$OuterClass$PrivateInnerClass";
    assertEquals(expectedName, innerClass.getName());
  }

  @Test
  public void should_throw_error_if_static_inner_class_does_not_exist() {
    Class<?> target = getClass();
    thrown.expect(ReflectionError.class, "Failed to find static inner class SomeInnerClass in " + target.getName());
    Reflection.innerClass("SomeInnerClass").in(target).get();
  }

  static class OuterClass {
    @SuppressWarnings("unused")
    private static class PrivateInnerClass {
      private final String name;

      PrivateInnerClass(String name) {
        this.name = name;
      }

      String name() {
        return name;
      }
    }
  }
}
