package be.normegil.mylibrary.tools.generator;

import be.normegil.mylibrary.tools.Generator;
import be.normegil.mylibrary.tools.IGenerator;

import java.net.URI;

@Generator(URI.class)
public class URIGenerator implements IGenerator<URI> {

	private static long index = 0L;

	@Override
	public URI getDefault(final boolean withLink, final boolean withIds) {
		return URI.create("http://www.normegil.be:1337/mylibrary");
	}

	@Override
	public URI getNew(final boolean withLink, final boolean withIds) {
		return URI.create("http://www.normegil.be:1337/mylibrary/" + getIndex());
	}

	public synchronized long getIndex() {
		index += 1;
		return index;
	}
}
