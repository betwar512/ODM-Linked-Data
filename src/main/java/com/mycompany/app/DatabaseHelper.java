package com.mycompany.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**
 * @author Abbas.h.Safaie
 * 
 * Handling Conn to database 
 * */

public class DatabaseHelper {

  static final	String databaseUri="Databases/";
  
   Dataset dataset=null;
	
	public DatabaseHelper(){
		// dataset=TDBFactory.createDataset(databaseUri);
		
	}
	
	/*
	 * Close dataset 
	 * Query Can't be use after this method 
	 * */
	public void closeCon(){
		
		dataset.close();
		
		  System.out.println("Database connection closed");
	}
	
	
	/*
	 * write model into TDB
	 * */
	public void writeModel(ModelMaker mm){

	  	ExtendedIterator<String> iter=mm.listModels();
	    dataset.begin(ReadWrite.WRITE) ;
	  while(iter.hasNext()){
		String modelName=iter.next();
		Model model= mm.openModel(modelName);
		dataset.addNamedModel(modelName, model);
	  }
		dataset.commit();	
		 System.out.println("----------------------");
		System.out.println("Data saved into TDB");
		 System.out.println("----------------------");
	}
	
	/*
	 * get Model by name from database
	 * 
	 * */
	public Model getModelByName(String name){
		
	
	dataset.begin(ReadWrite.READ);	
    Model model=dataset.getNamedModel(name);
    	return model;
	}
	
	
	/*
	 * save modelMaker Models into files 
	 * 
	 * */
	public void saveToFile(ModelMaker mm){
		
		
		ExtendedIterator<String> iter=mm.listModels();
		while(iter.hasNext()){
		//
			String modelName=iter.next();
			Model model =mm.openModel(modelName);
			
			try{
		
				String fileName = System.getProperty("user.dir")+"\\RDF_ModelMaker\\"+modelName+".ttl" ;
				File file =new File(fileName);
				FileOutputStream output = new FileOutputStream( file );
				
				RDFDataMgr.write(output, model, RDFFormat.TURTLE_BLOCKS);
				 System.out.println("----------------------");
					System.out.println("Model: "+modelName +" saved into Folder RDF_ModelMaker");
					 System.out.println("----------------------");
				
				
				  	} catch (Exception e) { 
				  		System.out.println("Failed: " + e); 
		    } 
		}
	}
	
	
}
