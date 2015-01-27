package be.normegil.mylibrary.tools.dao;

import be.normegil.mylibrary.framework.NumbersHelper;
import be.normegil.mylibrary.framework.constraint.NotOptional;
import be.normegil.mylibrary.framework.dao.DAO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class MemoryDAO<E> implements DAO<E> {

	private static final NumbersHelper numbersHelper = new NumbersHelper();
	private List<E> datas = new ArrayList<>();

	@Override
	public Stream<E> getAll() {
		return new ArrayList<>(datas)
				.stream();
	}

	@Override
	public Stream<E> getAll(final long offset, final int limit) {
		ArrayList<E> toReturn = new ArrayList<>();
		for (int i = 0; i < offset + limit; i++) {
			if (i + offset < datas.size()) {
				toReturn.add(datas.get(i + numbersHelper.safeLongToInt(offset)));
			}
		}
		return toReturn.stream();
	}

	protected abstract boolean correspondingID(final E e, final Object id);

	@Override
	public Optional<E> get(@NotNull @NotOptional final Object id) {
		List<E> foundItems = datas.stream()
				.filter((e) -> correspondingID(e, id))
				.collect(Collectors.toList());
		if (foundItems.size() == 0) {
			return Optional.empty();
		} else if (foundItems.size() == 1) {
			return Optional.of(foundItems.iterator().next());
		} else {
			throw new IllegalStateException("ID not unique : " + id);
		}
	}

	@Override
	public void persist(@NotNull @Valid final E entity) {
		datas.add(entity);
	}

	@Override
	public void remove(@NotNull @Valid final E entity) {
		datas.remove(entity);
	}
}
