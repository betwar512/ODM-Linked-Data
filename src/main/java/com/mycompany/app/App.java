package com.mycompany.app;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hp.hpl.jena.rdf.model.Model;
/**
 * @author Abbas.h.Safaie
 * 
 * */
public class App 
{


    public static void main( String[] args )
    {
    	


 
  	RDFModelHelper modelHelper=new RDFModelHelper();
  	
  	HashMap<String,Model> models=modelHelper.createModel();
  	
  	
	for (Entry<String, Model> entry : models.entrySet()) {
	try{
  Model model=entry.getValue();
  String syntax = "TURTLE"; // also try "N-TRIPLE" 
  StringWriter out = new StringWriter();
  model.write(out, syntax);
  String result = out.toString();
 
  
  String fileName = "Aug21_RDF_Slice_subjectKey_"+entry.getKey()+".rdf";
  FileWriter output = new FileWriter( fileName );
  
  model.write( output, "TURTLE" );
  output.close();
 // RDFDataMgr.write(System.out, model, "N-Triples") ;
System.out.println(result);
    
    	} catch (Exception e) { 
    		System.out.println("Failed: " + e); 
    		} 
	
		}
    
    }
}
