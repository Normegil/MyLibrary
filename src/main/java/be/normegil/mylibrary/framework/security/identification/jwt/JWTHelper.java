package be.normegil.mylibrary.util.security.identification.jwt;

import be.normegil.mylibrary.ApplicationProperties;
import be.normegil.mylibrary.user.User;
import be.normegil.mylibrary.user.UserDatabaseDAO;
import be.normegil.mylibrary.util.DateHelper;
import be.normegil.mylibrary.util.exception.JOSERuntimeException;
import be.normegil.mylibrary.util.exception.ParseRuntimeException;
import be.normegil.mylibrary.util.security.identification.key.KeyManager;
import be.normegil.mylibrary.util.security.identification.key.KeyType;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.ECPoint;
import java.text.ParseException;
import java.time.LocalDateTime;

@Stateless
public class JWTHelper {

	public static final String JWT_SIGNING_KEY_NAME = "JWTSigning";
	public static final String JWE_CRYPTOGRAPHIC_KEY_NAME = "JWECryptographic";
	private static final String JWT_HEADER_TYP_VALUE = "JWT";
	@Inject
	private KeyManager keyManager;

	@Inject
	private UserDatabaseDAO dao;

	/**
	 * Doesn't work - Need specific manipulation of Wildfly due to BouncyCastle
	 * Not Needed until we have a confidential information in the JWT
	 */
//	public JWEObject generateEncryptedJWT(User user) {
//		try {
//			SignedJWT signedJWT = generateSignedJWT(user);
//			JWEObject jweObject = new JWEObject(
//					new JWEHeader(JWEAlgorithm.RSA_OAEP, EncryptionMethod.A128GCM),
//					new Payload(signedJWT)
//			);
//			KeyPair cryptographicKeys = keyManager.load(JWE_CRYPTOGRAPHIC_KEY_NAME, KeyType.RSA);
//			RSAPublicKey publicKey = (RSAPublicKey) cryptographicKeys.getPublic();
//			RSAEncrypter encrypter = new RSAEncrypter(publicKey);
//			jweObject.encrypt(encrypter);
//			return jweObject;
//		} catch (JOSEException e) {
//			throw new JOSERuntimeException(e);
//		}
//	}
	public SignedJWT generateSignedJWT(final User user) throws JOSEException {
		KeyPair keyPair = keyManager.load(JWT_SIGNING_KEY_NAME, KeyType.ECDSA);
		ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();

		JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES512)
				.type(new JOSEObjectType(JWT_HEADER_TYP_VALUE))
				.build();

		SignedJWT jwt = new SignedJWT(
				header,
				generateClaims(user)
		);

		ECDSASigner signer = new ECDSASigner(privateKey.getS());
		jwt.sign(signer);
		return jwt;
	}

	public boolean isValid(final SignedJWT token) {
		try {
			KeyPair keyPair = keyManager.load(JWT_SIGNING_KEY_NAME, KeyType.ECDSA);
			if (!validateSignature(token, keyPair)) {
				return false;
			}
			if (!validateTimeInfo(token)) {
				return false;
			}

			// TODO Use jti (JWT ID) field

			return true;
		} catch (JOSEException e) {
			throw new JOSERuntimeException(e);
		} catch (ParseException e) {
			throw new ParseRuntimeException(e);
		}
	}

	private boolean validateSignature(final SignedJWT token, final KeyPair keyPair) throws JOSEException {
		ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();

		ECPoint ecPoint = publicKey.getW();
		BigInteger x = ecPoint.getAffineX();
		BigInteger y = ecPoint.getAffineY();

		ECDSAVerifier verifier = new ECDSAVerifier(x, y);
		return token.verify(verifier);
	}

	private boolean validateTimeInfo(final SignedJWT token) throws ParseException {
		LocalDateTime issueTime = new DateHelper().from(token.getJWTClaimsSet().getIssueTime());
		LocalDateTime now = LocalDateTime.now();
		if (now.isBefore(issueTime)) {
			return false;
		}
		LocalDateTime expirationTime = new DateHelper().from(token.getJWTClaimsSet().getExpirationTime());
		if (now.isAfter(expirationTime)) {
			return false;
		}
		return true;
	}

	private SignedJWT getSignedJWT(final JWEObject encryptedToken) throws JOSEException {
		KeyPair cryptographicKeys = keyManager.load(JWE_CRYPTOGRAPHIC_KEY_NAME, KeyType.RSA);
		encryptedToken.decrypt(new RSADecrypter((RSAPrivateKey) cryptographicKeys.getPrivate()));
		return encryptedToken.getPayload().toSignedJWT();
	}

	private JWTClaimsSet generateClaims(final User user) {
		JWTClaimsSet claimsSet = new JWTClaimsSet();
		claimsSet.setIssuer(user.getPseudo());

		LocalDateTime issueTime = LocalDateTime.now();
		claimsSet.setIssueTime(new DateHelper().toDate(issueTime));

		LocalDateTime expirationTime = issueTime.plus(ApplicationProperties.Security.JSonWebToken.TOKEN_VALIDITY_PERIOD);
		claimsSet.setExpirationTime(new DateHelper().toDate(expirationTime));

		return claimsSet;
	}
}
