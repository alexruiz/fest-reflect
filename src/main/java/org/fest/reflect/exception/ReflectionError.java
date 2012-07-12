/*
 * Created on Nov 16, 2006
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
package org.fest.reflect.exception;

/**
 * Understands an error raised when using reflection.
 * 
 * @author Alex Ruiz
 */
public final class ReflectionError extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * Creates a new <code>{@link ReflectionError}</code>.
   * @param message the detail message.
   */
  public ReflectionError(String message) {
    super(message);
  }

  /**
   * Creates a new <code>{@link ReflectionError}</code>.
   * @param message the detail message.
   * @param cause the cause of the exception.
   */
  public ReflectionError(String message, Throwable cause) {
    super(message, cause);
  }
}
