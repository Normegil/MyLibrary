package be.normegil.mylibrary.framework.parser;

import be.normegil.mylibrary.framework.rest.error.RESTError;
import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.Validator;
import org.junit.Test;

import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

}
