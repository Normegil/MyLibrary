package be.normegil.mylibrary.framework.security;

import be.normegil.mylibrary.Constants;
import be.normegil.mylibrary.framework.Couple;
import be.normegil.mylibrary.framework.exception.WebApplicationException;
import be.normegil.mylibrary.framework.rest.RESTHelper;
import be.normegil.mylibrary.framework.rest.RESTMethod;
import be.normegil.mylibrary.framework.security.identification.Authenticator;
import be.normegil.mylibrary.framework.security.identification.jwt.JWTHelper;
import be.normegil.mylibrary.framework.security.identification.key.KeyManager;
import be.normegil.mylibrary.framework.security.rightsmanagement.RightsManager;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.Resource;
import be.normegil.mylibrary.manga.MangaREST;
import be.normegil.mylibrary.tools.Assert;
import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.tools.IGenerator;
import be.normegil.mylibrary.tools.dao.KeyMemoryDAO;
import be.normegil.mylibrary.tools.dao.UserMemoryDAO;
import be.normegil.mylibrary.tools.generator.UserGenerator;
import be.normegil.mylibrary.user.User;
import com.nimbusds.jwt.SignedJWT;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.UriInfo;
import java.util.Base64;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UTSecurityInterceptor {
	private static final IGenerator<User> USER_GENERATOR = GeneratorRepository.get(User.class);
	public static final String DEFAULT_PASSWORD = "Password";
	public static final String DEFAULT_PSEUDO = "Pseudo";

	@Mock
	private Authenticator authenticator;

	@Mock
	private UriInfo uriInfo;

	@Mock
	private RightsManager rightsManager;

	@Mock
	private ContainerRequestContext requestContext;

	@Mock
	private ContainerResponseContext responseContext;

	@Mock
	private RESTHelper restHelper;

	@Test(expected = WebApplicationException.class)
	public void testFilterRequest_NoAuthenticationInfp() throws Exception {
		SecurityInterceptor securityInterceptor = new SecurityInterceptor(authenticator, rightsManager, restHelper);
		securityInterceptor.filter(requestContext);
	}

	@Test
	public void testIdentificiation_UserReturned() throws Exception {
		User user = USER_GENERATOR.getDefault(true, false);
		UserMemoryDAO dao = new UserMemoryDAO();
		dao.persist(user);

		Authenticator authenticator = new Authenticator(dao, new JWTHelper(new KeyManager(new KeyMemoryDAO())));

		SecurityInterceptor securityInterceptor = new SecurityInterceptor(authenticator, rightsManager, restHelper);
		Couple<User, SignedJWT> userSignedJWTCouple = securityInterceptor.identify(getAuthorizationHeader(user.getPseudo(), UserGenerator.PASSWORD), null);
		assertEquals(user, userSignedJWTCouple.getFirst());
	}

	@Test
	public void testIdentificiation_JWTReturned() throws Exception {
		User user = USER_GENERATOR.getNew(true, false);
		UserMemoryDAO dao = new UserMemoryDAO();
		dao.persist(user);
		JWTHelper jwtHelper = new JWTHelper(new KeyManager(new KeyMemoryDAO()));
		Authenticator authenticator = new Authenticator(dao, jwtHelper);
		SignedJWT signedJWT = jwtHelper.generateSignedJWT(user);

		SecurityInterceptor securityInterceptor = new SecurityInterceptor(authenticator, rightsManager, restHelper);
		Couple<User, SignedJWT> userSignedJWTCouple = securityInterceptor.identify(null, signedJWT.serialize());
		new Assert().assertJWTNotEquals(signedJWT, userSignedJWTCouple.getSecond());
	}

	@Test
	public void testFilterRequest_AuthenticateUserAndPassword() throws Exception {
		String authorizationHeader = getAuthorizationHeader(DEFAULT_PSEUDO, DEFAULT_PASSWORD);
		when(requestContext.getHeaderString(Constants.HTTP.Header.AUTHORIZATION))
				.thenReturn(authorizationHeader);
		when(authenticator.authenticateUserPassword(DEFAULT_PSEUDO, DEFAULT_PASSWORD))
				.thenThrow(WebApplicationException.class);
		try {
			SecurityInterceptor securityInterceptor = new SecurityInterceptor(authenticator, rightsManager, restHelper);
			securityInterceptor.filter(requestContext);
		} catch (WebApplicationException e) {
			verify(authenticator, times(1))
					.authenticateUserPassword(DEFAULT_PSEUDO, DEFAULT_PASSWORD);
		}
	}

	@Test
	public void testFilterRequest_AuthenticateToken() throws Exception {
		JWTHelper jwtHelper = new JWTHelper(new KeyManager(new KeyMemoryDAO()));
		User user = USER_GENERATOR.getNew(true, false);
		SignedJWT signedJWT = jwtHelper.generateSignedJWT(user);
		String serializedToken = signedJWT.serialize();

		when(requestContext.getHeaderString(Constants.HTTP.Header.TOKEN))
				.thenReturn(serializedToken);
		when(authenticator.authenticateToken(any()))
				.thenThrow(WebApplicationException.class);
		try {
			SecurityInterceptor interceptor = new SecurityInterceptor(authenticator, rightsManager, restHelper);
			interceptor.filter(requestContext);
			fail("Should sen an WebApplicationException");
		} catch (WebApplicationException e) {
			verify(authenticator, times(1))
					.authenticateToken(any());
		}
	}

	@Test
	public void testFilter_RightChecking_ValidRight_GETALLMethod() throws Exception {
		User user = USER_GENERATOR.getNew(true, false);
		JWTHelper jwtHelper = new JWTHelper(new KeyManager(new KeyMemoryDAO()));
		SignedJWT signedJWT = jwtHelper.generateSignedJWT(user);
		SecurityInterceptor interceptor = new SecurityInterceptor(authenticator, rightsManager, restHelper) {
			@Override
			protected Couple<User, SignedJWT> identify(final String authorizationHeader, final String tokenHeader) {
				return new Couple<>(user, signedJWT);
			}
		};


		when(requestContext.getMethod())
				.thenReturn("GET");
		when(requestContext.getUriInfo())
				.thenReturn(uriInfo);
		Resource resource = new Resource(MangaREST.class);
		when(restHelper.getResourceFor(uriInfo))
				.thenReturn(resource);
		when(rightsManager.canAccess(user, resource, RESTMethod.GET_ALL))
				.thenReturn(true);

		interceptor.filter(requestContext);

		verify(rightsManager, times(1))
				.canAccess(user, resource, RESTMethod.GET_ALL);
	}

	@Test
	public void testFilter_RightChecking_ValidRight_PUTMethod() throws Exception {
		User user = USER_GENERATOR.getNew(true, false);
		JWTHelper jwtHelper = new JWTHelper(new KeyManager(new KeyMemoryDAO()));
		SignedJWT signedJWT = jwtHelper.generateSignedJWT(user);
		SecurityInterceptor interceptor = new SecurityInterceptor(authenticator, rightsManager, restHelper) {
			@Override
			protected Couple<User, SignedJWT> identify(final String authorizationHeader, final String tokenHeader) {
				return new Couple<>(user, signedJWT);
			}
		};


		when(requestContext.getMethod())
				.thenReturn("PUT");
		when(requestContext.getUriInfo())
				.thenReturn(uriInfo);
		Resource resource = new Resource(MangaREST.class);
		when(restHelper.getResourceFor(uriInfo))
				.thenReturn(resource);
		when(rightsManager.canAccess(user, resource, RESTMethod.PUT))
				.thenReturn(true);

		interceptor.filter(requestContext);

		verify(rightsManager, times(1))
				.canAccess(user, resource, RESTMethod.PUT);
	}

	@Test
	public void testFilter_RightChecking_InvalidRight() throws Exception {
		User user = USER_GENERATOR.getNew(true, false);
		JWTHelper jwtHelper = new JWTHelper(new KeyManager(new KeyMemoryDAO()));
		SignedJWT signedJWT = jwtHelper.generateSignedJWT(user);
		SecurityInterceptor interceptor = new SecurityInterceptor(authenticator, rightsManager, restHelper) {
			@Override
			protected Couple<User, SignedJWT> identify(final String authorizationHeader, final String tokenHeader) {
				return new Couple<>(user, signedJWT);
			}
		};


		when(requestContext.getMethod())
				.thenReturn("GET");
		when(requestContext.getUriInfo())
				.thenReturn(uriInfo);
		Resource resource = new Resource(MangaREST.class);
		when(restHelper.getResourceFor(uriInfo))
				.thenReturn(resource);
		when(rightsManager.canAccess(user, resource, RESTMethod.GET_ALL))
				.thenReturn(false);

		try {
			interceptor.filter(requestContext);
			fail();
		} catch (WebApplicationException e) {
			verify(rightsManager, times(1))
					.canAccess(user, resource, RESTMethod.GET_ALL);
		}
	}

	@Test
	public void testSignedJWTSerialized() throws Exception {
		User user = USER_GENERATOR.getNew(true, false);
		JWTHelper jwtHelper = new JWTHelper(new KeyManager(new KeyMemoryDAO()));
		SignedJWT signedJWT = jwtHelper.generateSignedJWT(user);

		SecurityInterceptor interceptor = new SecurityInterceptor() {
			@Override
			protected Couple<User, SignedJWT> identify(final String authorizationHeader, final String tokenHeader) {
				return new Couple<>(user, signedJWT);
			}

			@Override
			protected void checkRights(final User user, final UriInfo uriInfo, final String methodName) {
			}
		};
		interceptor.filter(requestContext);
		verify(requestContext, times(1))
				.setProperty(Constants.HTTP.Header.TOKEN, signedJWT.serialize());
	}

	private String getAuthorizationHeader(final String pseudo, final String password) {
		String userPass = pseudo + ":" + password;
		byte[] bytes = userPass.getBytes();
		byte[] encoded = Base64.getEncoder().encode(bytes);
		String encodedUserPass = new String(encoded);
		return Constants.Security.AUTHORIZATION_SCHEME + " " + encodedUserPass;
	}
}
