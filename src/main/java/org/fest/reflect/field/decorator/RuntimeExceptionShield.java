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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * A proxy for shielding from exception
 * @author Ivan Hristov
 * 
 */
public class RuntimeExceptionShield implements InvocationHandler {

  private static final Map<Class<?>, Object> DEFAULT_RETURN_VALUES = new HashMap<Class<?>, Object>();
  private final Object target;
  private final Class<?> exceptionClass;

  // static init - reference JLS
  {
    DEFAULT_RETURN_VALUES.put(byte.class, 0);
    DEFAULT_RETURN_VALUES.put(short.class, 0);
    DEFAULT_RETURN_VALUES.put(int.class, 0);
    DEFAULT_RETURN_VALUES.put(long.class, 0L);
    DEFAULT_RETURN_VALUES.put(float.class, 0f);
    DEFAULT_RETURN_VALUES.put(double.class, 0d);
    DEFAULT_RETURN_VALUES.put(char.class, '\u0000');
    DEFAULT_RETURN_VALUES.put(boolean.class, false);
  }

  public RuntimeExceptionShield(Object target, Class<?> exceptionClass) {
    this.target = target;
    this.exceptionClass = exceptionClass;
  }

  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    try {
      return method.invoke(target, args);
    } catch (InvocationTargetException e) {
      if (!(e.getCause().getClass() == exceptionClass)) throw e.getCause();
      // shield from specified exceptions
    }
    return DEFAULT_RETURN_VALUES.get(method.getReturnType());
  }

}
