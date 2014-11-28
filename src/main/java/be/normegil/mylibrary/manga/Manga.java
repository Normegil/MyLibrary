package be.normegil.mylibrary.manga;

import be.normegil.mylibrary.util.Entity;
import be.normegil.mylibrary.util.Updatable;
import be.normegil.mylibrary.util.constraint.ExistingID;
import be.normegil.mylibrary.util.constraint.NotEmpty;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.constraints.NotNull;
import java.net.URI;

@javax.persistence.Entity
@Access(AccessType.FIELD)
public class Manga extends Entity implements Updatable<Manga> {

	@NotEmpty
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

	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
	public static class Digest extends Entity.Digest {

		@NotEmpty
		private String name;

		public void fromBase(@NotNull final URI baseUri, @NotNull @ExistingID final Manga manga) {
			super.fromBase(baseUri, manga);
			this.name = manga.getName();
		}

		public Manga toBase() {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public void from(final Manga entity, final boolean includingNullFields) {
		if (includingNullFields || entity.getName() != null) {
			setName(entity.getName());
		}
	}
}
