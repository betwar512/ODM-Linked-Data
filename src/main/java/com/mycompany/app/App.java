package com.mycompany.app;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.sparql.core.DatasetGraph;
import com.hp.hpl.jena.vocabulary.OWL2;

/**
 * @author Abbas.h.Safaie
 * A.safaie@y7mail.com
 * */


public class App 
{
    public static void main( String[] args )
    {   	
    	
    	
    BasicConfigurator.configure();//logger
    //dateTime
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    Date date = new Date();
    String time=dateFormat.format(date); //2014/08/06 15:59:48	
    
    	System.out.println("Please Enter your file Path or push enter:");
      Scanner sc=new Scanner(System.in);
      String filePath="";
    	   filePath=sc.nextLine();
    	 sc.close(); 
      if(filePath.length()==0){
  	     //TODO Change the String to your file path 
    		filePath="src/main/java/resources/odm1.3_clinical_ext_Full_study_extract_2015-05-22-162457368.xml";
      }
    	
    	
    	//Helper class for RDF models	
      RDFModelHelper modelHelper=new RDFModelHelper(); 
      
        //Complete Graph include all 6 models 
    	//from ODM file 
    	ModelMaker mm=modelHelper.modelHandler(filePath);
    		
    		Model m=mm.openModel("Slice-Theme");
    		m.write(System.out,"TURTLE");
    	
    	//Triple store init
    	DatabaseHelper dbh=new DatabaseHelper();
  	
    	
    		//write to store
    		dbh.writeModel(mm);
    		//write to file
    		dbh.saveToFile(mm);
    	
    	System.out.println(time);
    		
    		/*
    		 String queryString =        
                     "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>" +
    		         "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
    		        "PREFIX lcdcobs:<http://purl.org/sstats/lcdc/def/obs#>"+ 
    		         "PREFIX cardiovitalsigns:<http://aehrc-ci.it.csiro.au/cardio/lcdc/vitalsigns/def/cardio-vitalsigns#>"+
    		         "PREFIX lcdccore:<http://purl.org/sstats/lcdc/def/core#>"+
    		            "select ?uri ?subject "+
    		            "where {?uri cardiovitalsigns:MedicationStartDate '2014-07-01'."+
    		            "?x lcdccore:subject ?subject.} \n ";
    		
    		*/
    	
    	// Create a new query
       String queryString2 =        
         "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#> "+
         "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
         "PREFIX lcdcobs:<http://purl.org/sstats/lcdc/def/obs#>"+ 
         "PREFIX cardiovitalsigns:<http://aehrc-ci.it.csiro.au/cardio/lcdc/vitalsigns/def/cardio-vitalsigns#>"+
         "PREFIX lcdccore:<http://purl.org/sstats/lcdc/def/core#>"+
         "PREFIX owl:<"+OWL2.getURI()+">" +
         "PREFIX lcdcodm:  <http://purl.org/sstats/lcdc/def/odm#>" +
            "select * "+
            "where { ?vs a owl:DatatypeProperty ;"+
        " lcdcodm:basedOn ?based ; "+
        " rdfs:label ?label ; "+
        " rdfs:domain ?domain ; "+
        " lcdccore:themeId ?themeId ."+
        " ?themeId lcdccore:themeKey ?themeKey."+
        " ?based lcdcodm:formOid ?form; "+
        " lcdcodm:itemGroupOid ?itemGroup; "+
        " lcdcodm:ItemOid ?item. } \n";
       
      String qu="select * where { { graph ?g {?s ?p ?o} } union {?s ?p ?o} } limit 1000";

      String q2= "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>\n"+
    	         "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"+
    	         "PREFIX lcdcobs:<http://purl.org/sstats/lcdc/def/obs#>\n"+ 
    	         "PREFIX cardiovitalsigns:<http://aehrc-ci.it.csiro.au/cardio/lcdc/vitalsigns/def/cardio-vitalsigns#>\n"+
    	         "PREFIX lcdccore:<http://purl.org/sstats/lcdc/def/core#>\n"+
    	         "PREFIX owl:<http://www.w3.org/2002/07/owl#>\n"+
    	         "PREFIX lcdcodm:  <http://purl.org/sstats/lcdc/def/odm#>\n"+
    	         "select ?s ?subject ?sysBP where { ?s a lcdcobs:Observation ; lcdccore:subject ?subject ; lcdccore:phase <http://purl.org/sstats/lcdc/id/phase/SE_HOME8> ; cardiovitalsigns:SystolicBP ?sysBP . }";
      
      
       Query query = QueryFactory.create(qu);
        System.out.println("----------------------");
        System.out.println("Query Result Sheet");
        System.out.println("----------------------");
  

       System.out.println("Query on Model : ");
    // Model model=  mm.openModel("Cardio-vital");
       
//       DatabaseHelper dbh2=new DatabaseHelper();
       
       //Model capture by modelName 
      Dataset model=dbh.dataset;
      
      
      
 //   Model grap=dbh.dataset.getDefaultModel();
     model.begin(ReadWrite.WRITE);
        QueryExecution qe = QueryExecutionFactory.create(query, model);
       com.hp.hpl.jena.query.ResultSet results =  qe.execSelect();
        ResultSetFormatter.out(System.out, results, query);
      qe.close();
     model.end();

     

     dbh.dataset.begin(ReadWrite.READ);
     	//Get name for all the Models in TDB
        Iterator<String> it = dbh.dataset.listNames();
         while(it.hasNext()){
    	  String name =it.next();
    	  System.out.println(name);
    	  
         }
     dbh.dataset.end();
     	//close database
         dbh.closeCon();
    } 
 }

