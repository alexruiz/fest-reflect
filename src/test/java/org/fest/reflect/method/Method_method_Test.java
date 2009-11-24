/*
 * Created on May 18, 2007
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.reflect.method;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.util.ExpectedFailures.*;

import java.util.List;

import org.fest.reflect.Jedi;
import org.fest.reflect.reference.TypeRef;
import org.fest.test.CodeToTest;
import org.junit.Before;
import org.junit.Test;

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
    Jedi.clearCommonPowers();
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
        MethodName.startMethodAccess("setName").withReturnType((Class<?>)null);
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
    assertThat(jedi.getName()).isEqualTo("Leia");
  }

  @Test
  public void should_call_method_with_no_args_and_return_value() {
    String personName = MethodName.startMethodAccess("getName").withReturnType(String.class).in(jedi).invoke();
    assertThat(personName).isEqualTo("Luke");
  }

  @Test
  public void should_call_method_with_args_and_return_value() {
    jedi.addPower("healing");
    String power = MethodName.startMethodAccess("powerAt").withReturnType(String.class)
                                            .withParameterTypes(int.class)
                                            .in(jedi).invoke(0);
    assertThat(power).isEqualTo("healing");
  }

  @Test
  public void should_call_method_with_no_args_and_return_TypeRef() {
    jedi.addPower("jump");
    List<String> powers = MethodName.startMethodAccess("powers").withReturnType(new TypeRef<List<String>>() {})
                                                                .in(jedi).invoke();
    assertThat(powers).containsOnly("jump");
  }

  @Test
  public void should_call_method_with_args_and_return_TypeRef() {
    jedi.addPower("healing");
    jedi.addPower("jump");
    String method = "powersThatStartWith";
    List<String> powers =  MethodName.startMethodAccess(method).withReturnType(new TypeRef<List<String>>() {})
                                                               .withParameterTypes(String.class).in(jedi).invoke("ju");
    assertThat(powers).containsOnly("jump");
  }

  @Test
  public void should_call_method_with_no_args_and_no_return_value() {
    assertThat(jedi.isMaster()).isFalse();
    MethodName.startMethodAccess("makeMaster").in(jedi).invoke();
    assertThat(jedi.isMaster()).isTrue();
  }

  @Test
  public void should_return_real_method() {
    java.lang.reflect.Method method = MethodName.startMethodAccess("setName").withParameterTypes(String.class)
                                                                             .in(jedi).info();
    assertThat(method).isNotNull();
    assertThat(method.getName()).isEqualTo("setName");
    Class<?>[] parameterTypes = method.getParameterTypes();
    assertThat(parameterTypes).hasSize(1);
    assertThat(parameterTypes[0]).isEqualTo(String.class);
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
}
