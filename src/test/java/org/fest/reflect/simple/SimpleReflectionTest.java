package org.fest.reflect.simple;

import junit.framework.Assert;

import org.fest.test.CodeToTest;
import org.fest.test.ExpectedFailure;
import org.junit.Test;

public class SimpleReflectionTest {

	@SuppressWarnings("unused")
	class ClassWithTwoFieldsOfTheSameType {
		private Crowdy crowdy;
		private Crowdy reallyCrowdy;
		public ClassWithTwoFieldsOfTheSameType(Crowdy crowdy, Crowdy reallyCrowdy) {
			super();
			this.crowdy = crowdy;
			this.reallyCrowdy = reallyCrowdy;
		}
		public ClassWithTwoFieldsOfTheSameType() {
			super();
		}
	}
	
	class ClassWithExactlyOneField {
		private Logger logger;

		public Logger getLogger() {
			return logger;
		}
	}
	
	class ClassWithExactlyOneCompatibleField {
		private SomeInterface someInterface;

		public SomeInterface getSomeInterface() {
			return someInterface;
		}
	}
	
	class CompletelyEmpty {
	}

	class Logger {
	}

	interface SomeInterface {
	}

	class ClassWithInterface implements SomeInterface {
	}

	class Crowdy {
	}

	@Test
	public void shouldInjectFieldByTypeInAnObject() throws Exception {
		// given
		ClassWithExactlyOneField exactlyOneField = new ClassWithExactlyOneField();
		Logger logger = new Logger();
	
		// when
		SimpleReflection.set(logger).in(exactlyOneField);
	
		// then
		Assert.assertEquals(logger, exactlyOneField.getLogger());
	}

	@Test
	public void shouldInjectFieldByTypeInAnObjectWhenTheInjectedValueIfOfTypeExtendingTypeOfTargetField()
			throws Exception {
		// given
		ClassWithExactlyOneCompatibleField exactlyOneCompatibleField = new ClassWithExactlyOneCompatibleField();
		ClassWithInterface classWithInterface = new ClassWithInterface();

		// when
		SimpleReflection.set(classWithInterface).in(exactlyOneCompatibleField);

		// then
		Assert.assertEquals(classWithInterface, exactlyOneCompatibleField.getSomeInterface());
	}
	
	@Test
	public void shouldThrowISEWhenInjectingValueOfTypeWhenThereAreNoneFieldsOfThisTypeInTargetObject() throws Exception {
		final CompletelyEmpty completelyEmpty = new CompletelyEmpty();
		final Logger logger = new Logger();
		ExpectedFailure
				.expect(IllegalStateException.class)
				.withMessage(
						"There must be exactly ONE field of type " + Logger.class.getName() + " (or assignable from "+ Logger.class.getName() + ") in target class "
								+ CompletelyEmpty.class.getName() + "!\nBut found none.").on(new CodeToTest() {
					public void run() throws Throwable {
						SimpleReflection.set(logger).in(completelyEmpty);
					}
				});
	}

	@Test
	public void shouldThrowISEWhenInjectingValueOfTypeWhenThereAreMoreFieldsOfThisTypeInTargetObject() throws Exception {
		final Crowdy crowdy = new Crowdy();
		final ClassWithTwoFieldsOfTheSameType classWithTwoFieldsOfTheSameType = new ClassWithTwoFieldsOfTheSameType();
		ExpectedFailure
				.expect(IllegalStateException.class)
				.withMessage(
						"There must be exactly ONE field of type " + Crowdy.class.getName() + " (or assignable from " + Crowdy.class.getName() + ") in target class "
								+ ClassWithTwoFieldsOfTheSameType.class.getName() + "!\nBut found 2:\ncrowdy\nreallyCrowdy\n")
				.on(new CodeToTest() {
					public void run() throws Throwable {
						SimpleReflection.set(crowdy).in(classWithTwoFieldsOfTheSameType);
					}
				});
	}
	
	@Test
	public void shouldRetrieveFieldByTypeInAnObjectWhenOnlyOneFieldOfThisTypeExist() throws Exception {
		// given
		ClassWithExactlyOneField exactlyOneField = new ClassWithExactlyOneField();
		Logger logger = new Logger();
		SimpleReflection.set(logger).in(exactlyOneField);

		// when
		Logger result = SimpleReflection.get(Logger.class).from(exactlyOneField);
		// then
		Assert.assertEquals(logger, result);
	}
	
	@Test
	public void shouldRetrieveFieldValueFromSourceObjectByTypeWhenSourceClassHasCompatibleField() throws Exception {
		// given
		// compatible = from the inheritance hierarchy
		ClassWithExactlyOneCompatibleField classWithExactlyOneCompatibleField = new ClassWithExactlyOneCompatibleField();
		ClassWithInterface value = new ClassWithInterface();
		SimpleReflection.set(value).in(classWithExactlyOneCompatibleField);
		// when
		SomeInterface result = SimpleReflection.get(SomeInterface.class).from(classWithExactlyOneCompatibleField);
		// then
		Assert.assertEquals(value, result);
	}
	
	@Test
	public void shouldThrowISEWhenTryingToGetFieldByTypeInAnObjectWhenThereAreMoreFieldsOfThisTypeInSourceObject() throws Exception {
		// given
		Crowdy crowdy1 = new Crowdy();
		Crowdy crowdy2 = new Crowdy();
		final ClassWithTwoFieldsOfTheSameType source = new ClassWithTwoFieldsOfTheSameType(crowdy1, crowdy2);
		ExpectedFailure
		.expect(IllegalStateException.class)
		.withMessage("Can't retrieve value of type: " + Crowdy.class.getName() + " because there are more fields of this type in source bean (" + ClassWithTwoFieldsOfTheSameType.class.getName() + ")").on(new CodeToTest() {
			public void run() throws Throwable {
				SimpleReflection.get(Crowdy.class).from(source);
			}
		});
	}
	
	@Test
	public void shouldThrowISEWhenTryingToGetFieldByTypeInAnObjectWhichDoesNotDefineFieldOfThisType() throws Exception {
		// given
		final CompletelyEmpty source = new CompletelyEmpty();
		ExpectedFailure
		.expect(IllegalStateException.class)
		.withMessage("Can't retrieve value of type: " + Crowdy.class.getName() + " because there none fields of this type (or compatible type) in source bean (" + CompletelyEmpty.class.getName() + ")").on(new CodeToTest() {
			public void run() throws Throwable {
				SimpleReflection.get(Crowdy.class).from(source);
			}
		});
	}
}
