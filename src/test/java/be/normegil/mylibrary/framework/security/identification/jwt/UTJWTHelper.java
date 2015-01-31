package be.normegil.mylibrary.framework.security.identification.jwt;

import be.normegil.mylibrary.ApplicationProperties;
import be.normegil.mylibrary.framework.DateHelper;
import be.normegil.mylibrary.framework.exception.JOSERuntimeException;
import be.normegil.mylibrary.framework.security.identification.key.KeyManager;
import be.normegil.mylibrary.framework.security.identification.key.KeyType;
import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.FieldWrapper;
import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.tools.IGenerator;
import be.normegil.mylibrary.tools.dao.KeyMemoryDAO;
import be.normegil.mylibrary.user.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static org.junit.Assert.*;

public class UTJWTHelper {

	private static final IGenerator<User> GENERATOR = GeneratorRepository.get(User.class);
	private static final LocalDateTime DEFAULT_TIME = LocalDateTime.of(2015, Month.OCTOBER, 26, 11, 50, 32);
	public static final LocalDateTime DEFAULT_VALIDITY_DATE = DEFAULT_TIME.plus(ApplicationProperties.Security.JSonWebToken.TOKEN_VALIDITY_PERIOD);
	private JWTHelper jwtHelper;
	private KeyManager keyManager;

	@Before
	public void setUp() throws Exception {
		jwtHelper = new JWTHelper() {
			@Override
			protected LocalDateTime getCurrentTime() {
				return DEFAULT_TIME;
			}
		};
		ClassWrapper<JWTHelper> jwtHelperClass = new ClassWrapper<>(JWTHelper.class);
		FieldWrapper field = jwtHelperClass.getField("keyManager");

		keyManager = new KeyManager(new KeyMemoryDAO());
		field.set(jwtHelper, keyManager);
	}

	@After
	public void tearDown() throws Exception {
		jwtHelper = null;
		keyManager = null;
	}

	@Test
	public void testGenerateSignedJWT_Validity() throws Exception {
		User user = GENERATOR.getDefault(true, true);
		assertTrue(jwtHelper.isValid(jwtHelper.generateSignedJWT(user)));
	}

	@Test
	public void testGenerateSignedJWT_ValidityDate() throws Exception {
		User user = GENERATOR.getDefault(true, true);
		SignedJWT signedJWT = jwtHelper.generateSignedJWT(user);
		ReadOnlyJWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
		LocalDateTime issueTime = new DateHelper().from(claimsSet.getIssueTime());
		ChronoUnit chronoUnit = ChronoUnit.MINUTES;
		LocalDateTime truncatedIssueTime = issueTime.truncatedTo(chronoUnit);
		LocalDateTime expirationTime = new DateHelper().from(claimsSet.getExpirationTime());
		LocalDateTime truncatedExpirationTime = expirationTime.truncatedTo(chronoUnit);
		assertEquals(truncatedIssueTime.plus(ApplicationProperties.Security.JSonWebToken.TOKEN_VALIDITY_PERIOD), truncatedExpirationTime);
	}

	@Test
	public void testGenerateSignedJWT_PropertiesAndHeader() throws Exception {
		User user = GENERATOR.getDefault(true, true);
		KeyPair keyPair = keyManager.load(JWTHelper.JWT_SIGNING_KEY_NAME, KeyType.ECDSA);
		SignedJWT toTest = jwtHelper.generateSignedJWT(user);
		String jwtid = toTest.getJWTClaimsSet().getJWTID();
		SignedJWT jwt = new JWTHelperTestSuite().getSignedJWT(user, keyPair, DEFAULT_TIME, DEFAULT_VALIDITY_DATE, jwtid);
		assertJWTEquals(jwt, toTest);
	}

	@Test
	public void testIsValid_ValidJWT() throws Exception {
		User user = GENERATOR.getDefault(true, true);
		KeyPair keyPair = keyManager.load(JWTHelper.JWT_SIGNING_KEY_NAME, KeyType.ECDSA);
		SignedJWT signedJWT = new JWTHelperTestSuite().getSignedJWT(user, keyPair, DEFAULT_TIME, DEFAULT_VALIDITY_DATE, "");
		assertTrue(jwtHelper.isValid(signedJWT));
	}

