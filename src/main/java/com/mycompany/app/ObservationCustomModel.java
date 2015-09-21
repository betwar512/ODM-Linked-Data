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

public class ObservationCustomModel {

	
	 
	  /*
	   * Observation model 
	   * version 1.0.0
	   * */
	  public static void createObservation
	  (ModelMaker mm,ArrayList<ItemDetail> itemDtos,HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDefs,ODMcomplexTypeDefinitionMetaDataVersion meta){
		  Model model=null;
		  final String cardioVitalSign="http://aehrc-ci.it.csiro.au/cardio/lcdc/vitalsigns/def/cardio-vitalsigns#";
		  	final String baseUri="http://aehrc-ci.it.csiro.au/dataset/cardio/lcdc/20150713/theme/";
		  for (ItemDetail itemDto : itemDtos) { 
			  	//ItemsDto typeData 
			  String theme="";
			  String baseUriType="http://purl.org/sstats/lcdc/id/";
			  String obsUri="";
	
		
			List<ODMcomplexTypeDefinitionItemData> itemList=itemDto.items;
			for (ODMcomplexTypeDefinitionItemData item : itemList) {  
				String itemOidName=item.getItemOID();
				

				
				 theme=StringCustomHelper.groupType(itemOidName).toLowerCase();
				 obsUri=baseUri+theme+"/phase/";
				 model=mm.createModel("Observation-"+theme);
				 Property themeP=model.createProperty("http://purl.org/sstats/lcdc/id/theme/", theme);
				  
				ODMcomplexTypeDefinitionItemDef itemDef=itemDefs.get(itemOidName);
				String phase="";
				String subject="";
				//Create observation uri 
				Resource obs=null;
			    String obsPhase=obsUri+ itemDto.eventOid+"/subject/"+itemDto.subjectKey;  
					phase=baseUriType+"phase/"+itemDto.eventOid;
					subject=baseUriType+"subject/"+itemDto.subjectKey;
				//Observation resource
				 obs=model.createResource(obsPhase,Obs.OBSERVATION);
				 obs.addProperty(LcdcCore.phase, phase)
				 .addProperty(LcdcCore.subject, subject)
				 .addProperty(LcdcCore.themeId, themeP);
				 
				//Obs value
				Literal value=null;
				//if not empty 
				Property pr=model.createProperty(cardioVitalSign+itemDef.getName());
				
				if(itemDef.getCodeListRef() == null){ //Check if codeList reference exist 
				
				if(!itemDef.getRangeCheck().isEmpty()){
					String range=itemDef.getDataType().toString();
					if(range.contains("INTEGER")){
					value=model.createTypedLiteral(item.getValue(),XSDDatatype.XSDinteger);
					
					}else if(range.contains("FLOAT")){
						
						value=model.createTypedLiteral(item.getValue(),XSDDatatype.XSDfloat);
					}else{
					
						value=model.createTypedLiteral(item.getValue());
					}		
					Statement stVal=model.createStatement(obs,pr,value);
					model.add(stVal);
				}else{
		
					obs.addProperty(pr,item.getValue());
						
					}
				}else{//code list exist 
					
					String codeListOid=itemDef.getCodeListRef().getCodeListOID();
					String decode=CodeListDecoder.codeListValue(codeListOid,item.getValue(),meta);
					String coCl[]=codeListOid.split("_");
					String codeNumber=coCl[coCl.length-1];
					String cardioVaitalProperty=itemDef.getName()+codeNumber+"-"+decode;
					Property pr2=model.createProperty(cardioVitalSign+cardioVaitalProperty);
					obs.addProperty(pr,pr2);
				
					
				}	  
		    }

	    }
	  
	 }
	
	
}
