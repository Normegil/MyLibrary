package be.normegil.mylibrary.manga;

import be.normegil.mylibrary.Entity;
import be.normegil.mylibrary.dao.GetAllQuery;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Access;
import javax.persistence.AccessType;

@javax.persistence.Entity
@Access(AccessType.FIELD)
@GetAllQuery("SELECT m FROM Manga m ORDER BY m.name")
public class Manga extends Entity {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
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
		Manga rhs = (Manga) obj;
		return new EqualsBuilder()
				.append(this.name, rhs.name)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(name)
				.toHashCode();
	}
}
