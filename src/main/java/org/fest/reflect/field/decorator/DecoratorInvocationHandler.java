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

/**
 * @author Ivan Hristov
 * 
 */
public abstract class DecoratorInvocationHandler<T> implements InvocationHandler {

  private final T target;
  private T decorator;
  private boolean returnDecoratorResult = false;

  public DecoratorInvocationHandler(T target, T decorator) {
    this.target = target;
    this.decorator = decorator;
  }

  public void setDecorator(T decorator) {
    this.decorator = decorator;
  }

  public T getTarget() {
    return target;
  }

  public T getDecorator() {
    return decorator;
  }

  public final Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

    Object firstResult = null;
    try {
      firstResult = invokeFirst(method, args);
    } catch (InvocationTargetException e) {
      throw e.getCause();
    }

    Object secondResult = null;
    try {
      secondResult = invokeSecond(method, args);
    } catch (InvocationTargetException e) {
      throw e.getCause();
    }

    return getResult(firstResult, secondResult);
  }

  protected abstract Object invokeFirst(Method method, Object[] args) throws IllegalArgumentException, IllegalAccessException,
      InvocationTargetException;

  protected abstract Object invokeSecond(Method method, Object[] args) throws IllegalArgumentException, IllegalAccessException,
      InvocationTargetException;

  protected abstract Object getResult(Object firstResult, Object secondResult);

  public void setReturnDecoratorResult(boolean returnDecoratorResult) {
    this.returnDecoratorResult = returnDecoratorResult;
  }

  protected boolean shouldReturnDecoratorResult() {
    return returnDecoratorResult;
  }
}
