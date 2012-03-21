/*
 * Created on Mar 21, 2012
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2006-2009 the original author or authors.
 */
package org.fest.reflect.field;


/**
 * An invoker which provides methods only for returning result from a decorator
 * @author Ivan Hristov
 */
public final class DecoratedResultInvoker<T> {

  private final T target;
  private final T decorator;
  private final Invoker<T> invoker;
  private final Class<?> expectedType;
  private final DecoratorInvocationHandler<T> decoratorInvocationHandler;

  static <T> DecoratedResultInvoker<T> newInvoker(T target, T decorator, Class<?> expectedType, Invoker<T> invoker,
      DecoratorInvocationHandler<T> decoratorInvocationHandler) {
    return createInvoker(target, decorator, expectedType, invoker, decoratorInvocationHandler);
  }

  private static <T> DecoratedResultInvoker<T> createInvoker(T target, T decorator, Class<?> expectedType,
      Invoker<T> invoker, DecoratorInvocationHandler<T> decoratorInvocationHandler) {
    return new DecoratedResultInvoker<T>(target, decorator, expectedType, invoker, decoratorInvocationHandler);
  }

  private DecoratedResultInvoker(T target, T decorator, Class<?> expectedType, Invoker<T> invoker,
      DecoratorInvocationHandler<T> decoratorInvocationHandler) {
    this.target = target;
    this.decorator = decorator;
    this.invoker = invoker;
    this.expectedType = expectedType;
    this.decoratorInvocationHandler = decoratorInvocationHandler;
  }

  /**
   * Specifies that the result from the decorator should be returned.
   * 
   * <pre>
   * NOTE: 
   *    If {@link IgnoringDecoratedExceptionInvoker#ignoringDecoratorExceptions(..)} is used in combination with this
   * method and an exception happens default value will be returned (as defined by JLS) for all primitives or null for all non-primitive
   * 
   * Example 1: If a {@link RuntimeException} happens while executing one of the decorated IExampleService field methods which returns primitive boolean value, 
   * a default value false value will be returned.
   * 
   *     field("fieldName").ofType(IExampleService.class)//
   *        .in(target).postDecoratedWith(postDecoratorService)//
   *        .returningDecoratorResult().ignoringDecoratorExceptions();
   *        
   * NOTE:       
   *    In case of several decorator attached to a field, the result from the latest will be returned.
   * 
   * Example 1: the result from the preDecoratorService will be returned
   *   field("fieldName").ofType(IExampleService.class).in(target)//
   *     .preDecoratedWith(preDecoratorService).returningDecoratorResult();
   *     
   * Example 2: the result from the postDecoratorService will be returned
   *   field("fieldName").ofType(IExampleService.class).in(target)//
   *     .postDecoratedWith(postDecoratorService).returningDecoratorResult();
   *     
   * Example 3: the result from the preDecoratorService will be returned, since it's the latest attached decorator
   *    field("fieldName").ofType(IExampleService.class).in(target)//
   *     .postDecoratedWith(postDecoratorService).returningDecoratorResult()//
   *     .preDecoratedWith(preDecoratorService).returningDecoratorResult();
   * </pre>
   * 
   */
  public IgnoringDecoratedExceptionInvoker<T> returningDecoratorResult() {
    decoratorInvocationHandler.setReturnDecoratorResult(true);
    return IgnoringDecoratedExceptionInvoker.newInvoker(target, decorator, expectedType, invoker,
        decoratorInvocationHandler);
  }

  /**
   * Adds additional pre-decorator to an already decorated field; Note that if there are more than one pre-decorators
   * assigned to a field they will be executed starting from the last attached decorator.
   * @param decorator
   * @return
   */
  public DecoratedInvoker<T> preDecoratedWith(T decorator) {
    return invoker.preDecoratedWith(decorator);
  }

  /**
   * Adds additional post-decorator to an already decorated field; Note that if there are more than one post-decorators
   * assigned to a field they will be executed starting from the first attached decorator.
   * @param decorator
   * @return
   */
  public DecoratedInvoker<T> postDecoratedWith(T decorator) {
    return invoker.postDecoratedWith(decorator);
  }
}
