/*
 * Created on Oct 31, 2006
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
package org.fest.reflect.core;

import static org.fest.reflect.beanproperty.PropertyName.startPropertyAccess;
import static org.fest.reflect.constructor.TargetType.startConstructorAccess;
import static org.fest.reflect.field.FieldName.beginFieldAccess;
import static org.fest.reflect.field.StaticFieldName.beginStaticFieldAccess;
import static org.fest.reflect.innerclass.StaticInnerClassName.startStaticInnerClassAccess;
import static org.fest.reflect.method.MethodName.startMethodAccess;
import static org.fest.reflect.method.StaticMethodName.startStaticMethodAccess;
import static org.fest.reflect.type.Type.newType;

import org.fest.reflect.beanproperty.PropertyName;
import org.fest.reflect.beanproperty.PropertyType;
import org.fest.reflect.constructor.TargetType;
import org.fest.reflect.field.FieldName;
import org.fest.reflect.field.FieldTypeRef;
import org.fest.reflect.field.StaticFieldName;
import org.fest.reflect.field.StaticFieldType;
import org.fest.reflect.field.StaticFieldTypeRef;
import org.fest.reflect.innerclass.StaticInnerClassName;
import org.fest.reflect.method.Invoker;
import org.fest.reflect.method.MethodName;
import org.fest.reflect.method.MethodReturnTypeRef;
import org.fest.reflect.method.StaticMethodName;
import org.fest.reflect.method.StaticMethodReturnTypeRef;
import org.fest.reflect.reference.TypeRef;
import org.fest.reflect.type.Type;

/**
 * Understands the entry point for the classes in this package.
 * <p>
 * The following is an example of proper usage of the classes in this package:
 * 
 * <pre>
 *   // Loads the class 'org.republic.Jedi'
 *   Class&lt;?&gt; jediType = {@link org.fest.reflect.core.Reflection#type(String) type}("org.republic.Jedi").{@link Type#load() load}();
 *
 *   // Loads the class 'org.republic.Jedi' as 'org.republic.Person' (Jedi extends Person)
 *   Class&lt;Person&gt; jediType = {@link org.fest.reflect.core.Reflection#type(String) type}("org.republic.Jedi").{@link Type#loadAs(Class) loadAs}(Person.class);
 *
 *   // Loads the class 'org.republic.Jedi' using a custom class loader
 *   Class&lt;?&gt; jediType = {@link org.fest.reflect.core.Reflection#type(String) type}("org.republic.Jedi").{@link Type#withClassLoader(ClassLoader) withClassLoader}(myClassLoader).{@link org.fest.reflect.type.TypeLoader#load() load}();
 *
 *   // Gets the inner class 'Master' in the declaring class 'Jedi':
 *   Class&lt;?&gt; masterClass = {@link org.fest.reflect.core.Reflection#staticInnerClass(String) staticInnerClass}("Master").{@link org.fest.reflect.innerclass.StaticInnerClassName#in(Class) in}(Jedi.class).{@link org.fest.reflect.innerclass.Invoker#get() get}();
 *
 *   // Equivalent to call 'new Person()'
 *   Person p = {@link org.fest.reflect.core.Reflection#constructor() constructor}().{@link TargetType#in in}(Person.class).{@link org.fest.reflect.constructor.Invoker#newInstance(Object...) newInstance}();
 *
 *   // Equivalent to call 'new Person("Yoda")'
 *   Person p = {@link org.fest.reflect.core.Reflection#constructor() constructor}().{@link TargetType#withParameterTypes(Class...) withParameterTypes}(String.class).{@link org.fest.reflect.constructor.ParameterTypes#in(Class) in}(Person.class).{@link org.fest.reflect.constructor.Invoker#newInstance(Object...) newInstance}("Yoda");
 *
 *   // Retrieves the value of the field "name"
 *   String name = {@link org.fest.reflect.core.Reflection#field(String) field}("name").{@link org.fest.reflect.field.FieldName#ofType(Class) ofType}(String.class).{@link org.fest.reflect.field.FieldType#in(Object) in}(person).{@link org.fest.reflect.field.Invoker#get() get}();
 *
 *   // Sets the value of the field "name" to "Yoda"
 *   {@link org.fest.reflect.core.Reflection#field(String) field}("name").{@link org.fest.reflect.field.FieldName#ofType(Class) ofType}(String.class).{@link org.fest.reflect.field.FieldType#in(Object) in}(person).{@link org.fest.reflect.field.Invoker#set(Object) set}("Yoda");
 *
 *   // Retrieves the value of the field "powers"
 *   List&lt;String&gt; powers = {@link org.fest.reflect.core.Reflection#field(String) field}("powers").{@link FieldName#ofType(TypeRef) ofType}(new {@link TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link FieldTypeRef#in(Object) in}(jedi).{@link org.fest.reflect.field.Invoker#get() get}();
 *
 *   // Equivalent to call 'person.setName("Luke")'
 *   {@link org.fest.reflect.core.Reflection#method(String) method}("setName").{@link org.fest.reflect.method.MethodName#withParameterTypes(Class...) withParameterTypes}(String.class)
 *                    .{@link org.fest.reflect.method.MethodParameterTypes#in(Object) in}(person)
 *                    .{@link org.fest.reflect.method.Invoker#invoke(Object...) invoke}("Luke");
 *
 *   // Equivalent to call 'jedi.getPowers()'
 *   List&lt;String&gt; powers = {@link org.fest.reflect.core.Reflection#method(String) method}("getPowers").{@link MethodName#withReturnType(TypeRef) withReturnType}(new {@link TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {})
 *                                            .{@link MethodReturnTypeRef#in(Object) in}(person)
 *                                            .{@link Invoker#invoke(Object...) invoke}();
 *
 *   // Retrieves the value of the static field "count" in Person.class
 *   int count = {@link org.fest.reflect.core.Reflection#staticField(String) staticField}("count").{@link StaticFieldName#ofType(Class) ofType}(int.class).{@link StaticFieldType#in(Class) in}(Person.class).{@link org.fest.reflect.field.Invoker#get() get}();
 *
 *   // Sets the value of the static field "count" to 3 in Person.class
 *   {@link org.fest.reflect.core.Reflection#staticField(String) staticField}("count").{@link StaticFieldName#ofType(Class) ofType}(int.class).{@link StaticFieldType#in(Class) in}(Person.class).{@link org.fest.reflect.field.Invoker#set(Object) set}(3);
 *
 *   // Retrieves the value of the static field "commonPowers" in Jedi.class
 *   List&lt;String&gt; commmonPowers = {@link org.fest.reflect.core.Reflection#staticField(String) staticField}("commonPowers").{@link StaticFieldName#ofType(TypeRef) ofType}(new {@link TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {}).{@link StaticFieldTypeRef#in(Class) in}(Jedi.class).{@link org.fest.reflect.field.Invoker#get() get}();
 *
 *   // Equivalent to call 'person.concentrate()'
 *   {@link org.fest.reflect.core.Reflection#method(String) method}("concentrate").{@link org.fest.reflect.method.MethodName#in(Object) in}(person).{@link org.fest.reflect.method.Invoker#invoke(Object...) invoke}();
 *
 *   // Equivalent to call 'person.getName()'
 *   String name = {@link org.fest.reflect.core.Reflection#method(String) method}("getName").{@link org.fest.reflect.method.MethodName#withReturnType(Class) withReturnType}(String.class)
 *                                  .{@link org.fest.reflect.method.MethodReturnType#in(Object) in}(person)
 *                                  .{@link org.fest.reflect.method.Invoker#invoke(Object...) invoke}();
 *
 *   // Equivalent to call 'Jedi.setCommonPower("Jump")'
 *   {@link org.fest.reflect.core.Reflection#staticMethod(String) staticMethod}("setCommonPower").{@link org.fest.reflect.method.StaticMethodName#withParameterTypes(Class...) withParameterTypes}(String.class)
 *                                 .{@link org.fest.reflect.method.StaticMethodParameterTypes#in(Class) in}(Jedi.class)
 *                                 .{@link org.fest.reflect.method.Invoker#invoke(Object...) invoke}("Jump");
 *
 *   // Equivalent to call 'Jedi.addPadawan()'
 *   {@link org.fest.reflect.core.Reflection#staticMethod(String) staticMethod}("addPadawan").{@link org.fest.reflect.method.StaticMethodName#in(Class) in}(Jedi.class).{@link org.fest.reflect.method.Invoker#invoke(Object...) invoke}();
 *
 *   // Equivalent to call 'Jedi.commonPowerCount()'
 *   String name = {@link org.fest.reflect.core.Reflection#staticMethod(String) staticMethod}("commonPowerCount").{@link org.fest.reflect.method.StaticMethodName#withReturnType(Class) withReturnType}(String.class)
 *                                                 .{@link org.fest.reflect.method.StaticMethodReturnType#in(Class) in}(Jedi.class)
 *                                                 .{@link org.fest.reflect.method.Invoker#invoke(Object...) invoke}();
 *
 *   // Equivalent to call 'Jedi.getCommonPowers()'
 *   List&lt;String&gt; powers = {@link org.fest.reflect.core.Reflection#staticMethod(String) staticMethod}("getCommonPowers").{@link StaticMethodName#withReturnType(TypeRef) withReturnType}(new {@link TypeRef TypeRef}&lt;List&lt;String&gt;&gt;() {})
 *                                                        .{@link StaticMethodReturnTypeRef#in(Class) in}(Jedi.class)
 *                                                        .{@link Invoker#invoke(Object...) invoke}();
 *
 *   // Retrieves the value of the property "name"
 *   String name = {@link org.fest.reflect.core.Reflection#property(String) property}("name").{@link PropertyName#ofType(Class) ofType}(String.class).{@link PropertyType#in(Object) in}(person).{@link org.fest.reflect.beanproperty.Invoker#get() get}();
 *
 *   // Sets the value of the property "name" to "Yoda"
 *   {@link org.fest.reflect.core.Reflection#property(String) property}("name").{@link PropertyName#ofType(Class) ofType}(String.class).{@link PropertyType#in(Object) in}(person).{@link org.fest.reflect.beanproperty.Invoker#set(Object) set}("Yoda");
 * </pre>
 * </p>
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 * @author Ivan Hristov
 */
