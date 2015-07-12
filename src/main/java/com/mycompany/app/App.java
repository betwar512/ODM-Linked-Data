package com.mycompany.app;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionClinicalData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionFormData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemGroupData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionMetaDataVersion;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionStudy;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.VCARD;
import com.mycompany.app.lcdc.Lcdc;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	
    	
    	//Model
    	Model model=ModelFactory.createDefaultModel();
    	
     	
    	Property itemGroupRepeatKey1=model.createProperty("lcdc:", Lcdc.itemGroupRepeatKey);
    	Property itemGroupOid=model.createProperty("lcdc:",Lcdc.itemGroupOID);
    	
    	List<String> itemGroupOIdList=new ArrayList<String>();
    	
    	
    	
    	
    	
    	try{
    	// TODO Auto-generated method stub

    	JaxBinder myJax=new JaxBinder();
    	
    //	List<ODMcomplexTypeDefinitionStudy> study=
    			myJax.catchStudy("src/main/java/odm1.3_clinical_ext_Full_study_extract_2015-05-22-162457368.xml");	
    	
    //	ODMcomplexTypeDefinitionMetaDataVersion metaData=myJax.catchMetaData(study);
    	ODMcomplexTypeDefinitionClinicalData clinicalData=
    			myJax.getClinicalData("src/main/java/odm1.3_clinical_ext_Full_study_extract_2015-05-22-162457368.xml");
    	
    	
    	//GetForms with Key
    	 Map<String,List<ODMcomplexTypeDefinitionFormData>> forms=  myJax.getForms(clinicalData);
    	 
    	 
    	 
    	//Get ItemGroups with Key 
   	Map<String, ODMcomplexTypeDefinitionItemGroupData>itemGroups=myJax.getItemGroupData(forms);
   	
    	   
  	for(Iterator<Entry<String, ODMcomplexTypeDefinitionItemGroupData>> i=itemGroups.entrySet().iterator();i.hasNext();){
   		
    		Entry<String, ODMcomplexTypeDefinitionItemGroupData> itemGroup= i.next();
   		   		
    		
    	ODMcomplexTypeDefinitionItemGroupData	it= itemGroup.getValue();
    		
////    		//
////    		// Test the method and get the ItemGroupId + ItemGroupRepeatKey
////    		//Added to list of strings 	
    	String mapKey=itemGroup.getKey();
   		String itemOid= it.getItemGroupOID();
   		String subjectKey=mapKey +"/"+itemOid;
   		
    	String itemGroupRepeatKey=it.getItemGroupRepeatKey();
    		
    		//Add to model 
    	model.createResource(subjectKey)
    	.addProperty(itemGroupRepeatKey1,itemGroupRepeatKey)
    	.addProperty(itemGroupOid, itemOid);
    	
    	
   
    	
   		itemGroupOIdList.add(subjectKey);  	
   			
   		}
  	
  	InfModel inf = ModelFactory.createRDFSModel(model);
    
  Resource a=inf.getResource(itemGroupOIdList.get(0));
  
  
  
  String syntax = "RDF/XML-ABBREV"; // also try "N-TRIPLE" and "TURTLE"
  StringWriter out = new StringWriter();
  model.write(out, syntax);
  String result = out.toString();
 
 // RDFDataMgr.write(System.out, model, "N-Triples") ;
System.out.println(result);
    
    	} catch (Exception e) { 
    		System.out.println("Failed: " + e); 
    		} 
    	
    	
    	 
    
    	
    	
    	
    	
//    	
//    	
//    	String personURI    = "http://somewhere/JohnSmith";
//    	String givenName    = "John";
//    	String familyName   = "Smith";
//    	String fullName     = givenName + " " + familyName;
//
//    	// create an empty Model
//    	Model model = ModelFactory.createDefaultModel();
//
//    	
//    	model.createResource(personURI);
//    	
    	
    	
    	
    	// create the resource
    	//   and add the properties cascading style
//    	Resource johnSmith
//    	  = model.createResource(personURI)
   //	         .addProperty(VCARD.FN, fullName)
//    	         .addProperty(VCARD.N,
//    	                      model.createResource()
//    	                           .addProperty(VCARD.Given, givenName)
//    	                           .addProperty(VCARD.Family, familyName));
//    
    	
    	
    	
    		
    		
    
    }
}
