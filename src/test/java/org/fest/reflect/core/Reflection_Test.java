/*
 * Created on May 18, 2007
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
package org.fest.reflect.core;

import static org.junit.Assert.*;

import org.junit.Test;

import org.fest.reflect.beanproperty.PropertyName;
import org.fest.reflect.constructor.TargetType;
import org.fest.reflect.field.FieldName;
import org.fest.reflect.field.StaticFieldName;
import org.fest.reflect.innerclass.StaticInnerClassName;
import org.fest.reflect.method.MethodName;
import org.fest.reflect.method.StaticMethodName;
import org.fest.reflect.type.Type;

/**
 * Tests for <code>{@link Reflection}</code>.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class Reflection_Test {

  @Test
  public void should_return_fluent_interface_for_constructor() {
    verifyTypesAreEqual(TargetType.class, Reflection.constructor());
  }

  @Test
  public void should_return_fluent_interface_for_field() {
    verifyTypesAreEqual(FieldName.class, Reflection.field("foo"));
  }

  @Test
  public void should_return_fluent_interface_for_static_field() {
    verifyTypesAreEqual(StaticFieldName.class, Reflection.staticField("foo"));
  }

  @Test
  public void should_return_fluent_interface_for_method() {
    verifyTypesAreEqual(MethodName.class, Reflection.method("foo"));
  }

  @Test
  public void should_return_fluent_interface_for_static_method() {
    verifyTypesAreEqual(StaticMethodName.class, Reflection.staticMethod("foo"));
  }

  @Test
  public void should_return_fluent_interface_for_type() {
    verifyTypesAreEqual(Type.class, Reflection.type("foo"));
  }

  @Test
  public void should_return_fluent_interface_for_static_inner_class() {
    verifyTypesAreEqual(StaticInnerClassName.class, Reflection.staticInnerClass("foo"));
  }

  @Test
  public void should_return_fluent_interface_for_property() {
    verifyTypesAreEqual(PropertyName.class, Reflection.property("foo"));
  }

  private void verifyTypesAreEqual(Class<?> expected, Object o) {
    assertEquals(expected, o.getClass());
  }
}
