package be.normegil.mylibrary.framework.parser;

import be.normegil.mylibrary.framework.exception.IORuntimeException;
import be.normegil.mylibrary.framework.exception.ReadOnlyRuntimeException;
import be.normegil.mylibrary.framework.rest.HttpStatus;
import be.normegil.mylibrary.framework.rest.error.RESTError;
import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.Validator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Test;
import org.mockito.Mockito;

import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class UTCsvParserSafety {

	private static final ClassWrapper<CsvParser> CLASS = new ClassWrapper<>(CsvParser.class);
	private CsvParser<RESTError> entity = new CsvParser<>(RESTError.class);

	@Test(expected = ConstraintViolationException.class)
	public void testFrom_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("from", InputStream.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testTo_NullEntities() throws Exception {
		Validator.validate(entity, CLASS.getMethod("to", List.class, File.class), null, new File(""));
	}

	@Test(expected = ConstraintViolationException.class)
	public void testTo_NullFile() throws Exception {
		Validator.validate(entity, CLASS.getMethod("to", List.class, File.class), new ArrayList<>(), null);
	}

	@Test(expected = ReadOnlyRuntimeException.class)
	public void testTo_ReadOnlyModel() throws Exception {
		RESTError readOnlyModel = RESTError.builder()
				.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
				.setCode(42)
				.setDeveloperMessage("DevelopperMessage")
				.setMessage("Message")
				.build();
		entity.to(Arrays.asList(readOnlyModel), new File(""));
	}

	@Test(expected = IORuntimeException.class)
	public void testFrom_BehaviorWhenIOException() throws Exception {
		File file = File.createTempFile("ExceptionFrom", ".csv");
		FileInputStream inputStream = new FileInputStream(file);

		ObjectReader reader = Mockito.mock(ObjectReader.class);
		doThrow(new IOException())
				.when(writer)
				.writeValue(file, entities);

		CsvParser csvParser = new CsvParser<RESTError>(RESTError.class) {
			@Override
			protected ObjectReader getReader() {
				return reader;
			}
		};
		csvParser.from(inputStream);
	}

	@Test(expected = IORuntimeException.class)
	public void testTo_BehaviorWhenIOException() throws Exception {
		File file = File.createTempFile("Exception", ".csv");
		ArrayList<RESTError> entities = new ArrayList<>();
		ObjectWriter writer = Mockito.mock(ObjectWriter.class);
		doThrow(new IOException()).when(writer).writeValue(file, entities);
		CsvParser csvParser = new CsvParser<RESTError>(RESTError.class) {
			@Override
			protected ObjectWriter getWriter() {
				return super.getWriter();
			}
		};
		csvParser.to(entities, File.createTempFile("ExceptionTo", ".csv"));
	}
}
