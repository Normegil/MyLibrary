package be.normegil.mylibrary.tools;

public interface DataFactory<E> {

	public E getDefault();

	public E getNew();

	public E getDefault(boolean withLink, boolean withIds);

	public E getNew(boolean withLink, boolean withIds);
}
