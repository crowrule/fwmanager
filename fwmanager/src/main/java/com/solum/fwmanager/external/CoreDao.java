package com.solum.fwmanager.external;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CoreDao {

	@Autowired
	@Qualifier("aimsCoreEntityManagerFactory")
	private	EntityManagerFactory emFactory;
	
	public	long getTest() {
		EntityManager entityManager = emFactory.createEntityManager();
		
	    String sql = "SELECT COUNT(*) FROM accesspoint";
	 
	    Query nativeQuery = entityManager.createNativeQuery(sql);
	    BigInteger ret = (BigInteger)nativeQuery.getSingleResult();
	    
	    return ret.longValue();
	}
}
