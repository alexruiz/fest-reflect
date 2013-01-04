/*
 * Created on Mar 19, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.reflect.util;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Nullable;

/**
 * Understands utility methods related to {@code Throwable}s.
 * 
 * @author Alex Ruiz
 * @since 1.2
 */
public final class Throwables {
  /**
   * Obtains the target of the given {@code Throwable}. If the {@code Throwable} is a {@code InvocationTargetException},
   * this method will return the "target exception" (not the cause.) For other {@code Throwable}s, the same instance is
   * returned unmodified.
   * 
   * @param t the given {@code Throwable}.
   * @return the target exception, if applicable. Otherwise, this method returns the same {@code Throwable} passed as
   *         argument.
   * @see InvocationTargetException
   */
  public static @Nullable Throwable targetOf(@Nullable Throwable t) {
    if (t instanceof InvocationTargetException) {
      return ((InvocationTargetException) t).getTargetException();
    }
    return t;
  }

  private Throwables() {}
}
