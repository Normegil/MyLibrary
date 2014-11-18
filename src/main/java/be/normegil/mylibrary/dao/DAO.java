package be.normegil.mylibrary.dao;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.stream.Stream;

public interface DAO<E> {

	public Stream<E> getAll();

	public Stream<E> getAll(final int offset, final int limit);

	public E get(@NotNull final Object id);

	public void persist(@NotNull @Valid final E entity);

	public void remove(@NotNull @Valid final E entity);

}
