package be.normegil.mylibrary.util.security.identification;

import be.normegil.mylibrary.util.security.SecurityInterceptor;
import be.normegil.mylibrary.util.security.identification.annotation.AuthenticationNotRequired;

import javax.inject.Inject;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;

@Provider
public class AuthenticationFeature implements DynamicFeature {

	@Inject
	private SecurityInterceptor securityInterceptor;

	@Override
	public void configure(final ResourceInfo resourceInfo, final FeatureContext context) {
		Method method = resourceInfo.getResourceMethod();
		if (!method.isAnnotationPresent(AuthenticationNotRequired.class)) {
			context.register(securityInterceptor);
		}
	}
}
