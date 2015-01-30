package be.normegil.mylibrary.tools;

import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Map;

public class Assert {

	private static final Logger LOGGER = LoggerFactory.getLogger(Assert.class);

	public void assertJWTNotEquals(final SignedJWT expected, final SignedJWT toTest) throws ParseException {
		if (headerEquals(expected.getHeader(), toTest.getHeader())
				&& claimEquals(expected.getJWTClaimsSet(), toTest.getJWTClaimsSet())) {
			throw new AssertionError();
		}
	}

	public void assertJWTEquals(final SignedJWT expected, final SignedJWT toTest) throws ParseException {
		if (!headerEquals(expected.getHeader(), toTest.getHeader())
				|| !claimEquals(expected.getJWTClaimsSet(), toTest.getJWTClaimsSet())) {
			throw new AssertionError();
		}
	}

	private boolean headerEquals(final JWSHeader expected, final JWSHeader toTest) {
		EqualsBuilder builder = new EqualsBuilder()
				.append(expected.getAlgorithm(), toTest.getAlgorithm())
				.append(expected.getType(), toTest.getType())
				.append(expected.getCustomParams().size(), toTest.getCustomParams().size());
		Map<String, Object> expectedParams = expected.getCustomParams();
		Map<String, Object> toTestParams = toTest.getCustomParams();
		for (Map.Entry<String, Object> expectedParam : expectedParams.entrySet()) {
			builder.append(expectedParam.getValue(), toTestParams.get(expectedParam.getKey()));
		}
		return builder.isEquals();
	}

	private boolean claimEquals(final ReadOnlyJWTClaimsSet expectedClaimsSet, final ReadOnlyJWTClaimsSet toTestClaimsSet) {
		EqualsBuilder equalsBuilder = new EqualsBuilder()
				.append(expectedClaimsSet.getAllClaims().size(), toTestClaimsSet.getAllClaims().size());
		Map<String, Object> expectedClaims = expectedClaimsSet.getAllClaims();
		Map<String, Object> toTestClaims = toTestClaimsSet.getAllClaims();
		for (Map.Entry<String, Object> expectedClaim : expectedClaims.entrySet()) {
			equalsBuilder.append(expectedClaim.getValue(), toTestClaims.get(expectedClaim.getKey()));
		}
		return equalsBuilder.isEquals();
	}

}
