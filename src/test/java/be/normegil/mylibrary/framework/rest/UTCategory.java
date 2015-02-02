package be.normegil.mylibrary.framework.rest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UTCategory {

	@Test
	public void testValueOfHttpStatus() throws Exception {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		assertEquals(HttpStatus.Category.SERVER_ERROR, HttpStatus.Category.valueOf(status));
	}

	@Test
	public void testGetValue() throws Exception {
		assertEquals(5, HttpStatus.Category.SERVER_ERROR.value());
	}
}
