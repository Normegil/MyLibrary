package be.normegil.mylibrary;

import java.util.stream.Stream;

public interface DAO<E> {

	public Stream<E> getAll();

	public Stream<E> getAll(final long offset, final int limit);

	public E get(final Object id);

	public void persist(final E entity);

	public void remove(final E entity);

}
