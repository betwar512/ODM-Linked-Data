package com.mycompany.app;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionClinicalData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionFormData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemGroupData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionMeasurementUnitRef;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
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
    //	List<String> itemGroupOIdList=new ArrayList<String>();
    	
    	Property ItemOid=model.createProperty("lcdc: ", Lcdc.itemOid);
    	Property value=model.createProperty("lcdc:",Lcdc.value);
    	Property messurmentUnit=model.createProperty("lcdc:",Lcdc.measurementUnitOID);
    	
    	
    	try{
    

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
   	
   	//Get Items with Uri keys 
   	Map<String, ODMcomplexTypeDefinitionItemData> items=myJax.getItemsList(itemGroups);
   	
   	
   	
    	   
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

   			
   		}
  	
  	for(Iterator<Entry<String, ODMcomplexTypeDefinitionItemData>> itemDatas=items.entrySet().iterator();itemDatas.hasNext();){
  		
  		Entry<String, ODMcomplexTypeDefinitionItemData> item=itemDatas.next();
  		
  		String mapKey=item.getKey();
  				String itemid=item.getValue().getItemOID();
  				String key=mapKey+"/"+itemid;
  	
  		
  		String valu=item.getValue().getValue();
         ODMcomplexTypeDefinitionMeasurementUnitRef mesur=item.getValue().getMeasurementUnitRef();
         if(mesur!=null){
	String mesurUnit=mesur.getMeasurementUnitOID().toString();
	
	model.createResource(key)
		.addProperty(itemGroupOid, itemid)
		.addProperty(value, valu)
		.addProperty(messurmentUnit, mesurUnit);
	}else{
  		model.createResource(key)
  		.addProperty(itemGroupOid, itemid)
  		.addProperty(value, valu);
  		//.addProperty(messurmentUnit, mesurUnit);
	}
  	}
  	
  	
  	
  
  String syntax = "TURTLE"; // also try "N-TRIPLE" and "TURTLE"
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
