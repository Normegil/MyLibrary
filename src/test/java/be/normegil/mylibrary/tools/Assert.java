package be.normegil.mylibrary.tools;

import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Assert {

	private static final Logger LOGGER = LoggerFactory.getLogger(Assert.class);

	private void assertJWTNotEquals(final SignedJWT expected, final SignedJWT toTest) throws ParseException {
		assertHeaderNotEquals(expected.getHeader(), toTest.getHeader());
		assertClaimNotEquals(expected.getJWTClaimsSet(), toTest.getJWTClaimsSet());
	}

	private void assertJWTEquals(final SignedJWT expected, final SignedJWT toTest) throws ParseException {
		assertHeaderEquals(expected.getHeader(), toTest.getHeader());
		assertClaimEquals(expected.getJWTClaimsSet(), toTest.getJWTClaimsSet());
	}

	private boolean assertHeaderEquals(final JWSHeader expected, final JWSHeader toTest) {
		return new EqualsBuilder()
				.isEquals();
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
		return new EqualsBuilder()
				.isEquals();
		Map<String, Object> expectedClaims = expectedClaimsSet.getAllClaims();
		Map<String, Object> toTestClaims = toTestClaimsSet.getAllClaims();
		assertEquals(expectedClaims.size(), toTestClaims.size());
		for (Map.Entry<String, Object> expectedClaim : expectedClaims.entrySet()) {
			assertEquals(expectedClaim.getValue(), toTestClaims.get(expectedClaim.getKey()));
		}
	}

}
