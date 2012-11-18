/*
 * Created on Oct 13, 2012
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
 * Copyright @2006-2012 the original author or authors.
 */
package org.fest.reflect.simple;

import org.fest.reflect.core.Reflection;

/**
 * Provides a &quot;fluent&quot; API that makes usage of the Java Reflection API
 * to help achieve some common case scenarios when setting/retrieving state of
 * the objects in tests. If some more advanced scenarios are needed use {@link Reflection} instead.
 * <p>
 * Here are some examples:
 * <pre>
 *    // import static {@link org.fest.reflect.simple.SimpleReflection org.fest.reflect.simple.SimpleReflection}.*;
 * 
 *    // Retrieves the value of the field &quot;logger&quot; from object userService
 *    UserService userService = ...
 *    Logger logger = SimpleReflection.get(Logger.class).from(userService);
 *    
 *    // Sets the value of the field 'logger' to &quot;Yoda&quot;
 *    UserService userService = ...
 *    Logger logger = ...
 *    Logger logger = SimpleReflection.set(logger).to(userService);
 * </pre>
 * 
 * @author Marek Dominiak
 */
public class SimpleReflection {
	/**
	 * Begins the setting/injecting of the valueToSet to some object.
	 * @param valueToSet
	 * @return fluent API interface to set/inject valueToSet to some object.
	 */
	public static SimpleInjector set(Object valueToSet) {
		return SimpleInjector.set(valueToSet);
	}

	/**
	 * Begins the getting/retrieving of the field value of the provided type from some object.
	 * @param fieldValueType
	 * @return fluent API interface to get/retrieve field value of the provided type from some object.
	 */
	public static <T> SimpleRetriever<T> get(Class<T> fieldValueType) {
		return new SimpleRetriever<T>(fieldValueType);
	}
}
