package be.normegil.mylibrary.tools;

public interface Generator<E> {

	public E getDefault(boolean withLink, boolean withIds);

	public E getNew(boolean withLink, boolean withIds);

	public Class<E> getSupportedClass();
}
