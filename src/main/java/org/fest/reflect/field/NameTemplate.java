/*
 * Created on Feb 6, 2008
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.reflect.field;

import static org.fest.util.Strings.isEmpty;

/**
 * Understands a template for the name of a field to access using Java Reflection.
 *
 * @author Alex Ruiz
 */
class NameTemplate {
  final String name;

  public NameTemplate(String name) {
    if (name == null) 
      throw new NullPointerException("The name of the field to access should not be null");
    if (isEmpty(name)) 
      throw new IllegalArgumentException("The name of the field to access should not be empty");
    this.name = name;
  }
}
