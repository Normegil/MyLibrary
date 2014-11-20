package be.normegil.mylibrary.util.dao;

import be.normegil.mylibrary.ApplicationProperties;
import be.normegil.mylibrary.manga.Manga;
import be.normegil.mylibrary.util.NumbersHelper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.stream.Stream;

public abstract class DatabaseDAO<E> implements DAO<E> {

	@PersistenceContext(unitName = ApplicationProperties.PERSISTENCE_UNIT_NAME)
	private EntityManager entityManager;

	public long getNumberOfElements() {
		return (long) entityManager.createQuery(getNumberOfElementsQuery()).getSingleResult();
	}

	@Override
	public Stream<E> getAll() {
		return getAll(0, ApplicationProperties.REST.DEFAULT_LIMIT);
	}

	@Override
	public Stream<E> getAll(final long offset, final int limit) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<E> query = builder.createQuery(getEntityClass());
		query.from(Manga.class);
		return entityManager.createQuery(query)
				// Crappy offset convertion due to setFirstResult unable to take use long
				.setFirstResult(new NumbersHelper().safeLongToInt(offset))
				.setMaxResults(limit)
				.getResultList().stream();
	}

	public E get(@NotNull final Object id) {
		return entityManager.find(getEntityClass(), id);
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

	private String getNumberOfElementsQuery() {
		return "select count(e.id) from " + getEntityClass().getName() + " e";
	}
}
