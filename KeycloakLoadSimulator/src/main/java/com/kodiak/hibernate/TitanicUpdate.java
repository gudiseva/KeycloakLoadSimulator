package com.kodiak.hibernate;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.mapdb.DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kodiak.models.Titanic;
import com.kodiak.parsers.BabyNames;
import com.kodiak.utils.HibernateUtil;
import com.kodiak.utils.MapDBUtil;


public class TitanicUpdate implements Callable<Object> {

	static Logger logger = LoggerFactory.getLogger(TitanicUpdate.class);
	
	@Override
	public Object call() throws Exception {

		DB db = MapDBUtil.getDB();
		ConcurrentMap<Integer, String> map = MapDBUtil.getMap(db);

		if (map.size() == 0) {
			MapDBUtil.closeDB(db);
			logger.info("MapDB is empty :: Populating ::");
			BabyNames.populateBabyNames();
		}

		if (db.isClosed()) {
			logger.info("MapDB is closed :: Opening the connection ::");
			db = MapDBUtil.getDB();
			map = MapDBUtil.getMap(db);
			logger.debug("Map Size: " + map.size());
		}

		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		
		try {
			Session session1 = sessionFactory.getCurrentSession();

			//start transaction
			Transaction transaction1 = session1.beginTransaction();
			
			//HQL
			@SuppressWarnings({ "deprecation", "unchecked" })
			SQLQuery query = session1.createSQLQuery("SELECT p_id FROM jmeter_titanic ORDER BY random() LIMIT 10;");
			
			List<Integer> pIdList = query.list();
			logger.info("P_Id to UPDATE Record: " + pIdList + "\n");
			
			//Commit transaction
			transaction1.commit();
			session1.close();
			
			for (int pId : pIdList) {
				
				Session session2 = sessionFactory.getCurrentSession();

				//start transaction
				Transaction transaction2 = session2.beginTransaction();

				Object object = session2.load(Titanic.class,new Integer(pId));
				Titanic titanic = (Titanic) object;

				titanic.setpId(pId);
				titanic.setName(map.get(pId));
				session2.update(titanic);
				
				//Commit transaction
				transaction2.commit();
				session2.close();
			}
			    
			// Close resources
			//sessionFactory.close();
			
	        //HibernateUtil.shutdown();
	        
	        MapDBUtil.closeDB(db);
	        
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
