package be.normegil.mylibrary.util.security.rightsmanagement;

import be.normegil.mylibrary.user.User;
import be.normegil.mylibrary.util.rest.RESTMethod;
import be.normegil.mylibrary.util.security.rightsmanagement.group.Group;
import be.normegil.mylibrary.util.security.rightsmanagement.ressource.Resource;
import be.normegil.mylibrary.util.security.rightsmanagement.ressource.ResourceDatabaseDAO;
import be.normegil.mylibrary.util.security.rightsmanagement.ressource.SpecificResource;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;

@Stateless
@LocalBean
public class RightsManager {

	@Inject
	private RightDatabaseDAO rightDAO;

	@Inject
	private ResourceDatabaseDAO resourceDAO;

	public boolean canAccess(final User user, final Resource resource, final RESTMethod method) {
		boolean canAccess = false;
		if (resource instanceof SpecificResource && ((SpecificResource) resource).getOwner().isPresent()) {
			User owner = ((SpecificResource) resource).getOwner().get();
			if (user.equals(owner)) {
				canAccess = true;
			}
		} else {
			canAccess = checkGroupsRights(user.getGroups(), resource, method);
		}
		return canAccess;
	}

	private boolean checkGroupsRights(final Collection<Group> groups, final Resource resource, final RESTMethod method) {
		boolean canAccess = false;

		Optional<Resource> mainResourceOptional = resourceDAO.get(resource.getRestService());
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

	public boolean canAccess(final Group group, final Resource resource, final RESTMethod method) {
		Optional<Right> rightOptional = rightDAO.get(group, resource, method);
		return rightOptional.isPresent();
	}

	public void grantAccess(final Group group, final Resource resource, final RESTMethod method) {
		rightDAO.persist(new Right(group, resource, method));
	}

	public void denyAccess(final Group group, final Resource resource, final RESTMethod method) {
		Optional<Right> rightOptional = rightDAO.get(group, resource, method);
		if (rightOptional.isPresent()) {
			rightDAO.remove(rightOptional.get());
		}
	}
}
