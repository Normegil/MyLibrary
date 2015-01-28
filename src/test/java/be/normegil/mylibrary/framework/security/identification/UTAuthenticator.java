package be.normegil.mylibrary.framework.security.identification;

import be.normegil.mylibrary.ApplicationProperties;
import be.normegil.mylibrary.framework.Couple;
import be.normegil.mylibrary.framework.exception.JOSERuntimeException;
import be.normegil.mylibrary.framework.exception.ParseRuntimeException;
import be.normegil.mylibrary.framework.exception.WebApplicationException;
import be.normegil.mylibrary.framework.rest.error.ErrorCode;
import be.normegil.mylibrary.framework.security.identification.jwt.JWTHelper;
import be.normegil.mylibrary.framework.security.identification.jwt.JWTHelperTestSuite;
import be.normegil.mylibrary.framework.security.identification.key.KeyManager;
import be.normegil.mylibrary.framework.security.identification.key.KeyType;
import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.tools.IGenerator;
import be.normegil.mylibrary.tools.dao.KeyMemoryDAO;
import be.normegil.mylibrary.tools.dao.UserMemoryDAO;
import be.normegil.mylibrary.tools.generator.UserGenerator;
import be.normegil.mylibrary.user.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mockito;

import java.text.ParseException;
import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class UTAuthenticator {

	private static final IGenerator<User> GENERATOR = GeneratorRepository.get(User.class);
	private Authenticator authenticator;
	private UserMemoryDAO dao;
	private JWTHelper jwtHelper;

	@Before
	public void setUp() throws Exception {
		dao = new UserMemoryDAO();
		jwtHelper = new JWTHelper(new KeyManager(new KeyMemoryDAO()));
		authenticator = new Authenticator(dao, jwtHelper);
	}

	@After
	public void tearDown() throws Exception {
		jwtHelper = null;
		dao = null;
		authenticator = null;
	}

	@Test
	public void testAuthenticateByPassword_Successful_UserReturned() throws Exception {
		User user = GENERATOR.getNew(true, true);
		String password = UserGenerator.PASSWORD;
		user.setHashedPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
		dao.persist(user);

		Couple<User, SignedJWT> userWithJWT = authenticator.authenticateUserPassword(user.getPseudo(), password);
		assertEquals(user, userWithJWT.getFirst());
	}

	@Test
	public void testAuthenticateByPassword_Successful_JWT() throws Exception {
		User user = GENERATOR.getNew(true, true);
		String password = UserGenerator.PASSWORD;
		user.setHashedPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
		dao.persist(user);

		Couple<User, SignedJWT> userWithJWT = authenticator.authenticateUserPassword(user.getPseudo(), password);
		assertTrue(jwtHelper.isValid(userWithJWT.getSecond()));
		assertEquals(user.getPseudo(), userWithJWT.getSecond().getJWTClaimsSet().getIssuer());
	}

	@Test
	public void testAuthenticateByPassword_WrongPassword() throws Exception {
		User user = GENERATOR.getNew(true, true);
		dao.persist(user);

		String wrongPassword = UserGenerator.PASSWORD;
		try {
			authenticator.authenticateUserPassword(user.getPseudo(), wrongPassword);
			fail("Failure should result in WebApplicationException");
		} catch (WebApplicationException e) {
			assertEquals(ErrorCode.AUTHENTICATION_WRONG_PASSWORD, e.getErrorCode());
		}
	}

	@Test
	public void testAuthenticateByPassword_UserNotExisting() throws Exception {
		User user = GENERATOR.getNew(true, true);
		String password = UserGenerator.PASSWORD;
		user.setHashedPassword(BCrypt.hashpw(password, BCrypt.gensalt()));

		try {
			authenticator.authenticateUserPassword(user.getPseudo(), password);
			fail("Failure should result in WebApplicationException");
		} catch (WebApplicationException e) {
			assertEquals(ErrorCode.AUTHENTICATION_USER_NOT_FOUND, e.getErrorCode());
		}

	}

	@Test
	public void testAuthenticateJWT_ValidJWT_UserReturned() throws Exception {
		User user = GENERATOR.getNew(true, true);
		dao.persist(user);

		SignedJWT jwt = jwtHelper.generateSignedJWT(user);

		Couple<User, SignedJWT> userWithOtherJWT = authenticator.authenticateToken(jwt);
		assertEquals(user, userWithOtherJWT.getFirst());
	}

	@Test
	public void testAuthenticateJWT_ValidJWT_JWTReturned() throws Exception {
		User user = GENERATOR.getNew(true, true);
		dao.persist(user);

		SignedJWT jwt = jwtHelper.generateSignedJWT(user);

		Couple<User, SignedJWT> userWithOtherJWT = authenticator.authenticateToken(jwt);
		assertTrue(jwtHelper.isValid(userWithOtherJWT.getSecond()));
		assertEquals(user.getPseudo(), userWithOtherJWT.getSecond().getJWTClaimsSet().getIssuer());
	}

	@Test
	public void testAuthenticateJWT_InvalidJWT() throws Exception {
		User user = GENERATOR.getNew(true, true);
		dao.persist(user);

		KeyManager manager = new KeyManager(new KeyMemoryDAO());
		LocalDateTime issueTime = LocalDateTime.now();
		SignedJWT jwt = new JWTHelperTestSuite().getSignedJWT(user, manager.load("Test", KeyType.ECDSA), issueTime, issueTime.plus(ApplicationProperties.Security.JSonWebToken.TOKEN_VALIDITY_PERIOD));

		try {
			authenticator.authenticateToken(jwt);
			fail("Failure should result in WebApplicationException");
		} catch (WebApplicationException e) {
			assertEquals(ErrorCode.AUTHENTICATION_INVALID_TOKEN, e.getErrorCode());
		}
	}

	@Test(expected = ParseRuntimeException.class)
	public void testParseExceptionHandling() throws Exception {
		JWTHelper helper = Mockito.mock(JWTHelper.class);
		SignedJWT signedJWT = Mockito.mock(SignedJWT.class);
		when(helper.isValid(signedJWT))
				.thenReturn(true);
		when(signedJWT.getJWTClaimsSet())
				.thenThrow(ParseException.class);
		Authenticator authenticator = new Authenticator(dao, helper);
		authenticator.authenticateToken(signedJWT);
	}
}
