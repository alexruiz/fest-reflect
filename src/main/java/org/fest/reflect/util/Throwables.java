/*
 * Created on Mar 19, 2009
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
package org.fest.reflect.util;

import java.lang.reflect.InvocationTargetException;

/**
 * Understands utility methods related to <code>{@link Throwable}</code>s.
 * 
 * @author Alex Ruiz
 * @since 1.2
 */
public final class Throwables {

  /**
   * Obtains the target of the given <code>{@link Throwable}</code>. If the <code>Throwable</code> is a
   * <code>{@link InvocationTargetException}</code>, this method will return the "target exception" (not the cause.) For other
   * <code>Throwable</code>s, the same instance is returned unmodified.
   * @param t the given <code>Throwable</code>.
   * @return the target exception, if applicable. Otherwise, this method returns the same <code>Throwable</code> passed as
   *         argument.
   */
  public static Throwable targetOf(Throwable t) {
    if (t instanceof InvocationTargetException) return ((InvocationTargetException) t).getTargetException();
    return t;
  }

  private Throwables() {}
}
