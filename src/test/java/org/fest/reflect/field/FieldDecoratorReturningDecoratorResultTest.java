/*
 * Created on Mar 20, 2012
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
 * 
 * @author Ivan Hristov
 * 
 */
public class FieldDecoratorReturningDecoratorResultTest {

  public interface IUploadFileService {
    String upload(String file, String destination);
  }

  public interface INotificationService {
    void notify(String msg);
  }

  private class FileManager {

    private static final String DEFAULT_DESTINATION = "http://example.org/default/destination/";

    private final IUploadFileService uploadFileService;
    private INotificationService notificationService;

    public FileManager() {
      this.uploadFileService = new IUploadFileService() {

        public String upload(String file, String destination) {
          return "Default result";
        }
      };
    }

    public void manage(String fileName) {
      notificationService.notify(uploadFileService.upload(fileName, DEFAULT_DESTINATION));
    }
  }

  @Test
  public void should_pre_decorate_field_and_return_decorator_result() {
    // GIVEN
    IUploadFileService uploadFileServiceMock = mock(IUploadFileService.class);
    INotificationService notificationServiceMock = mock(INotificationService.class);

    String expectedResult = "pre-decorator result";
    when(uploadFileServiceMock.upload(anyString(), anyString())).thenReturn(expectedResult);

    FileManager fileManager = new FileManager();

    field("uploadFileService").ofType(IUploadFileService.class)//
        .in(fileManager).preDecorateWith(uploadFileServiceMock).returningDecoratorResult();

    field("notificationService").ofType(INotificationService.class).in(fileManager).set(notificationServiceMock);

    // WHEN
    String fileName = "testFileName";
    fileManager.manage(fileName);

    // THEN
    verify(uploadFileServiceMock, times(1)).upload(eq("testFileName"), anyString());
    verify(notificationServiceMock, times(1)).notify(eq(expectedResult));
  }

  @Test
  public void should_post_decorate_field_and_return_decorator_result() {
    // GIVEN
    IUploadFileService uploadFileServiceMock = mock(IUploadFileService.class);
    INotificationService notificationServiceMock = mock(INotificationService.class);

    String expectedResult = "post-decorator result";
    when(uploadFileServiceMock.upload(anyString(), anyString())).thenReturn(expectedResult);

    FileManager fileManager = new FileManager();

    field("uploadFileService").ofType(IUploadFileService.class)//
        .in(fileManager).postDecorateWith(uploadFileServiceMock).returningDecoratorResult();

    field("notificationService").ofType(INotificationService.class).in(fileManager).set(notificationServiceMock);

    // WHEN
    String fileName = "testFileName";
    fileManager.manage(fileName);

    // THEN
    verify(uploadFileServiceMock, times(1)).upload(eq("testFileName"), anyString());
    verify(notificationServiceMock, times(1)).notify(eq(expectedResult));
  }

  @Test
  public void should_pre_and_post_decorate_field_and_return_pre_decorator_result() {
    // GIVEN
    IUploadFileService uploadFileServiceMock = mock(IUploadFileService.class);
    INotificationService notificationServiceMock = mock(INotificationService.class);

    String expectedResult = "pre-decorator result";
    when(uploadFileServiceMock.upload(anyString(), anyString()))//
        .thenReturn(expectedResult).thenReturn("post-decorator result");

    FileManager fileManager = new FileManager();

    field("uploadFileService").ofType(IUploadFileService.class).in(fileManager)//
        .preDecorateWith(uploadFileServiceMock).returningDecoratorResult().postDecorateWith(uploadFileServiceMock);

    field("notificationService").ofType(INotificationService.class).in(fileManager).set(notificationServiceMock);

    // WHEN
    String fileName = "testFileName";
    fileManager.manage(fileName);

    // THEN
    verify(uploadFileServiceMock, times(2)).upload(eq("testFileName"), anyString());
    verify(notificationServiceMock, times(1)).notify(eq(expectedResult));
  }

  @Test
  public void should_pre_and_post_decorate_field_and_return_post_decorator_result() {
    // GIVEN
    IUploadFileService uploadFileServiceMock = mock(IUploadFileService.class);
    INotificationService notificationServiceMock = mock(INotificationService.class);

    String expectedResult = "post-decorator result";
    when(uploadFileServiceMock.upload(anyString(), anyString())).thenReturn("pre-decorator result").thenReturn(expectedResult);

    FileManager fileManager = new FileManager();

    field("uploadFileService").ofType(IUploadFileService.class).in(fileManager)//
        .preDecorateWith(uploadFileServiceMock).postDecorateWith(uploadFileServiceMock).returningDecoratorResult();

    field("notificationService").ofType(INotificationService.class).in(fileManager).set(notificationServiceMock);

    // WHEN
    String fileName = "testFileName";
    fileManager.manage(fileName);

    // THEN
    verify(uploadFileServiceMock, times(2)).upload(eq("testFileName"), anyString());
    verify(notificationServiceMock, times(1)).notify(eq(expectedResult));
  }

  @Test
  public void should_pre_and_post_decorate_field_both_returning_results_but_return_post_decorator_result() {
    // GIVEN
    IUploadFileService uploadFileServiceMock = mock(IUploadFileService.class);
    INotificationService notificationServiceMock = mock(INotificationService.class);

    String expectedResult = "post-decorator result";
    when(uploadFileServiceMock.upload(anyString(), anyString()))//
        .thenReturn("pre-decorator result").thenReturn(expectedResult);

    FileManager fileManager = new FileManager();

    field("uploadFileService").ofType(IUploadFileService.class).in(fileManager)//
        .preDecorateWith(uploadFileServiceMock).returningDecoratorResult()//
        .postDecorateWith(uploadFileServiceMock).returningDecoratorResult();

    field("notificationService").ofType(INotificationService.class).in(fileManager).set(notificationServiceMock);

    // WHEN
    String fileName = "testFileName";
    fileManager.manage(fileName);

    // THEN
    verify(uploadFileServiceMock, times(2)).upload(eq("testFileName"), anyString());
    verify(notificationServiceMock, times(1)).notify(eq(expectedResult));
  }

  @Test
  public void should_pre_and_post_decorate_field_both_returning_results_but_return_pre_decorator_result() {
    // GIVEN
    IUploadFileService uploadFileServiceMock = mock(IUploadFileService.class);
    INotificationService notificationServiceMock = mock(INotificationService.class);

    String expectedResult = "pre-decorator result";
    when(uploadFileServiceMock.upload(anyString(), anyString()))//
        .thenReturn(expectedResult).thenReturn("post-decorator result");

    FileManager fileManager = new FileManager();

    field("uploadFileService").ofType(IUploadFileService.class).in(fileManager)//
        .postDecorateWith(uploadFileServiceMock).returningDecoratorResult()//
        .preDecorateWith(uploadFileServiceMock).returningDecoratorResult();

    field("notificationService").ofType(INotificationService.class).in(fileManager).set(notificationServiceMock);

    // WHEN
    String fileName = "testFileName";
    fileManager.manage(fileName);

    // THEN
    verify(uploadFileServiceMock, times(2)).upload(eq("testFileName"), anyString());
    verify(notificationServiceMock, times(1)).notify(eq(expectedResult));
  }
}
