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

import static org.fest.util.Collections.list;
import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for <code>{@link Properties#removeFirstPropertyIfNested(String)}</code>
 *
 * @author Joel Costigliola
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class Properties_removeFirstPropertyIfNested_withNestedProperties_Test {

  @Parameters
  public static Collection<Object[]> propertyNames() {
    return list(new Object[][] {
        { "person.name", "name" },
        { "address.street.name", "street.name" }
    });
  }

  private final String propertyName;
  private final String withoutFirstProperty;

  public Properties_removeFirstPropertyIfNested_withNestedProperties_Test(String propertyName,
      String withoutFirstProperty) {
    this.propertyName = propertyName;
    this.withoutFirstProperty = withoutFirstProperty;
  }

  @Test
  public void should_remove_first_property() {
    assertEquals(withoutFirstProperty, Properties.removeFirstPropertyIfNested(propertyName));
  }
}
