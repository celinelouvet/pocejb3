package org.kercoin.pocejb3.ejb;

import javax.ejb.Remote;

import org.kercoin.pocejb3.dao.CategoryDAO;



@Remote
public interface CategoryDAORemote extends CategoryDAO {
	public static final String JNDI = "org.kercoin.pocejb3/dao/Category";

}
