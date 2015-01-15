package be.normegil.mylibrary.framework.dao;

import be.normegil.mylibrary.ApplicationProperties;
import be.normegil.mylibrary.SpecificIntegrationTestProperties;
import be.normegil.mylibrary.WarningTypes;
import be.normegil.mylibrary.manga.Manga;
import be.normegil.mylibrary.tools.DAOHelper;
import be.normegil.mylibrary.tools.Generator;
import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.framework.Entity;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class ITDatabaseDAO {

	@SuppressWarnings(WarningTypes.UNCHECKED_CAST)
	private static final Generator<Manga> FACTORY = GeneratorRepository.get(Manga.class);
	private static final String ALTERNATIVE_TITLE = "AlternativeTitle";
	private static final int DEFAULT_OFFSET = 0;
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private DatabaseDAO dao;
	private Manga entity;

	@Before
	public void setUp() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory(SpecificIntegrationTestProperties.PERSISTENCE_UNIT_NAME);
		entityManager = entityManagerFactory.createEntityManager();

		dao = new DatabaseDAO<Manga>() {
			@Override
			protected Class getEntityClass() {
				return Manga.class;
			}
		};
		new DAOHelper().setEntityManager(dao, entityManager);

//		entity = insertDataInDatabase();
	}

	@After
	public void tearDown() throws Exception {
		entity = null;

		dao = null;

		entityManager.close();
		entityManager = null;

		entityManagerFactory.close();
		entityManagerFactory = null;
	}

//	@Test
//	public void testGetAll() throws Exception {
//		List<Manga> completeList = insertDatas(ApplicationProperties.REST.DEFAULT_LIMIT + 10);
//		completeList.add(entity);
//		Collections.sort(completeList, (o1, o2) -> new CompareToBuilder().append(o1.getName(), o2.getName()).toComparison());
//
//		List<Manga> expected = get(completeList, DEFAULT_OFFSET, ApplicationProperties.REST.DEFAULT_LIMIT);
//		Stream<Manga> toTest = dao.getAll();
//		List<Manga> toTestAsList = toTest.collect(Collectors.toCollection(ArrayList::new));
//		assertTrue(getErrorMessage(expected, toTestAsList),
//				CollectionUtils.isEqualCollection(expected, toTestAsList));
//	}
//
//	@Test
//	public void testGetAll_OffsetAndLimit() throws Exception {
//		int offset = 2;
//		int limit = 3;
//
//		List<Manga> completeList = insertDatas(ApplicationProperties.REST.DEFAULT_LIMIT + 10);
//		completeList.add(entity);
//		Collections.sort(completeList, (o1, o2) -> new CompareToBuilder().append(o1.getName(), o2.getName()).toComparison());
//
//		List<Manga> expected = get(completeList, offset, limit);
//		Stream<Manga> toTest = dao.getAll(offset, limit);
//		List<Manga> toTestAsList = toTest.collect(Collectors.toCollection(ArrayList::new));
//		assertTrue(getErrorMessage(expected, toTestAsList),
//				CollectionUtils.isEqualCollection(expected, toTestAsList));
//	}
//
//	@Test
//	public void testGetNumberOfElements() throws Exception {
//		Object result = entityManager.createQuery("select count(e.id) from " + entity.getClass().getName() + " e").getSingleResult();
//		long numberOfElements = dao.getNumberOfElements();
//		assertEquals(result, numberOfElements);
//	}
//
//	@Test
//	public void testGet() throws Exception {
//		Optional optional = dao.get(entity.getId());
//		Entity foundEntity = (Entity) optional.get();
//		assertEquals(entity, foundEntity);
//	}
//
//	@Test
//	public void testSave_AlreadyExistingObject() throws Exception {
//		EntityTransaction transaction = entityManager.getTransaction();
//		transaction.begin();
//		Optional optional = dao.get(entity.getId());
//		Manga loadedEntity = (Manga) optional.get();
//		loadedEntity.setName(ALTERNATIVE_TITLE);
//		dao.persist(loadedEntity);
//		transaction.commit();
//
//		Optional otherOptional = dao.get(entity.getId());
//		Manga foundEntity = (Manga) otherOptional.get();
//		assertEquals(ALTERNATIVE_TITLE, foundEntity.getName());
//	}
//
//	@Test
//	public void testSave() throws Exception {
//		Entity newEntity = FACTORY.getNew(true, false);
//
//		EntityTransaction transaction = entityManager.getTransaction();
//		transaction.begin();
//		dao.persist(newEntity);
//		transaction.commit();
//
//		Optional optional = dao.get(newEntity.getId());
//		Entity foundEntity = (Entity) optional.get();
//		assertEquals(newEntity, foundEntity);
//	}
//
//	@Test
//	public void testRemove() throws Exception {
//		Object entityId = entity.getId();
//
//		EntityTransaction transaction = entityManager.getTransaction();
//		transaction.begin();
//		Optional optional = dao.get(entityId);
//		Manga loadedEntity = (Manga) optional.get();
//		dao.remove(loadedEntity);
//		transaction.commit();
//
//		assertNull(dao.get(entityId));
//	}
//
//	private List<Manga> insertDatas(long numberOfElements) {
//		if (numberOfElements < 1) {
//			throw new IllegalArgumentException("Not enough Elements");
//		} else {
//			List<Manga> mangas = new ArrayList<>();
//			mangas.add(insertDataInDatabase());
//			if (numberOfElements > 1) {
//				mangas.addAll(insertDatas(numberOfElements - 1));
//			}
//			return mangas;
//		}
//	}
//
//	private Manga insertDataInDatabase() {
//		EntityManager manager = entityManagerFactory.createEntityManager();
//		EntityTransaction transaction = manager.getTransaction();
//		transaction.begin();
//		Manga entity = FACTORY.getNew(true, false);
//		manager.persist(entity);
//		transaction.commit();
//		manager.close();
//		return entity;
//	}
//
//	private List<Manga> get(final List<Manga> completeList, int offset, int limit) {
//		List<Manga> expected = new ArrayList<>();
//		for (int i = offset; i < limit + offset; i++) {
//			expected.add(completeList.get(i));
//		}
//		return expected;
//	}
//
//	private String getErrorMessage(List<Manga> expected, List<Manga> toTest) {
//		String expectedSting = expected.stream().map(Manga::getName)
//				.collect(Collectors.joining(", "));
//		String toTestString = toTest.stream().map(Manga::getName)
//				.collect(Collectors.joining(", "));
//		return "Entitys Defined[" + expectedSting + "]\n" +
//				"Entitys Loaded[" + toTestString + "]";
//	}
}
