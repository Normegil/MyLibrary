package be.normegil.mylibrary.framework;

import be.normegil.mylibrary.framework.constraint.ExistingID;
import be.normegil.mylibrary.framework.constraint.URIWithID;
import be.normegil.mylibrary.framework.rest.RESTHelper;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import java.net.URI;
import java.util.UUID;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class Entity {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2",
			strategy = "uuid2")
	@Type(type = "uuid-char")
	@XmlAttribute
	private UUID id;

	public UUID getId() {
		return id;
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
		Entity rhs = (Entity) obj;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(id)
				.toHashCode();
	}

	public abstract static class Digest {

		@NotNull
		@URIWithID
		protected URI href;

		public Entity toBase() {
			throw new UnsupportedOperationException();
		}

		protected void fromBase(@NotNull final URI baseUri, @NotNull @ExistingID final Entity entity) {
			href = new RESTHelper().toUri(baseUri, entity);
		}
	}
}
