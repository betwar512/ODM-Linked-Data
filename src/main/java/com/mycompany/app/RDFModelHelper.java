package com.mycompany.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionClinicalData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemData;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.OWL2;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.mycompany.app.lcdc.Lcdc;

/**
 * @author Abbas H Safaie
 * RDFModel /helper class to generate model and add specified properties to the model 
 * Main function CreateModel 
 *
 */
public class RDFModelHelper {

	   //================================================================================
    // Uri 
    //================================================================================

	
	
	//Check for Uri dataset type 
	public String datasetType(String str){
		String returnStr="";
		
	final String blood="BLOOD";
	final String vital="VITAL";
	final String medic="MEDIC";
	
	String[] comparStrs=str.split("_");
	
	String comparStr=comparStrs[1];
	
	//Compare String 
		if(comparStr.equals(blood)){
			returnStr=blood;
		}else if(comparStr.equals(vital)){
			returnStr=vital;
		}else if(comparStr.equals(medic)){
			returnStr=medic;
		}
		return returnStr.toLowerCase();
	}
	
	
	
	//Generate Uri for Dataset
	public String uriDataset(ItemDetail item){
		
		String uriComplete;
		String baseUri="http://aehrc-ci.it.csiro.au/dataset/";		
		String version="20150713";
		String dataset=datasetType(item.itemGroupOid);
		String uri=baseUri+dataset+"/lcdc/"+version+"/subject/"+ item.subjectKey+"/phase/"+
		item.eventOid+"/form/"+item.formOid+"/itemGroup/"+item.itemGroupOid;
		//Key optional
		if(item.itemGroupOid.length()>0)
		 uriComplete=uri+"/Key/"+item.itemRepeatKey+"/variable/";
		else
			uriComplete=uri+"/variable/";
		return uriComplete;
		
	}
	
	
	//Generate Uri for Slice
	public String uriSlice(ItemDetail item){
		String uriComplete;
		String baseUri="http://aehrc-ci.it.csiro.au/slice/";		
		String version="20150713";
		String uri=baseUri+item.subjectKey+"/lcdc/"+version+"/subject/"+ item.subjectKey+"/phase/"+
		item.eventOid+"/form/"+item.formOid+"/itemGroup/"+item.itemGroupOid+
		"/RepeatKey/"+item.itemRepeatKey+"/variable/";
		//Key optional
				if(item.itemGroupOid.length()>0)
				 uriComplete=uri+"/Key/"+item.itemRepeatKey+"/variable/";
				else
					uriComplete=uri+"/variable/";
				
		return uriComplete;
		
	}
	
	   //================================================================================
    // Model
    //================================================================================

  public HashMap<String,Model> sliceModels(){
		
		
		//Model
    	Model model=ModelFactory.createDefaultModel();

    	JaxBinder myJax=new JaxBinder();

    	ODMcomplexTypeDefinitionClinicalData clinicalData=
    			myJax.getClinicalData("src/main/java/odm1.3_clinical_ext_Full_study_extract_2015-05-22-162457368.xml");
    	ArrayList<ItemDetail> itemDtos=myJax.makeItemsObjects(clinicalData);
  
    	HashMap<String,Model> sliceModels=sliceRdf(itemDtos);
    	//completeRdf(itemDtos,model);
         return sliceModels;
	}
	
  
  //Grouping by SubjectKey
  public HashMap<String, List<ItemDetail>> groupBySubjectKey(ArrayList<ItemDetail> itemDetails){
	  
	  
	  HashMap<String, List<ItemDetail>> hashMap=new HashMap<String, List<ItemDetail>>();
	  
	  for(ItemDetail itemDetail:itemDetails){
		  
		  
		  if(!hashMap.containsKey(itemDetail.subjectKey)){
	          List<ItemDetail> list= new ArrayList<ItemDetail>();
	          list.add(itemDetail);
	      hashMap.put(itemDetail.subjectKey,list);
	      }
	      else
	          hashMap.get(itemDetail.subjectKey).add(itemDetail);
		  
	    }
		  
	  return hashMap;
	  }

  
  
