package be.normegil.mylibrary.framework.security;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.container.ContainerRequestContext;

@RunWith(MockitoJUnitRunner.class)
public class UTSecurityInterceptor {

	@Mock
	private ContainerRequestContext requestContext;

}
