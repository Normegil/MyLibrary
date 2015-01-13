package be.normegil.mylibrary.util.security.rightsmanagement.ressource;

import be.normegil.mylibrary.util.dao.DatabaseDAO;
import be.normegil.mylibrary.util.rest.RESTService;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Optional;

@Stateless
public class ResourceDatabaseDAO extends DatabaseDAO<Resource> {
	@Override
	protected Class<Resource> getEntityClass() {
		return Resource.class;
	}

	public ResourceDatabaseDAO() {
	}

	public ResourceDatabaseDAO(final EntityManager entityManager) {
		super(entityManager);
	}

	public Optional<Resource> get(final Class<? extends RESTService> restServiceClass) {
		Resource resource = getEntityManager()
				.createQuery(getQuery(restServiceClass))
				.getSingleResult();
		return Optional.ofNullable(resource);
	}

	private CriteriaQuery<Resource> getQuery(final Class<? extends RESTService> restService) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Resource> query = builder.createQuery(getEntityClass());
		generateWhereClause(builder, query, restService);
		return query;
	}

	private void generateWhereClause(final CriteriaBuilder builder, final CriteriaQuery<Resource> query, final Class<? extends RESTService> restService) {
		Metamodel metamodel = getEntityManager().getMetamodel();
		EntityType<Resource> userType = metamodel.entity(getEntityClass());
		Root<Resource> resource = query.from(getEntityClass());

		SingularAttribute<Resource, Class> attribute = userType.getDeclaredSingularAttribute("restService", Class.class);
		Path<Class> servicePath = resource.get(attribute);

		query.where(builder.equal(servicePath, restService));
	}
}
