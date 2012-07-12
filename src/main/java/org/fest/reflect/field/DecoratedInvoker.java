/*
 * Created on Mar 18, 2012
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
package org.fest.reflect.field;

import java.lang.reflect.Proxy;

import org.fest.reflect.field.decorator.DecoratorInvocationHandler;
import org.fest.reflect.field.decorator.RuntimeExceptionShield;

/**
 * A decorated invoker allowing to ignore some exceptions or returning decorator result instead of field result.
 * @author Ivan Hristov
 */
public final class DecoratedInvoker<T> {

  private final T target;
  private final T decorator;
  private final Invoker<T> invoker;
  private final Class<?> expectedType;
  private final DecoratorInvocationHandler<T> decoratorInvocationHandler;

  static <T> DecoratedInvoker<T> newInvoker(T target, T decorator, Class<?> expectedType, Invoker<T> invoker,
      DecoratorInvocationHandler<T> decoratorInvocationHandler) {
    return new DecoratedInvoker<T>(target, decorator, expectedType, invoker, decoratorInvocationHandler);
  }

  private DecoratedInvoker(T target, T decorator, Class<?> expectedType, Invoker<T> invoker,
      DecoratorInvocationHandler<T> decoratorInvocationHandler) {
    this.target = target;
    this.decorator = decorator;
    this.invoker = invoker;
    this.expectedType = expectedType;
    this.decoratorInvocationHandler = decoratorInvocationHandler;
  }

  /**
   * Ignores any {@link RuntimeException} which comes from the preceding decorator.
   * @return the DecoratedResultInvoker ignoring exceptions.
   */
  public DecoratedInvoker<T> ignoringDecoratorExceptions() {
    return ignoringDecoratorExceptionsOfType(RuntimeException.class);
  }

  /**
   * Ignores any exception of the {@code exceptionClass} type which comes from the preceding decorator.
   * @param exceptionClass the exception to ignore - usually a checked exception of decorator method
   * @return the DecoratedResultInvoker ignoring given exception type.
   */
  public DecoratedInvoker<T> ignoringDecoratorExceptionsOfType(Class<?> exceptionClass) {
    RuntimeExceptionShield runtimeExceptionShield = new RuntimeExceptionShield(decorator, exceptionClass);
    @SuppressWarnings("unchecked")
    T exceptionSafeDecorator = (T) Proxy.newProxyInstance(decorator.getClass().getClassLoader(),//
        new Class[] { expectedType }, runtimeExceptionShield);

    decoratorInvocationHandler.setDecorator(exceptionSafeDecorator);

    return newInvoker(target, exceptionSafeDecorator, expectedType, invoker, decoratorInvocationHandler);
  }

  /**
   * Specifies that the result from the decorator should be returned.
   * <p>
   * If {@link DecoratedInvoker#ignoringDecoratorExceptions() ignoringDecoratorExceptions()} is used in combination with this
   * method and an exception is thrown, the default value will be returned (as defined by JLS) for all primitives or null for all
   * non-primitive.
   * <p>
   * Example :<br>
   * If a {@link RuntimeException} is thrown while executing one of the decorated <code>IExampleService</code> field methods which
   * returns primitive boolean value, the default value <i>false</i> will be returned.
   * 
   * <pre>
   * field("fieldName").ofType(IExampleService.class).in(target)
   *                   .postDecorateWith(postDecoratorService)
   *                   .returningDecoratorResult()
   *                   .ignoringDecoratorExceptions();
   * </pre>
   * In case of several decorators attached to a field, the result from the latest will be returned.
   * <p>
   * Example 1:<br>
   * The result from the <b>pre</b>DecoratorService will be returned
   * 
   * <pre>
   * field("fieldName").ofType(IExampleService.class).in(target)
   *                   .preDecorateWith(preDecoratorService)
   *                   .returningDecoratorResult();
   * </pre>
   * Example 2:<br>
   * The result from the <b>post</b>DecoratorService will be returned
   * 
   * <pre>
   * field("fieldName").ofType(IExampleService.class).in(target)
   *                   .postDecorateWith(postDecoratorService)
   *                   .returningDecoratorResult();
   * </pre>
   * Example 3:<br>
   * The result from the <b>pre</b>DecoratorService will be returned, since it's the <b>latest</b> attached decorator.
   * 
   * <pre>
   * field("fieldName").ofType(IExampleService.class).in(target)
   *                   .postDecorateWith(postDecoratorService)
   *                   .returningDecoratorResult()
   *                   .<b>pre</b>DecorateWith(preDecoratorService)
   *                   .returningDecoratorResult();
   * </pre>
   */
  public DecoratedInvoker<T> returningDecoratorResult() {
    decoratorInvocationHandler.setReturnDecoratorResult(true);
    return newInvoker(target, decorator, expectedType, invoker, decoratorInvocationHandler);
  }

  /**
   * Adds a pre-decorator to an already decorated field.
   * <p>
   * Note that if there are more than one pre-decorators assigned to a field they will be executed starting from the last attached
   * decorator.
   * @param decorator which methods be called before the same targeted object methods
   * @return the {@link DecoratedInvoker} pre decorating the target field interface with given decorator.
   */
  public DecoratedInvoker<T> preDecorateWith(T decorator) {
    return invoker.preDecorateWith(decorator);
  }

  /**
   * Adds a post-decorator to an already decorated field
   * <p>
   * Note that if there are more than one post-decorators assigned to a field they will be executed starting from the first
   * attached decorator.
   * @param decorator which methods be called after the same targeted object methods
   * @return the {@link DecoratedInvoker} post decorating the target field interface with given decorator.
   */
  public DecoratedInvoker<T> postDecorateWith(T decorator) {
    return invoker.postDecorateWith(decorator);
  }
}
