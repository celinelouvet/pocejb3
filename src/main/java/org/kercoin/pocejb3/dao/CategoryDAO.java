package org.kercoin.pocejb3.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.kercoin.pocejb3.model.Category;



public interface CategoryDAO {

		void add(Category c) throws Exception;

		void save(Category c) throws Exception;

		void delete(Category c) throws Exception;

		List<Category> getAll();

		Query findCategory(Map<String, Object> params) throws Exception;
		
		Category findCategory(int id) throws Exception;

		boolean exists(Category c) throws NoResultException;

}
