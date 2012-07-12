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

/**
 * Tests returning decorator result in case of a decorator exceptions
 * @author Ivan Hristov
 * 
 */
public class FieldDecoratorCombinedTest {

  public interface IUploadFileService {
    Boolean upload(String file, String destination) throws CustomeException;
  }

  public interface INotificationService {
    void notify(String msg);
  }

  @SuppressWarnings("serial")
  public class CustomeException extends RuntimeException {
    public CustomeException() {
      super("This is a test exception");
    }
  }

  private class FileManager {

    private static final String DEFAULT_DESTINATION = "http://example.org/default/destination/";

    private final IUploadFileService uploadFileService;
    private INotificationService notificationService;

    public FileManager() {
      this.uploadFileService = new IUploadFileService() {

        public Boolean upload(String file, String destination) {
          return false;
        }
      };
    }

    public void manage(String fileName) {
      notificationService.notify("Upload successful? : " + uploadFileService.upload(fileName, DEFAULT_DESTINATION));
    }
  }

  @Test
  public void should_pre_decorate_field_returning_decorator_result_ignoring_custom_exception_in_decorator() {
    // GIVEN
    IUploadFileService uploadFileServiceMock = mock(IUploadFileService.class);
    INotificationService notificationServiceMock = mock(INotificationService.class);

    when(uploadFileServiceMock.upload(anyString(), anyString())).thenThrow(new CustomeException());

    FileManager fileManager = new FileManager();

    field("uploadFileService").ofType(IUploadFileService.class)//
        .in(fileManager).preDecorateWith(uploadFileServiceMock)//
        .returningDecoratorResult().ignoringDecoratorExceptionsOfType(CustomeException.class);

    field("notificationService").ofType(INotificationService.class)//
        .in(fileManager).set(notificationServiceMock);

    // WHEN
    String fileName = "testFileName";
    fileManager.manage(fileName);

    // THEN
    verify(uploadFileServiceMock, times(1)).upload(eq("testFileName"), anyString());
    verify(notificationServiceMock, times(1)).notify(eq("Upload successful? : null"));
  }

  @Test
  public void should_post_decorate_field_returning_decorator_result_ignoring_decorator_exceptions() {
    // GIVEN
    IUploadFileService uploadFileServiceMock = mock(IUploadFileService.class);
    INotificationService notificationServiceMock = mock(INotificationService.class);

    when(uploadFileServiceMock.upload(anyString(), anyString())).thenThrow(new RuntimeException());

    FileManager fileManager = new FileManager();

    field("uploadFileService").ofType(IUploadFileService.class)//
        .in(fileManager).postDecorateWith(uploadFileServiceMock)//
        .returningDecoratorResult().ignoringDecoratorExceptions();

    field("notificationService").ofType(INotificationService.class)//
        .in(fileManager).set(notificationServiceMock);

    // WHEN
    String fileName = "testFileName";
    fileManager.manage(fileName);

    // THEN
    verify(uploadFileServiceMock, times(1)).upload(eq("testFileName"), anyString());
    verify(notificationServiceMock, times(1)).notify(eq("Upload successful? : null"));
  }

  @Test
  public void should_pre_and_post_decorate_field_returning_decorator_result_ignoring_decorator_exceptions() {
    // GIVEN
    IUploadFileService uploadFileServiceMock = mock(IUploadFileService.class);
    INotificationService notificationServiceMock = mock(INotificationService.class);

    when(uploadFileServiceMock.upload(anyString(), anyString())).thenThrow(new RuntimeException()).thenThrow(
        new CustomeException());

    FileManager fileManager = new FileManager();

    field("uploadFileService").ofType(IUploadFileService.class).in(fileManager).preDecorateWith(uploadFileServiceMock)
        .ignoringDecoratorExceptions().returningDecoratorResult().postDecorateWith(uploadFileServiceMock)
        .ignoringDecoratorExceptionsOfType(CustomeException.class).returningDecoratorResult();

    field("notificationService").ofType(INotificationService.class).in(fileManager).set(notificationServiceMock);

    // WHEN
    String fileName = "testFileName";
    fileManager.manage(fileName);

    // THEN
    verify(uploadFileServiceMock, times(2)).upload(eq("testFileName"), anyString());
    verify(notificationServiceMock, times(1)).notify(eq("Upload successful? : null"));
  }

}
