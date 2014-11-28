package be.normegil.mylibrary.util;

public interface Updatable<E> {

	public void from(E entity, boolean includingNullFields);

}
