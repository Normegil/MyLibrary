package be.normegil.mylibrary.framework;

import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.Validator;
import org.junit.Test;

import javax.validation.ConstraintViolationException;
import java.net.URI;

public class UTEntityDigestSafety {

	private final static ClassWrapper<Entity.Digest> CLASS = new ClassWrapper<>(Entity.Digest.class);
	private Entity.Digest digest = new Entity.Digest() {
	};

	@Test(expected = ConstraintViolationException.class)
	public void testFromBase_NullBaseURI() throws Exception {
		Validator.validate(digest, CLASS.getMethod("fromBase", URI.class, Entity.class), null, new Entity() {
		});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testFromBase_NullEntity() throws Exception {
		Validator.validate(digest, CLASS.getMethod("fromBase", URI.class, Entity.class), URI.create("http://www.example.com/"), null);
	}

}
