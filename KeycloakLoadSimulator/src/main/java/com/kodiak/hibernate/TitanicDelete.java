package com.kodiak.hibernate;

import java.io.Serializable;
import java.util.concurrent.Callable;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kodiak.models.Titanic;
import com.kodiak.utils.HibernateUtil;


public class TitanicDelete implements Callable<Object> {
	
	static Logger logger = LoggerFactory.getLogger(TitanicDelete.class);

	@Override
	public Object call() throws Exception {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		
		try {
			Session session = sessionFactory.getCurrentSession();

			//start transaction
			Transaction transaction = session.beginTransaction();
			
			//HQL
			@SuppressWarnings({ "deprecation", "unchecked" })
			SQLQuery<Integer> query = session.createSQLQuery("SELECT p_id FROM jmeter_titanic ORDER BY random() LIMIT 1;");
			
			@SuppressWarnings("deprecation")
			Integer row = query.uniqueResult();
			int pId = row.intValue();
			logger.info("P_Id to DELETE Record: " + pId);
		
			Serializable id = new Integer(pId);
			Object persistentInstance = session.load(Titanic.class, id);
			if (persistentInstance != null) {
			    session.delete(persistentInstance);
			}
			
			//Commit transaction
			transaction.commit();
			session.close();
						
			// Close resources
			//sessionFactory.close();
			
	        //HibernateUtil.shutdown();
	        
		} catch (Exception e) {
			e.printStackTrace();
			
			//sessionFactory.close();
	        //HibernateUtil.shutdown();
		} finally {
	        //if(!sessionFactory.isClosed()){
	            //sessionFactory.close();
	        //}
	    }

		return null;
	}

}
