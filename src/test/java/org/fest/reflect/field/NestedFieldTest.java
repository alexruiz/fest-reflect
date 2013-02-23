/*
 * Created on Apr 8, 2012
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
import static org.fest.util.Lists.newArrayList;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
import org.junit.rules.ExpectedException;

import org.fest.reflect.exception.ReflectionError;
import org.fest.reflect.reference.TypeRef;

/**
 * @author Ivan Hristov
 * 
 */
public class NestedFieldTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  private static final class BusinessService {
    private final NotificationService notificationService;

    public BusinessService() {
      this.notificationService = new NotificationService();
    }

    public void doLogic() {
      notificationService.save();
    }

    public NotificationService getNotificationService() {
      return notificationService;
    }
    // ...
  }

  private static final class NotificationService {
    private final Logger logger = new Logger();
    private final IClientStatusDao clientStatusDao;

    public NotificationService() {
      this.clientStatusDao = new ClientStatusDao();
    }

    // ...

    public void save() {
      clientStatusDao.update();
    }

    public Logger getLogger() {
      return logger;
    }
  }

  private static class Logger {
    // ...
  }

  private static interface IClientStatusDao {
    void update();
  }

  private static class ClientStatusDao implements IClientStatusDao {
    private final Session session;
    @SuppressWarnings("unused")
    private final List<String> listOfNames = newArrayList("Ivan", "Joel", "Alex");

    public ClientStatusDao() {
      this.session = new SessionImpl();
    }

    public void update() {
      session.manageSession();
    }
  }

  private static class SessionImpl implements Session {
    private final SessionMonitor sessionMonitor = new SessionMonitor();

    public void manageSession() {
      sessionMonitor.monitor();
      // logic goes here
    }

  }

  private static interface Session {
    void manageSession();
  }

  private static class SessionMonitor {
    public void monitor() {
      // monitoring logic here
    }
  }

  @Test
  public void shouldSetOneLevelNestedLoggerField() {
    // GIVEN
    BusinessService businessService = new BusinessService();
    Logger loggerMock = mock(Logger.class);

    // WHEN
    field("notificationService.logger").ofType(Logger.class).in(businessService).set(loggerMock);

    // THEN
    assertSame(businessService.getNotificationService().getLogger(), loggerMock);
  }

  @Test
  public void shouldThrowExceptionBecauseOfWrongOrder() {

    expectedException.expect(ReflectionError.class);
    expectedException.expectMessage(JUnitMatchers.containsString(//
        "Unable to find field 'clientStatusDao' in org.fest.reflect.field.NestedFieldTest$BusinessService"));

    // GIVEN
    BusinessService businessService = new BusinessService();
    Session sessionMock = mock(Session.class);

    field("clientStatusDao.notificationService.session").ofType(Session.class).in(businessService).set(sessionMock);

    // WHEN
    businessService.doLogic();

    // THEN
    verify(sessionMock, times(1)).manageSession();
  }

  @Test
  public void shouldSetSecondLevelNestedSessionField() {
    // GIVEN
    BusinessService businessService = new BusinessService();
    Session sessionMock = mock(Session.class);

    field("notificationService.clientStatusDao.session").ofType(Session.class).in(businessService).set(sessionMock);

    // WHEN
    businessService.doLogic();

    // THEN
    verify(sessionMock, times(1)).manageSession();
  }

  @Test
  public void shouldGetSecondLevelNestedListOfNamesField() {
    // GIVEN
    BusinessService businessService = new BusinessService();

    // WHEN
    List<String> listOfNames = field("notificationService.clientStatusDao.listOfNames").ofType(new TypeRef<List<String>>() {})
        .in(businessService).get();

    // THEN
    Assert.assertThat(listOfNames, JUnitMatchers.hasItems("Ivan", "Joel", "Alex"));
  }

  @Test
  public void shouldSetThirdLevelNestedSessionFactoryField() {
    // GIVEN
    BusinessService businessService = new BusinessService();
    SessionMonitor sessionMonitorMock = mock(SessionMonitor.class);

    field("notificationService.clientStatusDao.session.sessionMonitor").ofType(SessionMonitor.class)//
        .in(businessService).set(sessionMonitorMock);

    // WHEN
    businessService.doLogic();

    // THEN
    verify(sessionMonitorMock, times(1)).monitor();
  }
}
