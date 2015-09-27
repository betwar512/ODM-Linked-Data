package com.mycompany.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemDef;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionMetaDataVersion;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.mycompany.app.lcdc.Qb;

/**
 * @author Abbas.h.Safaie
 * 
 * Handling adding slice resources to model 
 * 
 * */
public class SliceCustomModel {

	  
	  
	//Grouping by SubjectKey
	  public static HashMap<String, List<ItemDetail>> groupBySubjectKey(ArrayList<ItemDetail> itemDtos){
		    
		  HashMap<String, List<ItemDetail>> hashMap=new HashMap<String, List<ItemDetail>>(); 
		  for(ItemDetail itemDto:itemDtos){ 
			  if(!hashMap.containsKey(itemDto.subjectKey)){
		          List<ItemDetail> list= new ArrayList<ItemDetail>();
		          list.add(itemDto);
		      hashMap.put(itemDto.subjectKey,list);
		      }
		      else
		          hashMap.get(itemDto.subjectKey).add(itemDto);  
		    }	  
		  return hashMap;
		  }

	
	
	/*
	 * Generate Slice Model from qb  Subject  
	 * */
	public static void sliceBySubject(ModelMaker mm,ArrayList<ItemDetail> itemDtos
			,HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDefs
			,ODMcomplexTypeDefinitionMetaDataVersion meta){
		  Model model=mm.createModel("Slice-Subject");	
		
		  HashMap<String, List<ItemDetail>> groupBySubject=groupBySubjectKey(itemDtos);
	
		  	Set<String> key=groupBySubject.keySet();
		  
		  for (String subject:key){
			  
			  List<ItemDetail> listPhases=groupBySubject.get(subject);
			  
			  
			  
			  for (ItemDetail itemDto : listPhases) { 
	
					//  String definedBy=UriCustomHelper.rdfDefinition(itemDto.itemGroupOid);
						List<ODMcomplexTypeDefinitionItemData> itemList=itemDto.items;
						for (ODMcomplexTypeDefinitionItemData item : itemList) {  
							String itemOidName=item.getItemOID();
						
							//Create observation uri 
							String theme=StringCustomHelper.groupType(itemOidName);
							
							  String obsPhase=UriCustomHelper.obsBase+theme+"/phase/"+ itemDto.eventOid+"/subject/"+itemDto.subjectKey;  
									
							Resource r=model.createResource(UriCustomHelper.sliceBase+"cs-slice"+"/subject/"+subject,Qb.Slice);			
							Property p=model.createProperty(obsPhase);	
							r.addProperty(Qb.observation, p);
			   }	  
			 }	  
		  }

	    }
	
	
	
	/*
	 * Slice
	 * Generate Slice by Theme 
	 * */
	
	
	public static void sliceByTheme(ModelMaker mm,ArrayList<ItemDetail> itemDtos
			,HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDefs
			,ODMcomplexTypeDefinitionMetaDataVersion meta){
		  Model model=mm.createModel("Slice-Theme");	
		
		  
		  HashMap<String, List<ItemDetail>> groupBySubject=groupBySubjectKey(itemDtos);
	
		  	Set<String> key=groupBySubject.keySet();
		  
		  for (String subject:key){
			  
			  List<ItemDetail> listPhases=groupBySubject.get(subject);
			  
			  
			  
			  for (ItemDetail itemDto : listPhases) { 
	
					//  String definedBy=UriCustomHelper.rdfDefinition(itemDto.itemGroupOid);
						List<ODMcomplexTypeDefinitionItemData> itemList=itemDto.items;
						for (ODMcomplexTypeDefinitionItemData item : itemList) {  
							String itemOidName=item.getItemOID();
						
							//get Theme
							String theme=StringCustomHelper.groupType(itemOidName);
							
						  String obsPhase=UriCustomHelper.obsBase+theme+"/phase/"+ itemDto.eventOid+"/subject/"+itemDto.subjectKey;  
									
							Resource r=model.createResource(UriCustomHelper.sliceBase+"ds-slice"+"/theme/"+theme,Qb.Slice);			
							Property p=model.createProperty(obsPhase);	
							r.addProperty(Qb.observation, p);
			   }	  
			 }	  
		  }

	    }
	
	/*
	 * Slice
	 * Generate Slice by Theme 
	 * */
	
	
	public static void sliceByPhase(ModelMaker mm,ArrayList<ItemDetail> itemDtos
			,HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDefs
			,ODMcomplexTypeDefinitionMetaDataVersion meta){
		  Model model=mm.createModel("Slice-Phase");	
	
		  HashMap<String, List<ItemDetail>> groupBySubject=groupBySubjectKey(itemDtos);
	
		  	Set<String> key=groupBySubject.keySet();
		  
		  for (String subject:key){
			  
			  List<ItemDetail> listPhases=groupBySubject.get(subject);
			  
			  
			  
			  for (ItemDetail itemDto : listPhases) { 
	
					//  String definedBy=UriCustomHelper.rdfDefinition(itemDto.itemGroupOid);
						List<ODMcomplexTypeDefinitionItemData> itemList=itemDto.items;
						for (ODMcomplexTypeDefinitionItemData item : itemList) {  
							String itemOidName=item.getItemOID();
						
							//get Theme
							String theme=StringCustomHelper.groupType(itemOidName);
							
						  String obsPhase=UriCustomHelper.obsBase+theme+"/phase/"+ itemDto.eventOid+"/subject/"+itemDto.subjectKey;  
									
							Resource r=model.createResource(UriCustomHelper.sliceBase+"tc-slice"+"/phase/"+itemDto.eventOid,Qb.Slice);			
							Property p=model.createProperty(obsPhase);	
							r.addProperty(Qb.observation, p);
			   }	  
			 }	  
		  }

	    }
	
	
	  
	 }
	


