package be.normegil.mylibrary.framework.security.rightsmanagement.resource;

import be.normegil.mylibrary.SpecificTestProperties;
import be.normegil.mylibrary.framework.rest.RESTServices;
import be.normegil.mylibrary.manga.MangaREST;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ITSpecificResourceDatabaseDAO {

	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private SpecificResourceDatabaseDAO dao;
	private Root<SpecificResource> root;
	private CriteriaBuilder criteriaBuilder;

	@Before
	public void setUp() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory(SpecificTestProperties.PERSISTENCE_UNIT_NAME);
		entityManager = entityManagerFactory.createEntityManager();
		dao = new SpecificResourceDatabaseDAO(entityManager);

		criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<SpecificResource> query = criteriaBuilder.createQuery(SpecificResource.class);
		root = query.from(SpecificResource.class);
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
		for (Order orderByParameter : orderByParameters) {
			assertTrue("Error on direction of " + orderByParameter.toString(), orderByParameter.isAscending());
		}
	}

	@Test
	public void testGetOrderParameter_OrderingProperties() throws Exception {
		Root<SpecificResource> mockRoot = Mockito.mock(Root.class);
		when(mockRoot.get("restService"))
				.thenReturn(root.get("restService"));
		when(mockRoot.get("resourceID"))
				.thenReturn(root.get("resourceID"));
		dao.getOrderByParameters(criteriaBuilder, mockRoot);
		verify(mockRoot, times(1)).get("restService");
		verify(mockRoot, times(1)).get("resourceID");
	}

	@Test
	public void testGetByClassAndID() throws Exception {
		insertAllResourceInDatabase();
		SpecificResource specificResource = dao.getAll().iterator().next();

		Optional optional = dao.get(specificResource.getRestService(), specificResource.getRessourceID());
		SpecificResource foundEntity = (SpecificResource) optional.get();
		assertEquals(specificResource, foundEntity);
	}

	@Test(expected = IllegalStateException.class)
	public void testGetByClassAndID_DuplicateRessource() throws Exception {
		UUID uuid = UUID.randomUUID();
		SpecificResource specificResource = new SpecificResource(MangaREST.class, uuid.toString());
		SpecificResource duplicatespecificResource = new SpecificResource(MangaREST.class, uuid.toString());

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		dao.persist(specificResource);
		dao.persist(duplicatespecificResource);
		transaction.commit();

		assertNotEquals(specificResource.getId(), duplicatespecificResource.getId());

		dao.get(specificResource.getRestService(), specificResource.getRessourceID());
	}

	@Test
	public void testGetByClassAndID_FalseIDs() throws Exception {
		Optional optional = dao.get(MangaREST.class, UUID.randomUUID().toString());
		assertFalse(optional.isPresent());
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

			SpecificResource resource = new SpecificResource(restServices.getDefaultServiceFor(path).get().getClass(), UUID.randomUUID().toString());
			manager.persist(resource);

			transaction.commit();
		}
		manager.close();
	}
}
