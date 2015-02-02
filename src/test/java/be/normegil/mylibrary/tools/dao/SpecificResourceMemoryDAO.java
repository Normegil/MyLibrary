package be.normegil.mylibrary.tools.dao;

import be.normegil.mylibrary.tools.CustomCollectors;
import be.normegil.mylibrary.framework.constraint.NotEmpty;
import be.normegil.mylibrary.framework.rest.RESTService;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.SpecificResource;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.SpecificResourceDAO;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public class SpecificResourceMemoryDAO extends MemoryDAO<SpecificResource> implements SpecificResourceDAO {
	@Override
	protected boolean correspondingID(final SpecificResource specificResource, final Object id) {
		return id.equals(specificResource.getId().get());
	}

	@Override
	public Optional<SpecificResource> get(@NotNull final Class<? extends RESTService> restServiceClass, @NotEmpty final String id) {
		return getAll()
				.filter((sr) -> sr.getRessourceID().equals(id) && sr.getRestService().equals(restServiceClass))
				.collect(new CustomCollectors().singletonCollector());
	}
}
