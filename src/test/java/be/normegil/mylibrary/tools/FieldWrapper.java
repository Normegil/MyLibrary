package be.normegil.mylibrary.tools;

import be.normegil.mylibrary.framework.exception.IllegalAccessRuntimeException;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;

public class FieldWrapper {

	private Field field;

	public FieldWrapper(@NotNull Field field) {
		this.field = field;
	}

	public String getName() {
		return field.getName();
	}

	public Object get(@NotNull Object entity) {
		boolean accessible = field.isAccessible();
		field.setAccessible(true);

		Object toReturn;
		try {
			toReturn = field.get(entity);
		} catch (IllegalAccessException e) {
			throw new IllegalAccessRuntimeException(e);
		} finally {
			field.setAccessible(accessible);
		}
		return toReturn;
	}

	public void set(@NotNull Object entity, Object value) {
		boolean accessible = field.isAccessible();
		field.setAccessible(true);

		try {
			field.set(entity, value);
		} catch (IllegalAccessException e) {
			throw new IllegalAccessRuntimeException(e);
		} finally {
			field.setAccessible(accessible);
		}
	}

	public Field getOriginalField() {
		return field;
	}
}
