package be.normegil.mylibrary.tools;

import be.normegil.mylibrary.Constants;
import be.normegil.mylibrary.framework.exception.IllegalAccessRuntimeException;
import be.normegil.mylibrary.framework.exception.InvocationTargetRuntimeException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class Validator {

	public static void validate(Object object) {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		try {
			javax.validation.Validator validator = validatorFactory.getValidator();
			Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
			if (!constraintViolations.isEmpty()) {
				throw new ConstraintViolationException(constraintViolations);
			}
		} finally {
			validatorFactory.close();
		}
	}

	public static void validate(Object object, Method method, Object... parameters) {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

		try {
			javax.validation.Validator validator = validatorFactory.getValidator();
			ExecutableValidator methodValidator = validator.forExecutables();
			Set<ConstraintViolation<Object>> constraintViolations = methodValidator.validateParameters(object, method, parameters);
			if (!constraintViolations.isEmpty()) {
				throw new ConstraintViolationException(constraintViolations);
			} else {
				Object returnValue = invokeMethod(object, method, parameters);
				constraintViolations.addAll(methodValidator.validateReturnValue(object, method, returnValue));
				if (!constraintViolations.isEmpty()) {
					throw new ConstraintViolationException(constraintViolations);
				}
			}
		} finally {
			validatorFactory.close();
		}
	}

	public static void validate(Constructor<?> constructor, Object... parameters) {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

		try {
			javax.validation.Validator validator = validatorFactory.getValidator();
			ExecutableValidator methodValidator = validator.forExecutables();
			Set<ConstraintViolation<Object>> constraintViolations = methodValidator.validateConstructorParameters(constructor, parameters);
			if (!constraintViolations.isEmpty()) {
				throw new ConstraintViolationException(constraintViolations);
			}
		} finally {
			validatorFactory.close();
		}
	}

	private static Object invokeMethod(final Object object, final Method method, final Object[] parameters) {
		Object returnValue;
		try {
			returnValue = method.invoke(object, parameters);
		} catch (IllegalAccessException e) {
			throw new IllegalAccessRuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new InvocationTargetRuntimeException(e);
		}
		return returnValue;
	}

	private String generateErrorMessage(final Set<ConstraintViolation<Class>> constraintViolations) {
		StringBuilder stringBuilder = new StringBuilder();
		for (ConstraintViolation<Class> constraintViolation : constraintViolations) {
			String error = constraintViolation.getMessage() + " | " + constraintViolation.getPropertyPath() + "=" + constraintViolation.getInvalidValue() + " |" + Constants.LINE_ENDING;
		}
		return stringBuilder.toString();
	}
}
