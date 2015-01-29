package be.normegil.mylibrary.framework.security.rightsmanagement;

import be.normegil.mylibrary.SpecificTestProperties;
import be.normegil.mylibrary.framework.rest.RESTMethod;
import be.normegil.mylibrary.framework.security.identification.key.Key;
import be.normegil.mylibrary.framework.security.rightsmanagement.group.Group;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.Resource;
import be.normegil.mylibrary.framework.security.rightsmanagement.resource.SpecificResource;
import be.normegil.mylibrary.manga.MangaREST;
import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.tools.IGenerator;
import be.normegil.mylibrary.user.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

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
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ITRightDatabaseDAO {

	private static final IGenerator<User> USER_GENERATOR = GeneratorRepository.get(User.class);
	private static final IGenerator<Group> GROUP_GENERATOR = GeneratorRepository.get(Group.class);
	private static final String ORDERING_PROPERTY_NAME = "name";
	private static final long KEYS_NUMBER_TO_CREATE = 5L;
	private static final long NUMBER_OF_RIGHTS_TO_CREATE = 5L;
	private static final RESTMethod DEFAULT_METHOD = RESTMethod.GET;
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private RightDatabaseDAO dao;
	private Root<Right> root;
	private CriteriaBuilder criteriaBuilder;

	@Mock
	private Root<Right> mockRoot;

	@Before
	public void setUp() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory(SpecificTestProperties.PERSISTENCE_UNIT_NAME);
		entityManager = entityManagerFactory.createEntityManager();
		dao = new RightDatabaseDAO(entityManager);

		criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Key> query = criteriaBuilder.createQuery(Key.class);
		root = query.from(Right.class);
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
		assertEquals(2, orderByParameters.size());
	}

	@Test
	public void testGetOrderParameter_OrderingDirection() throws Exception {
		List<Order> orderByParameters = dao.getOrderByParameters(criteriaBuilder, root);
		assertTrue(orderByParameters.get(0).isAscending());
		assertTrue(orderByParameters.get(1).isAscending());
	}

	@Test
	public void testGetOrderParameter_OrderingProperty() throws Exception {
		when(mockRoot.get("resource"))
				.thenReturn(root.get("resource"));
		when(mockRoot.get("method"))
				.thenReturn(root.get("method"));

		dao.getOrderByParameters(criteriaBuilder, mockRoot);

		verify(mockRoot, times(1))
				.get("resource");
		verify(mockRoot, times(1))
				.get("method");
	}

	@Test
	public void testGetByUser() throws Exception {
		insertUserRightsInDatabase(NUMBER_OF_RIGHTS_TO_CREATE);
		Right right = dao.getAll().iterator().next();
		Optional<Right> rightOptional = dao.get(right.getUser().get(), (SpecificResource) right.getResource(), right.getMethod());
		assertEquals(right, rightOptional.get());
	}

	@Test
	public void testGetByGroup() throws Exception {
		insertGroupRightsInDatabase(NUMBER_OF_RIGHTS_TO_CREATE);
		Right right = dao.getAll().iterator().next();
		Optional<Right> rightOptional = dao.get(right.getGroup().get(), right.getResource(), right.getMethod());
		assertEquals(right, rightOptional.get());
	}

	@Test
	public void testGetByGroup_InexistingRight() throws Exception {
		Optional<Right> rightOptional = dao.get(GROUP_GENERATOR.getNew(false, false), new Resource(MangaREST.class), RESTMethod.DELETE);
		assertFalse(rightOptional.isPresent());
	}

	@Test
	public void testGetByUser_InexistingRight() throws Exception {
		Optional<Right> rightOptional = dao.get(USER_GENERATOR.getNew(false, false), new SpecificResource(MangaREST.class, UUID.randomUUID().toString()), RESTMethod.DELETE);
		assertFalse(rightOptional.isPresent());
	}

	private void insertUserRightsInDatabase(final long l) {
		EntityManager manager = entityManagerFactory.createEntityManager();
		for (long i = 0; i < l; i++) {
			EntityTransaction transaction = manager.getTransaction();
			transaction.begin();

			Right userRight = new Right(USER_GENERATOR.getNew(false, false), new SpecificResource(MangaREST.class, UUID.randomUUID().toString()), DEFAULT_METHOD);
			manager.persist(userRight);

			transaction.commit();
		}
		manager.close();
	}

	private void insertGroupRightsInDatabase(final long l) {
		EntityManager manager = entityManagerFactory.createEntityManager();
		for (long i = 0; i < l; i++) {
			EntityTransaction transaction = manager.getTransaction();
			transaction.begin();

			Right groupRight = new Right(GROUP_GENERATOR.getNew(false, false), new Resource(MangaREST.class), DEFAULT_METHOD);
			manager.persist(groupRight);

			transaction.commit();
		}
		manager.close();
	}
}
