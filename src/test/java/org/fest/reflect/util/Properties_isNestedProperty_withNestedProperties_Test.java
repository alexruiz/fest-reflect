/*
 * Created on Jun 2, 2010
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
 * Copyright @2010 the original author or authors.
 */
package org.fest.reflect.util;

import static org.fest.reflect.util.Properties.isNestedProperty;
import static org.fest.util.Collections.list;

import java.util.Collection;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for <code>{@link Properties#isNestedProperty(String)}</code>
 *
 * @author Joel Costigliola
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class Properties_isNestedProperty_withNestedProperties_Test {

  @Parameters
  public static Collection<Object[]> propertyNames() {
    return list(new Object[][] {
        { "person.name" },
        { "address.street" }
    });
  }

  private final String propertyName;

  public Properties_isNestedProperty_withNestedProperties_Test(String propertyName) {
    this.propertyName = propertyName;
  }

  @Test
  public void should_return_true_if_property_is_nested() {
    assertTrue(isNestedProperty(propertyName));
  }
}
