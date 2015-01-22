package be.normegil.mylibrary.framework.security.rightsmanagement.resource;

import be.normegil.mylibrary.SpecificTestProperties;
import be.normegil.mylibrary.framework.Entity;
import be.normegil.mylibrary.framework.rest.RESTServices;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

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
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ITResourceDatabaseDAO {

	public static final String ORDERING_PROPERTY_NAME = "restService";
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private ResourceDatabaseDAO dao;
	private Root<Resource> root;
	private CriteriaBuilder criteriaBuilder;

	@Before
	public void setUp() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory(SpecificTestProperties.PERSISTENCE_UNIT_NAME);
		entityManager = entityManagerFactory.createEntityManager();
		dao = new ResourceDatabaseDAO(entityManager);

		criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Resource> query = criteriaBuilder.createQuery(Resource.class);
		root = query.from(Resource.class);
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
		Root<Resource> mockRoot = Mockito.mock(Root.class);
		when(mockRoot.get(ORDERING_PROPERTY_NAME))
				.thenReturn(root.get(ORDERING_PROPERTY_NAME));
		dao.getOrderByParameters(criteriaBuilder, mockRoot);
		verify(mockRoot, times(1)).get(ORDERING_PROPERTY_NAME);
	}

	@Test
	public void testGetByClass() throws Exception {
		insertAllResourceInDatabase();
		Resource entity = dao.getAll().iterator().next();

		Optional optional = dao.getByClass(entity.getRestService());
		Entity foundEntity = (Entity) optional.get();
		assertEquals(entity, foundEntity);
	}

	private void insertAllResourceInDatabase() {
		EntityManager manager = entityManagerFactory.createEntityManager();
		RESTServices restServices = new RESTServices();
		List<String> pathList = restServices
				.getAllRESTServicesPaths()
				.collect(Collectors.toList());
		for (String path : pathList) {
			EntityTransaction transaction = manager.getTransaction();
			transaction.begin();

			Resource resource = new Resource(restServices.getDefaultServiceFor(path).get().getClass());
			manager.persist(resource);

			transaction.commit();
		}
		manager.close();
	}
}
