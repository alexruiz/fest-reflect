/*
 * Created on May 18, 2007
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
package org.fest.reflect.core;

import static org.junit.Assert.assertTrue;

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
  public void should_return_constructor_fluent_interface() {
    assertTrue(Reflection.constructor() instanceof TargetType);
  }

  @Test
  public void should_return_field_fluent_interface() {
    assertTrue(Reflection.field("field") instanceof FieldName);
  }

  @Test
  public void should_return_static_field_fluent_interface() {
    assertTrue(Reflection.staticField("field") instanceof StaticFieldName);
  }

  @Test
  public void should_return_method_fluent_interface() {
    assertTrue(Reflection.method("method") instanceof MethodName);
  }

  @Test
  public void should_return_static_method_fluent_interface() {
    assertTrue(Reflection.staticMethod("method") instanceof StaticMethodName);
  }

  @Test
  public void should_return_type_fluent_interface() {
    assertTrue(Reflection.type("type") instanceof Type);
  }

  @Test
  public void should_return_static_inner_class_fluent_interface() {
    assertTrue(Reflection.staticInnerClass("staticInnerClass") instanceof StaticInnerClassName);
  }

  @Test
  public void should_return_property_fluent_interface() {
    assertTrue(Reflection.property("property") instanceof PropertyName);
  }
}
