package be.normegil.mylibrary.framework.security.identification;

import be.normegil.mylibrary.framework.security.SecurityInterceptor;
import be.normegil.mylibrary.framework.security.identification.annotation.AuthenticationNotRequired;
import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.FieldWrapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UTAuthenticationFeature {

	@Mock
	private ResourceInfo resourceInfo;

	@Mock
	private FeatureContext context;

	@Mock
	private SecurityInterceptor securityInterceptor;

	private AuthenticationFeature authenticationFeature;

	@Before
	public void setUp() throws Exception {
		authenticationFeature = new AuthenticationFeature();
		ClassWrapper<AuthenticationFeature> classWrapper = new ClassWrapper<>(AuthenticationFeature.class);
		FieldWrapper securityInterceptorField = classWrapper.getField("securityInterceptor");
		securityInterceptorField.set(authenticationFeature, securityInterceptor);
	}

	@After
	public void tearDown() throws Exception {
		authenticationFeature = null;
	}

	@Test
	public void testConfigure_AuthenticationRequired() throws Exception {
		ClassWrapper<FakeRESTClass> testClass = new ClassWrapper<>(FakeRESTClass.class);
		Method method = testClass.getMethod("authenticationRequired");
		when(resourceInfo.getResourceMethod())
				.thenReturn(method);

		authenticationFeature.configure(resourceInfo, context);

		verify(context, times(1))
				.register(securityInterceptor);
	}

	@Test
	public void testConfigure_AuthenticationNotRequired() throws Exception {
		ClassWrapper<FakeRESTClass> testClass = new ClassWrapper<>(FakeRESTClass.class);
		Method method = testClass.getMethod("authenticationNotRequired");
		when(resourceInfo.getResourceMethod())
				.thenReturn(method);

		authenticationFeature.configure(resourceInfo, context);

		verify(context, never())
				.register(securityInterceptor);
	}

	private class FakeRESTClass {
		@AuthenticationNotRequired
		public void authenticationNotRequired() {
		}

		public void authenticationRequired() {
		}
	}
}
