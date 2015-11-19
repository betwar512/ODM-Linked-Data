package au.csiro.aehrc.jenaModels;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemDef;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionMetaDataVersion;
import au.csiro.aehrc.CsvHelper;
import au.csiro.aehrc.UriCustomHelper;
import au.csiro.aehrc.app.lcdc.LcdcCore;
import au.csiro.aehrc.app.lcdc.Qb;
import au.csiro.aehrc.app.lcdc.crossSection;
import au.csiro.aehrc.app.lcdc.domainSlice;
import au.csiro.aehrc.app.lcdc.timeSeries;
import au.csiro.aehrc.utils.ItemDetail;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.OWL2;

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
	 * 
	 * */
	public static void sliceBySubject(ModelMaker mm,ArrayList<ItemDetail> itemDtos
			,HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDefs
			,ODMcomplexTypeDefinitionMetaDataVersion meta,CsvHelper csvHlp){
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
							String theme = csvHlp.getTheme(itemOidName);
							
							String obsPhase=UriCustomHelper.generateObservationUri(theme, itemDto.eventOid, itemDto.subjectKey);
									//obsBase+theme+"/phase/"+ itemDto.eventOid+"/subject/"+itemDto.subjectKey;  
								//Adding Key
							  if(itemDto.repeating)
							    	obsPhase=UriCustomHelper.addKey(obsPhase, itemDto.itemRepeatKey);//"/key/"+itemDto.itemRepeatKey;
							  
							  model.setNsPrefix("crossSection", crossSection.getURI());
							Resource r=model.createResource(UriCustomHelper.csSliceUri(subject),crossSection.SubjectSection);			
							Property p=model.createProperty(obsPhase);	
							r.addProperty(Qb.observation, p);
							
							//Resource for Subject 							
							Resource subjectResource=model.createResource(UriCustomHelper.generateSubject(subject),OWL2.NamedIndividual);
						    Literal resourceLit=model.createTypedLiteral(subject,LcdcCore.subjectcode.getURI());
							subjectResource.addProperty(LcdcCore.subjectKey,resourceLit);					
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
			,ODMcomplexTypeDefinitionMetaDataVersion meta,CsvHelper csvHlp){
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
							String theme = csvHlp.getTheme(itemOidName);
							
									//StringCustomHelper.groupType(itemOidName).toLowerCase();
							String obsPhase=UriCustomHelper.generateObservationUri(theme, itemDto.eventOid, itemDto.subjectKey);
							//Adding Key	
						    if(itemDto.repeating)
						    	//obsPhase+="/key/"+itemDto.itemRepeatKey;
						    obsPhase=UriCustomHelper.addKey(obsPhase, itemDto.itemRepeatKey);

						    model.setNsPrefix("domainSlice", domainSlice.getURI());
							Resource r=model.createResource(UriCustomHelper.dsSliceUri(theme),domainSlice.ThemeSlice);			
							 Property p=model.createProperty(obsPhase);
							 
							 
							r.addProperty(Qb.observation, p);
													
							
							//Add them Resource NameINdevidual 
							Resource themeResource=model.createResource(UriCustomHelper.generateTheme(theme),OWL2.NamedIndividual);
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
			,ODMcomplexTypeDefinitionMetaDataVersion meta,CsvHelper csvHlp){
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
							String theme = csvHlp.getTheme(itemOidName);
							
							//Observation Uri
							String obsPhase=UriCustomHelper.generateObservationUri(theme, itemDto.eventOid, itemDto.subjectKey); 
							//Adding Key
						  if(itemDto.repeating)
						    	obsPhase=UriCustomHelper.addKey(obsPhase, itemDto.itemRepeatKey);
						  
						  model.setNsPrefix("timeSeries", timeSeries.getURI());
							Resource r=model.createResource(UriCustomHelper.tcSliceUri(itemDto.eventOid),timeSeries.PhaseSeries);			
							Property p=model.createProperty(obsPhase);	
							r.addProperty(Qb.observation, p);

							//Add Phase Resource NameINdevidual 
							Resource phaseResource=model.createResource(UriCustomHelper.generatePhase(phase),OWL2.NamedIndividual);
						    Literal resourceLit=model.createTypedLiteral(phase,LcdcCore.phasecode.getURI());
						    phaseResource.addProperty(LcdcCore.phaseKey,resourceLit);		
							//Add theme to slice 
							r.addProperty(LcdcCore.phase, phaseResource);
			
			   }	  
			 }	  
		  }
	    }
	
	
	  
	 }
	


