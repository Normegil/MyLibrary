package be.normegil.mylibrary.framework.security.identification;

import be.normegil.mylibrary.framework.Couple;
import be.normegil.mylibrary.framework.constraint.NotEmpty;
import be.normegil.mylibrary.framework.exception.ParseRuntimeException;
import be.normegil.mylibrary.framework.exception.WebApplicationException;
import be.normegil.mylibrary.framework.rest.error.ErrorCode;
import be.normegil.mylibrary.framework.security.identification.jwt.JWTHelper;
import be.normegil.mylibrary.user.User;
import be.normegil.mylibrary.user.UserDAO;
import com.nimbusds.jwt.SignedJWT;
import org.mindrot.jbcrypt.BCrypt;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.Optional;

@Stateless
public class Authenticator {

	@Inject
	private UserDAO dao;

	@Inject
	private JWTHelper jwtHelper;

	public Authenticator() {
	}

	public Authenticator(@NotNull final UserDAO dao, @NotNull final JWTHelper jwtHelper) {
		this.dao = dao;
		this.jwtHelper = jwtHelper;
	}

	public Couple<User, SignedJWT> authenticateUserPassword(@NotEmpty final String pseudo, @NotNull final String password) {
		User user = getUser(pseudo);
		if (BCrypt.checkpw(password, user.getHashedPassword())) {
			SignedJWT signedJWT = jwtHelper.generateSignedJWT(user);
			return new Couple<>(user, signedJWT);
		} else {
			throw new WebApplicationException(ErrorCode.AUTHENTICATION_WRONG_PASSWORD, new IllegalArgumentException("Password doesn't match"));
		}
	}

	public Couple<User, SignedJWT> authenticateToken(@NotNull final SignedJWT token) {
		if (jwtHelper.isValid(token)) {
			String issuer = getIssuer(token);
			User user = getUser(issuer);
			SignedJWT signedJWT = jwtHelper.generateSignedJWT(user);
			return new Couple<>(user, signedJWT);
		} else {
			throw new WebApplicationException(ErrorCode.AUTHENTICATION_INVALID_TOKEN, new IllegalArgumentException("Invalid Token detected"));
		}
	}

	private String getIssuer(final SignedJWT token) {
		String issuer;
		try {
			issuer = token.getJWTClaimsSet().getIssuer();
		} catch (ParseException e) {
			throw new ParseRuntimeException(e);
		}
		return issuer;
	}

	private User getUser(final String pseudo) {
		Optional<User> optionalUser = dao.getByPseudo(pseudo);
		if (!optionalUser.isPresent()) {
			throw new WebApplicationException(ErrorCode.AUTHENTICATION_USER_NOT_FOUND, new IllegalArgumentException("User not found exception"));
		}
		return optionalUser.get();
	}
}
