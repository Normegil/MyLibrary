package be.normegil.mylibrary.framework.dao;

import be.normegil.mylibrary.ApplicationProperties;
import be.normegil.mylibrary.SpecificTestProperties;
import be.normegil.mylibrary.WarningTypes;
import be.normegil.mylibrary.framework.Entity;
import be.normegil.mylibrary.manga.Manga;
import be.normegil.mylibrary.tools.Generator;
import be.normegil.mylibrary.tools.GeneratorRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class ITDatabaseDAO {

	@SuppressWarnings(WarningTypes.UNCHECKED_CAST)
	private static final Generator<Manga> FACTORY = GeneratorRepository.get(Manga.class);
	private static final String ALTERNATIVE_TITLE = "AlternativeTitle";
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private DatabaseDAO dao;

	@Before
	public void setUp() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory(SpecificTestProperties.PERSISTENCE_UNIT_NAME);
		entityManager = entityManagerFactory.createEntityManager();

		dao = new DatabaseDAO<Manga>(entityManager) {
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
		};
	}

	@After
	public void tearDown() throws Exception {
		dao = null;

		entityManager.close();
		entityManager = null;

		entityManagerFactory.close();
		entityManagerFactory = null;
	}

	@Test
	public void testGetEntityManager() throws Exception {
		assertEquals(entityManager, dao.getEntityManager());
	}

	@Test
	public void testGetAll() throws Exception {
		insertDataInDatabase(ApplicationProperties.REST.DEFAULT_LIMIT + 10);

		List expected = getAllDatabaseRows();

		Stream<Manga> toTest = dao.getAll();
		List<Manga> toTestAsList = toTest.collect(Collectors.toList());
		assertTrue(getErrorMessage(expected, toTestAsList),
				CollectionUtils.isEqualCollection(expected, toTestAsList));
	}

	@Test
	public void testGetAll_OffsetAndLimit() throws Exception {
		int offset = 2;
		int limit = 3;

		insertDataInDatabase(limit * 2);

		List completeList = getAllDatabaseRows();
		List<Manga> completeMangaList = completeList;
		Collections.sort(completeMangaList, (o1, o2) -> new CompareToBuilder().append(o1.getName(), o2.getName()).toComparison());

		List<Manga> expected = get(completeMangaList, offset, limit);
		Stream<Manga> toTest = dao.getAll(offset, limit);
		List<Manga> toTestAsList = toTest.collect(Collectors.toCollection(ArrayList::new));
		assertTrue(getErrorMessage(expected, toTestAsList),
				CollectionUtils.isEqualCollection(expected, toTestAsList));
	}

	private List getAllDatabaseRows() {
		return entityManager
				.createQuery("select m from Manga m order by m.name")
				.getResultList();
	}

	@Test
	public void testGetNumberOfElements() throws Exception {
		Object result = entityManager.createQuery("select count(m.id) from Manga m").getSingleResult();
		long numberOfElements = dao.getNumberOfElements();
		assertEquals(result, numberOfElements);
	}

	@Test
	public void testGet() throws Exception {
		insertDataInDatabase(2);
		Entity entity = (Entity) getAllDatabaseRows().iterator().next();

		Optional optional = dao.get(entity.getId().get());
		Entity foundEntity = (Entity) optional.get();
		assertEquals(entity, foundEntity);
	}

	@Test
	public void testSave_AlreadyExistingObject() throws Exception {
		insertDataInDatabase(2);

		EntityTransaction transaction = entityManager.getTransaction();
		Entity entity = (Entity) getAllDatabaseRows().iterator().next();

		transaction.begin();
		Optional optional = dao.get(entity.getId().get());
		Manga loadedEntity = (Manga) optional.get();
		loadedEntity.setName(ALTERNATIVE_TITLE);
		dao.persist(loadedEntity);
		transaction.commit();

		Optional otherOptional = dao.get(entity.getId().get());
		Manga foundEntity = (Manga) otherOptional.get();
		assertEquals(ALTERNATIVE_TITLE, foundEntity.getName());
	}

	@Test
	public void testSave() throws Exception {
		Entity newEntity = FACTORY.getNew(true, false);

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		dao.persist(newEntity);
		transaction.commit();

		Optional optional = dao.get(newEntity.getId().get());
		Entity foundEntity = (Entity) optional.get();
		assertEquals(newEntity, foundEntity);
	}

	@Test
	public void testRemove() throws Exception {
		insertDataInDatabase(2);
		Entity entity = (Entity) getAllDatabaseRows().iterator().next();
		Optional<UUID> idOptional = entity.getId();
		UUID id = idOptional.get();

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		Optional optional = dao.get(id);
		Manga loadedEntity = (Manga) optional.get();
		dao.remove(loadedEntity);
		transaction.commit();

		assertFalse(dao.get(id).isPresent());
	}

	private void insertDataInDatabase(long numberOfRowToInsert) {
		EntityManager manager = entityManagerFactory.createEntityManager();
		for (long l = 0; l < numberOfRowToInsert; l++) {
			EntityTransaction transaction = manager.getTransaction();
			transaction.begin();

			Entity entity = FACTORY.getNew(true, false);
			manager.persist(entity);

			transaction.commit();
		}
		manager.close();
	}

	private List<Manga> get(final List<Manga> completeList, int offset, int limit) {
		List<Manga> expected = new ArrayList<>();
		for (int i = offset; i < limit + offset; i++) {
			expected.add(completeList.get(i));
		}
		return expected;
	}

	private String getErrorMessage(List<Manga> expected, List<Manga> toTest) {
		String expectedNotIncludedElements = expected.stream()
				.filter((m) -> !toTest.contains(m))
				.map(Manga::getName)
				.collect(Collectors.joining(", "));

		String toTestNotIncludedElements = toTest.stream()
				.filter((m) -> !expected.contains(m))
				.map(Manga::getName)
				.collect(Collectors.joining(", "));

		return "Entitys Defined[" + expectedNotIncludedElements + "]\n" +
				"Entitys Loaded[" + toTestNotIncludedElements + "]";
	}


}
