/*
 * Created on Aug 17, 2006
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
 * Copyright @2006-2009 the original author or authors.
 */
package org.fest.reflect.field;

import static org.fest.reflect.field.Invoker.newInvoker;

import java.util.LinkedList;
import java.util.List;

import org.fest.reflect.core.Reflection;
import org.fest.reflect.exception.ReflectionError;

/**
 * Understands the type of a field to access using Java Reflection.
 * <p>
 * The following is an example of proper usage of this class:
 * 
 * <pre>
 * // Retrieves the value of the field "name"
 * String name = {@link org.fest.reflect.core.Reflection#field(String) field}("name").{@link FieldName#ofType(Class) ofType}(String.class).{@link FieldType#in(Object) in}(person).{@link Invoker#get() get}();
 *
 * // Sets the value of the field "name" to "Yoda"
 * {@link org.fest.reflect.core.Reflection#field(String) field}("name").{@link FieldName#ofType(Class) ofType}(String.class).{@link FieldType#in(Object) in}(person).{@link Invoker#set(Object) set}("Yoda");
 * </pre>
 * </p>
 * 
 * @param <T> the generic type of the field.
 * 
 * @author Alex Ruiz
 */
public class FieldType<T> {

  private final List<String> listOfNestedFields;

  static <T> FieldType<T> newFieldType(String name, Class<T> type) {
    if (type == null) throw new NullPointerException("The type of the field to access should not be null");
    return new FieldType<T>(name, type);
  }

  private final String name;
  private final Class<T> type;

  private FieldType(String name, Class<T> type) {
    this.name = name;
    this.type = type;
    this.listOfNestedFields = new LinkedList<String>();
  }

  /**
   * Returns a new field access invoker, capable of accessing (read/write) the underlying field.
   * @param target the object containing the field of interest.
   * @return the created field access invoker.
   * @throws NullPointerException if the given target is <code>null</code>.
   * @throws ReflectionError if a field with a matching name and type cannot be found.
   */
  public Invoker<T> in(Object target) {
    Object nestedTarget = null;
    int size = listOfNestedFields.size();

    for (--size; size >= 0; size--) {
      String fieldName = listOfNestedFields.remove(size);
      nestedTarget = Invoker.getNestedField(fieldName, nestedTarget == null ? target : nestedTarget);
    }

    return newInvoker(name, type, nestedTarget == null ? target : nestedTarget);
  }

  /**
   * Use this method to define the path to a targeted field (specified via {@link Reflection#field(String)} ), starting
   * from the nearest {@code nestedField} aggregating the targeted field. Building the path starting from the bottom to
   * the top-level instance (to which presumably you hold a reference):
   * 
   * <pre>
   * For example, let's say we have the following simple service:
   * 
   * public class BusinessService {
   *   private NotificationService notificationService = new NotificationService();
   *   //... logic goes here
   * }
   *  
   * Where NotificationService is defined as follows:
   * 
   * public class NotificationService {
   *   private Logger logger = new Logger();
   *   private IClientStatusDao clientStatusDao = new ClientStatusDao();
   *   //... logic goes here
   * }
   * 
   * And our ClientStatusDao looks like:
   * 
   * public class ClientStatusDao implements IClientStatusDao {
   *   private final Session session = new SessionImpl();
   *    //... logic goes here
   * }
   *     
   * Assuming we have only a reference to an instance of {@code BusinessService} (which is the top-level of our 
   * aggregation chain), we can use FEST as follows in order to set the nested {@code logger} field within 
   * the (nested) {@code NotificationService} in like this:
   *  
   *   field("logger").ofType(Logger.class).withinField("notificationService").in(businessService).set(loggerMock);
   *  
   * If you want you can also set the deeply nested {@code session} field within {@code ClientStatusDao} like this:
   * 
   *   field("session").ofType(Session.class).withinField("clientStatusDao").withinField("notificationService")
   *     .in(businessService).set(sessionMock);
   * 
   * And yes, you can also set, decorate, or do other interesting things with even more deeply nested fields. 
   * Just try out!  
   * </pre>
   * @param nestedField
   * @return
   */
  public FieldType<T> withinField(String nestedField) {
    listOfNestedFields.add(nestedField);
    return this;
  }
}