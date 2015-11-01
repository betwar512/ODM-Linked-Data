package com.mycompany.app;
/**
 * @author Abbas H Safaie
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.mycompany.app.lcdc.Snomed;

public class ObservationCustomModel {


	 
	  /*
	   * Observation model 
	   * version 2.1.0
	   * Input (ItemDetails,itemDefinition(metaOdm),metaData)
	   * returnType Void 
	   * Snomed added 
	   * Subject changed to Resource 
	   * */
	  public static void generateObservation
	  (ModelMaker mm,ArrayList<ItemDetail> itemDtos 
			  ,HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDefs
			  ,ODMcomplexTypeDefinitionMetaDataVersion meta){
		  Model model=null;
	
		  
		  for (ItemDetail itemDto : itemDtos) { 
			  	//ItemsDto typeData 
			  String theme="";
			 // String obsUri=""; 	
			  String repeatKey=itemDto.itemRepeatKey;
		
			List<ODMcomplexTypeDefinitionItemData> itemList=itemDto.items;
			
			for (ODMcomplexTypeDefinitionItemData item : itemList) {  
				
				
		  String itemOidName=item.getItemOID();
				 theme=StringCustomHelper.groupType(itemOidName).toLowerCase();
				// model name 
				 String modelName=StringCustomHelper.modelName(StringCustomHelper.getObservationmodel(), theme);
				 model=mm.createModel(modelName);
				 //add snomed uri to model medication
				 model.setNsPrefix("snomed", Snomed.snomedUri);
				 
				 Property themeP=model.createProperty(UriCustomHelper.themeBase, theme);
				  
				ODMcomplexTypeDefinitionItemDef itemDef=itemDefs.get(itemOidName);
				String phase="";
				String subject="";
				//Create observation uri 
				Resource obs=null;
				
				String obsPhase=UriCustomHelper.generateObservationUri(theme, itemDto.eventOid, itemDto.subjectKey);
			    //String obsPhase=obsUri+ itemDto.eventOid+"/subject/"+itemDto.subjectKey; 
			    
			    //Add groupKey if exist 
			    if(itemDto.repeating)
			    	obsPhase+="/key/"+repeatKey;
					

			    //String Uri phase subject 
			    
			    
			    	phase=UriCustomHelper.generatePhase(itemDto.eventOid);
			    	subject= UriCustomHelper.generateSubject(itemDto.subjectKey);
			    	
			  // resources Subject Phase	
			    	
					Resource subjectResource=model.createResource(subject);
					Resource phaseResource=model.createResource(phase);
					
				 obs=model.createResource(obsPhase,Obs.OBSERVATION);//Observation resource
				 
				 
				 
				 obs.addProperty(LcdcCore.phase, phaseResource)
				 	   .addProperty(LcdcCore.subject, subjectResource)
				 	   		.addProperty(LcdcCore.theme, themeP);
				 
				//Obs value
				Literal value=null;

				String cardioUri=UriCustomHelper.generateCardioUri(theme);

				Property pr=model.createProperty(cardioUri,itemDef.getName());
				
				if(itemDef.getName().equals("MedicationCode")){ //MedicationCode 
					
					Property snomedP=model.createProperty(Snomed.snomedUri, item.getValue());
					
					//create statement with snomed
					Statement medicationSnomed=model.createStatement(obs, pr,snomedP);
					model.add(medicationSnomed);
			
				}else{//not medicationCode 
				
				
				if(itemDef.getCodeListRef() == null){ //Check if codeList reference exist 
				
				if(!itemDef.getRangeCheck().isEmpty()){//check if item had rangeChceck or not (RangeCheck type integer or float)
					
					String range=itemDef.getDataType().toString(); //compare String 
					if(range.contains("INTEGER")){
					value=model.createTypedLiteral(item.getValue(),XSDDatatype.XSDinteger);
					
					}else if(range.contains("FLOAT")){
						
						value=model.createTypedLiteral(item.getValue(),XSDDatatype.XSDfloat);
					}else{//if none of these types then create with String 
							
						value=model.createTypedLiteral(item.getValue());
					}		
					Statement stVal=model.createStatement(obs,pr,value);
					model.add(stVal);
				}else{//Range check null add property 
		
					obs.addProperty(pr,item.getValue());
						
					}
				}else{//code list exist 
					
					String codeListOid=itemDef.getCodeListRef().getCodeListOID();
					String decode=CodeListDecoder.codeListValue(codeListOid,item.getValue(),meta);
					String coCl[]=codeListOid.split("_");
					String codeNumber=coCl[coCl.length-1];
					String cardioVaitalProperty=itemDef.getName()+codeNumber+"-"+decode;
					Property pr2=model.createProperty(cardioUri,cardioVaitalProperty);
					obs.addProperty(pr,pr2);
					//CodeList 
					CodeListDecoder.codeListRdf(cardioUri, codeListOid, meta, model);

				}	  
		    }
		}//check for medicationCode

	    }
	  
	 }
	
	
}
