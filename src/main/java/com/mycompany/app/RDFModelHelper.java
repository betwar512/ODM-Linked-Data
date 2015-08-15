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
import com.hp.hpl.jena.vocabulary.RDFS;
import com.mycompany.app.lcdc.ItemGroup;
import com.mycompany.app.lcdc.Items;

/**
 * @author Abbas H Safaie
 * RDFModel /helper class to generate model and add specified properties to the model 
 * Main function CreateModel 
 *
 */
public class RDFModelHelper {

	private static final String base="base_uri/subject/";
	//Create Model and return it 
  public Model createModel(){
		
		
		//Model
    	Model model=ModelFactory.createDefaultModel();

    	JaxBinder myJax=new JaxBinder();

    	ODMcomplexTypeDefinitionClinicalData clinicalData=
    			myJax.getClinicalData("src/main/java/odm1.3_clinical_ext_Full_study_extract_2015-05-22-162457368.xml");
    	
    	myJax.makeItemsObjects(clinicalData);
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
	  
	    	
	    	String base_Uri=base+subjectKey;
	    		//Add to model 
	    	model.createResource(base_Uri)
	    	.addProperty(ItemGroup.itemGroupRepeatKey,itemGroupRepeatKey)
	    	.addProperty(ItemGroup.itemGroupOID, itemOid);
	    			
	   		}
		}

	//Add Item to RDF model 
	public void addItems(Map<String, ODMcomplexTypeDefinitionItemData> items,Model model){

    	String itemString="variable/";
		//Create RDF model for Items
	  	for(Iterator<Entry<String, ODMcomplexTypeDefinitionItemData>> itemDatas=items.entrySet().iterator();itemDatas.hasNext();){
	  		
	  		Entry<String, ODMcomplexTypeDefinitionItemData> item=itemDatas.next();
	  	
	  		String mapKey=item.getKey();
	  		String itemid=item.getValue().getItemOID();
	  		
	  		String key= mapKey+"/"+itemString + itemid;
	  		String valu=item.getValue().getValue();
	  	  	//Create Comment
	    	StringCustomHelper stringHelper=new StringCustomHelper();	
	        String comm=stringHelper.Comment(key);
	        ODMcomplexTypeDefinitionMeasurementUnitRef mesur=item.getValue().getMeasurementUnitRef();
	        String base_Uri=base+key;
	         if(mesur!=null){  //ToString method throws exception if catches  null value
	     	String mesurUnit=mesur.getMeasurementUnitOID().toString();	
	        
	
	     
	    	model.createResource(base_Uri)
			.addProperty(Items.itemOid, itemid)
			.addProperty(Items.value, valu)
			.addProperty(Items.measurementUnitOID, mesurUnit)
	    	.addProperty(RDFS.comment,comm);
		}else{
	  		model.createResource(base_Uri)
	  		.addProperty(Items.itemOid, itemid)
	  		.addProperty(Items.value, valu)
	  		.addProperty(RDFS.comment,comm);

		  }
	  	}	
	  }

}
