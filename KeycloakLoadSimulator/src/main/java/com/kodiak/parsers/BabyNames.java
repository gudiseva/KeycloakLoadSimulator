package com.kodiak.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kodiak.models.BabyName;
import com.kodiak.utils.MapDBUtil;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class BabyNames {

	static Logger logger = LoggerFactory.getLogger(BabyNames.class);
	
	public BabyNames() {}
	
//	public static void main(String[] args) {
//		//BabyNames babyNames = new BabyNames();
//		BabyNames.populateBabyNames();
//	}
	
	public static void populateBabyNames() {
		
		DB db = MapDBUtil.getDB();		
		ConcurrentMap<Integer, String> map = MapDBUtil.getMap(db);
		
		if (map.size() != 0) {
			MapDBUtil.clearMap(map);	
		}

		try {
			URL url = new URL("https://raw.githubusercontent.com/hadley/data-baby-names/master/baby-names.csv");
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			if (connection.getResponseCode() == 200) {
				
				InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
				BufferedReader br = new BufferedReader(streamReader);
				
				ColumnPositionMappingStrategy<BabyName> strategy = new ColumnPositionMappingStrategy<BabyName>();
	            strategy.setType(BabyName.class);
	            String[] memberFieldsToBindTo = {"year","name","percent","sex"};
	            strategy.setColumnMapping(memberFieldsToBindTo);
	            
				CsvToBean<BabyName> csvToBean = new CsvToBeanBuilder<BabyName>(br)
						.withMappingStrategy(strategy)
						.withSeparator(',')
						.withQuoteChar('\"')
						.withSkipLines(1)
						.withMultilineLimit(1)
						.withIgnoreLeadingWhiteSpace(true)
						.withThrowExceptions(false)
						.build();
				
				List<BabyName> babyNameList = csvToBean.parse();
				int index = 1;
				for (BabyName babyName : babyNameList) {
					logger.info("Baby Name: " + babyName);
					map.put(index, babyName.getName());
					index ++;
				}
				
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		MapDBUtil.closeDB(db);
	}

}
