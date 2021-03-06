package be.normegil.mylibrary.framework.security.rightsmanagement;

import be.normegil.mylibrary.framework.Entity;
import be.normegil.mylibrary.framework.rest.RESTMethod;
import be.normegil.mylibrary.framework.security.rightsmanagement.group.Group;
import be.normegil.mylibrary.framework.security.rightsmanagement.ressource.Resource;
import be.normegil.mylibrary.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@javax.persistence.Entity
@Access(AccessType.FIELD)
@Table(name = "MyLibraryRight")
public class Right extends Entity {

	@ManyToOne
	@JoinColumn(name = "id_user")
	private User user;
	@ManyToOne
	@JoinColumn(name = "id_group")
	private Group group;
	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_resource")
	private Resource resource;
	@NotNull
	@Enumerated(EnumType.STRING)
	private RESTMethod method;

	public Right(final User user, final Resource resource, final RESTMethod method) {
		this.user = user;
		this.resource = resource;
		this.method = method;
	}

	public Right(final Group group, final Resource resource, final RESTMethod method) {
		this.group = group;
		this.resource = resource;
		this.method = method;
	}

	public Right() {
	}

	public Optional<User> getUser() {
		return Optional.ofNullable(user);
	}

	public Optional<Group> getGroup() {
		return Optional.ofNullable(group);
	}

	public Resource getResource() {
		return resource;
	}

	public RESTMethod getMethod() {
		return method;
	}
}
