package be.normegil.mylibrary.manga;

import be.normegil.mylibrary.util.dao.DatabaseDAO;

import javax.ejb.Stateless;

@Stateless
public class MangaDatabaseDAO extends DatabaseDAO<Manga> {
	@Override
	protected Class<Manga> getEntityClass() {
		return Manga.class;
	}
}
