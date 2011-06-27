package org.kercoin.pocejb3;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;
import org.kercoin.pocejb3.dao.impl.CategoryDAOImpl;
import org.kercoin.pocejb3.model.Category;



public class CategoryDAOImplTest {
	
	private static final ThreadLocal<EntityManager> ENTITY_MANAGER_CACHE = new ThreadLocal<EntityManager>();

	public CategoryDAOImpl categoryDAO;

	@Before
	public void setUp() {
		EntityManagerFactory emf = provideEntityManagerFactory("db-manager");
		EntityManager em = provideEntityManager(emf);
		
		
		categoryDAO = new CategoryDAOImpl(em);
	}

	@Test
	public void testGetAll() {
		System.out.println("** testGetAll - BEGIN");
		// given

		// when
		List<Category> categories = categoryDAO.getAll();

		// then
		for (Category category : categories) {
			System.out.println(category.toString());
		}

		System.out.println("** testGetAll - END");
	}

	@Test
	public void testFindById() {
		System.out.println("** testFindById - BEGIN");
		// given

		// when
		try {
			Category  category = categoryDAO.findCategory(1);
			System.out.println(category.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// then

		System.out.println("** testFindById - END");
	}
	
	
	
	public EntityManagerFactory provideEntityManagerFactory(String persistenceUnit) {
		return Persistence.createEntityManagerFactory(persistenceUnit);
	}

	public EntityManager provideEntityManager(EntityManagerFactory entityManagerFactory) {
		EntityManager entityManager = ENTITY_MANAGER_CACHE.get();
		if (entityManager == null) {
			ENTITY_MANAGER_CACHE.set(entityManager = entityManagerFactory.createEntityManager());
		}
		return entityManager;
	}

}
