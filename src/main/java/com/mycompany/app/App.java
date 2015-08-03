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
    	
//    	//GetForms with Key
//    	 Map<String,List<ODMcomplexTypeDefinitionFormData>> forms=  myJax.getForms(clinicalData);
//    	//Get ItemGroups with Key 
//   	Map<String, ODMcomplexTypeDefinitionItemGroupData>itemGroups=myJax.getItemGroupData(forms);	
//   	//Get Items with Uri keys 
//   	Map<String, ODMcomplexTypeDefinitionItemData> items=myJax.getItemsList(itemGroups);
//   	
//   	
//   	//RDF for Form 
//   	for(Iterator<Entry<String, List<ODMcomplexTypeDefinitionFormData>>> y=forms.entrySet().iterator();y.hasNext();){
//   		
//   		
//   		Entry<String, List<ODMcomplexTypeDefinitionFormData>> selectedFrom=y.next();
//   		
//   	            List<ODMcomplexTypeDefinitionFormData> formData=	selectedFrom.getValue();
//   	            
//   	            for(Iterator<ODMcomplexTypeDefinitionFormData> ft=formData.iterator();ft.hasNext();){
//   	            	
//   	            	
//   	             ODMcomplexTypeDefinitionFormData ft2= ft.next();
//   	             ft2.getFormOID();
//   	          
//   	            	
//   	            }
//   	        
//   		
//   	}


 
  	RDFModelHelper modelHelper=new RDFModelHelper();
  	
	 Model model2=modelHelper.createModel();
		
	try{
  
  String syntax = "TURTLE"; // also try "N-TRIPLE" 
  StringWriter out = new StringWriter();
  model2.write(out, syntax);
  String result = out.toString();
 
  
  String fileName = "Aug3_RDF_newModel_01.rdf";
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