  //Slice RDF Model 
public HashMap<String,Model> sliceRdf(ArrayList<ItemDetail> itemDtos){
	//Create models 
	HashMap<String,Model> models=new HashMap<String,Model>();
	
	HashMap<String, List<ItemDetail>> groupItemDtos=groupBySubjectKey(itemDtos);
	
	for (Entry<String, List<ItemDetail>> entry : groupItemDtos.entrySet()) {
		
		Model sliceModel =ModelFactory.createDefaultModel();
		
		
	    List<ItemDetail> mapValue = entry.getValue();
	    for(ItemDetail itemDetail:mapValue){
	    
	    String itemRepeatKey="";
 		//Create base Uri 
 		String uri =uriSlice(itemDetail);

 		List<ODMcomplexTypeDefinitionItemData> itemList=itemDetail.items;
 		String formOid=itemDetail.formOid;
 		String itemGroupOid=itemDetail.itemGroupOid;
 		String dataset=datasetType(itemDetail.itemGroupOid);

 		if(	itemDetail.itemRepeatKey ==null){
 			
 			itemRepeatKey="";
 		}else{
 			
 			itemRepeatKey=itemDetail.itemRepeatKey;
 		}
 	
 		System.out.println(itemList.size());

 		for (ODMcomplexTypeDefinitionItemData item : itemList) {
 			String value="";
 			String messurmentUnit="";
 			if(item.getMeasurementUnitRef() != null)
 				messurmentUnit=item.getMeasurementUnitRef().toString();
 			String itemOid=item.getItemOID();
 			value=item.getValue();
 	
 			//Object Model to create comment 
 			CommentModel commentDto=new CommentModel();	
 			//CreateComment
 			commentDto.itemOid=itemOid;
 			commentDto.eventOid=itemDetail.eventOid;
 			commentDto.subjectKey=itemDetail.subjectKey;
 		 	commentDto.dataSet=dataset;
 		 	//Comment and Label
 	    	String comment=	StringCustomHelper.Comment(commentDto);
 		    String label=StringCustomHelper.label(itemOid);
 		
 			 sliceModel.createResource(uri+itemOid)
 			.addProperty(Lcdc.itemOid,itemOid)
 	    	.addProperty(Lcdc.itemGroupOID,itemGroupOid)
 	    	.addProperty(Lcdc.formOID,formOid)
 	    	.addProperty(Lcdc.itemGroupRepeatKey,itemRepeatKey)
 	    	.addProperty(Lcdc.measurementUnitOID, messurmentUnit)
 	    	.addProperty(Lcdc.value, value)
 	    	.addProperty(RDFS.label, label)
 	    	.addProperty(RDFS.comment, comment);
 	    	
 	  
 			}//For loop item
	    
 	
	    } 
	   
		models.put(entry.getKey(),sliceModel);
	}
	return models;
}
  
  
  //Create model with return Object ItemDtos
  public void completeRdf(ArrayList<ItemDetail> itemDtos,OntModel model){
	  
	 

	 for (ItemDetail itemDetail : itemDtos) {
	
		        String itemRepeatKey="";
		 		//Create base Uri 
		 		String uri =uriDataset(itemDetail);

		 		List<ODMcomplexTypeDefinitionItemData> itemList=itemDetail.items;
		 		String formOid=itemDetail.formOid;
		 		String itemGroupOid=itemDetail.itemGroupOid;
		 		String dataset=datasetType(itemDetail.itemGroupOid);

		 		if(	itemDetail.itemRepeatKey ==null){
		 			
		 			itemRepeatKey="";
		 		}else{
		 			
		 			itemRepeatKey=itemDetail.itemRepeatKey;
		 		}
		 	
		 		System.out.println(itemList.size());
		
		 		for (ODMcomplexTypeDefinitionItemData item : itemList) {
		 			String value="";
		 			String messurmentUnit="";
		 			if(item.getMeasurementUnitRef() != null)
		 				messurmentUnit=item.getMeasurementUnitRef().getMeasurementUnitOID().toString();
		 			
		 	

		 			String itemOid=item.getItemOID();
		 			value=item.getValue();
		 	
		 			//Object Model to create comment 
		 			CommentModel commentDto=new CommentModel();	
		 			//CreateComment
		 			commentDto.itemOid=itemOid;
		 			commentDto.eventOid=itemDetail.eventOid;
		 			commentDto.subjectKey=itemDetail.subjectKey;
		 		 	commentDto.dataSet=dataset;
		 		 	//Comment and Label
		 	    	String comment=	StringCustomHelper.Comment(commentDto);
		 		    String label=StringCustomHelper.label(itemOid);
		 		    
		 		 
		 		   OntClass  testClass=model.createClass("");
		 		  
		 		   Resource r= model.createResource(uri+itemOid,OWL.Class);
		 		  
		 		  
		 		    r.addProperty(Lcdc.itemOid,itemOid)
		 	    	.addProperty(Lcdc.itemGroupOID,itemGroupOid)
		 	    	.addProperty(Lcdc.formOID,formOid)
		 	    	.addProperty(Lcdc.itemGroupRepeatKey,itemRepeatKey)
		 	    	.addProperty(Lcdc.measurementUnitOID, messurmentUnit)
		 	    	.addProperty(Lcdc.value, value)
		 	    	.addProperty(RDFS.label, label)
		 	    	.addProperty(RDFS.comment, comment);
		 		    
//		 		
//		 			 model.createResource(uri+itemOid)
//		 			.addProperty(Lcdc.itemOid,itemOid)
//		 	    	.addProperty(Lcdc.itemGroupOID,itemGroupOid)
//		 	    	.addProperty(Lcdc.formOID,formOid)
//		 	    	.addProperty(Lcdc.itemGroupRepeatKey,itemRepeatKey)
//		 	    	.addProperty(Lcdc.measurementUnitOID, messurmentUnit)
//		 	    	.addProperty(Lcdc.value, value)
//		 	    	.addProperty(RDFS.label, label)
//		 	    	.addProperty(RDFS.comment, comment);
		 	   
		 	}//For loop item
		 }//for loop itemDto 
	 }
	  

  public OntModel ontoModelTest(){
	  
		//Model
  	OntModel model=ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);

  	JaxBinder myJax=new JaxBinder();

  	ODMcomplexTypeDefinitionClinicalData clinicalData=
  			myJax.getClinicalData("src/main/java/odm1.3_clinical_ext_Full_study_extract_2015-05-22-162457368.xml");
  	ArrayList<ItemDetail> itemDtos=myJax.makeItemsObjects(clinicalData);

  	completeRdf(itemDtos,model);
      
  	return model;
	  
  }
  

}
