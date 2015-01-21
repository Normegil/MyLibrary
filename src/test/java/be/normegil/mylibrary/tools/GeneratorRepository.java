package be.normegil.mylibrary.tools;

import be.normegil.mylibrary.framework.exception.IllegalAccessRuntimeException;
import be.normegil.mylibrary.framework.exception.InstantiationRuntimeException;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GeneratorRepository {

	private static Reflections reflections = new Reflections("be.normegil");
	private static final Map<Class, IGenerator> factories = new HashMap<>();
	private static final Logger LOG = LoggerFactory.getLogger(GeneratorRepository.class);
	private static boolean isInitialized = false;

	public static IGenerator get(Class aClass) {

		if (!isInitialized) {
			synchronized (GeneratorRepository.class) {
				if (!isInitialized) {
					initialize();
					isInitialized = true;
				}
			}
		}

		IGenerator factory = factories.get(aClass);
		if (factory == null) {
			throw new UnsupportedOperationException("Factory not found for type [Type=" + aClass + "]");
		} else {
			return factory;
		}
	}

	private static void initialize() {
		Set<Class<?>> subTypes = reflections.getTypesAnnotatedWith(Generator.class);

		for (Class<?> subType : subTypes) {
			try {
				IGenerator generator = (IGenerator) subType.newInstance();
				if (!factories.keySet().contains(generator.getSupportedClass())) {
					factories.put(generator.getSupportedClass(), generator);
					LOG.info("Generator found : " + generator.getClass().getSimpleName() + "[" + generator.getSupportedClass().getSimpleName() + "]");
				}
			} catch (InstantiationException e) {
				throw new InstantiationRuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new IllegalAccessRuntimeException(e);
			}
		}
	}
}
