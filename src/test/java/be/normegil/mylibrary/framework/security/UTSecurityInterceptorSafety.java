package be.normegil.mylibrary.framework.security;

import be.normegil.mylibrary.tools.ClassWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;

@RunWith(MockitoJUnitRunner.class)
public class UTSecurityInterceptorSafety {

	private SecurityInterceptor entity = new SecurityInterceptor();

	@Mock
	private ContainerRequestContext requestContext;

	@Mock
	private ContainerResponseContext responseContext;

	@Test(expected = NullPointerException.class)
	public void testFilter_Null() throws Exception {
		entity.filter(null);
	}

	@Test(expected = NullPointerException.class)
	public void testFilterResponse_NullRequest() throws Exception {
		entity.filter(null, responseContext);
	}

	@Test(expected = NullPointerException.class)
	public void testFilterResponse_NullResponse() throws Exception {
		entity.filter(requestContext, null);
	}

}
