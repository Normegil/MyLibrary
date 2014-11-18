package be.normegil.mylibrary;

import be.normegil.mylibrary.manga.Manga;
import be.normegil.mylibrary.tools.DataFactory;
import be.normegil.mylibrary.tools.EntityHelper;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.util.UUID;

@RunWith(Suite.class)
@Suite.SuiteClasses({
})
public class MangaTestSuite implements DataFactory<Manga> {

	private static final UUID DEFAULT_ID = UUID.fromString("c5db3c40-6f00-11e4-9803-0800200c9a66");
	private static final String NAME = "Manga";
	private static long index = 0L;

	@Override
	public Manga getDefault() {
		return getDefault(true, true);
	}

	@Override
	public Manga getNew() {
		return getNew(true, true);
	}

	@Override
	public Manga getDefault(final boolean withLink, final boolean withIds) {
		Manga manga = new Manga();
		manga.setName(NAME);

		new EntityHelper().setId(manga, DEFAULT_ID);
		return manga;
	}

	@Override
	public Manga getNew(final boolean withLink, final boolean withIds) {
		Manga manga = new Manga();
		manga.setName(NAME + getIndex());

		new EntityHelper().setId(manga, UUID.randomUUID());
		return manga;
	}

	public synchronized long getIndex() {
		index += 1;
		return index;
	}
}