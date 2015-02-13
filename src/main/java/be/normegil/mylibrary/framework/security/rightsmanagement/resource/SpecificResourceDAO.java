package be.normegil.mylibrary.framework.security.rightsmanagement.resource;

import be.normegil.mylibrary.framework.constraint.NotEmpty;
import be.normegil.mylibrary.framework.dao.DAO;
import be.normegil.mylibrary.framework.rest.RESTService;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface SpecificResourceDAO extends DAO<SpecificResource> {

	public Optional<SpecificResource> get(@NotNull final Class<? extends RESTService> restServiceClass, @NotEmpty final String id);

}