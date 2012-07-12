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

import static org.fest.reflect.util.ExpectedFailures.expectIllegalArgumentException;
import static org.fest.reflect.util.ExpectedFailures.expectNullPointerException;
import static org.fest.reflect.util.ExpectedFailures.expectReflectionError;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.fest.reflect.Jedi;
import org.fest.reflect.exception.ReflectionError;
import org.fest.reflect.reference.TypeRef;
import org.fest.test.CodeToTest;

/**
 * Tests for the fluent interface for accessing methods.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class Method_method_Test {

  private Jedi jedi;

  @Before
  public void setUp() {
    jedi = new Jedi("Luke");
  }

  @Test
  public void should_throw_error_if_method_name_is_null() {
    expectNullPointerException("The name of the method to access should not be null").on(new CodeToTest() {
      public void run() {
        MethodName.startMethodAccess(null);
      }
    });
  }

  @Test
  public void should_throw_error_if_method_name_is_empty() {
    expectIllegalArgumentException("The name of the method to access should not be empty").on(new CodeToTest() {
      public void run() {
        MethodName.startMethodAccess("");
      }
    });
  }

  @Test
  public void should_throw_error_if_method_return_type_is_null() {
    String msg = "The return type of the method to access should not be null";
    expectNullPointerException(msg).on(new CodeToTest() {
      public void run() {
        MethodName.startMethodAccess("setName").withReturnType((Class<?>) null);
      }
    });
  }

  @Test
  public void should_throw_error_if_method_return_TypeRef_is_null() {
    String msg = "The return type reference of the method to access should not be null";
    expectNullPointerException(msg).on(new CodeToTest() {
      public void run() {
        MethodName.startMethodAccess("setName").withReturnType((TypeRef<?>) null);
      }
    });
  }

  @Test
  public void should_throw_error_if_method_parameter_array_is_null() {
    String msg = "The array of parameter types for the method to access should not be null";
    expectNullPointerException(msg).on(new CodeToTest() {
      public void run() {
        Class<?>[] parameterTypes = null;
        MethodName.startMethodAccess("setName").withParameterTypes(parameterTypes);
      }
    });
  }

  @Test
  public void should_throw_error_if_method_target_is_null() {
    expectNullPointerException("Target should not be null").on(new CodeToTest() {
      public void run() {
        MethodName.startMethodAccess("setName").in(null);
      }
    });
  }

  @Test
  public void should_call_method_with_args_and_no_return_value() {
    MethodName.startMethodAccess("setName").withParameterTypes(String.class).in(jedi).invoke("Leia");
    assertEquals("Leia", jedi.getName());
  }

  @Test
  public void should_call_method_with_no_args_and_return_value() {
    String personName = MethodName.startMethodAccess("getName").withReturnType(String.class).in(jedi).invoke();
    assertEquals("Luke", personName);
  }

  @Test
  public void should_call_method_with_args_and_return_value() {
    jedi.addPower("healing");
    String power = MethodName.startMethodAccess("powerAt").withReturnType(String.class).withParameterTypes(int.class).in(jedi)
        .invoke(0);
    assertEquals("healing", power);
  }

  @Test
  public void should_call_method_with_no_args_and_return_TypeRef() {
    jedi.addPower("jump");
    List<String> powers = MethodName.startMethodAccess("powers").withReturnType(new TypeRef<List<String>>() {}).in(jedi).invoke();
    assertEquals(1, powers.size());
    assertEquals("jump", powers.get(0));
  }

  @Test
  public void should_call_method_with_args_and_return_TypeRef() {
    jedi.addPower("healing");
    jedi.addPower("jump");
    String method = "powersThatStartWith";
    List<String> powers = MethodName.startMethodAccess(method).withReturnType(new TypeRef<List<String>>() {})
        .withParameterTypes(String.class).in(jedi).invoke("ju");
    assertEquals(1, powers.size());
    assertEquals("jump", powers.get(0));
  }

  @Test
  public void should_call_method_with_no_args_and_no_return_value() {
    assertFalse(jedi.isMaster());
    MethodName.startMethodAccess("makeMaster").in(jedi).invoke();
    assertTrue(jedi.isMaster());
  }

  @Test
  public void should_return_real_method() {
    java.lang.reflect.Method method = MethodName.startMethodAccess("setName").withParameterTypes(String.class).in(jedi).info();
    assertNotNull(method);
    assertEquals("setName", method.getName());
    Class<?>[] parameterTypes = method.getParameterTypes();
    assertEquals(1, parameterTypes.length);
    assertEquals(String.class, parameterTypes[0]);
  }

  @Test
  public void should_throw_error_if_method_name_is_invalid() {
    String message = "Unable to find method 'getAge' in org.fest.reflect.Jedi with parameter type(s) []";
    expectReflectionError(message).on(new CodeToTest() {
      public void run() {
        String invalidName = "getAge";
        MethodName.startMethodAccess(invalidName).withReturnType(Integer.class).in(jedi);
      }
    });
  }

  @Test
  public void should_throw_error_if_args_for_method_are_invalid() {
    expectIllegalArgumentException("argument type mismatch").on(new CodeToTest() {
      public void run() {
        int invalidArg = 8;
        MethodName.startMethodAccess("setName").withParameterTypes(String.class).in(jedi).invoke(invalidArg);
      }
    });
  }

  @Test
  public void should_rethrow_RuntimeException_thrown_by_method() {
    try {
      MethodName.startMethodAccess("throwRuntimeException").in(jedi).invoke();
      fail("Expecting an IllegalStateException");
    } catch (IllegalStateException e) {
      assertEquals("Somehow I got in an illegal state", e.getMessage());
    }
  }

  @Test
  public void should_wrap_with_a_ReflectionError_the_checked_Exception_thrown_by_method() {
    try {
      MethodName.startMethodAccess("throwCheckedException").in(jedi).invoke();
      fail("Expecting an ReflectionError");
    } catch (ReflectionError e) {
      Throwable cause = e.getCause();
      assertTrue(cause instanceof Exception);
      assertEquals("I don't know what's wrong", cause.getMessage());
    }
  }
}