public final class Reflection {

  /**
   * Starting point of the fluent interface for loading a class dynamically.
   * @param name the name of the class to load.
   * @return the starting point of the method chain.
   * @throws NullPointerException if the given name is <code>null</code>.
   * @throws IllegalArgumentException if the given name is empty.
   * @since 1.1
   */
  public static Type type(String name) {
    return newType(name);
  }

  /**
   * Starting point of the fluent interface for accessing static inner class via reflection.
   * @param name the name of the static inner class to access.
   * @return the starting point of the method chain.
   * @throws NullPointerException if the given name is <code>null</code>.
   * @throws IllegalArgumentException if the given name is empty.
   * @since 1.1
   */
  public static StaticInnerClassName staticInnerClass(String name) {
    return startStaticInnerClassAccess(name);
  }

  /**
   * Starting point of the fluent interface for accessing fields via reflection.<br>
   * Nested field are supported with dot notation, e.g. <code>"person.address.street"</code>.
   * <p>
   * Let's look how it works on an example :
   * 
   * <pre>
   * Let's say we have the following simple service:
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
   * Let's say we want to change the {@code logger} field of {@code NotificationService} within our instance of {@code BusinessService}, we can do this:
   *  
   *   field("notificationService.logger").ofType(Logger.class).in(businessService).set(loggerMock);
   *  
   * You can also set the deeply nested {@code session} field within {@code ClientStatusDao} like this:
   * 
   *   field("notificationService.clientStatusDao.session").ofType(Session.class).in(businessService).set(sessionMock);
   * </pre>
   * 
   * @param name the name of the field to access.
   * @return the starting point of the method chain.
   * @throws NullPointerException if the given name is <code>null</code>.
   * @throws IllegalArgumentException if the given name is empty.
   */
  public static FieldName field(String name) {
    return beginFieldAccess(name);
  }

