package be.normegil.mylibrary.framework.parser;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractParserTest<E> {

	private static final Logger LOG = LoggerFactory.getLogger(UTBaseCsvParser.class);
	private CsvParser<E> parser;
	private final Class<E> entityClass;

	public AbstractParserTest(final Class<E> classToParse) {
		entityClass = classToParse;
		this.parser = new CsvParser<>(entityClass);
	}

	protected abstract E initNewEntity();

	protected abstract E initDefaultEntity();

	@Test
	public void testTo() throws Exception {
		ArrayList<E> parsableObjects = new ArrayList<>();
		parsableObjects.add(initNewEntity());
		parsableObjects.add(initNewEntity());
		parsableObjects.add(initNewEntity());

		File file = File.createTempFile(entityClass.getSimpleName(), ".csv");
		LOG.info("Generated file : " + file.getAbsolutePath());

		parser.to(parsableObjects, file);

		List<E> loadedObjects;
		try (FileInputStream inputStream = new FileInputStream(file)) {
			loadedObjects = parser.from(inputStream);
		}
		assertEquals(parsableObjects, loadedObjects);
	}

	@Test
	public void testFrom() throws Exception {
		List<E> loadedObjects;
		try (InputStream inputStream = getClass().getResourceAsStream(entityClass.getSimpleName() + "Reference.csv")) {
			loadedObjects = parser.from(inputStream);
		}

		ArrayList<E> expected = new ArrayList<>();
		expected.add(initDefaultEntity());
		expected.add(initDefaultEntity());
		expected.add(initDefaultEntity());

		assertEquals(expected, loadedObjects);
	}
}
