package be.normegil.mylibrary.manga;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UTMangaDatabaseDAO {

	@Test
	public void testGetEntityClass() throws Exception {
		MangaDatabaseDAO dao = new MangaDatabaseDAO();
		assertEquals(Manga.class, dao.getEntityClass());
	}
}