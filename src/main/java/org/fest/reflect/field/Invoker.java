/*
 * Created on Feb 26, 2011
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
package org.fest.reflect.field;

import java.lang.reflect.Field;

import org.fest.reflect.exception.ReflectionError;

/**
 * Reads and writes to a field.
 * @param <T> the type of the field to access.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 * @author Ivan Hristov
 */
public interface Invoker<T> {

  /**
   * Reads the value of the field.
   * @return the value of the field.
   * @throws ReflectionError if the value of the field cannot be read.
   */
  T get();

  /**
   * Writes a value to the field.
   * @param value the value to set.
   * @throws ReflectionError if the value cannot be set on the field.
   */
  void set(T value);

  /**
   * Returns the underlying <code>{@link Field}</code>.
   * @return the underlying field.
   */
  Field info();

  /**
   * Decorates a targeted object's methods; Each execution of a targeted object's method will be first performed on the same
   * method of the {@code decorator} object. The result (if any) from the invocation of the targeted object's method will be
   * returned.
   * 
   * <pre>
   * Be aware:
   *  <li> The type of a targeted object should be an interface for this functionality to work
   *  <li> Any exception caused by an invocation of a {@code decorator} object's method will result in disrupting the default program's flow 
   * </pre>
   * 
   * *
   * 
   * <pre>
   * Example: 
   *  I.) Assuming we have the following scenario:
   * 
   * interface IUploadFileService { 
   *    boolean upload(String file, String destination); 
   * }
   * 
   * public class FileManager {
   *    
   *    private IUploadFileService uploadFileService;
   *    private static final String DEFAULT_DESTINATION = "http://example.org/default/destination/";
   * 
   *    public void manage(String fileName) {
   *      if( uploadFileService.upload(fileName, DEFAULT_DESTINATION) ) {
   *        System.out.println("File "+fileName+" sent to "+DEFAULT_DESTINATION);
   *      } else {
   *        System.out.println("Unable to sent "+fileName+" to "+DEFAULT_DESTINATION);
   *      } 
   *    }
   * }
   *    II.) Say we want to decorate the uploadFileService.upload(...) part, so that additional functionality is executed
   * <i>before</i> the actual uploadFileService.upload(...) logic. The following code will do the job:
   *
   *  IUploadFileService uploadFileServiceDecorator = ...; 
   *  
   *  FileManager fileManager = new FileManager();
   *  
   *  field("uploadFileService").ofType(IUploadFileService.class).in(fileManager).preDecoratedWith(uploadFileServiceDecorator);
   * 
   *   However, if there is an exception when calling uploadFileServiceDecorator.upload(fileName, DEFAULT_DESTINATION) the default 
   * program's flow will be interrupted and the uploadFileService.upload(fileName, DEFAULT_DESTINATION) will not be executed.
   * 
   * </pre>
   * 
   * @param decorator which methods be called before the same targeted object methods
   * @return a reference to customizing the decorator's behavior
   */
  DecoratedInvoker<T> preDecorateWith(T decorator);

  /**
   * Decorates a targeted object's methods; After each execution of a targeted object's method the same method will be executed on
   * the {@code decorator} object. The result (if any) from the invocation of the targeted object's method will be returned.
   * 
   * <pre>
   * Be aware:
   *  <li> The type of a targeted object should be an interface for this functionality to work
   *  <li> Any exception caused by an invocation of a {@code decorator} object's method will result in disrupting the default program's flow 
   * </pre>
   * 
   * <pre>
   * Example: 
   *  I.) Assuming we have the following scenario:
   * 
   * interface IUploadFileService { 
   *    boolean upload(String file, String destination); 
   * }
   * 
   * public class FileManager {
   *    
   *    private IUploadFileService uploadFileService;
   *    private static final String DEFAULT_DESTINATION = "http://example.org/default/destination/"
   * 
   *    public void manage(String fileName) {
   *      if( uploadFileService.upload(fileName, DEFAULT_DESTINATION) ) {
   *        System.out.println("File "+fileName+" sent to "+DEFAULT_DESTINATION);
   *      } else {
   *        System.out.println("Unable to sent "+fileName+" to "+DEFAULT_DESTINATION);
   *      } 
   *    }
   * }
   *    II.) Say we want to decorate the uploadFileService.upload(...) part, so that an additional functionality is executed 
   * <i>after</i> the actual uploadFileService.upload(...) logic. The following code will do the job:
   * 
   *  IUploadFileService uploadFileServiceDecorator = ...; 
   *  
   *  FileManager fileManager = new FileManager();
   *  
   *  field("uploadFileService").ofType(IUploadFileService.class).in(fileManager).postDecoratedWith(uploadFileServiceDecorator); 
   * 
   * </pre>
   * 
   * @param decorator which methods be called after the same targeted object methods
   * @return a reference to customizing the decorator's behavior
   */
  DecoratedInvoker<T> postDecorateWith(T decorator);
}
