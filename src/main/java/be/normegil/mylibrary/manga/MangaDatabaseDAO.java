package be.normegil.mylibrary.manga;

import be.normegil.mylibrary.framework.dao.DatabaseDAO;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

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

	@Override
	protected List<Order> getOrderByParameters(final CriteriaBuilder builder, final Root<Manga> root) {
		return Arrays.asList(
				builder.asc(root.get("name"))
		);
	}
}
