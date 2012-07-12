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
package org.fest.reflect.method;

import static org.fest.reflect.test.ExpectedException.none;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.List;

import org.fest.reflect.exception.ReflectionError;
import org.fest.reflect.reference.TypeRef;
import org.fest.reflect.test.*;
import org.junit.*;

/**
 * Tests for the fluent interface for accessing methods.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class Methods_Test {

  @Rule
  public ExpectedException thrown = none();

  private Jedi jedi;

  @Before
  public void setUp() {
    jedi = new Jedi("Luke");
  }

  @Test
  public void should_throw_error_if_method_return_type_is_null() {
    thrown.expectNullPointerException("The return type of the method to access should not be null");
    Methods.methodWithReturnType((Class<?>) null);
  }

  @Test
  public void should_throw_error_if_method_name_is_null() {
    thrown.expectNullPointerException("The name of the method to access should not be null");
    Methods.methodWithReturnType(String.class).withName(null);
  }

  @Test
  public void should_throw_error_if_method_name_is_empty() {
    thrown.expectIllegalArgumentException("The name of the method to access should not be empty");
    Methods.methodWithReturnType(String.class).withName("");
  }

  @Test
  public void should_throw_error_if_method_return_TypeRef_is_null() {
    thrown.expectNullPointerException("The reference to the return type of the method to access should not be null");
    Methods.methodWithReturnType((TypeRef<?>) null);
  }

  @Test
  public void should_throw_error_if_method_parameter_array_is_null() {
    thrown.expectNullPointerException("The array of parameter types should not be null");
    Class<?>[] parameterTypes = null;
    Methods.methodWithReturnType(String.class).withName("powerAt").withParameterTypes(parameterTypes);
  }

  @Test
  public void should_throw_error_if_target_is_null() {
    thrown.expectNullPointerException("The target object should not be null");
    Methods.methodWithReturnType(String.class).withName("powerAt").withParameterTypes(int.class).in((Object) null);
  }

  @Test
  public void should_call_method_with_args_and_no_return_value() {
    Methods.methodWithName("setName").withParameterTypes(String.class).in(jedi).invoke("Leia");
    assertEquals("Leia", jedi.getName());
  }

  @Test
  public void should_call_method_with_no_args_and_return_value() {
    String personName = Methods.methodWithReturnType(String.class).withName("getName").withNoParameters().in(jedi).invoke();
    assertEquals("Luke", personName);
  }

  @Test
  public void should_call_method_with_args_and_return_value() {
    jedi.addPower("healing");
    String power = Methods.methodWithReturnType(String.class).withName("powerAt").withParameterTypes(int.class).in(jedi)
        .invoke(0);
    assertEquals("healing", power);
  }

  @Test
  public void should_call_method_with_no_args_and_return_TypeRef() {
    jedi.addPower("jump");
    List<String> powers = Methods.methodWithReturnType(new TypeRef<List<String>>() {}).withName("powers").withNoParameters()
        .in(jedi).invoke();
    assertEquals(1, powers.size());
    assertEquals("jump", powers.get(0));
  }

  @Test
  public void should_call_method_with_args_and_return_TypeRef() {
    jedi.addPower("healing");
    jedi.addPower("jump");
    List<String> powers = Methods.methodWithReturnType(new TypeRef<List<String>>() {}).withName("powersThatStartWith")
        .withParameterTypes(String.class).in(jedi).invoke("ju");
    assertEquals(1, powers.size());
    assertEquals("jump", powers.get(0));
  }

  @Test
  public void should_call_method_with_no_args_and_no_return_value() {
    assertFalse(jedi.isMaster());
    Methods.methodWithName("makeMaster").withNoParameters().in(jedi).invoke();
    assertTrue(jedi.isMaster());
  }

  @Test
  public void should_return_real_method() {
    Method method = Methods.methodWithName("setName").withParameterTypes(String.class).in(jedi).info();
    assertNotNull(method);
    assertEquals("setName", method.getName());
    Class<?>[] parameterTypes = method.getParameterTypes();
    assertEquals(1, parameterTypes.length);
    assertEquals(String.class, parameterTypes[0]);
  }

  @Test
  public void should_throw_error_if_method_name_is_invalid() {
    thrown.expectReflectionError("Unable to find method 'getAge' in org.fest.reflect.test.Jedi with parameter type(s) []");
    Methods.methodWithReturnType(Integer.class).withName("getAge").withNoParameters().in(jedi);
  }

  @Test
  public void should_throw_error_if_args_for_method_are_invalid() {
    thrown.expectIllegalArgumentException("argument type mismatch");
    Methods.methodWithName("setName").withParameterTypes(String.class).in(jedi).invoke(8);
  }

  @Test
  public void should_rethrow_RuntimeException_thrown_by_method() {
    try {
      Methods.methodWithName("throwRuntimeException").withNoParameters().in(jedi).invoke();
      fail("Expecting an IllegalStateException");
    } catch (IllegalStateException e) {
      assertEquals("Somehow I got in an illegal state", e.getMessage());
    }
  }

  @Test
  public void should_wrap_with_a_ReflectionError_the_checked_Exception_thrown_by_method() {
    try {
      Methods.methodWithName("throwCheckedException").withNoParameters().in(jedi).invoke();
      fail("Expecting an ReflectionError");
    } catch (ReflectionError e) {
      Throwable cause = e.getCause();
      assertEquals("I don't know what's wrong", cause.getMessage());
    }
  }

  @Test
  public void should_throw_error_if_target_type_is_null() {
    thrown.expectNullPointerException("The target type should not be null");
    Methods.methodWithReturnType(String.class).withName("powerAt").withParameterTypes(int.class).in((Class<?>) null);
  }

  @Test
  public void should_call_static_method() {
    Jedi.addCommonPower("Jump");
    String power = Methods.methodWithReturnType(String.class).withName("commonPowerAt").withParameterTypes(int.class)
        .in(Jedi.class).invoke(0);
    assertEquals("Jump", power);
  }
}
