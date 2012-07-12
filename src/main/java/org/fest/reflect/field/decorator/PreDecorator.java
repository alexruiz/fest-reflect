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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A proxy for pre-decorating a field
 * @author Ivan Hristov
 * 
 */
public class PreDecorator<T> extends DecoratorInvocationHandler<T> {

  public PreDecorator(T target, T decorator) {
    super(target, decorator);
  }

  @Override
  protected Object invokeFirst(Method method, Object[] args) throws IllegalArgumentException, IllegalAccessException,
      InvocationTargetException {
    return method.invoke(getDecorator(), args);
  }

  @Override
  protected Object invokeSecond(Method method, Object[] args) throws IllegalArgumentException, IllegalAccessException,
      InvocationTargetException {
    return method.invoke(getTarget(), args);
  }

  @Override
  protected Object getResult(Object firstResult, Object secondResult) {
    return shouldReturnDecoratorResult() ? firstResult : secondResult;
  }
}
