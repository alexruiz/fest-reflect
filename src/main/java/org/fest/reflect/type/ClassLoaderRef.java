/*
 * Created on Mar 3, 2011
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
package org.fest.reflect.type;

/**
 * Holds the <code>{@link ClassLoader}</code> to use to load a type.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public interface ClassLoaderRef {

  /**
   * Sets the <code>{@link ClassLoader}</code> to use.
   * @param classLoader the {@code ClassLoader} to use.
   * @return the next object in the fluent interface.
   * @throws NullPointerException if the given {@code ClassLoader} is {@code null}.
   */
  TypeLoader withClassLoader(ClassLoader classLoader);

  /**
   * Specifies that this class' <code>{@link ClassLoader}</code> will be used.
   * @return the next object in the fluent interface.
   */
  TypeLoader withDefaultClassLoader();

}
