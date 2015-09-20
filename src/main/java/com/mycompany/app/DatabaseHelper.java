package com.mycompany.app;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelMaker;

import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class DatabaseHelper {

  static final	String databaseUri="Databases/";
  
   Dataset dataset=null;
	
	public DatabaseHelper(){
		
		 dataset=TDBFactory.createDataset(databaseUri);
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
		dataset.close();	
		System.out.println("Data saved into TDB");

	}
	
	/*
	 * get Model by name from database
	 * 
	 * */
	public Model getModelByName(String name){
		
		
	dataset.begin(ReadWrite.READ);	
    Model model=dataset.getNamedModel("Cardio-Vital");
    dataset.close();
    
    	return model;
	}
	
	
}
