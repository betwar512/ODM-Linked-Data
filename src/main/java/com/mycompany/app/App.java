package com.mycompany.app;
import java.io.FileWriter;
import java.io.StringWriter;
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
  	
	 Model model2=modelHelper.createModel();
		
	try{
  
  String syntax = "TURTLE"; // also try "N-TRIPLE" 
  StringWriter out = new StringWriter();
  model2.write(out, syntax);
  String result = out.toString();
 
  
  String fileName = "Aug20_RDF_newModel_03.rdf";
  FileWriter output = new FileWriter( fileName );
  
  model2.write( output, "TURTLE" );
  output.close();
 // RDFDataMgr.write(System.out, model, "N-Triples") ;
System.out.println(result);
    
    	} catch (Exception e) { 
    		System.out.println("Failed: " + e); 
    		} 

    
    }
}
