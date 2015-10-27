package com.mycompany.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemDef;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionMetaDataVersion;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.OWL2;
import com.mycompany.app.lcdc.LcdcCore;
import com.mycompany.app.lcdc.Qb;
import com.mycompany.app.lcdc.crossSection;
import com.mycompany.app.lcdc.domainSlice;
import com.mycompany.app.lcdc.timeSeries;

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
							String theme=StringCustomHelper.groupType(itemOidName).toLowerCase();
							String obsPhase=UriCustomHelper.generateObservationUri(theme, itemDto.eventOid, itemDto.subjectKey);
									//obsBase+theme+"/phase/"+ itemDto.eventOid+"/subject/"+itemDto.subjectKey;  
								//Adding Key
							  if(itemDto.repeating)
							    	obsPhase+="/key/"+itemDto.itemRepeatKey;
							  model.setNsPrefix("crossSection", crossSection.getURI());
							Resource r=model.createResource(UriCustomHelper.sliceBase+"cs-slice"+"/subject/"+subject,crossSection.SubjectSection);			
							Property p=model.createProperty(obsPhase);	
							r.addProperty(Qb.observation, p);
							
							//Resource for Subject 
												
							Resource subjectResource=model.createResource(UriCustomHelper.purl+"subject/"+subject,OWL2.NamedIndividual);
						    Literal resourceLit=model.createTypedLiteral(subject,LcdcCore.subjectcode.getURI());
							subjectResource.addProperty(LcdcCore.themeId,resourceLit);
								
							//Add subject to slice 
							r.addProperty(LcdcCore.subject, subjectResource);
							
			   }	  
			 }	  
		  }

	    }
	
	
	
	/*
	 * Slice
	 * Generate Slice by Theme
	 *  
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
							String theme=StringCustomHelper.groupType(itemOidName).toLowerCase();
							String obsPhase=UriCustomHelper.generateObservationUri(theme, itemDto.eventOid, itemDto.subjectKey);
							//Adding Key	
						    if(itemDto.repeating)
						    	obsPhase+="/key/"+itemDto.itemRepeatKey;

						    model.setNsPrefix("domainSlice", domainSlice.getURI());
							Resource r=model.createResource(UriCustomHelper.sliceBase+"ds-slice"+"/theme/"+theme,domainSlice.ThemeSlice);			
							 Property p=model.createProperty(obsPhase);
							 
							 
							r.addProperty(Qb.observation, p);
							
							
							
							//Add them Resource NameINdevidual 
							Resource themeResource=model.createResource(UriCustomHelper.purl+"theme/"+theme,OWL2.NamedIndividual);
						    Literal resourceLit=model.createTypedLiteral(theme,LcdcCore.themecode.getURI());
							themeResource.addProperty(LcdcCore.themeKey,resourceLit);
								
							//Add theme to slice 
							r.addProperty(LcdcCore.theme, themeResource);
			   }	  
			 }	  
		  }

	    }
	
	/*
	 * Slice
	 * Generate Slice by Theme 
	 * 
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
				  		
				  String phase=itemDto.eventOid;
				
						List<ODMcomplexTypeDefinitionItemData> itemList=itemDto.items;
						for (ODMcomplexTypeDefinitionItemData item : itemList) {  
							String itemOidName=item.getItemOID();
						
							//get Theme
							String theme=StringCustomHelper.groupType(itemOidName).toLowerCase();
							//Observation Uri
							String obsPhase=UriCustomHelper.generateObservationUri(theme, itemDto.eventOid, itemDto.subjectKey); 
							//Adding Key
						  if(itemDto.repeating)
						    	obsPhase+="/key/"+itemDto.itemRepeatKey;
						  
						  model.setNsPrefix("timeSeries", timeSeries.getURI());
							Resource r=model.createResource(UriCustomHelper.sliceBase+"tc-slice"+"/phase/"+itemDto.eventOid,timeSeries.PhaseSeries);			
							Property p=model.createProperty(obsPhase);	
							r.addProperty(Qb.observation, p);

							//Add Phase Resource NameINdevidual 
							Resource phaseResource=model.createResource(UriCustomHelper.purl+"phase/"+phase,OWL2.NamedIndividual);
						    Literal resourceLit=model.createTypedLiteral(phase,LcdcCore.phasecode.getURI());
						    phaseResource.addProperty(LcdcCore.phaseKey,resourceLit);		
							//Add theme to slice 
							r.addProperty(LcdcCore.phase, phaseResource);
			
			   }	  
			 }	  
		  }
	    }
	
	
	  
	 }
	


