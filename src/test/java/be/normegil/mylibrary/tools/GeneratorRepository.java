package be.normegil.mylibrary.tools;

import be.normegil.mylibrary.manga.Manga;
import be.normegil.mylibrary.tools.generator.MangaGenerator;

import java.util.HashMap;
import java.util.Map;

public class GeneratorRepository {

	private static Map<Class, Generator> factories = new HashMap<>();
	private static boolean isInitialized = false;

	public static Generator get(Class aClass) {

		if (!isInitialized) {
			synchronized (GeneratorRepository.class) {
				if (!isInitialized) {
					initialize();
					isInitialized = true;
				}
			}
		}

		Generator factory = factories.get(aClass);
		if (factory == null) {
			throw new UnsupportedOperationException("Factory not found for type [Type=" + aClass + "]");
		} else {
			return factory;
		}
	}

	private static void initialize() {
		// Framework classes

		// Model Classes
		factories.put(Manga.class, new MangaGenerator());

		//Test Classes
	}
}
