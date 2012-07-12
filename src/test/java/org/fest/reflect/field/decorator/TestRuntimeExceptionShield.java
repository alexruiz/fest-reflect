/*
 * Created on Mar 19, 2012
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
 * Copyright @2006-2009 the original author or authors.
 */
package org.fest.reflect.field.decorator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Proxy;

import org.junit.Rule;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
import org.junit.rules.ExpectedException;

/**
 * @author Ivan Hristov
 * 
 */
public class TestRuntimeExceptionShield {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  interface ITestService {
    boolean test() throws IllegalAccessError;
  }

  @Test
  public void shouldShieldFromRuntimeException() {
    // GIVEN
    ITestService testServiceMock = mock(ITestService.class);
    when(testServiceMock.test()).thenThrow(new RuntimeException("Expected test exception"));

    RuntimeExceptionShield runtimeExceptionShield = new RuntimeExceptionShield(testServiceMock, RuntimeException.class);

    ITestService proxyObject = (ITestService) Proxy.newProxyInstance(testServiceMock.getClass().getClassLoader(),//
        new Class[] { ITestService.class }, runtimeExceptionShield);

    // WHEN
    proxyObject.test();

    // THEN no exception should happen
    verify(testServiceMock, times(1)).test();
  }

  @Test
  public void shouldNotShieldFromNonRuntimeException() {
    // GIVEN
    expectedException.expect(IllegalAccessError.class);
    expectedException.expectMessage(JUnitMatchers.containsString("Expected test error"));

    ITestService testServiceMock = mock(ITestService.class);
    when(testServiceMock.test()).thenThrow(new IllegalAccessError("Expected test error"));

    RuntimeExceptionShield runtimeExceptionShield = new RuntimeExceptionShield(testServiceMock, RuntimeException.class);

    ITestService proxyObject = (ITestService) Proxy.newProxyInstance(testServiceMock.getClass().getClassLoader(),//
        new Class[] { ITestService.class }, runtimeExceptionShield);

    // WHEN
    proxyObject.test();

    // THEN there should be an error
  }
}