	@Test
	public void testIsValid_WrongSigningKeys() throws Exception {
		User user = GENERATOR.getDefault(true, true);
		KeyPair keyPair = keyManager.load("FakeKeys", KeyType.ECDSA);
		SignedJWT signedJWT = new JWTHelperTestSuite().getSignedJWT(user, keyPair, DEFAULT_TIME, DEFAULT_VALIDITY_DATE,"");
		assertFalse(jwtHelper.isValid(signedJWT));
	}

	@Test
	public void testIsValid_OutdatedJWT() throws Exception {
		User user = GENERATOR.getDefault(true, true);
		KeyPair keyPair = keyManager.load(JWTHelper.JWT_SIGNING_KEY_NAME, KeyType.ECDSA);
		SignedJWT signedJWT = new JWTHelperTestSuite().getSignedJWT(user, keyPair, DEFAULT_TIME, LocalDateTime.now().minus(5, ChronoUnit.YEARS), "");
		assertFalse(jwtHelper.isValid(signedJWT));
	}

	@Test
	public void testIsValid_JWTInFuture() throws Exception {
		User user = GENERATOR.getDefault(true, true);
		KeyPair keyPair = keyManager.load(JWTHelper.JWT_SIGNING_KEY_NAME, KeyType.ECDSA);
		SignedJWT signedJWT = new JWTHelperTestSuite().getSignedJWT(user, keyPair, LocalDateTime.now().plus(5, ChronoUnit.YEARS), DEFAULT_VALIDITY_DATE, "");
		assertFalse(jwtHelper.isValid(signedJWT));
	}

	@Test(expected = JOSERuntimeException.class)
	public void testSignException() throws Exception {
		User user = GENERATOR.getNew(true, true);
		SignedJWT temporarySignedJWT = jwtHelper.generateSignedJWT(user);
		SignedJWT signedJWT = new SignedJWT(temporarySignedJWT.getHeader(), temporarySignedJWT.getJWTClaimsSet()) {
			@Override
			public synchronized void sign(final JWSSigner signer) throws JOSEException {
				throw new JOSEException("");
			}
		};

		KeyPair keyPair = keyManager.load("TestKey", KeyType.ECDSA);
		ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();
		jwtHelper.signJWT(privateKey, signedJWT);
	}

	@Test
	public void testDefaultCurrentTime() throws Exception {
		ChronoUnit chronoUnit = ChronoUnit.MINUTES;
		assertEquals(LocalDateTime.now().truncatedTo(chronoUnit), new JWTHelper().getCurrentTime().truncatedTo(chronoUnit));
	}

	private void assertJWTEquals(final SignedJWT expected, final SignedJWT toTest) throws ParseException {
		assertHeaderEquals(expected.getHeader(), toTest.getHeader());
		assertClaimEquals(expected.getJWTClaimsSet(), toTest.getJWTClaimsSet());
	}

	private void assertHeaderEquals(final JWSHeader expected, final JWSHeader toTest) {
		assertEquals(expected.getAlgorithm(), toTest.getAlgorithm());
		assertEquals(expected.getType(), toTest.getType());
		Map<String, Object> expectedParams = expected.getCustomParams();
		Map<String, Object> toTestParams = toTest.getCustomParams();
		assertEquals(expectedParams.size(), toTestParams.size());
		for (Map.Entry<String, Object> expectedParam : expectedParams.entrySet()) {
			assertEquals(expectedParam.getValue(), toTestParams.get(expectedParam.getKey()));
		}
	}

	private void assertClaimEquals(final ReadOnlyJWTClaimsSet expectedClaimsSet, final ReadOnlyJWTClaimsSet toTestClaimsSet) {
		Map<String, Object> expectedClaims = expectedClaimsSet.getAllClaims();
		Map<String, Object> toTestClaims = toTestClaimsSet.getAllClaims();
		assertEquals(expectedClaims.size(), toTestClaims.size());
		for (Map.Entry<String, Object> expectedClaim : expectedClaims.entrySet()) {
			assertEquals(expectedClaim.getValue(), toTestClaims.get(expectedClaim.getKey()));
		}
	}
}
