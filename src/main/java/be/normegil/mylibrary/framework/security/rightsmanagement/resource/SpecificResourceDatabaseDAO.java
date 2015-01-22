package be.normegil.mylibrary.framework.security.rightsmanagement.resource;

import be.normegil.mylibrary.framework.dao.DatabaseDAO;
import be.normegil.mylibrary.framework.rest.RESTService;

import javax.ejb.Stateless;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Stateless
public class SpecificResourceDatabaseDAO extends DatabaseDAO<SpecificResource> {

	public Optional<SpecificResource> get(final Class<? extends RESTService> restServiceClass, final String id) {
		List<SpecificResource> resultList = getEntityManager()
				.createQuery(getQuery(restServiceClass, id))
				.getResultList();
		if (resultList.isEmpty()) {
			return Optional.empty();
		} else if (resultList.size() == 1) {
			return Optional.of(resultList.get(0));
		} else {
			throw new IllegalStateException("Found more than one specific ressource. [Class=" + restServiceClass + ";ID=" + id + ";]");
		}
	}

	private CriteriaQuery<SpecificResource> getQuery(final Class<? extends RESTService> restService, final String id) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<SpecificResource> query = builder.createQuery(getEntityClass());
		generateWhereClause(builder, query, restService, id);
		return query;
	}

	private void generateWhereClause(final CriteriaBuilder builder, final CriteriaQuery<SpecificResource> query,
	                                 final Class<? extends RESTService> restService, final String id) {
		Metamodel metamodel = getEntityManager().getMetamodel();
		EntityType<SpecificResource> userType = metamodel.entity(SpecificResource.class);
		Root<SpecificResource> resource = query.from(SpecificResource.class);

		SingularAttribute<? super SpecificResource, Class> serviceAttribute = userType.getSingularAttribute("restService", Class.class);
		Path<Class> servicePath = resource.get(serviceAttribute);
		Predicate servicePredicate = builder.equal(servicePath, restService);

		SingularAttribute<SpecificResource, String> idAttribute = userType.getDeclaredSingularAttribute("resourceID", String.class);
		Path<String> idPath = resource.get(idAttribute);
		Predicate idPredicate = builder.equal(idPath, id);

		query.select(resource);
		query.where(servicePredicate, idPredicate);
	}

	@Override
	protected Class<SpecificResource> getEntityClass() {
		return SpecificResource.class;
	}

	@Override
	protected List<Order> getOrderByParameters(final CriteriaBuilder builder, final Root<SpecificResource> root) {
		return Arrays.asList(
				builder.asc(root.get("ressourceID"))
		);
	}
}
