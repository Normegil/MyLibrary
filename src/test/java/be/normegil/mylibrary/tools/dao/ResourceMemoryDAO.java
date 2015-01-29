package be.normegil.mylibrary.tools.dao;

import be.normegil.mylibrary.framework.rest.RESTService;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.Resource;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.ResourceDAO;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ResourceMemoryDAO extends MemoryDAO<Resource> implements ResourceDAO {
	@Override
	protected boolean correspondingID(final Resource resource, final Object id) {
		return id.equals(resource.getId());
	}

	@Override
	public Optional<Resource> getByClass(@NotNull final Class<? extends RESTService> restServiceClass) {
		List<Resource> resources = getAll()
				.filter((r) -> restServiceClass.equals(r.getRestService()))
				.collect(Collectors.toList());
		if (resources.size() == 0) {
			return Optional.empty();
		} else if (resources.size() == 1) {
			return Optional.of(resources.iterator().next());
		} else {
			throw new IllegalStateException("Several corresponding Right[Class=" + restServiceClass.getSimpleName() + "]");
		}
	}
}
