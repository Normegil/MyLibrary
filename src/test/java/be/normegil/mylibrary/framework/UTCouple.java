package be.normegil.mylibrary.framework;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UTCouple {

	public static final String FIRST_VALUE = "Test";
	public static final String SECOND_VALUE = "Value";
	private Couple<String, String> couple;

	@Before
	public void setUp() throws Exception {
		couple = new Couple<>(FIRST_VALUE, SECOND_VALUE);
	}

	@Test
	public void testGetFirst() throws Exception {
		assertEquals(FIRST_VALUE, couple.getFirst());
	}

	@Test
	public void testGetSecond() throws Exception {
		assertEquals(SECOND_VALUE, couple.getSecond());
	}
}
