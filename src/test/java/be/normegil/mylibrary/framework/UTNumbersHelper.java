package be.normegil.mylibrary.framework;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UTNumbersHelper {

	private NumbersHelper numbersHelper = new NumbersHelper();

	@Test
	public void testSafeLongToInt() throws Exception {
		assertEquals(2, numbersHelper.safeLongToInt(2L));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSafeLongToInt_TooBig_Positive() throws Exception {
		long overflowValue = Integer.MAX_VALUE;
		overflowValue++;
		numbersHelper.safeLongToInt(overflowValue);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSafeLongToInt_TooBig_Negative() throws Exception {
		long underflowValue = Integer.MIN_VALUE;
		underflowValue--;
		numbersHelper.safeLongToInt(underflowValue);
	}

	@Test
	public void testSafeLongToInt_UpperLimit() throws Exception {
		long overflowValue = Integer.MAX_VALUE;
		assertEquals(Integer.MAX_VALUE, numbersHelper.safeLongToInt(overflowValue));
	}

	@Test
	public void testSafeLongToInt_LowerLimit() throws Exception {
		long underflowValue = Integer.MIN_VALUE;
		assertEquals(Integer.MIN_VALUE, numbersHelper.safeLongToInt(underflowValue));
	}
}
