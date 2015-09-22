package com.mycompany.app;

import java.util.Iterator;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelMaker;


/**
 * @author Abbas.h.Safaie
 * 
 * */
public class App 
{
    public static void main( String[] args )
    {   	
   
    
    //	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    //	Date date = new Date();
    //	String time=dateFormat.format(date); //2014/08/06 15:59:48
    RDFModelHelper modelHelper=new RDFModelHelper();	
    	String filePath="src/main/java/odm1.3_clinical_ext_Full_study_extract_2015-05-22-162457368.xml";
  		
    	ModelMaker mm=modelHelper.ontoModelTest(filePath);
    
    	DatabaseHelper dbh=new 	DatabaseHelper();
  	
    		
 	dbh.writeModel(mm);
    dbh.saveToFile(mm);

    		
//    		 String queryString =        
//                     "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>" +
//    		         "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
//    		        // "PREFIX lcdcobs:<http://purl.org/sstats/lcdc/def/obs#>"+ 
//    		         "PREFIX cardiovitalsigns:<http://aehrc-ci.it.csiro.au/cardio/lcdc/vitalsigns/def/cardio-vitalsigns#>"+
//    		         "PREFIX lcdccore:<http://purl.org/sstats/lcdc/def/core#>"+
//    		            "select ?uri ?subject "+
//    		            "where {?uri cardiovitalsigns:MedicationStartDate '2014-07-01'."+
//    		            "?x lcdccore:subject ?subject.} \n ";
//    		
    		
//     	  // Create a new query
//       String queryString =        
//          "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#> "+
//         "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
//         "PREFIX lcdcobs:<http://purl.org/sstats/lcdc/def/obs#>"+ 
//         "PREFIX cardiovitalsigns:<http://aehrc-ci.it.csiro.au/cardio/lcdc/vitalsigns/def/cardio-vitalsigns#>"+
//         "PREFIX lcdccore:<http://purl.org/sstats/lcdc/def/core#>"+
//            "select ?uri "+
//            "where {?uri lcdccore:subject 'http://purl.org/sstats/lcdc/id/subject/SS_10'. }\n ";
//       Query query = QueryFactory.create(queryString);
//        System.out.println("----------------------");
//        System.out.println("Query Result Sheet");
//        System.out.println("----------------------");
//
//       System.out.println("Query on Model : "+ModelNames.OBS_MEDIC);
//      Model model=dbh.getModelByName("Observation-medic");
//        QueryExecution qe = QueryExecutionFactory.create(query, model);
//       com.hp.hpl.jena.query.ResultSet results =  qe.execSelect();
//        ResultSetFormatter.out(System.out, results, query);
//       
//        
//        
        //Get name for all the Models in TDB
//        Iterator<String> it = dbh.dataset.listNames();
//         while(it.hasNext()){
//    	  String name =it.next();
//    	  System.out.println(name);
//    	  
//      }
        
        
        
        dbh.closeCon();
      
        
        
        

    }
}
