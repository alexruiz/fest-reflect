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
 * Copyright @2009 the original author or authors.
 */
package org.fest.reflect.innerclass;

import static org.fest.reflect.constructor.Constructors.constructorIn;
import static org.fest.reflect.field.Fields.fieldOfType;
import static org.fest.reflect.method.Methods.methodWithReturnType;
import static org.fest.reflect.test.ExpectedException.none;
import static org.junit.Assert.*;

import org.fest.reflect.test.ExpectedException;
import org.junit.*;

/**
 * Tests for <code>{@link StaticInnerClasses}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class StaticInnerClasses_Test {

  @Rule public ExpectedException thrown = none();

  @Test public void should_throw_error_if_static_inner_class_name_is_null() {
    thrown.expectNullPointerException("The name of the static inner class to access should not be null");
    StaticInnerClasses.staticInnerClass(null);
  }

  @Test public void should_throw_error_if_static_inner_class_name_is_empty() {
    thrown.expectIllegalArgumentException("The name of the static inner class to access should not be empty");
    StaticInnerClasses.staticInnerClass("");
  }

  @Test public void should_throw_error_if_declaring_class_is_null() {
    thrown.expectNullPointerException("The declaring class should not be null");
    StaticInnerClasses.staticInnerClass("Hello").in(null);
  }

  @Test public void should_see_static_inner_class() {
    Class<?> innerClass = StaticInnerClasses.staticInnerClass("PrivateInnerClass").in(OuterClass.class);
    assertTrue(innerClass.getName().contains("PrivateInnerClass"));
    // make sure we really got the inner classes by creating a new instance and accessing its fields and methods.
    Object leia = constructorIn(innerClass).withParameterTypes(String.class).newInstance("Leia");
    assertEquals("Leia", fieldOfType(String.class).withName("name").in(leia).get());
    assertEquals("Leia", methodWithReturnType(String.class).withName("name").withNoParameters().in(leia).invoke());
  }

  @Test public void should_return_null_if_static_inner_class_does_not_exist() {
    String message = "The static inner class <SomeInnerClass> cannot be found in org.fest.reflect.innerclass.OuterClass";
    thrown.expectReflectionError(message);
    StaticInnerClasses.staticInnerClass("SomeInnerClass").in(OuterClass.class);
  }
}
