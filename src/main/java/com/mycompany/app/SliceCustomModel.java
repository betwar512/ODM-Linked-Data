package com.mycompany.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemDef;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionMetaDataVersion;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.mycompany.app.lcdc.LcdcCore;
import com.mycompany.app.lcdc.Obs;
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
	 * Generate Slice Model from qb by Theme and Subject  
	 * */
	public static void sliceThemePhase(ModelMaker mm,ArrayList<ItemDetail> itemDtos,HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDefs,ODMcomplexTypeDefinitionMetaDataVersion meta){
		  Model model=null;
		  final String baseObs="http://aehrc-ci.it.csiro.au/dataset/cardio/lcdc/20150713/theme/";
		  final String baseUri="http://aehrc-ci.it.csiro.au/dataset/cardio/lcdc/20150713/slice/theme/";
		  
		  
		  
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
							
							  String obsPhase=baseObs+theme+"/phase/"+ itemDto.eventOid+"/subject/"+itemDto.subjectKey;  
							model=mm.createModel("Slice-"+theme);			
							Resource r=model.createResource(baseUri+theme+"/subject/"+subject,Qb.Slice);			
							Property p=model.createProperty(obsPhase);	
							r.addProperty(Qb.observation, p);

			   }	  
			 }
			  
		  }

	    }
	  
	 }
	


