package be.normegil.mylibrary.util;

import be.normegil.mylibrary.util.constraint.ExistingID;
import be.normegil.mylibrary.util.constraint.URIWithID;
import be.normegil.mylibrary.util.rest.RESTHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@MappedSuperclass
@Access(AccessType.FIELD)
public class Entity {

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

	public static class EntityDigest {

		@NotNull
		@URIWithID
		protected URI href;

		public void fromBase(@NotNull final URI baseUri, @NotNull @ExistingID final Entity entity) {
			href = new RESTHelper().getRESTUri(baseUri, entity.getClass(), entity);
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
			EntityDigest rhs = (EntityDigest) obj;
			return new EqualsBuilder()
					.append(this.href, rhs.href)
					.isEquals();
		}

		@Override
		public int hashCode() {
			return new HashCodeBuilder()
					.append(href)
					.toHashCode();
		}
	}

	public static Helper helper() {
		return new Helper();
	}

	public static class Helper {

		public UUID getIdFromRESTURI(@NotNull @URIWithID final URI restURI) {
			String idAsString = StringUtils.substringAfterLast(restURI.toString(), "/");
			try {
				return UUID.fromString(idAsString);
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("URI : " + restURI, e);
			}
		}

		public void setIdFromDigest(@NotNull @Valid final EntityDigest digest, @NotNull final Entity entity) {
			entity.id = digest.href != null ? getIdFromRESTURI(digest.href) : null;
		}

		public List<URI> convertToURLs(@NotNull final List<? extends Entity> entities, @NotNull final URI baseURI) {
			List<URI> urisToEntities = new ArrayList<>();
			RESTHelper helper = new RESTHelper();
			for (Entity entity : entities) {
				UUID id = entity.getId();
				URI uriToEntity = helper.toURI(baseURI, id);
				urisToEntities.add(uriToEntity);
			}
			return urisToEntities;
		}
	}
}
