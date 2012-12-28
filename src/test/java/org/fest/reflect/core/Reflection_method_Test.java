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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.reflect.core;

import static java.math.BigDecimal.ONE;
import static org.fest.util.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.fest.reflect.reference.TypeRef;
import org.junit.Test;

/**
 * Tests for {@link Reflection#method(String)}.
 * 
 * @author Alex Ruiz
 */
public class Reflection_method_Test {
  @Test
  public void should_invoke_method_without_parameters_and_return_type_void() {
    List<String> list = newArrayList();
    list.add("Test");
    Reflection.method("clear").in(list).invoke();
    assertEquals(0, list.size());
  }

  @Test
  public void should_invoke_method_with_parameters_and_return_type_void() {
    List<String> list = newArrayList();
    Reflection.method("add").withParameterTypes(int.class, Object.class).in(list).invoke(0, "Test");
    assertEquals(1, list.size());
    assertEquals("Test", list.get(0));
  }

  @Test
  public void should_invoke_method_without_parameters_and_primitive_return_type() {
    List<String> list = newArrayList();
    list.add("Test");
    int size = Reflection.method("size").withReturnType(int.class).in(list).invoke();
    assertEquals(1, size);
  }

  @Test
  public void should_invoke_method_without_parameters_and_Object_return_type() {
    List<String> list = newArrayList();
    list.add("Test");
    Object[] array = Reflection.method("toArray").withReturnType(Object[].class).in(list).invoke();
    assertEquals(1, array.length);
    assertEquals("Test", array[0]);
  }

  @Test
  public void should_invoke_method_without_parameters_and_TypeRef_return_type() {
    List<String> list = newArrayList();
    list.add("Test");
    Iterator<String> iterator =
        Reflection.method("iterator").withReturnType(new TypeRef<Iterator<String>>() {}).in(list).invoke();
    assertEquals(list, newArrayList(iterator));
  }

  @Test
  public void should_invoke_method_with_primitive_parameters_and_primitive_return_type() {
    Counter counter = new Counter();
    counter.value = 2;
    int result = Reflection.method("add").withReturnType(int.class).withParameterTypes(int.class).in(counter).invoke(6);
    assertEquals(8, result);
  }

  @Test
  public void should_invoke_method_with_Object_parameters_and_Object_return_type() {
    BigDecimal number = new BigDecimal("10.00");
    BigDecimal result = Reflection.method("add").withReturnType(BigDecimal.class)
                                                .withParameterTypes(BigDecimal.class)
                                                .in(number)
                                                .invoke(ONE);
    assertEquals(new BigDecimal("11.00"), result);
  }

  static class Counter {
    int value;

    int add(int value) {
      this.value += value;
      return this.value;
    }
  }
}
