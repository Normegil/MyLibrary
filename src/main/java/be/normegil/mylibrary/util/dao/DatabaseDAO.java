package be.normegil.mylibrary.util.dao;

import be.normegil.mylibrary.ApplicationProperties;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.stream.Stream;

public class DatabaseDAO<E> implements DAO<E> {

	@PersistenceContext(unitName = ApplicationProperties.PERSISTENCE_UNIT_NAME)
	private EntityManager entityManager;

	private Class<E> entityClass;

	public DatabaseDAO(@NotNull Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	public long getNumberOfElements() {
		return (long) entityManager.createQuery(getNumberOfElementsQuery()).getSingleResult();
	}

	@Override
	public Stream<E> getAll() {
		return getAll(0, ApplicationProperties.REST.DEFAULT_LIMIT);
	}

	@Override
	public Stream<E> getAll(final int offset, final int limit) {
		return entityManager.createQuery(getGetAllQuery())
				.setFirstResult(offset)
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

	protected Class<E> getEntityClass() {
		return entityClass;
	}

	private String getGetAllQuery() {
		GetAllQuery annotation = entityClass.getAnnotation(GetAllQuery.class);
		if (annotation == null) {
			throw new IllegalStateException("GetAllQuery Annotation not found for " + entityClass);
		} else if (StringUtils.isBlank(annotation.value())) {
			throw new IllegalStateException("Blank query found for " + entityClass);
		} else {
			return annotation.value();
		}
	}

	private String getNumberOfElementsQuery() {
		return "select count(e.id) from " + getEntityClass().getName() + " e";
	}

}
