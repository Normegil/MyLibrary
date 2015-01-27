package be.normegil.mylibrary.framework.security.identification.key;

import be.normegil.mylibrary.SpecificTestProperties;
import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.tools.IGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ITKeyDatabaseDAO {

	public static final String ORDERING_PROPERTY_NAME = "name";
	private static final IGenerator<Key> GENERATOR = GeneratorRepository.get(Key.class);
	public static final long KEYS_NUMBER_TO_CREATE = 5L;
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private KeyDatabaseDAO dao;
	private Root<Key> root;
	private CriteriaBuilder criteriaBuilder;

	@Mock
	private Root<Key> mockRoot;

	@Before
	public void setUp() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory(SpecificTestProperties.PERSISTENCE_UNIT_NAME);
		entityManager = entityManagerFactory.createEntityManager();
		dao = new KeyDatabaseDAO(entityManager);

		criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Key> query = criteriaBuilder.createQuery(Key.class);
		root = query.from(Key.class);
	}

	@After
	public void tearDown() throws Exception {
		root = null;
		criteriaBuilder = null;
		dao = null;

		entityManager.close();
		entityManager = null;

		entityManagerFactory.close();
		entityManagerFactory = null;
	}

	@Test
	public void testGetOrderParameter_NumberOfParameters() throws Exception {
		List<Order> orderByParameters = dao.getOrderByParameters(criteriaBuilder, root);
		assertEquals(1, orderByParameters.size());
	}

	@Test
	public void testGetOrderParameter_OrderingDirection() throws Exception {
		List<Order> orderByParameters = dao.getOrderByParameters(criteriaBuilder, root);
		assertTrue(orderByParameters.get(0).isAscending());
	}

	@Test
	public void testGetOrderParameter_OrderingProperty() throws Exception {
		when(mockRoot.get(ORDERING_PROPERTY_NAME))
				.thenReturn(root.get(ORDERING_PROPERTY_NAME));
		dao.getOrderByParameters(criteriaBuilder, mockRoot);
		verify(mockRoot, times(1)).get(ORDERING_PROPERTY_NAME);
	}

	@Test
	public void testGetByName() throws Exception {
		insertKeysInDatabase(KEYS_NUMBER_TO_CREATE);
		Key key = dao.getAll().iterator().next();

		Optional<Key> keyOptional = dao.getByName(key.getName());
		Key loadedKey = keyOptional.get();
		assertEquals(key, loadedKey);
	}

	@Test
	public void testGetByName_FakeName() throws Exception {
		Optional<Key> keyOptional = dao.getByName("FakeName");
		assertFalse(keyOptional.isPresent());
	}

	private void insertKeysInDatabase(final long l) {
		EntityManager manager = entityManagerFactory.createEntityManager();
		for (long i = 0; i < l; i++) {
			EntityTransaction transaction = manager.getTransaction();
			transaction.begin();

			Key key = GENERATOR.getNew(true, false);
			manager.persist(key);

			transaction.commit();
		}
		manager.close();
	}
}
