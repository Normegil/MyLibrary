package be.normegil.mylibrary.framework;

import javax.validation.constraints.NotNull;

public class Couple<X, Y> {

	private final X x;
	private final Y y;

	public Couple(@NotNull final X x, @NotNull final Y y) {
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
