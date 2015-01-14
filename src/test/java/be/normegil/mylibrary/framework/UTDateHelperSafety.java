package be.normegil.mylibrary.framework;

import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.Validator;
import org.junit.Test;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class UTDateHelperSafety {

	private static final ClassWrapper<DateHelper> CLASS = new ClassWrapper<>(DateHelper.class);
	private DateHelper entity = new DateHelper();

	@Test(expected = ConstraintViolationException.class)
	public void testFormatLocaDate_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("format", LocalDate.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testParseLocalDate_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("parseLocalDate", String.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testParseLocalDate_Empty() throws Exception {
		Validator.validate(entity, CLASS.getMethod("parseLocalDate", String.class), "");
	}

	@Test(expected = ConstraintViolationException.class)
	public void testParseLocalDate_WrongFormat() throws Exception {
		Validator.validate(entity, CLASS.getMethod("parseLocalDate", String.class), "2015/02/21");
	}

	@Test(expected = ConstraintViolationException.class)
	public void testFormat_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("format", LocalDateTime.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testParseLocalDateTime_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("parseLocalDateTime", String.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testParseLocalDateTime_Empty() throws Exception {
		Validator.validate(entity, CLASS.getMethod("parseLocalDateTime", String.class), "");
	}

	@Test(expected = ConstraintViolationException.class)
	public void testParseLocalDateTime_WrongFormat() throws Exception {
		Validator.validate(entity, CLASS.getMethod("parseLocalDateTime", String.class), "2015/02/21");
	}

	@Test(expected = ConstraintViolationException.class)
	public void testFrom_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("from", Date.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testToDate_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("toDate", LocalDateTime.class), new Object[]{null});
	}
}
