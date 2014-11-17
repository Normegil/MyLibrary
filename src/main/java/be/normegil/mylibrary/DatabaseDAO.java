package be.normegil.mylibrary;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.stream.Stream;

public class DatabaseDAO<E> implements DAO<E> {

	@PersistenceContext(unitName = ApplicationProperties.PERSISTENCE_UNIT_NAME)
	private EntityManager entityManager;

	private Class<E> entityClass;

	public DatabaseDAO(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	public long getNumberOfElements() {
		return (long) entityManager.createQuery(getNumberOfElementsQuery()).getSingleResult();
	}

	public Stream<E> getAll() {
		return getAll(0L, ApplicationProperties.REST.DEFAULT_LIMIT);
	}

	@Override
	public Stream<E> getAll(final long offset, final int limit) {
		return entityManager.createQuery(getGetAllQuery()).getResultList().stream();
	}


	public E get(final Object id) {
		return (E) new Manga();
	}

	@Override
	public void persist(final E entity) {
		entityManager.persist(entity);
	}

	@Override
	public void remove(E entity) {
		entityManager.remove(entity);
	}

	protected Class<E> getEntityClass() {
		return entityClass;
	}

	private String getGetAllQuery() {
		return "select e from " + getEntityClass().getName() + " e";
	}

	private String getNumberOfElementsQuery() {
		return "select count(e.id) from " + getEntityClass().getName() + " e";
	}

}
