package be.normegil.mylibrary.framework.security;

import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;

@RunWith(MockitoJUnitRunner.class)
public class UTSecurityInterceptorSafety {

	private static final ClassWrapper<SecurityInterceptor> CLASS = new ClassWrapper<>(SecurityInterceptor.class);
	private SecurityInterceptor entity = new SecurityInterceptor();

	@Mock
	private ContainerRequestContext requestContext;

	@Mock
	private ContainerResponseContext responseContext;

	@Test(expected = ConstraintViolationException.class)
	public void testFilter_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("filter", ContainerRequestContext.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testFilterResponse_NullRequest() throws Exception {
		Validator.validate(entity, CLASS.getMethod("filter", ContainerRequestContext.class, ContainerResponseContext.class), null, responseContext);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testFilterResponse_NullResponse() throws Exception {
		Validator.validate(entity, CLASS.getMethod("filter", ContainerRequestContext.class, ContainerResponseContext.class), requestContext, null);
	}

}
