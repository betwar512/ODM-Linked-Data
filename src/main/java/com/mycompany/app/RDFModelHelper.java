package com.mycompany.app;

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
import com.mycompany.app.lcdc.Lcdc;

/**
 * @author Abbas H Safaie
 * RDFModel /helper class to generate model and add specified properties to the model 
 * Main function CreateModel 
 *
 */
public class RDFModelHelper {

	
	//Create Model and return it 
  public Model createModel(){
		
		
		//Model
    	Model model=ModelFactory.createDefaultModel();

    	JaxBinder myJax=new JaxBinder();

    	ODMcomplexTypeDefinitionClinicalData clinicalData=
    			myJax.getClinicalData("src/main/java/odm1.3_clinical_ext_Full_study_extract_2015-05-22-162457368.xml");
    	//GetForms with Key
    	 Map<String,List<ODMcomplexTypeDefinitionFormData>> forms=  myJax.getForms(clinicalData);
    	//Get ItemGroups with Key 
    	 Map<String, ODMcomplexTypeDefinitionItemGroupData>itemGroups=myJax.getItemGroupData(forms);	
      	//Get Items with Uri keys 
    	 Map<String, ODMcomplexTypeDefinitionItemData> items=myJax.getItemsList(itemGroups);
    	
    	 addItems(items,model);
    	 addItemGroups(itemGroups,model);
    	 
         return model;
	}
	
public void addItemGroups(Map<String, ODMcomplexTypeDefinitionItemGroupData> itemGroups,Model model){
	
	Property itemGroupRepeatKey1=model.createProperty("lcdc:", Lcdc.itemGroupRepeatKey);
	Property itemGroupOid=model.createProperty("lcdc:",Lcdc.itemGroupOID);	
	   //Create RDF sets for GroupItems
	  	for(Iterator<Entry<String, ODMcomplexTypeDefinitionItemGroupData>> i=itemGroups.entrySet().iterator();i.hasNext();){  		
	       Entry<String, ODMcomplexTypeDefinitionItemGroupData> itemGroup= i.next();
	    	ODMcomplexTypeDefinitionItemGroupData	it= itemGroup.getValue();
	    	String mapKey=itemGroup.getKey();
	   		String itemOid= it.getItemGroupOID();
	   		String subjectKey=mapKey +"/"+itemOid;	
	    	String itemGroupRepeatKey="";
	    	itemGroupRepeatKey="";
	    	if(it.getItemGroupRepeatKey()!=null){
	    		itemGroupRepeatKey=it.getItemGroupRepeatKey();
	    	}	
	  
	    		//Add to model 
	    	model.createResource(subjectKey)
	    	.addProperty(itemGroupRepeatKey1,itemGroupRepeatKey)
	    	.addProperty(itemGroupOid, itemOid);
	    			
	   		}
		}

	//Add Item to RDF model 
	public void addItems(Map<String, ODMcomplexTypeDefinitionItemData> items,Model model){

		Property ItemOid=model.createProperty("lcdc: ", Lcdc.itemOid);
    	Property value=model.createProperty("lcdc:",Lcdc.value);
    //	Property customerBla=model.createProperty(Lcdc.customDate);
    	Property messurmentUnit=model.createProperty("lcdc:",Lcdc.measurementUnitOID);
    	Property comment=model.createProperty("rdfs:", "comment");
    	
    	
		//Create RDF model for Items
	  	for(Iterator<Entry<String, ODMcomplexTypeDefinitionItemData>> itemDatas=items.entrySet().iterator();itemDatas.hasNext();){
	  		
	  		Entry<String, ODMcomplexTypeDefinitionItemData> item=itemDatas.next();
	  		
	  		String mapKey=item.getKey();
	  		String itemid=item.getValue().getItemOID();
	  		String key=mapKey+"/"+itemid;
	  		String valu=item.getValue().getValue();
	  	  	//Create Comment
	    	StringCustomHelper stringHelper=new StringCustomHelper();	
	        String comm=stringHelper.Comment(key);
	        ODMcomplexTypeDefinitionMeasurementUnitRef mesur=item.getValue().getMeasurementUnitRef();
	         if(mesur!=null){  //ToString method throws exception if catches  null value
	     	String mesurUnit=mesur.getMeasurementUnitOID().toString();
	     	
	    
	     	//Create model
	    	model.createResource(key)
			.addProperty(ItemOid, itemid)
			.addProperty(value, valu)
			.addProperty(messurmentUnit, mesurUnit)
	    	.addProperty(comment,comm);
		}else{
	  		model.createResource(key)
	  		.addProperty(ItemOid, itemid)
	  		.addProperty(value, valu).addProperty(comment,comm);

		  }
	  	}	
	  }

}
