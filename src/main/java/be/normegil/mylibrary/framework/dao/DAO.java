package be.normegil.mylibrary.framework.dao;

import be.normegil.mylibrary.framework.constraint.NotOptional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.stream.Stream;

public interface DAO<E> {

	public Stream<E> getAll();

	public Stream<E> getAll(final long offset, final int limit);

	public Optional<E> get(@NotNull @NotOptional final Object id);

	public void persist(@NotNull @Valid final E entity);

	public void remove(@NotNull @Valid final E entity);

}
