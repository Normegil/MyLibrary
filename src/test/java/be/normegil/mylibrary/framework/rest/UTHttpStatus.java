package be.normegil.mylibrary.framework.rest;

import be.normegil.mylibrary.framework.exception.UnknownEnumValueRuntimeException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UTHttpStatus {

	@Test
	public void testValueOfInt_Existing() throws Exception {
		HttpStatus expected = HttpStatus.INTERNAL_SERVER_ERROR;
		HttpStatus toTest = HttpStatus.valueOf(expected.value());
		assertEquals(expected, toTest);
	}

	@Test(expected = UnknownEnumValueRuntimeException.class)
	public void testValueOfInt_NotExisting() throws Exception {
		HttpStatus.valueOf(-1);
	}

	@Test
	public void testGetValue() throws Exception {
		assertEquals(500, HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	@Test
	public void testGetReasonPhrase() throws Exception {
		assertEquals("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
	}

	@Test
	public void testGetSerie_ServerError() throws Exception {
		assertEquals(HttpStatus.Category.SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.series());
	}

	@Test
	public void testGetSerie_Successful() throws Exception {
		assertEquals(HttpStatus.Category.SUCCESSFUL, HttpStatus.OK.series());
	}

	@Test
	public void testToString() throws Exception {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		assertEquals(Integer.toString(status.value()), status.toString());
	}
}
