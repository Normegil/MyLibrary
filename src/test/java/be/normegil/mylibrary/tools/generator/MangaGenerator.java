package be.normegil.mylibrary.tools.generator;

import be.normegil.mylibrary.manga.Manga;
import be.normegil.mylibrary.tools.EntityHelper;
import be.normegil.mylibrary.tools.Generator;

import java.util.UUID;

public class MangaGenerator implements Generator<Manga> {

	private static final UUID DEFAULT_ID = UUID.fromString("41920e92-e4fa-457a-b705-ccecacf22c4e");
	private static final String NAME = "MangaName";
	private static long index = 0L;

	@Override
	public Manga getDefault(final boolean withLink, final boolean withIds) {
		Manga manga = new Manga();
		manga.setName(NAME);

		if (withIds) {
			new EntityHelper().setId(manga, DEFAULT_ID);
		}

		return manga;
	}

	@Override
	public Manga getNew(final boolean withLink, final boolean withIds) {
		Manga manga = new Manga();
		long index = getIndex();
		manga.setName(NAME + index);

		if (withIds) {
			new EntityHelper().setId(manga, UUID.randomUUID());
		}

		return manga;
	}

	@Override
	public Class<Manga> getSupportedClass() {
		return Manga.class;
	}

	public synchronized long getIndex() {
		index += 1;
		return index;
	}
}
