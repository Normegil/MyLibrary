package be.normegil.mylibrary.framework;

public class Couple<X, Y> {

	private final X x;
	private final Y y;

	public Couple(final X x, final Y y) {
		this.x = x;
		this.y = y;
	}

	public X getFirst() {
		return x;
	}

	public Y getSecond() {
		return y;
	}
}
