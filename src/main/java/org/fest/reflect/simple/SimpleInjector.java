package org.fest.reflect.simple;

import static java.text.MessageFormat.format;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides simple injection capabilities when needed in tests. To be used by {@link SimpleReflection}. 
 * 
 * @author Marek Dominiak
 */
public class SimpleInjector {
	private final Object valueToSet;
	
	static SimpleInjector set(Object value) {
		return new SimpleInjector(value);
	}

	private SimpleInjector(Object value) {
		this.valueToSet = value;
	}

	/**
	 * Sets {@link #valueToSet} into target object if it is possible. 
	 * Rules for setting: 
	 * <ul>
	 * <li>there must be EXACTLY ONE field in "target" object which is of type which is assignable from "valueToSet" type</li>
	 * </ul>
	 * @param target object into which we try to inject 
	 * @throws IllegalStateException when there are none or more than one fields of "source" object in "target" type.   
	 */
	public void in(Object target) {
		Class<? extends Object> targetClass = target.getClass();
		Field[] fields = targetClass.getDeclaredFields();
		List<Field> matchingFields = getAllFieldsAssignableFrom(fields);
		checkThatNumberOfFieldsOfValueTypeIsValid(targetClass, matchingFields);
		Field fieldToInjectTo = matchingFields.get(0);
		setValueOnField(fieldToInjectTo, target);
	}

	private List<Field> getAllFieldsAssignableFrom(Field[] fields) {
		List<Field> result = new ArrayList<Field>();
		for (Field field : fields) {
			if (field.getType().isAssignableFrom(valueToSet.getClass())) {
				result.add(field);
			}
		}
		return result;
	}

	private void checkThatNumberOfFieldsOfValueTypeIsValid(Class<? extends Object> targetClass,
			List<Field> matchingFields) {
		if (matchingFields.isEmpty()) {
			String errorMessage = format("There must be exactly ONE field of type {0} (or assignable from {0}) in target class {1}!\nBut found none.", valueToSet.getClass().getName(), targetClass.getName());
			throw new IllegalStateException(errorMessage);
		}
		
		if (matchingFields.size() > 1) {
			String errorMessageListOfFields = "" + matchingFields.size() + ":\n";
			for (Field field : matchingFields) {
				errorMessageListOfFields += field.getName() + "\n";
			}
			String errorMessage = format("There must be exactly ONE field of type {0} (or assignable from {0}) in target class {1}!\nBut found {2}", valueToSet.getClass().getName(), targetClass.getName(), errorMessageListOfFields);
			throw new IllegalStateException(errorMessage);
		}
	}

	private void setValueOnField(Field found, Object target) {
		found.setAccessible(true);
		try {
			found.set(target, valueToSet);
		} catch (final IllegalAccessException ex) {
			throw new IllegalStateException("Unexpected reflection exception - " + ex.getClass().getName() + ": "
					+ ex.getMessage());
		}     
	}	
}
