package com.mycompany.app;
import java.io.File;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.*;
import org.cdisc.ns.odm.v1.*;



/**
 * @author Abbas.h.Safaie
 * 
 * */

public class JaxBinder {

	

	
	
	
	
	//itemDef
	public List<ODMcomplexTypeDefinitionItemDef> catchItemDef(ODMcomplexTypeDefinitionMetaDataVersion metaData){
		
		
	List<ODMcomplexTypeDefinitionItemDef> itemDef=metaData.getItemDef();
		
	
		return itemDef;
		
	}
	
	/*
	public void catchItem(List<ODMcomplexTypeDefinitionItemDef> itemDef){
		
	for(ODMcomplexTypeDefinitionItemDef item : itemDef){
	
	
		
	     }

	}
	*/
	
	
	
	

	//returning metaData belong to study
	public ODMcomplexTypeDefinitionMetaDataVersion catchMetaData(List<ODMcomplexTypeDefinitionStudy> study){
		
		
		ODMcomplexTypeDefinitionMetaDataVersion metaData=study.get(0).getMetaDataVersion().get(0);
	
		return metaData;
	}
	
	
	
	//return Study belong to XML file 
	public List<ODMcomplexTypeDefinitionStudy> catchStudy(String inputFile){
		
		if(inputFile.length()==0){
			throw new EmptyStackException();
		}
		
		try{
			
			org.cdisc.ns.odm.v1.ObjectFactory factory=new org.cdisc.ns.odm.v1.ObjectFactory();
			
			JAXBContext myJaxb=JAXBContext.newInstance
					(factory.getClass());
		    Unmarshaller u = 
		            myJaxb.createUnmarshaller();
		    org.cdisc.ns.odm.v1.ODM o=(ODM) u.unmarshal(new File(inputFile));
		    System.out.println("o created");
		   List<ODMcomplexTypeDefinitionStudy> study=o.getStudy();	
		   study.get(0); 
		    return study;	
		    }catch(JAXBException e){
		    	e.printStackTrace();
		    
		   
		    	}
		
		return null;
	}
	     	
		
		
	

	/*
	 *  Get for Object ClinicalData
	 * */
	public  ODMcomplexTypeDefinitionClinicalData getClinicalData(String inputFile){
		
		
		
		if(inputFile.length()==0){
			
			  throw new EmptyStackException();
		} else {
			
			try{
			
				org.cdisc.ns.odm.v1.ObjectFactory factory=new org.cdisc.ns.odm.v1.ObjectFactory();
				
				JAXBContext myJaxb=JAXBContext.newInstance
						(factory.getClass());
			    Unmarshaller u = 
			            myJaxb.createUnmarshaller();
			    org.cdisc.ns.odm.v1.ODM o=(ODM) u.unmarshal(new File(inputFile));
			    System.out.println("o created");
			   List<ODMcomplexTypeDefinitionClinicalData> clinicalData=o.getClinicalData();
			   
				return 	clinicalData.get(0);
				
	           }catch(JAXBException e){
	        	   
	           
	        	   e.printStackTrace();   
	         }
		}
	return null;
	}
	
	
	
	/* 
	 * Forms as an Array of Arrays
	 * return Map <String , forms>
	 * */
	
