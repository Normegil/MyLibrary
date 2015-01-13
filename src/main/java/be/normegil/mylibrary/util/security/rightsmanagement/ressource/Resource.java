package be.normegil.mylibrary.util.security.rightsmanagement.ressource;

import be.normegil.mylibrary.ApplicationProperties;
import be.normegil.mylibrary.util.Entity;
import be.normegil.mylibrary.util.rest.RESTService;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Access;
import javax.persistence.AccessType;

@javax.persistence.Entity
@Access(AccessType.FIELD)
public class Resource extends Entity {
	private Class<? extends RESTService> restService;

	public Resource(final Class<? extends RESTService> restService) {
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
}
