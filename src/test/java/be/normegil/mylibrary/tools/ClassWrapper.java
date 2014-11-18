package be.normegil.mylibrary.tools;

import be.normegil.mylibrary.util.constraint.NotEmpty;
import be.normegil.mylibrary.util.exception.InterfaceNotFoundException;
import be.normegil.mylibrary.util.exception.NoSuchFieldRuntimeException;
import be.normegil.mylibrary.util.exception.NoSuchMethodRuntimeException;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

public class ClassWrapper<E> {

	private Class<E> entityClass;

	public ClassWrapper(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	public Constructor<E> getConstructor(Class<?>... parameterClasses) {
		try {
			return entityClass.getConstructor(parameterClasses);
		} catch (NoSuchMethodException e) {
			try {
				return entityClass.getDeclaredConstructor(parameterClasses);
			} catch (NoSuchMethodException e1) {
				throw new NoSuchMethodRuntimeException(e1);
			}
		}
	}

	public Method getMethod(@NotEmpty String methodName, Class<?>... parameterClasses) {
		try {
			return entityClass.getMethod(methodName, parameterClasses);
		} catch (NoSuchMethodException e) {
			try {
				return entityClass.getDeclaredMethod(methodName, parameterClasses);
			} catch (NoSuchMethodException e1) {
				throw new NoSuchMethodRuntimeException(e1);
			}
		}
	}

	public FieldWrapper getField(@NotEmpty String fieldName) {
		for (FieldWrapper field : getAllFields()) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		throw new NoSuchFieldRuntimeException("Field not found [Name=" + fieldName + "]");
	}

	public Collection<FieldWrapper> getAllFields() {
		List<FieldWrapper> fields = new ArrayList<>();
		java.lang.reflect.Field[] declaredFields = entityClass.getDeclaredFields();
		java.lang.reflect.Field[] classFields = entityClass.getFields();

		for (java.lang.reflect.Field field : declaredFields) {
			fields.add(new FieldWrapper(field));
		}
		for (java.lang.reflect.Field field : classFields) {
			fields.add(new FieldWrapper(field));
		}

		Class superclass = entityClass.getSuperclass();
		if (superclass != null) {
			ClassWrapper classWrapper = new ClassWrapper<>(superclass);
			fields.addAll(classWrapper.getAllFields());
		}
		return fields;
	}

	public String getSimpleName() {
		return entityClass.getSimpleName();
	}

	public String getCanonicalName() {
		return entityClass.getCanonicalName();
	}

	public Collection<Class<?>> getInterfaces() {
		Class<?>[] interfacesTable = entityClass.getInterfaces();
		Set<Class<?>> interfaces = new HashSet<>();
		for (Class<?> anInterface : interfacesTable) {
			interfaces.add(anInterface);
		}
		return interfaces;
	}

	public Class<?> getInterface(@NotNull Class<?> aClass) {
		Collection<Class<?>> interfaces = getInterfaces();
		for (Class<?> anInterface : interfaces) {
			if (aClass.isAssignableFrom(anInterface)) {
				return anInterface;
			}
		}
		throw new InterfaceNotFoundException("Interface not found for " + aClass);
	}
}
