package be.normegil.mylibrary.framework.security.rightsmanagement;

import be.normegil.mylibrary.framework.rest.RESTMethod;
import be.normegil.mylibrary.framework.security.rightsmanagement.group.Group;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.Resource;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.ResourceDAO;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.SpecificResource;
import be.normegil.mylibrary.user.User;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

@Stateless
@LocalBean
public class RightsManager {

	@Inject
	private RightDAO rightDAO;

	@Inject
	private ResourceDAO resourceDAO;

	public RightsManager() {
	}

	public RightsManager(@NotNull final RightDAO rightDAO, @NotNull final ResourceDAO resourceDAO) {
		this.rightDAO = rightDAO;
		this.resourceDAO = resourceDAO;
	}

	public boolean canAccess(@NotNull final User user, @NotNull final Resource resource, @NotNull final RESTMethod method) {
		boolean canAccess = false;
		if (checkGroupsRights(user.getGroups(), resource, method)) {
			canAccess = true;
		}
		if (resource instanceof SpecificResource && ((SpecificResource) resource).getOwner().isPresent()) {
			User owner = ((SpecificResource) resource).getOwner().get();
			if (user.equals(owner)) {
				canAccess = true;
			}
		}
		return canAccess;
	}

	private boolean checkGroupsRights(@NotNull final Collection<Group> groups, @NotNull final Resource resource, @NotNull final RESTMethod method) {
		boolean canAccess = false;

		Optional<Resource> mainResourceOptional = resourceDAO.getByClass(resource.getRestService());
		Resource mainResource = mainResourceOptional
				.orElseThrow(() -> new IllegalStateException("Resource [" + resource.getRestService() + "] not found"));

		for (Group group : groups) {
			if (canAccess(group, mainResource, method)) {
				canAccess = true;
				break;
			}
		}
		return canAccess;
	}

	public boolean canAccess(@NotNull final Group group, @NotNull final Resource resource, @NotNull final RESTMethod method) {
		Optional<Right> rightOptional = rightDAO.get(group, resource, method);
		return rightOptional.isPresent();
	}

	public void grantAccess(@NotNull final Group group, @NotNull final Resource resource, @NotNull final RESTMethod method) {
		rightDAO.persist(new Right(group, resource, method));
	}

	public void denyAccess(@NotNull final Group group, @NotNull final Resource resource, @NotNull final RESTMethod method) {
		Optional<Right> rightOptional = rightDAO.get(group, resource, method);
		if (rightOptional.isPresent()) {
			rightDAO.remove(rightOptional.get());
		}
	}
}
