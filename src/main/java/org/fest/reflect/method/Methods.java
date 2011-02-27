/*
 * Created on Feb 26, 2011
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
 * Copyright @2011 the original author or authors.
 */
package org.fest.reflect.method;

import org.fest.reflect.reference.TypeRef;

/**
 * TODO.
 *
 * @author
 */
public class Methods {

  private static final Class<?>[] NO_PARAMETERS = new Class<?>[0];

  /**
   * @param type
   * @return
   */
  public static <T> Name<T> methodWithReturnType(Class<T> type) {
    return new FluentMethod<T>(type);
  }

  /**
   * @param typeRef
   * @return
   */
  public static <T> Name<T> methodWithReturnType(TypeRef<T> typeRef) {
    return new FluentMethod<T>(typeRef);
  }

  /**
   *
   */
  public static Name<Void> methodWithReturnTypeVoid() {
    return new FluentMethod<Void>(Void.class);
  }

  private Methods() {}
}
