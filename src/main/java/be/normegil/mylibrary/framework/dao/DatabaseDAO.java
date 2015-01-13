package be.normegil.mylibrary.framework.dao;

import be.normegil.mylibrary.ApplicationProperties;
import be.normegil.mylibrary.framework.NumbersHelper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class DatabaseDAO<E> implements DAO<E> {

	@PersistenceContext(unitName = ApplicationProperties.PERSISTENCE_UNIT_NAME)
	private EntityManager entityManager;

	protected DatabaseDAO() {
	}

	protected DatabaseDAO(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public long getNumberOfElements() {
		return (long) entityManager.createQuery(getNumberOfElementsQuery()).getSingleResult();
	}

	@Override
	public Stream<E> getAll() {
		CriteriaQuery<E> query = getQuery();
		return entityManager.createQuery(query)
				.getResultList().stream();
	}

	@Override
	public Stream<E> getAll(final long offset, final int limit) {
		CriteriaQuery<E> query = getQuery();
		return entityManager.createQuery(query)
				// Crappy offset convertion due to setFirstResult unable to take use long
				.setFirstResult(new NumbersHelper().safeLongToInt(offset))
				.setMaxResults(limit)
				.getResultList().stream();
	}

	public Optional<E> get(@NotNull final Object id) {
		return Optional.ofNullable(entityManager.find(getEntityClass(), id));
	}

	@Override
	public void persist(@NotNull @Valid final E entity) {
		entityManager.persist(entity);
	}

	@Override
	public void remove(@NotNull @Valid final E entity) {
		entityManager.remove(entity);
	}

	protected abstract Class<E> getEntityClass();

	protected EntityManager getEntityManager() {
		return entityManager;
	}

	private String getNumberOfElementsQuery() {
		return "select count(e.id) from " + getEntityClass().getName() + " e";
	}

	private CriteriaQuery<E> getQuery() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<E> query = builder.createQuery(getEntityClass());
		query.from(getEntityClass());
		return query;
	}
}
