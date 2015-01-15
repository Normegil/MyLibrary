package be.normegil.mylibrary.framework;

import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.assertEquals;

public class UTURIHelper {

	private static final String DEFAULT_URI = "http://www.example.com/rest";
	private URIHelper uriHelper = new URIHelper();

	@Test
	public void testRemoveParameters() throws Exception {
		URI uri = URI.create(DEFAULT_URI + "?parameter=0");
		assertEquals(URI.create(DEFAULT_URI), uriHelper.removeParameters(uri));
	}

	@Test
	public void testRemoveParameters_NoParameters() throws Exception {
		URI uri = URI.create(DEFAULT_URI);
		assertEquals(uri, uriHelper.removeParameters(uri));
	}
}
