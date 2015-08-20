package com.mycompany.app;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
    	
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    	Date date = new Date();
    	String time=dateFormat.format(date); //2014/08/06 15:59:48

 
  	RDFModelHelper modelHelper=new RDFModelHelper();
  	
  	HashMap<String,Model> models=modelHelper.sliceModels();
  	
  	
	for (Entry<String, Model> entry : models.entrySet()) {
	try{
  Model model=entry.getValue();
  String syntax = "TURTLE"; // also try "N-TRIPLE" 
  StringWriter out = new StringWriter();
  model.write(out, syntax);
  String result = out.toString();

  String fileName = "\\RDF_Slice_files\\"+time+"_RDF_Slice_subjectKey_"+entry.getKey()+".rdf";
  File file =new File(fileName);
  FileWriter output = new FileWriter( file );
 
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
