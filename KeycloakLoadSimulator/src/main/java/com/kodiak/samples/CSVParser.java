package com.kodiak.samples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.kodiak.models.BabyName;
import com.kodiak.models.Titanic;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CSVParser {

	//Delimiters used in the CSV file
    private static final String COMMA_DELIMITER = ",";
    
	public static void main(String[] args) {
		
		
		try {
			//URL url = new URL("https://raw.githubusercontent.com/vincentarelbundock/Rdatasets/master/csv/Stat2Data/Titanic.csv");
			URL url = new URL("https://raw.githubusercontent.com/hadley/data-baby-names/master/baby-names.csv");
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			if (connection.getResponseCode() == 200) {
				/*
				try {
					InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
					BufferedReader br = new BufferedReader(streamReader);
					
					// skip the header of the csv
					List<Titanic> titanicList = br.lines()
							//.substream(1)
							.skip(1)
							.map(mapToTitanic)
							.collect(Collectors.toList());
				    br.close();
				    
			        for(Titanic t : titanicList) {
			            System.out.println(t);
			        }

				    
				} catch (IOException e) {
					e.printStackTrace();
				}
				*/
			
				/*	
				try (InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
				      BufferedReader br = new BufferedReader(streamReader);
				      Stream<String> sLines = br.lines()) {
					  //lines.skip(1).forEach(s -> parseLine(s)); //should be a method reference
					  sLines
					  .skip(1)
					  .limit(10)
					  //.forEach(s -> System.out.println(s));
					  .forEach(System.out::println);
					  //.collect(Collectors.toList());
					  
					  br.close();
				}
				*/
				
				/*
				try {
					InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
					BufferedReader br = new BufferedReader(streamReader);

					CSVReader reader = new CSVReader(br, ',', '\"', 1);
					
					List<Titanic> titanicList = new ArrayList<Titanic>();

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
						
						titanicList.add(titanic);

					}

					System.out.println(titanicList);
					reader.close();
					*/

				try {
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
					for (BabyName babyName : babyNameList) {
						System.out.println(babyName);
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static Function<String, Titanic> mapToTitanic = (line) -> {
		
		  String[] titanicDetails = line.split(COMMA_DELIMITER);// a CSV has comma separated lines
		  
		  Titanic titanic = new Titanic(
					Integer.parseInt(titanicDetails[0]),
					titanicDetails[1],
					titanicDetails[2],
					Integer.parseInt(titanicDetails[3]),
					titanicDetails[4],
					Integer.parseInt(titanicDetails[5]),
					Integer.parseInt(titanicDetails[6]));
		  
		  return titanic;
	};
	
	private static void parseLine(String line) {
		
		System.out.println(line);
		
		//Create List for holding Titanic objects
        List<Titanic> titanicList = new ArrayList<Titanic>();
		
		String[] titanicDetails = line.split(COMMA_DELIMITER);
		
		if(titanicDetails.length > 0) {
			
            //Save the titanic details in Titanic object
			Titanic titanic = new Titanic(
					Integer.parseInt(titanicDetails[0]),
					titanicDetails[1],
					titanicDetails[2],
					Integer.parseInt(titanicDetails[3]),
					titanicDetails[4],
					Integer.parseInt(titanicDetails[5]),
					Integer.parseInt(titanicDetails[6]));
			
            titanicList.add(titanic);
        }
        
	    //Lets print the Titanic List
        for(Titanic t : titanicList) {
            System.out.println(t);
        }

	}
}
