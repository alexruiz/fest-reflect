package org.fest.reflect.simple;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides simple retrieving capabilities when needed in tests. To be used by {@link SimpleReflection}. 
 * 
 * @author Marek Dominiak
 */
public class SimpleRetriever<T> {

	private final Class<T> typeToRetrieve;

	public SimpleRetriever(Class<T> typeToRetrieve) {
		this.typeToRetrieve = typeToRetrieve;
	}

	/**
	 * Retrieves the value of field of type {@link #typeToRetrieve} from the "source" object.
	 * 
	 * Rules for retrieving: 
	 * <ul>
	 * <li>there must be EXACTLY ONE field in "source" object which is of type which is assignable from {@link #typeToRetrieve} type</li>
	 * </ul>
	 * @param source object from which we are retrieving the value 
	 * @throws IllegalStateException when there are none or more than one fields of {@link #typeToRetrieve} type in "source" object type.   
	 */
	public T from(Object source) {
		Field[] declaredFields = source.getClass().getDeclaredFields();
		List<Field> allPossibleFields = getAllFieldsAssignableFrom(declaredFields);
		checkThatThereIsOnlyOneFieldWithCompatibleType(source, allPossibleFields);
		return retrieveFieldValueFromBean(source, allPossibleFields.get(0));
	}

	private void checkThatThereIsOnlyOneFieldWithCompatibleType(Object source, List<Field> allPossibleFields) {
		if (allPossibleFields.size() > 1) {
			throw new IllegalStateException("Can't retrieve value of type: " + typeToRetrieve.getName()
					+ " because there are more fields of this type in source bean (" + source.getClass().getName()
					+ ")");
		}
		if (allPossibleFields.size() == 0) {
			throw new IllegalStateException("Can't retrieve value of type: " + typeToRetrieve.getName()
					+ " because there none fields of this type (or compatible type) in source bean ("
					+ source.getClass().getName() + ")");
		}
	}

	@SuppressWarnings("unchecked")
	private T retrieveFieldValueFromBean(Object source, Field field) {
		field.setAccessible(true);
		Object object = null;
		try {
			object = field.get(source);
		} catch (Exception e) {
			throw new IllegalStateException("Cannot retrieve value " + e.getMessage());
		}
		return (T) object;
	}

	private List<Field> getAllFieldsAssignableFrom(Field[] fields) {
		List<Field> result = new ArrayList<Field>();
		for (Field field : fields) {
			if (field.getType().isAssignableFrom(typeToRetrieve)) {
				result.add(field);
			}
		}
		return result;
	}
}
