/*
 * Created on Nov 23, 2009
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
package org.fest.reflect.method;

import static org.fest.reflect.util.ExpectedFailures.expectIllegalArgumentException;
import static org.fest.reflect.util.ExpectedFailures.expectNullPointerException;
import static org.fest.reflect.util.ExpectedFailures.expectReflectionError;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.fest.reflect.Jedi;
import org.fest.reflect.reference.TypeRef;
import org.fest.test.CodeToTest;

/**
 * Tests for the fluent interface for accessing static methods.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class Method_staticMethod_Test {

  @Before
  public void setUp() {
    Jedi.clearCommonPowers();
  }

  @Test
  public void should_throw_error_if_static_method_name_is_null() {
    expectNullPointerException("The name of the static method to access should not be null").on(new CodeToTest() {
      public void run() {
        StaticMethodName.startStaticMethodAccess(null);
      }
    });
  }

  @Test
  public void should_throw_error_if_static_method_name_is_empty() {
    expectIllegalArgumentException("The name of the static method to access should not be empty").on(new CodeToTest() {
      public void run() {
        StaticMethodName.startStaticMethodAccess("");
      }
    });
  }

  @Test
  public void should_throw_error_if_static_method_return_type_is_null() {
    String msg = "The return type of the static method to access should not be null";
    expectNullPointerException(msg).on(new CodeToTest() {
      public void run() {
        StaticMethodName.startStaticMethodAccess("commonPowerCount").withReturnType((Class<?>) null);
      }
    });
  }

  @Test
  public void should_throw_error_if_static_method_return_TypeRef_is_null() {
    String msg = "The return type reference of the static method to access should not be null";
    expectNullPointerException(msg).on(new CodeToTest() {
      public void run() {
        StaticMethodName.startStaticMethodAccess("commonPowerCount").withReturnType((TypeRef<?>) null);
      }
    });
  }

  @Test
  public void should_throw_error_if_static_method_parameter_array_is_null() {
    String msg = "The array of parameter types for the static method to access should not be null";
    expectNullPointerException(msg).on(new CodeToTest() {
      public void run() {
        Class<?>[] parameterTypes = null;
        StaticMethodName.startStaticMethodAccess("commonPowerCount").withParameterTypes(parameterTypes);
      }
    });
  }

  @Test
  public void should_throw_error_if_static_method_target_is_null() {
    expectNullPointerException("Target should not be null").on(new CodeToTest() {
      public void run() {
        StaticMethodName.startStaticMethodAccess("commonPowerCount").in(null);
      }
    });
  }

  @Test
  public void should_call_static_method_with_no_args_and_return_value() {
    Jedi.addCommonPower("Jump");
    int count = StaticMethodName.startStaticMethodAccess("commonPowerCount").withReturnType(int.class).in(Jedi.class).invoke();
    assertEquals(Jedi.commonPowerCount(), count);
  }

  @Test
  public void should_call_static_method_with_args_and_return_value() {
    Jedi.addCommonPower("Jump");
    String power = StaticMethodName.startStaticMethodAccess("commonPowerAt").withReturnType(String.class)
        .withParameterTypes(int.class).in(Jedi.class).invoke(0);
    assertEquals("Jump", power);
  }

  @Test
  public void should_call_static_method_with_no_args_and_return_TypeRef() {
    Jedi.addCommonPower("jump");
    String method = "commonPowers";
    List<String> powers = StaticMethodName.startStaticMethodAccess(method).withReturnType(new TypeRef<List<String>>() {})
        .in(Jedi.class).invoke();
    assertEquals(1, powers.size());
    assertEquals("jump", powers.get(0));
  }

  @Test
  public void should_call_static_method_with_args_and_return_TypeRef() {
    Jedi.addCommonPower("jump");
    String method = "commonPowersThatStartWith";
    List<String> powers = StaticMethodName.startStaticMethodAccess(method).withReturnType(new TypeRef<List<String>>() {})
        .withParameterTypes(String.class).in(Jedi.class).invoke("ju");
    assertEquals(1, powers.size());
    assertEquals("jump", powers.get(0));
  }

  @Test
  public void should_call_static_method_with_args_and_no_return_value() {
    StaticMethodName.startStaticMethodAccess("addCommonPower").withParameterTypes(String.class).in(Jedi.class).invoke("Jump");
    assertEquals("Jump", Jedi.commonPowerAt(0));
  }

  @Test
  public void should_call_static_method_with_no_args_and_no_return_value() {
    Jedi.addCommonPower("Jump");
    assertEquals(1, Jedi.commonPowerCount());
    assertEquals("Jump", Jedi.commonPowerAt(0));
    StaticMethodName.startStaticMethodAccess("clearCommonPowers").in(Jedi.class).invoke();
    assertEquals(0, Jedi.commonPowerCount());
  }

  @Test
  public void should_throw_error_if_static_method_name_is_invalid() {
    String message = "Unable to find method 'powerSize' in org.fest.reflect.Jedi with parameter type(s) []";
    expectReflectionError(message).on(new CodeToTest() {
      public void run() {
        String invalidName = "powerSize";
        StaticMethodName.startStaticMethodAccess(invalidName).in(Jedi.class);
      }
    });
  }

  @Test
  public void should_throw_error_if_args_for_static_method_are_invalid() {
    expectIllegalArgumentException("argument type mismatch").on(new CodeToTest() {
      public void run() {
        int invalidArg = 8;
        StaticMethodName.startStaticMethodAccess("addCommonPower").withParameterTypes(String.class).in(Jedi.class)
            .invoke(invalidArg);
      }
    });
  }
}
