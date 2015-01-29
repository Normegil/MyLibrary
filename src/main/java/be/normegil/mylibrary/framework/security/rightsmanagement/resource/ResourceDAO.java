package be.normegil.mylibrary.framework.security.rightsmanagement.resource;

import be.normegil.mylibrary.framework.dao.DAO;
import be.normegil.mylibrary.framework.rest.RESTService;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface ResourceDAO extends DAO<Resource> {

	public Optional<Resource> getByClass(@NotNull final Class<? extends RESTService> restServiceClass);

}
