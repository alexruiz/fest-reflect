/*
 * Created on Jan 1, 2008
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
 * Copyright @2008-2013 the original author or authors.
 */
package org.fest.reflect.util;

import org.jetbrains.annotations.NotNull;
import java.lang.reflect.AccessibleObject;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.fest.util.Preconditions.checkNotNull;

/**
 * Utility methods related to {@code AccessibleObject}s.
 *
 * @author Alex Ruiz
 */
public final class Accessibles {
  private static Logger logger = Logger.getLogger(Accessibles.class.getCanonicalName());

  private Accessibles() {
  }

  /**
   * Sets the {@code accessible} flag of the given {@code AccessibleObject} to the given {@code boolean} value, ignoring
   * any thrown exception.
   *
   * @param o          the given {@code AccessibleObject}.
   * @param accessible the value to set the {@code accessible} flag to.
   */
  public static void setAccessibleIgnoringExceptions(@NotNull AccessibleObject o, boolean accessible) {
    try {
      setAccessible(o, accessible);
    } catch (RuntimeException ignored) {
      String format = "Failed to set 'accessible' flag of %s to %s";
      logger.log(Level.SEVERE, String.format(format, o.toString(), String.valueOf(accessible)), ignored);
    }
  }

  /**
   * Sets the {@code accessible} flag of the given {@code AccessibleObject} to {@code true}.
   *
   * @param o the given {@code AccessibleObject}.
   * @throws NullPointerException if the given  {@code AccessibleObject} is {@code null}.
   * @throws SecurityException    if the request is denied.
   */
  public static void makeAccessible(@NotNull AccessibleObject o) {
    setAccessible(o, true);
  }

  /**
   * Sets the {@code accessible} flag of the given {@code AccessibleObject} to the given {@code boolean} value.
   *
   * @param o          the given {@code AccessibleObject}.
   * @param accessible the value to set the {@code accessible} flag to.
   * @throws NullPointerException if the given  {@code AccessibleObject} is {@code null}.
   * @throws SecurityException    if the request is denied.
   */
  public static void setAccessible(@NotNull AccessibleObject o, boolean accessible) {
    AccessController.doPrivileged(new SetAccessibleAction(o, accessible));
  }

  private static class SetAccessibleAction implements PrivilegedAction<Void> {
    private final AccessibleObject o;
    private final boolean accessible;

    SetAccessibleAction(@NotNull AccessibleObject o, boolean accessible) {
      this.o = checkNotNull(o);
      this.accessible = accessible;
    }

    @Override
    public Void run() {
      o.setAccessible(accessible);
      return null;
    }
  }
}
