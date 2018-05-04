package com.kodiak.hibernate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kodiak.models.Titanic;
import com.kodiak.utils.HibernateUtil;

public class TitanicRead implements Callable<Object> {
	
	static Logger logger = LoggerFactory.getLogger(TitanicRead.class);

	@Override
	public Object call() throws Exception {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		
		try {
			Session session = sessionFactory.getCurrentSession();
			Transaction transaction = session.beginTransaction();

			//Get All Titanic
			Criteria criteria = session.createCriteria(Titanic.class);
			List<Titanic> titanicList = criteria.list();
			for(Titanic titanic1 : titanicList){
				logger.info("Titanic" + titanic1);
			}				

			// Get with ID, creating new Criteria to remove all the settings
			criteria = session.createCriteria(Titanic.class)
						.add(Restrictions.eq("id", new Integer(3)));
			Titanic titanic2 = (Titanic) criteria.uniqueResult();
			logger.info("Titanic" + titanic2);
			
			//Pagination Example
			titanicList = session.createCriteria(Titanic.class)
						.addOrder(Order.desc("id"))
						.setFirstResult(0)
						.setMaxResults(2)
						.list();
			for(Titanic titanic3 : titanicList){
				logger.info("Paginated Titanic :: " + titanic3);
			}
			
			//Like example
			titanicList = session.createCriteria(Titanic.class)
					.add(Restrictions.like("name", "%i%"))
					.list();
			for(Titanic titanic4 : titanicList){
				logger.info("Titanic having 'i' in name :: " + titanic4);
			}
		
			//Projections example
			long count = (Long) session.createCriteria(Titanic.class)
					.setProjection(Projections.rowCount())
					.add(Restrictions.like("name", "%i%"))
					.uniqueResult();
			logger.info("Number of Titanic with 'i' in name=" + count);
			
			//using Projections for sum, min, max aggregation functions
			double sumAge = (Double) session.createCriteria(Titanic.class)
				.setProjection(Projections.sum("age"))
				.uniqueResult();
			logger.info("Sum of Ages=" + sumAge);
			
			//Join example for selecting few columns
			criteria = session.createCriteria(Titanic.class, "titanic");
			//criteria.setFetchMode("titanic.sex", FetchMode.JOIN);
			//criteria.createAlias("titanic.sex", "sex"); // inner join by default

			ProjectionList columns = Projections.projectionList()
					.add(Projections.property("name"))
					.add(Projections.property("sex"));

			criteria.setProjection(columns);

			List<Object[]> list = criteria.list();
			for(Object[] arr : list){
				logger.info(Arrays.toString(arr));
			}		
			
			// Rollback transaction to avoid messing test data
			transaction.commit();
			session.close();
			
			// closing hibernate resources
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
