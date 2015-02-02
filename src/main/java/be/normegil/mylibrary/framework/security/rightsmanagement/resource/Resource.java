package be.normegil.mylibrary.framework.security.rightsmanagement.resource;

import be.normegil.mylibrary.ApplicationProperties;
import be.normegil.mylibrary.framework.Entity;
import be.normegil.mylibrary.framework.rest.RESTService;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.constraints.NotNull;

@javax.persistence.Entity
@Access(AccessType.FIELD)
public class Resource extends Entity {
	private Class<? extends RESTService> restService;

	public Resource(@NotNull final Class<? extends RESTService> restService) {
		this.restService = restService;
	}

	protected Resource() {
	}

	public Class<? extends RESTService> getRestService() {
		return restService;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ApplicationProperties.TO_STRING_STYLE)
				.appendSuper(super.toString())
				.append("restService", restService.getSimpleName())
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
		Resource rhs = (Resource) obj;
		return new EqualsBuilder()
				.append(this.restService, rhs.restService)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(restService)
				.toHashCode();
	}
}
