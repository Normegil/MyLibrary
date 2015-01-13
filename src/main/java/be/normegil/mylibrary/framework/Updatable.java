package be.normegil.mylibrary.framework;

public interface Updatable<E> {

	public void from(E entity, boolean includingNullFields);

}
