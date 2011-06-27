package org.kercoin.pocejb3.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kercoin.pocejb3.ejb.CategoryDAOLocal;
import org.kercoin.pocejb3.ejb.CategoryDAORemote;
import org.kercoin.pocejb3.model.Category;



@Stateless(mappedName = CategoryDAORemote.JNDI)
public class CategoryDAOImpl implements CategoryDAOLocal, CategoryDAORemote {

	private static final Logger log = Logger.getLogger(CategoryDAOImpl.class.getName());

	@PersistenceContext
	protected EntityManager entityManager;
	
	

	public CategoryDAOImpl() {
		super();
	}
	
	
	

	public CategoryDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}




	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	@Override
	public void add(Category c) throws Exception {
		log.fine("Add Category");

		if (!exists(c)) {
			this.entityManager.persist(c);
		} else {
			throw new Exception(c.toString() + " already exists.");
		}
	}

	@Override
	public void save(Category c) throws Exception {
		log.fine("Save Category");
		if (exists(c)) {
			this.entityManager.merge(c);
		} else {
			throw new Exception(c.toString() + " doesn't exists.");
		}
	}

	@Override
	public void delete(Category c) throws Exception {
		log.fine("Delete Category");
		if (exists(c)) {
			String query = "select c from Category c where id = " + c.getId();
			c = (Category) this.entityManager.createQuery(query).getSingleResult();

			this.entityManager.remove(c);
		} else {
			throw new Exception(c.toString() + " doesn't exists.");
		}
	}

	@Override
	public List<Category> getAll() {
		String query = "select c from Category c";

		@SuppressWarnings("unchecked")
		List<Category> results = (List<Category>) this.entityManager.createQuery(query).getResultList();
		log.fine("List Categories : found : "+results.size());
		return results;
	}

	@Override
	public Query findCategory(Map<String, Object> params) throws Exception {
		boolean isFirst = true;

		String table = "c";
		StringBuffer query = new StringBuffer("select "+ table+" from Category "+ table+"  where ");

		for (String s : params.keySet()) {
			if (!isFirst) { 
				query.append(" and ");
			}

			query.append(table+"."+s).append(" = :").append(s);
			isFirst = false;
		}

		Query q = this.entityManager.createQuery(query.toString());

		for (String s : params.keySet()) {
			q.setParameter(s, params.get(s));
		}
		return q;
	}

	@Override
	public boolean exists(Category c) throws NoResultException {
		if (this.entityManager.contains(c)) {
			return true;
		}

		String query = "select c from Category c where c.id = " + c.getId();

		try {
			Category checkPresence = (Category) this.entityManager.createQuery(query).getSingleResult();
			return checkPresence != null;

		} catch (NoResultException ex) {
			return false;
		}
	}

	@Override
	public Category findCategory(int id) throws Exception {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);

			return (Category) findCategory(params).getSingleResult();

		} catch (Throwable t) {
			throw new Exception("Category not found with id = " + id);
		}
	}

}
