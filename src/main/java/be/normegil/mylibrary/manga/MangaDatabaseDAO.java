package be.normegil.mylibrary.manga;

import be.normegil.mylibrary.framework.dao.DatabaseDAO;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

@Stateless
public class MangaDatabaseDAO extends DatabaseDAO<Manga> {

	public MangaDatabaseDAO() {
	}

	public MangaDatabaseDAO(final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	protected Class<Manga> getEntityClass() {
		return Manga.class;
	}
}
