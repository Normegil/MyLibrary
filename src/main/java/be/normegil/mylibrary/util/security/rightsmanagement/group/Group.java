package be.normegil.mylibrary.util.security.rightsmanagement.group;

import be.normegil.mylibrary.user.User;
import be.normegil.mylibrary.util.Entity;
import be.normegil.mylibrary.util.constraint.NotEmpty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@javax.persistence.Entity
@Access(AccessType.FIELD)
@Table(name = "MyLibraryGroup")
public class Group extends Entity {

	@NotNull
	@NotEmpty
	private String name;
	@ManyToMany
	private List<User> users = new ArrayList<>();

	public Group(final String name) {
		this.name = name;
	}

	// To be used by external frameworks only
	protected Group() {
	}

	public String getName() {
		return name;
	}

	public List<User> getUsers() {
		return new ArrayList<>(users);
	}

	public void addAllUsers(@NotNull final Collection<User> users) {
		for (User user : users) {
			addUser(user);
		}
	}

	public void addUser(@NotNull final User user) {
		users.add(user);
	}

	public void removeAllUsers(@NotNull final Collection<User> users) {
		for (User user : users) {
			removeUser(user);
		}
	}

	public void removeUser(@NotNull final User user) {
		users.remove(user);
	}
}
