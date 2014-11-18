package be.normegil.mylibrary.tools;

import be.normegil.mylibrary.MangaTestSuite;
import be.normegil.mylibrary.manga.Manga;

import java.util.HashMap;
import java.util.Map;

public class FactoryRepository {

	private static Map<Class, DataFactory> factories = new HashMap<>();
	private static boolean isInitialized = false;

	public static DataFactory get(Class aClass) {

		if (!isInitialized) {
			synchronized (FactoryRepository.class) {
				if (!isInitialized) {
					initialize();
					isInitialized = true;
				}
			}
		}

		DataFactory factory = factories.get(aClass);
		if (factory == null) {
			throw new UnsupportedOperationException("Factory not found for type [Type=" + aClass + "]");
		} else {
			return factory;
		}
	}

	private static void initialize() {
		// Framework classes

		// Model Classes
		factories.put(Manga.class, new MangaTestSuite());

		//Test Classes
	}
}
