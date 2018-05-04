package com.kodiak.hibernate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kodiak.models.Titanic;
import com.kodiak.utils.HibernateUtil;
import com.opencsv.CSVReader;

public class TitanicInsert implements Callable<Object> {

	static Logger logger = LoggerFactory.getLogger(TitanicInsert.class);
	
	@Override
	public Object call() throws Exception {

		//Get Session
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		
		try {
			URL url = new URL("https://raw.githubusercontent.com/vincentarelbundock/Rdatasets/master/csv/Stat2Data/Titanic.csv");
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			if (connection.getResponseCode() == 200) {
				
				try {
					InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
					BufferedReader br = new BufferedReader(streamReader);

					@SuppressWarnings("deprecation")
					CSVReader reader = new CSVReader(br, ',', '\"', 1);
					  
					  	// read line by line
						String[] titanicDetails = null;
						double tAge = 0.0; 

						while ((titanicDetails = reader.readNext()) != null) {
							
							if(titanicDetails[3].equalsIgnoreCase("NA")) {
								tAge = 0;
							} else {
								tAge = Double.parseDouble(titanicDetails[3]);
							}
							
							Titanic titanic = new Titanic(
								Integer.parseInt(titanicDetails[0]),
								titanicDetails[1],
								titanicDetails[2],
								tAge,
								titanicDetails[4],
								Integer.parseInt(titanicDetails[5]),
								Integer.parseInt(titanicDetails[6]));
							
							Session  session = sessionFactory.getCurrentSession();
							
							//start transaction
					        Transaction transaction = session.beginTransaction();
					        
					        // Should be specified after the transaction
					        session.setCacheMode(CacheMode.IGNORE);
					        
							session.saveOrUpdate(titanic);
							
							//Commit transaction
							transaction.commit();
							session.close();
						}
						
						reader.close();
						
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

			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
