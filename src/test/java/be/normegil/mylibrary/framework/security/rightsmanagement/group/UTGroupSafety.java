package be.normegil.mylibrary.framework.security.rightsmanagement.group;

import be.normegil.mylibrary.tools.ClassWrapper;
import be.normegil.mylibrary.tools.Validator;
import be.normegil.mylibrary.user.User;
import org.junit.Test;

import javax.validation.ConstraintViolationException;
import java.util.Collection;

public class UTGroupSafety {

	private static final ClassWrapper<Group> CLASS = new ClassWrapper<>(Group.class);
	public static final String EMPTY_STRING = "";
	private Group entity = new Group();

	@Test(expected = ConstraintViolationException.class)
	public void testConstructor_Null() throws Exception {
		Validator.validate(CLASS.getConstructor(String.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testConstructor_Empty() throws Exception {
		Validator.validate(CLASS.getConstructor(String.class), EMPTY_STRING);
	}

	@Test(expected = ConstraintViolationException.class)
	public void testAddAllUsers_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("addAllUsers", Collection.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testAddUser_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("addUser", User.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testRemoveAllUsers_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("removeAllUsers", Collection.class), new Object[]{null});
	}

	@Test(expected = ConstraintViolationException.class)
	public void testRemoveUser_Null() throws Exception {
		Validator.validate(entity, CLASS.getMethod("removeUser", User.class), new Object[]{null});
	}

}