	public  Map<String,List<ODMcomplexTypeDefinitionFormData>> getForms(ODMcomplexTypeDefinitionClinicalData clinicalData){
		
		
		
		String phase="Phase/";
		
		
		Map<String,List<ODMcomplexTypeDefinitionFormData>>  formsDataKey = new HashMap<String,List<ODMcomplexTypeDefinitionFormData>>();
		List<ODMcomplexTypeDefinitionSubjectData> subjectData=clinicalData.getSubjectData();
		for(Iterator<ODMcomplexTypeDefinitionSubjectData> i = subjectData.iterator(); i.hasNext();){
			
			ODMcomplexTypeDefinitionSubjectData subject=i.next();
	
			//subjectKey
			
		String subjectkey=	subject.getSubjectKey();
			
			
			//Subject study event Data
		List<ODMcomplexTypeDefinitionStudyEventData> data=	subject.getStudyEventData();
		
			//get forms 
		for(Iterator<ODMcomplexTypeDefinitionStudyEventData> j=data.iterator();j.hasNext();){
			
			//EvenData 
		ODMcomplexTypeDefinitionStudyEventData studyEventData= j.next();
			//List of forms 
		
		  List<ODMcomplexTypeDefinitionFormData> formData=  studyEventData.getFormData();
		  //Key for eventOID
		  String eventOID=studyEventData.getStudyEventOID();
		  //MapKey 
		String  customeKey= subjectkey + "/" + phase + eventOID;

		  formsDataKey.put(customeKey,formData);
		  
		  }
		}
		
		return formsDataKey;
	}
	
	
	/*
	 * getItemGroup data
	 * 
	 *  return: Map<Key,ItemGroups>
	 *  
	 *  */
	public Map<String, ODMcomplexTypeDefinitionItemGroupData> getItemGroupData( Map<String,List<ODMcomplexTypeDefinitionFormData>>  formsDataKey){
		
		String formString="form/"; //String to create Uri 
		
		
		Map<String,ODMcomplexTypeDefinitionItemGroupData> itemGroupsReturn = new HashMap<String,ODMcomplexTypeDefinitionItemGroupData>();
	  //get itemGroupData <List of ItemsGroups>
	  for(Iterator<Entry<String, List<ODMcomplexTypeDefinitionFormData>>> k=formsDataKey.entrySet().iterator();k.hasNext();){
		
		//form
		  Entry<String, List<ODMcomplexTypeDefinitionFormData>> form=k.next();
		String key=form.getKey(); 
		  for(Iterator<ODMcomplexTypeDefinitionFormData> f=form.getValue().iterator();f.hasNext();){
		  //GroupItemData List
		 ODMcomplexTypeDefinitionFormData fo=f.next();
		     List<ODMcomplexTypeDefinitionItemGroupData> itemGroupData=fo.getItemGroupData();
		     //FormOId key 
		    String formOid= fo.getFormOID();
		    
		    String mapKey= key+"/"+ formString +formOid;
		    
		    
		     //get itemGroup
		  for(Iterator<ODMcomplexTypeDefinitionItemGroupData> g=itemGroupData.iterator();g.hasNext();){
			  //ItemGroup
			  ODMcomplexTypeDefinitionItemGroupData itemGroup= g.next();
			  
			  	
			  /*Adding to list and return it */
			  itemGroupsReturn.put(mapKey,itemGroup);
			   					
		  	}//ItemGroup itemDataGroup
		  }//GroupItemData
	  	}//form
	  
	  return itemGroupsReturn;
	}	
	
	
	
	
	/*get Items belong to the ItemGroup
	 * 
	 * return type List<ODMcomplexTypeDefinitionItemData>=Items
	 *  
	 *  */
public Map<String,ODMcomplexTypeDefinitionItemData> getItemsList(Map<String,ODMcomplexTypeDefinitionItemGroupData> groupData){
	
	
	String itemGroupString="itemGroup/";
	Map<String,ODMcomplexTypeDefinitionItemData> itemData=new HashMap<String,ODMcomplexTypeDefinitionItemData>();
	for(Iterator<Entry<String, ODMcomplexTypeDefinitionItemGroupData>> g=groupData.entrySet().iterator();g.hasNext();){
		Entry<String, ODMcomplexTypeDefinitionItemGroupData> itemMap=g.next();
		
		String key=itemMap.getKey();
		
		String itemGroupId=itemMap.getValue().getItemGroupOID();
		
		
		String mapKey=key + "/" + itemGroupString + itemGroupId;
		for(Iterator<ODMcomplexTypeDefinitionItemData> it=itemMap.getValue().getItemDataGroup().iterator();it.hasNext();)
		{
			 
			ODMcomplexTypeDefinitionItemData items= it.next();	
			itemData.put(mapKey, items);
			  		  
		}
	}
	
	return itemData;
	
 }
	
}


