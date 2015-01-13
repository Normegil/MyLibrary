package be.normegil.mylibrary.user;

import be.normegil.mylibrary.ApplicationProperties;
import be.normegil.mylibrary.framework.Entity;
import be.normegil.mylibrary.framework.Updatable;
import be.normegil.mylibrary.framework.constraint.NotEmpty;
import be.normegil.mylibrary.framework.security.rightsmanagement.group.Group;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@javax.persistence.Entity
@Access(AccessType.FIELD)
@Table(name = "MyLibraryUser")
public class User extends Entity implements Updatable<User> {
	@Column(unique = true)
	@NotEmpty
	private String pseudo;
	@NotEmpty
	private String hashedPassword;
	@ManyToMany
	private Set<Group> groups = new HashSet<>();

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(final String pseudo) {
		this.pseudo = pseudo;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(final String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public Set<Group> getGroups() {
		return new HashSet<>(groups);
	}

	public void addAllGroups(@NotNull final Collection<Group> groups) {
		for (Group group : groups) {
			addGroup(group);
		}
	}

	public void addGroup(@NotNull final Group group) {
		groups.add(group);
	}

	public void removeAllGroups(@NotNull final Collection<Group> groups) {
		for (Group group : groups) {
			removeGroup(group);
		}
	}

	public void removeGroup(@NotNull final Group group) {
		groups.remove(group);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ApplicationProperties.TO_STRING_STYLE)
				.appendSuper(super.toString())
				.append("pseudo", pseudo)
				.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		User rhs = (User) obj;
		return new EqualsBuilder()
				.appendSuper(super.equals(obj))
				.append(this.pseudo, rhs.pseudo)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.appendSuper(super.hashCode())
				.append(pseudo)
				.toHashCode();
	}

	@Override
	public void from(final User entity, final boolean includingNullFields) {
		if (includingNullFields || entity.getPseudo() != null) {
			setPseudo(entity.getPseudo());
		}
		if (includingNullFields || entity.getHashedPassword() != null) {
			setHashedPassword(entity.getHashedPassword());
		}
	}
}
