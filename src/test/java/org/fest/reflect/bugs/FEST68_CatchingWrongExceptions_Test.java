/*
 * Created on Mar 19, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.reflect.bugs;

import static org.fest.reflect.constructor.Constructors.constructorIn;
import static org.fest.reflect.method.Methods.methodWithName;

import org.junit.Test;

/**
 * Tests for <a href="http://jira.codehaus.org/browse/FEST-68" target="_blank">FEST-68</a>.
 *
 * @author Francis ANDRE
 * @author Alex Ruiz
 */
public class FEST68_CatchingWrongExceptions_Test {

  @Test(expected = MyRuntimeException.class)
  public void should_not_catch_RuntimeException_when_calling_method() {
    Main main = new Main();
    methodWithName("set").withNoParameters().in(main).invoke();
  }

  @Test(expected = MyRuntimeException.class)
  public void should_not_catch_RuntimeException_when_calling_constructor() {
    constructorIn(Main.class).withParameterTypes(String.class).newInstance("Hello");
  }

  static class MyRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public MyRuntimeException(String text) {
      super(text);
    }
  }

  public static class Main {
    public Main() {}

    public Main(String hello) {
      throw new MyRuntimeException("Main");
    }

    public void set()  {
      throw new MyRuntimeException("set");
    }
  }
}
