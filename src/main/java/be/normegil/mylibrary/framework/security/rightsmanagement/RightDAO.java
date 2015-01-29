package be.normegil.mylibrary.framework.security.rightsmanagement;

import be.normegil.mylibrary.framework.dao.DAO;
import be.normegil.mylibrary.framework.rest.RESTMethod;
import be.normegil.mylibrary.framework.security.rightsmanagement.group.Group;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.Resource;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.SpecificResource;
import be.normegil.mylibrary.user.User;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface RightDAO extends DAO<Right> {

	public Optional<Right> get(@NotNull final Group group, @NotNull final Resource resource, @NotNull final RESTMethod method);

	public Optional<Right> get(@NotNull final User user, @NotNull final SpecificResource resource, @NotNull final RESTMethod method);

}
