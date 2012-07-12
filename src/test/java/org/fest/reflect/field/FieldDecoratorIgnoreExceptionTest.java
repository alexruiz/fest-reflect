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

import java.net.SocketTimeoutException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
import org.junit.rules.ExpectedException;

/**
 * 
 * @author Ivan Hristov
 * 
 */
public class FieldDecoratorIgnoreExceptionTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  public interface IUploadFileService {
    Boolean upload(String file, String destination) throws SocketTimeoutException;
  }

  public interface INotifierService {
    void notify(String msg);
  }

  private class FileManager {

    private static final String DEFAULT_DESTINATION = "http://example.org/default/destination/";

    private final IUploadFileService uploadFileService;
    private INotifierService notifierService;

    public FileManager() {
      this.uploadFileService = new IUploadFileService() {

        public Boolean upload(String file, String destination) {
          return false;
        }
      };
    }

    public void manage(String fileName) throws SocketTimeoutException {
      if (uploadFileService.upload(fileName, DEFAULT_DESTINATION)) {
        System.out.println("File " + fileName + " sent to " + DEFAULT_DESTINATION);
      } else {
        notifierService.notify("Unable to send msg!");
      }
    }
  }

  @Test
  public void should_not_ignore_pre_decorator_exceptions() throws SocketTimeoutException {
    // GIVEN
    expectedException.expect(SocketTimeoutException.class);
    String expectedExceptionMsg = "Expected test exception";
    expectedException.expectMessage(JUnitMatchers.containsString(expectedExceptionMsg));

    IUploadFileService uploadFileServiceMock = mock(IUploadFileService.class);
    INotifierService notifierServiceMock = mock(INotifierService.class);

    when(uploadFileServiceMock.upload(anyString(), anyString()))//
        .thenThrow(new SocketTimeoutException(expectedExceptionMsg));

    FileManager fileManager = new FileManager();

    field("uploadFileService").ofType(IUploadFileService.class)//
        .in(fileManager).preDecorateWith(uploadFileServiceMock).ignoringDecoratorExceptions();

    field("notifierService").ofType(INotifierService.class).in(fileManager).set(notifierServiceMock);

    // WHEN
    String fileName = "testFileName";
    fileManager.manage(fileName);

    // THEN there should be an exception
  }

  @Test
  public void should_not_ignore_post_decorator_exceptions() throws SocketTimeoutException {
    // GIVEN
    expectedException.expect(SocketTimeoutException.class);
    String expectedExceptionMsg = "Expected test exception";
    expectedException.expectMessage(JUnitMatchers.containsString(expectedExceptionMsg));

    IUploadFileService uploadFileServiceMock = mock(IUploadFileService.class);
    INotifierService notifierServiceMock = mock(INotifierService.class);

    when(uploadFileServiceMock.upload(anyString(), anyString()))//
        .thenThrow(new SocketTimeoutException(expectedExceptionMsg));

    FileManager fileManager = new FileManager();

    field("uploadFileService").ofType(IUploadFileService.class)//
        .in(fileManager).postDecorateWith(uploadFileServiceMock).ignoringDecoratorExceptions();

    field("notifierService").ofType(INotifierService.class).in(fileManager).set(notifierServiceMock);

    // WHEN
    String fileName = "testFileName";
    fileManager.manage(fileName);

    // THEN there should be an exception
  }

  @Test
  public void should_pre_decorate_field_ignoring_decorator_exceptions() throws SocketTimeoutException {
    // GIVEN
    IUploadFileService uploadFileServiceMock = mock(IUploadFileService.class);
    INotifierService notifierServiceMock = mock(INotifierService.class);

    when(uploadFileServiceMock.upload(anyString(), anyString()))//
        .thenThrow(new RuntimeException("Expected test exception"));

    FileManager fileManager = new FileManager();

    field("uploadFileService").ofType(IUploadFileService.class)//
        .in(fileManager).preDecorateWith(uploadFileServiceMock).ignoringDecoratorExceptions();

    field("notifierService").ofType(INotifierService.class).in(fileManager).set(notifierServiceMock);

    // WHEN
    String fileName = "testFileName";
    fileManager.manage(fileName);

    // THEN
    verify(notifierServiceMock, times(1)).notify(eq("Unable to send msg!"));
  }

  @Test
  public void should_post_decorate_field_ignoring_decorator_exceptions() throws SocketTimeoutException {
    // GIVEN
    IUploadFileService uploadFileServiceMock = mock(IUploadFileService.class);
    INotifierService notifierServiceMock = mock(INotifierService.class);

    when(uploadFileServiceMock.upload(anyString(), anyString()))//
        .thenThrow(new RuntimeException("Expected test exception"));

    FileManager fileManager = new FileManager();

    field("uploadFileService").ofType(IUploadFileService.class)//
        .in(fileManager).postDecorateWith(uploadFileServiceMock).ignoringDecoratorExceptions();

    field("notifierService").ofType(INotifierService.class).in(fileManager).set(notifierServiceMock);

    // WHEN
    String fileName = "testFileName";
    fileManager.manage(fileName);

    // THEN
    verify(uploadFileServiceMock, times(1)).upload(eq("testFileName"), anyString());
    verify(notifierServiceMock, times(1)).notify(eq("Unable to send msg!"));
  }

  @Test
  public void should_pre_and_post_decorate_field_ignoring_decorator_exceptions() throws SocketTimeoutException {
    // GIVEN
    IUploadFileService uploadFileServiceMock = mock(IUploadFileService.class);
    INotifierService notifierServiceMock = mock(INotifierService.class);

    when(uploadFileServiceMock.upload(anyString(), anyString()))//
        .thenThrow(new SocketTimeoutException("Expected test exception"))//
        .thenThrow(new RuntimeException("Expected test exception"))//
        .thenThrow(new RuntimeException("Expected test exception"));

    FileManager fileManager = new FileManager();

    field("uploadFileService").ofType(IUploadFileService.class).in(fileManager)//
        .preDecorateWith(uploadFileServiceMock).ignoringDecoratorExceptions()//
        .postDecorateWith(uploadFileServiceMock).ignoringDecoratorExceptions()//
        .preDecorateWith(uploadFileServiceMock).ignoringDecoratorExceptionsOfType(SocketTimeoutException.class);

    field("notifierService").ofType(INotifierService.class).in(fileManager).set(notifierServiceMock);

    // WHEN
    String fileName = "testFileName";
    fileManager.manage(fileName);

    // THEN
    verify(uploadFileServiceMock, times(3)).upload(eq("testFileName"), anyString());
    verify(notifierServiceMock, times(1)).notify(eq("Unable to send msg!"));
  }

}
