package au.csiro.aehrc;
import java.io.File;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.*;
import org.cdisc.ns.odm.v1.*;

import au.csiro.aehrc.utils.ItemDetail;



/**
 * @author Abbas.h.Safaie
 * 
 * */

public class JaxBinder {

	
	 private org.cdisc.ns.odm.v1.ODM odm;
	
	 
	 
	 
		public JaxBinder(String inputFile){
			
			if(inputFile.length()==0){
				throw new EmptyStackException();
			}
			
			try{
				org.cdisc.ns.odm.v1.ObjectFactory factory=new org.cdisc.ns.odm.v1.ObjectFactory();
				
				JAXBContext myJaxb=JAXBContext.newInstance
						(factory.getClass());
			    Unmarshaller u = 
			            myJaxb.createUnmarshaller();
			  odm=(ODM) u.unmarshal(new File(inputFile));
			    System.out.println("odm created");
			    }catch(JAXBException e){
			    	e.printStackTrace();
			    	}
		}
		
	 
	 
	 //================================================================================
	// MetaData 
   //================================================================================
	
	/*
	 * capture all the codeLists in metaData 
	 * */
	public static HashMap<String,ODMcomplexTypeDefinitionCodeList> 
	catchCodeList(ODMcomplexTypeDefinitionMetaDataVersion metaData){
	
		List<ODMcomplexTypeDefinitionCodeList> codeLists=metaData.getCodeList(); //capture codeList
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
	public static HashMap<String,ODMcomplexTypeDefinitionItemGroupDef> 
	getItemGroupDef(ODMcomplexTypeDefinitionMetaDataVersion metaData){
		
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
	   * Using Map to separate Keys and find the value later by using java HashMap method get(Key) 
	   * 
	   *
	   * */
	public static HashMap<String,ODMcomplexTypeDefinitionItemDef> 
	catchItemDef(ODMcomplexTypeDefinitionMetaDataVersion metaData){
		
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
	public  ODMcomplexTypeDefinitionMetaDataVersion 
	catchMetaData( ){
		
		ODMcomplexTypeDefinitionMetaDataVersion metaData = odm.getStudy().get(0).getMetaDataVersion().get(0);
		
		return metaData;
	}
	
	
	   //================================================================================
    // ClinicaData 
    //================================================================================

	

	/*
	 * Input: ODM xml file
	 * Capturing clinicalData from ODM
	 * return: ClinicalData type: ODMcomplexTypeDefinitionClinicalData

	 * */
	public  ODMcomplexTypeDefinitionClinicalData getClinicalData(){
		
	ODMcomplexTypeDefinitionClinicalData clinical=odm.getClinicalData().get(0);

	return clinical;
	}
	
	
	
		/*
		 * return : ArrayList Custom Objects Type:ItemDetail
		 * input ClinicaData 
		 * 
		 * */
	
	public ArrayList<ItemDetail> 
	makeItemsObjects(ODMcomplexTypeDefinitionClinicalData clinicalData
			,ODMcomplexTypeDefinitionMetaDataVersion metaData){
	
	
		
		HashMap<String,ODMcomplexTypeDefinitionItemGroupDef> itemGroupDefs=getItemGroupDef(metaData);	
		
		ArrayList<ItemDetail> itemDtos=new ArrayList<ItemDetail>();

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
			 
			  //Data Object
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


		  }//ItemGroupData
	    }//FormData
	  }
	}	

	return itemDtos;
 }

//End of Class 

}







