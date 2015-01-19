package be.normegil.mylibrary.framework.security.rightsmanagement.group;

import be.normegil.mylibrary.SpecificTestProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ITGroupDatabaseDAO {

	public static final String ORDERING_PROPERTY_NAME = "name";
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private GroupDatabaseDAO dao;
	private Root<Group> root;
	private CriteriaBuilder criteriaBuilder;

	@Mock
	private Root<Group> mockRoot;

	@Before
	public void setUp() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory(SpecificTestProperties.PERSISTENCE_UNIT_NAME);
		entityManager = entityManagerFactory.createEntityManager();
		dao = new GroupDatabaseDAO(entityManager);

		criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Group> query = criteriaBuilder.createQuery(Group.class);
		root = query.from(Group.class);
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
}
