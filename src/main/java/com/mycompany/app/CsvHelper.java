/**
 * 
 */
package com.mycompany.app;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

/**
 * @author Abbas H Safaie
 *
 */
public class CsvHelper {

		//CSV resource file lcdcMAp
		private static final String filePath="src/main/java/resources/CsvMap.csv";
			

	/*
	 * return snomad uri for passed label 
	 * */
	    public static String getSnomedUri(String itemOid) 
	    		throws FileNotFoundException{
	    	String uri="";
	       
	       List<String[]> myEntries;
		try {
			
			@SuppressWarnings("resource")
			CSVReader reader = new CSVReader(new FileReader(filePath));
			myEntries = reader.readAll();
			
			
		       Iterator<String[]> it =myEntries.iterator();
		       
	      		Boolean exit=false;  //find uri exit 

				

	     		while(!exit && it.hasNext()){

	     				String[] str=it.next();
	   			String s=str[0];
	   				if (s.equals(itemOid)){
	   					 uri=str[3];
	   					 exit=true;
	   				  }
	   				}
	     		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


	     return uri;
	    }

	    
		/*
		 * return label
		 * Input ItemOid
		 * 
		 * */
		    public static String getLabel(String itemOid) 
		    		throws FileNotFoundException{
		    	String label="";
		       
		       List<String[]> myEntries;
			try {
				
				@SuppressWarnings("resource")
				CSVReader reader = new CSVReader(new FileReader(filePath));
				myEntries = reader.readAll();
				
				
			       Iterator<String[]> it =myEntries.iterator();
			       
		      		Boolean exit=false;  //find uri exit 

					

		     		while(!exit && it.hasNext()){

		     				String[] str=it.next();
		   			String s=str[0];
		   				if (s.equals(itemOid)){
		   					 label=str[4];
		   					 exit=true;
		   				  }
		   				}
		     		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		     return label;
		    }
		    
		    
		    
			/*
			 * return Domain 
			 * input ItemOid
			 * */
			    public static String getDomain(String itemOid) 
			    		throws FileNotFoundException{
			    	String uri="";
			       
			       List<String[]> myEntries;
				try {
					
					@SuppressWarnings("resource")
					CSVReader reader = new CSVReader(new FileReader(filePath));
					myEntries = reader.readAll();
					
					
				       Iterator<String[]> it =myEntries.iterator();
				       
			      		Boolean exit=false;  //find uri exit 

						

			     		while(!exit && it.hasNext()){

			     				String[] str=it.next();
			   			String s=str[0];
			   				if (s.equals(itemOid)){
			   					 uri=str[2];
			   					 exit=true;
			   				  }
			   				}
			     		
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				


			     return uri;
			    }
	    
	    
	}


