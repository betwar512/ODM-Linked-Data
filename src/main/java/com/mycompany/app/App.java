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
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.mycompany.app.lcdc.Lcdc;

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
  	String databaseUri="Databases/";
	Dataset dataset=TDBFactory.createDataset(databaseUri);
	  dataset.begin(ReadWrite.WRITE) ;
  while(iter.hasNext()){

	 String modelName=iter.next();
	Model model= mm.openModel(modelName);
	
	dataset.addNamedModel(modelName, model);

	dataset.getNamedModel(modelName);
	
	
	try{

		String fileName = System.getProperty("user.dir")+"\\RDF_ModelMaker\\"+modelName+".rdf" ;
		File file =new File(fileName);
		FileOutputStream output = new FileOutputStream( file );
		RDFDataMgr.write(output, model, RDFFormat.TURTLE_BLOCKS);

		  	} catch (Exception e) { 
		  		System.out.println("Failed: " + e); 
		  		} 
  }

	dataset.commit();
	dataset.close();
  	
//    String queryString="SELECT ?ItemOid WHERE{ ?y "+ RDFS.label.getURI() +" HeartRate .?y  "+Lcdc.eventOID.getURI()+"  ?ItemOid . } ";
//    Query query = QueryFactory.create(queryString) ;
//  	String databaseUri="Databases/";
//  	
//	Dataset dataset=TDBFactory.createDataset(databaseUri);
//	
//    	dataset.begin(ReadWrite.READ);
//    	
//    	Model model=dataset.getNamedModel("Cardio-Vital");
//    	
//    	RDFDataMgr.write(System.out, model, RDFFormat.TURTLE_BLOCKS);
//    	
//    	
//    	
//    	  try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
//    		    ResultSet results = qexec.execSelect() ;
//    		    
//    		    for ( ; results.hasNext() ; )
//    		    {
//    		      QuerySolution soln = results.nextSolution() ;
//    		      RDFNode x = soln.get("varName") ;       // Get a result variable by name.
//    		      Resource r = soln.getResource("VarR") ; // Get a result variable - must be a resource
//    		      Literal l = soln.getLiteral("VarL") ;   // Get a result variable - must be a literal
//    		    }
//    		  }
//    	
    	
    	
    	
    	
    	
    	
    	
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
