package be.normegil.mylibrary.framework.rest.error;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UTErrorCode {

	@Test
	public void testGetCode() throws Exception {
		assertEquals(40300, ErrorCode.ACCESS_DENIED.getCode());
	}
}