  /**
   * Starting point of the fluent interface for accessing static fields via reflection.
   * @param name the name of the static field to access.
   * @return the starting point of the method chain.
   * @throws NullPointerException if the given name is <code>null</code>.
   * @throws IllegalArgumentException if the given name is empty.
   */
  public static StaticFieldName staticField(String name) {
    return beginStaticFieldAccess(name);
  }

  /**
   * Starting point of the fluent interface for invoking methods via reflection.
   * @param name the name of the method to invoke.
   * @return the starting point of the method chain.
   * @throws NullPointerException if the given name is <code>null</code>.
   * @throws IllegalArgumentException if the given name is empty.
   */
  public static MethodName method(String name) {
    return startMethodAccess(name);
  }

  /**
   * Starting point of the fluent interface for invoking static methods via reflection.
   * @param name the name of the static method to invoke.
   * @return the starting point of the static method chain.
   * @throws NullPointerException if the given name is <code>null</code>.
   * @throws IllegalArgumentException if the given name is empty.
   */
  public static StaticMethodName staticMethod(String name) {
    return startStaticMethodAccess(name);
  }

  /**
   * Starting point of the fluent interface for invoking constructors via reflection.
   * @return the starting point of the method chain.
   */
  public static TargetType constructor() {
    return startConstructorAccess();
  }

  /**
   * Starting point of the fluent interface for accessing properties via Bean Introspection.
   * @param name the name of the property to access.
   * @return the starting point of the method chain.
   * @throws NullPointerException if the given name is <code>null</code>.
   * @throws IllegalArgumentException if the given name is empty.
   * @since 1.2
   */
  public static PropertyName property(String name) {
    return startPropertyAccess(name);
  }

  private Reflection() {}
}
