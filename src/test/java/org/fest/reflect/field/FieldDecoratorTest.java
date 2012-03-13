/**
 * 
 */
package org.fest.reflect.field;

import static org.fest.reflect.core.Reflection.field;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

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

  @Test public void shouldAddDecoratorBeforeLoggerField() {
    // GIVEN
    ILogger mySmartLoggerMock = mock(ILogger.class);

    LogMyName logMyName = new LogMyName("FEST");

    field("logger").ofType(ILogger.class).in(logMyName).addBefore(mySmartLoggerMock);

    // WHEN
    logMyName.logMyName();

    // THEN
    verify(mySmartLoggerMock, times(1)).info(eq("FEST"));
  }

  private interface IUploadFileService {
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

  @Test public void shouldAddDecoratorAfterUploadFileService() {
    // GIVEN
    IUploadFileService uploadFileServiceMock = mock(IUploadFileService.class);

    FileManager fileManager = new FileManager();

    field("uploadFileService").ofType(IUploadFileService.class).in(fileManager).addAfter(uploadFileServiceMock);

    // WHEN
    String fileName = "testFileName";
    fileManager.manage(fileName);

    // THEN
    verify(uploadFileServiceMock, times(1)).upload(eq("testFileName"), anyString());
  }

  @Test public void shouldAddDecoratorBeforeAndAfterUploadFileService() {
    // GIVEN
    IUploadFileService uploadFileServiceMock = mock(IUploadFileService.class);

    FileManager fileManager = new FileManager();

    field("uploadFileService").ofType(IUploadFileService.class).in(fileManager).addBefore(uploadFileServiceMock)
        .addAfter(uploadFileServiceMock);

    // WHEN
    String fileName = "testFileName";
    fileManager.manage(fileName);

    // THEN
    verify(uploadFileServiceMock, times(2)).upload(eq("testFileName"), anyString());
  }

}
