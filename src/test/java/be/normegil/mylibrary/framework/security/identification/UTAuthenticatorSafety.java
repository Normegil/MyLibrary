package be.normegil.mylibrary.framework.security.identification;

import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.Validator;
import com.nimbusds.jwt.SignedJWT;
import org.junit.Test;

import javax.validation.ConstraintViolationException;

public class UTAuthenticatorSafety {

	private static final ClassWrapper<Authenticator> CLASS = new ClassWrapper<>(Authenticator.class);
	public static final String DEFAULT_PASSWORD = "Password";
	public static final String DEFAULT_PSEUDO = "Pseudo";
	private Authenticator entity = new Authenticator();

	@Test(expected = ConstraintViolationException.class)
	public void testAuthenticateUserPassword_NullPseudo() throws Exception {
		Validator.validate(entity, CLASS.getMethod("authenticateUserPassword", String.class, String.class), null, DEFAULT_PASSWORD);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testAuthenticateUserPassword_EmptyPseudo() throws Exception {
		Validator.validate(entity, CLASS.getMethod("authenticateUserPassword", String.class, String.class), "", DEFAULT_PASSWORD);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testAuthenticateUserPassword_NullPassword() throws Exception {
		Validator.validate(entity, CLASS.getMethod("authenticateUserPassword", String.class, String.class), DEFAULT_PSEUDO, null);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testAuthenticateToken_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("authenticateToken", SignedJWT.class), new Object[]{null});
	}
}
