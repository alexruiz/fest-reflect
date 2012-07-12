/*
 * Created on Mar 19, 2012
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
package org.fest.reflect.field;

import static org.fest.reflect.core.Reflection.field;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.InOrder;

/**
 * 
 * @author Ivan Hristov
 * 
 */
public class FieldDecoratorTest {

  private static class LogMyName {

    private final ILogger logger = new MySmartLogger(this.getClass());
    private final String name;

    public LogMyName(String name) {
      this.name = name;
    }

    public void logMyName() {
      logger.info(name);
    }
  }

  private static class MySmartLogger implements ILogger {
    private final Class<?> _clazz;

    public MySmartLogger(Class<?> _clazz) {
      this._clazz = _clazz;
    }

    public void info(String infoStr) {
      System.out.println(_clazz.getSimpleName() + " : " + infoStr);
    }
  }

  public interface ILogger {
    public void info(String infoStr);
  }

  @Test
  public void should_pre_decorate_field() {
    // GIVEN
    ILogger mySmartLoggerMock = mock(ILogger.class);

    LogMyName logMyName = new LogMyName("FEST");

    field("logger").ofType(ILogger.class).in(logMyName).preDecorateWith(mySmartLoggerMock);

    // WHEN
    logMyName.logMyName();

    // THEN
    verify(mySmartLoggerMock, times(1)).info(eq("FEST"));
  }

  public interface IUploadFileService {
    boolean upload(String file, String destination);
  }

  private class FileManager {

    private static final String DEFAULT_DESTINATION = "http://example.org/default/destination/";

    private final IUploadFileService uploadFileService;

    public FileManager() {
      this.uploadFileService = new IUploadFileService() {

        public boolean upload(String file, String destination) {
          return false;
        }
      };
    }

    public void manage(String fileName) {
      if (uploadFileService.upload(fileName, DEFAULT_DESTINATION)) {
        System.out.println("File " + fileName + " sent to " + DEFAULT_DESTINATION);
      } else {
        System.out.println("Unable to sent " + fileName + " to " + DEFAULT_DESTINATION);
      }
    }
  }

  @Test
  public void should_attach_two_pre_decorators() {
    // GIVEN
    IUploadFileService firstUploadFileServiceMock = mock(IUploadFileService.class);
    IUploadFileService secondUploadFileServiceMock = mock(IUploadFileService.class);

    FileManager fileManager = new FileManager();

    field("uploadFileService").ofType(IUploadFileService.class)//
        .in(fileManager).preDecorateWith(firstUploadFileServiceMock).preDecorateWith(secondUploadFileServiceMock);

    // WHEN
    String fileName = "testFileName";
    fileManager.manage(fileName);

    // THEN
    InOrder inOrder = inOrder(firstUploadFileServiceMock, secondUploadFileServiceMock);
    inOrder.verify(secondUploadFileServiceMock, times(1)).upload(eq("testFileName"), anyString());
    inOrder.verify(firstUploadFileServiceMock, times(1)).upload(eq("testFileName"), anyString());
  }

  @Test
  public void should_post_decorate_field() {
    // GIVEN
    IUploadFileService uploadFileServiceMock = mock(IUploadFileService.class);

    FileManager fileManager = new FileManager();

    field("uploadFileService").ofType(IUploadFileService.class).in(fileManager).postDecorateWith(uploadFileServiceMock);

    // WHEN
    String fileName = "testFileName";
    fileManager.manage(fileName);

    // THEN
    verify(uploadFileServiceMock, times(1)).upload(eq("testFileName"), anyString());
  }

  @Test
  public void should_attach_two_post_decorators() {
    // GIVEN
    IUploadFileService firstUploadFileServiceMock = mock(IUploadFileService.class);
    IUploadFileService secondUploadFileServiceMock = mock(IUploadFileService.class);

    FileManager fileManager = new FileManager();

    field("uploadFileService").ofType(IUploadFileService.class)//
        .in(fileManager).postDecorateWith(firstUploadFileServiceMock).postDecorateWith(secondUploadFileServiceMock);

    // WHEN
    String fileName = "testFileName";
    fileManager.manage(fileName);

    // THEN
    InOrder inOrder = inOrder(firstUploadFileServiceMock, secondUploadFileServiceMock);
    inOrder.verify(firstUploadFileServiceMock, times(1)).upload(eq("testFileName"), anyString());
    inOrder.verify(secondUploadFileServiceMock, times(1)).upload(eq("testFileName"), anyString());
  }

  @Test
  public void should_pre_and_post_decorate_field() {
    // GIVEN
    IUploadFileService uploadFileServiceMock = mock(IUploadFileService.class);

    FileManager fileManager = new FileManager();

    field("uploadFileService").ofType(IUploadFileService.class).in(fileManager)
    //
        .preDecorateWith(uploadFileServiceMock).postDecorateWith(uploadFileServiceMock).preDecorateWith(uploadFileServiceMock);

    // WHEN
    String fileName = "testFileName";
    fileManager.manage(fileName);

    // THEN
    verify(uploadFileServiceMock, times(3)).upload(eq("testFileName"), anyString());
  }

}
