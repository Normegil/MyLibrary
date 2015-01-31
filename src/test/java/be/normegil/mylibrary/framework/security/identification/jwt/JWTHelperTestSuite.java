package be.normegil.mylibrary.framework.security.identification.jwt;

import be.normegil.mylibrary.framework.DateHelper;
import be.normegil.mylibrary.user.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.time.LocalDateTime;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		UTJWTHelperSafety.class,
		UTJWTHelper.class
})
public class JWTHelperTestSuite {

	public SignedJWT getSignedJWT(final User user, final KeyPair keyPair, final LocalDateTime issueTime, final LocalDateTime validityDate, final String jwtID) throws JOSEException {
		ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();

		JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES512)
				.type(new JOSEObjectType(JWTHelper.JWT_HEADER_TYP_VALUE))
				.build();
		SignedJWT jwt = new SignedJWT(
				header,
				generateClaims(user, issueTime, validityDate, jwtID)

		);
		ECDSASigner signer = new ECDSASigner(privateKey.getS());
		jwt.sign(signer);
		return jwt;
	}

	private JWTClaimsSet generateClaims(final User user, final LocalDateTime issueTime, final LocalDateTime validityDate, final String jwtID) {
		JWTClaimsSet claimsSet = new JWTClaimsSet();
		claimsSet.setIssuer(user.getPseudo());
		claimsSet.setIssueTime(new DateHelper().toDate(issueTime));
		claimsSet.setExpirationTime(new DateHelper().toDate(validityDate));
		claimsSet.setJWTID(jwtID);
		return claimsSet;
	}

}