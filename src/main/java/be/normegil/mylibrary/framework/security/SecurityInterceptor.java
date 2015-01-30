package be.normegil.mylibrary.framework.security;

import be.normegil.mylibrary.Constants;
import be.normegil.mylibrary.framework.Couple;
import be.normegil.mylibrary.framework.URIHelper;
import be.normegil.mylibrary.framework.exception.ParseRuntimeException;
import be.normegil.mylibrary.framework.exception.WebApplicationException;
import be.normegil.mylibrary.framework.rest.RESTHelper;
import be.normegil.mylibrary.framework.rest.RESTMethod;
import be.normegil.mylibrary.framework.rest.error.ErrorCode;
import be.normegil.mylibrary.framework.security.identification.Authenticator;
import be.normegil.mylibrary.framework.security.rightsmanagement.RightsManager;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.Resource;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.SpecificResource;
import be.normegil.mylibrary.user.User;
import be.normegil.mylibrary.user.UserDatabaseDAO;
import com.nimbusds.jwt.SignedJWT;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Stateless
@LocalBean
public class SecurityInterceptor implements ContainerRequestFilter, ContainerResponseFilter {

	@Inject
	private UserDatabaseDAO userDAO;

	@Inject
	private Authenticator authenticator;

	@Inject
	private RightsManager rightsManager;

	@Inject
	private URIHelper uriHelper;

	@Inject
	private RESTHelper restHelper;

	protected SecurityInterceptor() {
	}

	protected SecurityInterceptor(final Authenticator authenticator, final RightsManager rightsManager, final RESTHelper restHelper) {
		this.authenticator = authenticator;
		this.rightsManager = rightsManager;
		this.restHelper = restHelper;
	}

	@Override
	public void filter(final ContainerRequestContext request) throws IOException {
		if (request == null) {
			throw new NullPointerException();
		}

		String authorizationHeader = request.getHeaderString(Constants.HTTP.Header.AUTHORIZATION);
		String tokenHeader = request.getHeaderString(Constants.HTTP.Header.TOKEN);
		Couple<User, SignedJWT> userInfo = identify(authorizationHeader, tokenHeader);

		checkRights(userInfo.getFirst(), request.getUriInfo(), request.getMethod());

		request.setProperty(Constants.HTTP.Header.TOKEN, userInfo.getSecond().serialize());
	}

	protected void checkRights(final User user, final UriInfo uriInfo, final String methodName) {
		Resource resource = restHelper.getResourceFor(uriInfo);
		RESTMethod method;
		if ("GET".equals(methodName) && isMainResource(resource)) {
			method = RESTMethod.GET_ALL;
		} else {
			method = RESTMethod.valueOf(methodName);
		}

		if (!rightsManager.canAccess(user, resource, method)) {
			throw new WebApplicationException(ErrorCode.ACCESS_DENIED, new IllegalArgumentException("User [" + user.getPseudo() + "] tried to [" + method + "] " + resource.toString() + " but is denied"));
		}
	}

	private boolean isMainResource(final Resource resource) {
		return !(resource instanceof SpecificResource);
	}

	protected Couple<User, SignedJWT> identify(final String authorizationHeader, final String tokenHeader) {
		Couple<User, SignedJWT> userInfo;
		if (!StringUtils.isBlank(authorizationHeader)) {
			userInfo = authenticateUserPassword(authorizationHeader);
		} else if (!StringUtils.isBlank(tokenHeader)) {
			userInfo = authenticateToken(tokenHeader);
		} else {
			throw new WebApplicationException(ErrorCode.BAD_AUTHENTICATION_REQUEST, new IllegalArgumentException("Authentication failed - Request invalid"));
		}
		return userInfo;
	}

	private Couple<User, SignedJWT> authenticateToken(final String token) {
		try {
			SignedJWT signedJWT = SignedJWT.parse(token);
			return authenticator.authenticateToken(signedJWT);
		} catch (ParseException e) {
			throw new ParseRuntimeException(e);
		}
	}

	private Couple<User, SignedJWT> authenticateUserPassword(final String authorizationHeader) {
		String authorizationDatas = authorizationHeader.replaceFirst(Constants.Security.AUTHORIZATION_SCHEME + " ", "");
		String clearUserAndPass;
		clearUserAndPass = new String(Base64.getDecoder().decode(authorizationDatas));
		String[] splittedUserPass = clearUserAndPass.split(":");
		String user = splittedUserPass[0];
		String pass = splittedUserPass[1];
		return authenticator.authenticateUserPassword(user, pass);
	}

	@Override
	public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext) throws IOException {
		if (requestContext == null || responseContext == null) {
			throw new NullPointerException();
		}

		Object token = requestContext.getProperty(Constants.HTTP.Header.TOKEN);
		if (token != null) {
			List<Object> tokenHeader = Arrays.asList(token);
			MultivaluedMap<String, Object> headers = responseContext.getHeaders();
			headers.put(Constants.HTTP.Header.TOKEN, tokenHeader);
		}
	}
}
