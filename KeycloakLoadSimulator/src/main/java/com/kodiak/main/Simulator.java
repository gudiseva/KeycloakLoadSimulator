package com.kodiak.main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kodiak.hibernate.TitanicDelete;
import com.kodiak.hibernate.TitanicInsert;
import com.kodiak.hibernate.TitanicRead;
import com.kodiak.hibernate.TitanicUpdate;
import com.kodiak.utils.HibernateUtil;

public class Simulator {
	
	static Logger logger = LoggerFactory.getLogger(Simulator.class);
	
	public static void main(String[] args) throws InterruptedException {
		
		//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		
		final int cpus = Runtime.getRuntime().availableProcessors();
        System.out.println("Provisioned :: " + cpus + " :: for Thread Pool" );
        
        final ExecutorService service = Executors.newFixedThreadPool( cpus );
	    //ExecutorService service = Executors.newFixedThreadPool(4);
        
        long start = System.currentTimeMillis();
        
        for ( int i = 0; i < cpus; i++ ) {
    	    service.submit(new TitanicInsert());
    	    service.submit(new TitanicUpdate());
    	    service.submit(new TitanicDelete());
    	    service.submit(new TitanicRead());
        }

	    
	    service.shutdown();
	    service.awaitTermination(1, TimeUnit.DAYS);
	    
	    long stop = System.currentTimeMillis();
	    System.out.println( "Time taken :: " + ( stop - start ) );

	    
	    // Close resources
//	    if(!sessionFactory.isClosed()){
//            sessionFactory.close();
//        }
        //HibernateUtil.shutdown();

	    System.exit(0);
	}

	
}