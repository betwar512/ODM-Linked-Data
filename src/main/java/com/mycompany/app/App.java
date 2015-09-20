package com.mycompany.app;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.mycompany.app.lcdc.Lcdc;
import com.mycompany.app.lcdc.LcdcCore;

/**
 * @author Abbas.h.Safaie
 * 
 * */
public class App 
{
    public static void main( String[] args )
    {   	
   
    
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    	Date date = new Date();
    	String time=dateFormat.format(date); //2014/08/06 15:59:48
  	RDFModelHelper modelHelper=new RDFModelHelper();
  	
  	//HashMap<String,Model> models=modelHelper.sliceModels();
  	
  	String filePath="src/main/java/odm1.3_clinical_ext_Full_study_extract_2015-05-22-162457368.xml";
  		//OntModel model=modelHelper.ontoModelTest();
  	ModelMaker mm=modelHelper.ontoModelTest(filePath);
  	ExtendedIterator<String> iter=mm.listModels();
  //	DatabaseHelper dbh=new 	DatabaseHelper();
  	
  	
   //  	dbh.writeModel(mm);

// while(iter.hasNext()){
//
	// String modelName=iter.next();

//	
//	
//	try{
//
//		String fileName = System.getProperty("user.dir")+"\\RDF_ModelMaker\\"+modelName+".rdf" ;
//		File file =new File(fileName);
//		FileOutputStream output = new FileOutputStream( file );
//		RDFDataMgr.write(output, model, RDFFormat.TURTLE_BLOCKS);
//
//		  	} catch (Exception e) { 
//		  		System.out.println("Failed: " + e); 
//		  		} 
//}


     	  // Create a new query
        String queryString =        
          "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#> "+
         "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
        // "PREFIX lcdcobs:<http://purl.org/sstats/lcdc/def/obs#>"+ 
         "PREFIX cardiovitalsigns:<http://aehrc-ci.it.csiro.au/cardio/lcdc/vitalsigns/def/cardio-vitalsigns#>"+
         "PREFIX lcdccore:<http://purl.org/sstats/lcdc/def/core#>"+
            "select ?uri ?subject "+
            "where {?uri cardiovitalsigns:MedicationStartDate '2014-07-01'."+
            "?x lcdccore:subject ?subject.} \n ";
        Query query = QueryFactory.create(queryString);
   

        System.out.println("----------------------");

        System.out.println("Query Result Sheet");

        System.out.println("----------------------");

        System.out.println("Direct&Indirect Descendants (obs-medication)");
        Model model=mm.getModel("observations-medication");
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        com.hp.hpl.jena.query.ResultSet results =  qe.execSelect();

        // Output query results    
        ResultSetFormatter.out(System.out, results, query);
  //   model.write(System.out);

    	
    	
    	
    	
//	for (Entry<String, Model> entry : models.entrySet()) {
//	try{
//  Model model=entry.getValue();
//  String syntax = "TURTLE"; // also try "N-TRIPLE" 
//  StringWriter out = new StringWriter();
//  model.write(out, syntax);
//  String result = out.toString();
//
//  String fileName = "\\RDF_Slice_files\\"+time+"_RDF_Slice_subjectKey_"+entry.getKey()+".rdf";
//  File file =new File(fileName);
//  FileWriter output = new FileWriter( file );
// 
//  model.write( output, "TURTLE" );
//  output.close();
// // RDFDataMgr.write(System.out, model, "N-Triples") ;
//System.out.println(result);
//    
//    	} catch (Exception e) { 
//    		System.out.println("Failed: " + e); 
//    		} 
//	
//		}
    
    }
}
