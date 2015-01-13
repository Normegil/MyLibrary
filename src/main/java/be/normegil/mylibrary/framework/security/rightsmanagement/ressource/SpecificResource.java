package be.normegil.mylibrary.framework.security.rightsmanagement.ressource;

import be.normegil.mylibrary.ApplicationProperties;
import be.normegil.mylibrary.user.User;
import be.normegil.mylibrary.framework.rest.RESTService;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Optional;

@javax.persistence.Entity
@Access(AccessType.FIELD)
public class SpecificResource extends Resource {
	@ManyToOne
	@JoinColumn(name = "id_user")
	private User owner;
	private String resourceID;

	protected SpecificResource() {
	}

	public SpecificResource(final Class<? extends RESTService> restService, final String resourceID) {
		super(restService);
		this.resourceID = resourceID;
	}

	public SpecificResource(final Class<? extends RESTService> restService, final String resourceID, final User owner) {
		super(restService);
		this.owner = owner;
		this.resourceID = resourceID;
	}

	public Optional<User> getOwner() {
		return Optional.ofNullable(owner);
	}

	public String getRessourceID() {
		return resourceID;
	}


	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this, ApplicationProperties.TO_STRING_STYLE)
				.appendSuper(super.toString())
				.append("resourceID", resourceID);
		if (owner != null) {
			builder
					.append("owner", owner);
		}
		return builder.toString();
	}
}
