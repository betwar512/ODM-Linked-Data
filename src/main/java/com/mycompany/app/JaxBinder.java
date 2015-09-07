package com.mycompany.app;
import java.io.File;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.*;
import org.cdisc.ns.odm.v1.*;



/**
 * @author Abbas.h.Safaie
 * 
 * */

public class JaxBinder {

	

	
	 //================================================================================
	// MetaData 
   //================================================================================
	
	/*
	 * capture all the codeLists in metaData 
	 * */
	public static HashMap<String,ODMcomplexTypeDefinitionCodeList> catchCodeList(ODMcomplexTypeDefinitionMetaDataVersion metaData){
	
		List<ODMcomplexTypeDefinitionCodeList> codeLists=metaData.getCodeList();
		HashMap<String,ODMcomplexTypeDefinitionCodeList> hashMap=new HashMap<String ,ODMcomplexTypeDefinitionCodeList>();
		for(ODMcomplexTypeDefinitionCodeList codeList:codeLists){
			
			String codeListOid=codeList.getOID();
			hashMap.put(codeListOid, codeList);
		}
		
		return hashMap;
		
	}
	
	
	/*
	 * get hashMap for ItemGroupDef 
	 * */
	public static HashMap<String,ODMcomplexTypeDefinitionItemGroupDef> getItemGroupDef(ODMcomplexTypeDefinitionMetaDataVersion metaData){
		
		HashMap<String,ODMcomplexTypeDefinitionItemGroupDef> itemMap=new HashMap<String,ODMcomplexTypeDefinitionItemGroupDef>();
			List<ODMcomplexTypeDefinitionItemGroupDef> itemGroupDefs=metaData.getItemGroupDef();
			for(ODMcomplexTypeDefinitionItemGroupDef itemGroupDef:itemGroupDefs){	
				String groupId=itemGroupDef.getOID();
				itemMap.put(groupId, itemGroupDef);
			}
		return itemMap;	
	}
	
	//itemDefHash map with keys 
	  /*
	   * Using Map to seperate Keys and find the value later by using java HashMap method get(Key) later on 
	   * */
	public static HashMap<String,ODMcomplexTypeDefinitionItemDef> catchItemDef(ODMcomplexTypeDefinitionMetaDataVersion metaData){
		
		HashMap<String,ODMcomplexTypeDefinitionItemDef> itemMap=new HashMap<String,ODMcomplexTypeDefinitionItemDef>();
		List<ODMcomplexTypeDefinitionItemDef> itemDef=metaData.getItemDef();
	
	for(ODMcomplexTypeDefinitionItemDef item:itemDef){
		
		String id=item.getOID();
		
		itemMap.put(id, item);	
	}

		return itemMap;
		
	}
	
	
	//returning metaData belong to study
	//Static Method 
	public static ODMcomplexTypeDefinitionMetaDataVersion catchMetaData(String inputFile){
		
		ODMcomplexTypeDefinitionMetaDataVersion metaData = null;
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
		   
			metaData=study.get(0).getMetaDataVersion().get(0);
			
		    }catch(JAXBException e){
		    	e.printStackTrace();
		    
		   
		    	}

		return metaData;
	}
	
	
	   //================================================================================
    // ClinicaData 
    //================================================================================

	

	/*
	 *  Get for Object ClinicalData
	 * */
	public  ODMcomplexTypeDefinitionClinicalData getClinicalData(String inputFile){
		
		
		
		if(inputFile.length()==0){
			
			  throw new EmptyStackException();
		} else {
			
			try{
			
				org.cdisc.ns.odm.v1.ObjectFactory factory=new org.cdisc.ns.odm.v1.ObjectFactory();
			//	org.openclinica.ns.odm_ext_v130.v3.ObjectFactory factory2=new org.openclinica.ns.odm_ext_v130.v3.ObjectFactory();
				
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
		 * List Customer Object ItemDetail Class
		 * input ClinicaData 
		 * */
	public ArrayList<ItemDetail> makeItemsObjects(ODMcomplexTypeDefinitionClinicalData clinicalData,ODMcomplexTypeDefinitionMetaDataVersion metaData){
	
	//	HashMap<String,ArrayList<ItemDetail>> itemDetailList=new HashMap<String,ArrayList<ItemDetail>>();
		
		HashMap<String,ODMcomplexTypeDefinitionItemGroupDef> itemGroupDefs=getItemGroupDef(metaData);	
		
		ArrayList<ItemDetail> itemDtos=new ArrayList<ItemDetail>();
	//	ArrayList<ItemDetail> itemsDtoBlood=new ArrayList<ItemDetail>();
	//	ArrayList<ItemDetail> itemsDtoMedication=new ArrayList<ItemDetail>();
		//subjectData 
	List<ODMcomplexTypeDefinitionSubjectData> subjectData=clinicalData.getSubjectData();
	//make GroupItemOut 
	for(Iterator<ODMcomplexTypeDefinitionSubjectData> i = subjectData.iterator(); i.hasNext();){	
		ODMcomplexTypeDefinitionSubjectData subject=i.next();
		//subjectKey	
	String subjectkey=subject.getSubjectKey();
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
	  		//forLoop form
	  for(Iterator<ODMcomplexTypeDefinitionFormData> k=formData.iterator();k.hasNext();){ 
		  ODMcomplexTypeDefinitionFormData form=k.next();   
		  //FormOID Captures here 
		  String formOidCapture= form.getFormOID();
		  List<ODMcomplexTypeDefinitionItemGroupData> itemGroupData=form.getItemGroupData();
		 //forLoop itemGroup 
		  for(Iterator<ODMcomplexTypeDefinitionItemGroupData> g=itemGroupData.iterator();g.hasNext();){
			  //ItemGroup
			  ODMcomplexTypeDefinitionItemGroupData itemGroup= g.next();
			  ODMcomplexTypeDefinitionItemGroupDef itemGroupDef=itemGroupDefs.get(itemGroup.getItemGroupOID());
			  //Repeating
			  YesOrNo repeatYesorNo=itemGroupDef.getRepeating();
			  Boolean repeat=false;
			  if(repeatYesorNo.equals(YesOrNo.YES))
				  repeat=true;
			 
			  //DTO Object
			  ItemDetail itemDto=new ItemDetail();
			  //Set properties
			  itemDto.subjectKey=subjectkey;
			  itemDto.eventOid=eventOID;
			  itemDto.formOid=formOidCapture;
			  itemDto.itemGroupOid=itemGroup.getItemGroupOID();
			  itemDto.itemRepeatKey=itemGroup.getItemGroupRepeatKey();
			  itemDto.items=itemGroup.getItemDataGroup();
			  itemDto.repeating=repeat;
			  
			  itemDtos.add(itemDto);
			  	//ItemsDto ArrayList 
//			  if(StringCustomHelper.vitalSeparate(itemDto.itemGroupOid)){
//				  itemsDtoVital.add(itemDto);
//			  }else if(StringCustomHelper.bloodSeparate(itemDto.itemGroupOid)){
//				  itemsDtoBlood.add(itemDto);
//			  }else if(StringCustomHelper.medicinSeparate(itemDto.itemGroupOid)){
//				  itemsDtoMedication.add(itemDto);
//			  }

		  }//ItemGroupData
	  }//FormData
	 }
	}	
	//itemDetailList.put("vital", itemsDtoVital);
	//itemDetailList.put("blood", itemsDtoBlood);
//	itemDetailList.put("medic", itemsDtoMedication);
	
	
	return itemDtos;
 }

//End of Class 

}







