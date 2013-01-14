/*
 * Created on Dec 16, 2012
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
 * Copyright @2012-2013 the original author or authors.
 */
package org.fest.reflect.util;

import static org.fest.util.Preconditions.checkNotNull;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Utility methods related to Java types.
 * 
 * @author Alex Ruiz
 */
public final class Types {
  private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER_TYPE = new HashMap<Class<?>, Class<?>>();

  static {
    boolean.class.isPrimitive();
    PRIMITIVE_TO_WRAPPER_TYPE.put(boolean.class, Boolean.class);
    PRIMITIVE_TO_WRAPPER_TYPE.put(byte.class, Byte.class);
    PRIMITIVE_TO_WRAPPER_TYPE.put(short.class, Short.class);
    PRIMITIVE_TO_WRAPPER_TYPE.put(int.class, Integer.class);
    PRIMITIVE_TO_WRAPPER_TYPE.put(long.class, Long.class);
    PRIMITIVE_TO_WRAPPER_TYPE.put(float.class, Float.class);
    PRIMITIVE_TO_WRAPPER_TYPE.put(double.class, Double.class);
    PRIMITIVE_TO_WRAPPER_TYPE.put(char.class, Character.class);
  }

  /**
   * Casts the given object to the given type. This method handles primitive types properly.
   * 
   * @param o the object to cast.
   * @param type the type to cast the given object to.
   * @return the given object casted to the given type.
   */
  public static <T> T castSafely(@Nullable Object o, @Nonnull Class<T> type) {
    checkNotNull(type);
    if (type.isPrimitive()) {
      return getWrapperType(type).cast(o);
    }
    return type.cast(o);
  }

  @SuppressWarnings("unchecked")
  private static @Nonnull <T> Class<T> getWrapperType(@Nonnull Class<T> primitiveClass) {
    checkNotNull(primitiveClass);
    if (!primitiveClass.isPrimitive()) {
      String msg = String.format("The class %s is not a primitive type", primitiveClass.getName());
      throw new IllegalArgumentException(msg);
    }
    return (Class<T>) checkNotNull(PRIMITIVE_TO_WRAPPER_TYPE.get(primitiveClass));
  }

  private Types() {}
}
