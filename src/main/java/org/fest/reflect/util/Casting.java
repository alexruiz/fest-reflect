/*
 * Created on Feb 27, 2011
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
 * Copyright @2011 the original author or authors.
 */
package org.fest.reflect.util;

import java.util.*;

/**
 * Casts an object to a given type, safely and effectively.
 * 
 * @author Yvonne Wang
 */
public final class Casting {

  private static final Map<Class<?>, Class<?>> PRIMITIVES = new HashMap<Class<?>, Class<?>>();

  static {
    PRIMITIVES.put(boolean.class, Boolean.class);
    PRIMITIVES.put(byte.class, Byte.class);
    PRIMITIVES.put(char.class, Character.class);
    PRIMITIVES.put(double.class, Double.class);
    PRIMITIVES.put(float.class, Float.class);
    PRIMITIVES.put(int.class, Integer.class);
    PRIMITIVES.put(long.class, Long.class);
    PRIMITIVES.put(short.class, Short.class);
  }

  @SuppressWarnings("unchecked")
  public static <T> T cast(Object o, Class<T> type) {
    if (!type.isPrimitive()) return type.cast(o);
    return (T) PRIMITIVES.get(type).cast(o);
  }

  private Casting() {}

}
