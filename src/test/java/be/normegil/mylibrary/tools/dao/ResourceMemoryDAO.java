package be.normegil.mylibrary.tools.dao;

import be.normegil.mylibrary.tools.CustomCollectors;
import be.normegil.mylibrary.framework.rest.RESTService;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.Resource;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.ResourceDAO;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public class ResourceMemoryDAO extends MemoryDAO<Resource> implements ResourceDAO {
	@Override
	protected boolean correspondingID(final Resource resource, final Object id) {
		return id.equals(resource.getId());
	}

	@Override
	public Optional<Resource> getByClass(@NotNull final Class<? extends RESTService> restServiceClass) {
		return getAll()
				.filter((r) -> restServiceClass.equals(r.getRestService()))
				.collect(new CustomCollectors().singletonCollector());
	}
}
