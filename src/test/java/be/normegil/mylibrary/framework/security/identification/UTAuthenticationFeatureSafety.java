package be.normegil.mylibrary.framework.security.identification;

import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

public class UTAuthenticationFeatureSafety {

	private AuthenticationFeature entity = new AuthenticationFeature();

	@Test(expected = NullPointerException.class)
	public void testConfigure_NullResourceInfo() throws Exception {
		FeatureContext mock = Mockito.mock(FeatureContext.class);
		entity.configure(null, mock);
	}

	@Test(expected = NullPointerException.class)
	public void testConfigure_NullFeatureContext() throws Exception {
		ResourceInfo mock = Mockito.mock(ResourceInfo.class);
		entity.configure(mock, null);
	}
}
