package be.normegil.mylibrary.framework.security.identification.jwt;

import be.normegil.mylibrary.framework.exception.JOSERuntimeException;
import be.normegil.mylibrary.framework.exception.ParseRuntimeException;
import be.normegil.mylibrary.framework.security.identification.key.KeyManager;
import be.normegil.mylibrary.tools.*;
import be.normegil.mylibrary.tools.dao.KeyMemoryDAO;
import be.normegil.mylibrary.user.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import org.junit.Test;

import javax.validation.ConstraintViolationException;
import java.security.KeyPair;
import java.text.ParseException;

public class UTJWTHelperSafety {

	private static final ClassWrapper<JWTHelper> CLASS = new ClassWrapper<>(JWTHelper.class);
	private static final IGenerator<User> GENERATOR = GeneratorRepository.get(User.class);
	private JWTHelper entity = new JWTHelper();

	@Test(expected = ConstraintViolationException.class)
	public void testGenerateSignedJWT_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("generateSignedJWT", User.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testIsValid_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("isValid", SignedJWT.class), new Object[]{null});
	}

	@Test(expected = JOSERuntimeException.class)
	public void testJOSEException() throws Exception {
		JWTHelper jwtHelper = new JWTHelper() {
			@Override
			protected boolean validateSignature(final SignedJWT token, final KeyPair keyPair) throws JOSEException {
				throw new JOSEException("");
			}
		};
		ClassWrapper<JWTHelper> jwtHelperClass = new ClassWrapper<>(JWTHelper.class);
		FieldWrapper field = jwtHelperClass.getField("keyManager");
		KeyManager keyManager = new KeyManager(new KeyMemoryDAO());
		field.set(jwtHelper, keyManager);

		jwtHelper.isValid(jwtHelper.generateSignedJWT(GENERATOR.getDefault(true, true)));
	}

	@Test(expected = ParseRuntimeException.class)
	public void testParseException() throws Exception {
		JWTHelper jwtHelper = new JWTHelper() {
			@Override
			protected boolean validateTimeInfo(final SignedJWT token) throws ParseException {
				throw new ParseException("", 0);
			}
		};
		ClassWrapper<JWTHelper> jwtHelperClass = new ClassWrapper<>(JWTHelper.class);
		FieldWrapper field = jwtHelperClass.getField("keyManager");
		KeyManager keyManager = new KeyManager(new KeyMemoryDAO());
		field.set(jwtHelper, keyManager);

		jwtHelper.isValid(jwtHelper.generateSignedJWT(GENERATOR.getDefault(true, true)));
	}
}
