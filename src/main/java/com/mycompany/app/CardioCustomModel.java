/**
 * 
 */
package com.mycompany.app;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemDef;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.XSD;
import com.mycompany.app.lcdc.Disco;
import com.mycompany.app.lcdc.LcdcCore;

/**
 * @author Abbas H Safaie
 *
 */
public class CardioCustomModel {

	
	
	  /*
	   * Cardio model 
	   * Model name : Cardio-'rest of the name captures from theme'
	   * 
	   * 
	   * */
	  public static void generateCardio(ArrayList<ItemDetail> itemDtos
			  ,HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDefs
			  ,ModelMaker mm){
		  
		  Model model=null;
	
		  for (ItemDetail itemDto : itemDtos) { 	

	
			List<ODMcomplexTypeDefinitionItemData> itemList=itemDto.items;
			for (ODMcomplexTypeDefinitionItemData item : itemList) {  
				String itemOidName=item.getItemOID();
			
		     	//Find itemDef belong to OpenClinica Item 
				ODMcomplexTypeDefinitionItemDef itemDef=itemDefs.get(itemOidName);
			//	List<ODMcomplexTypeDefinitionRangeCheck> listRange=itemDef.getRangeCheck();

					/*
					 * gets the theme from the item name
					 * ideally, the theme should be passed in
					 * 
					 * */
				String theme=StringCustomHelper.groupType(itemOidName).toLowerCase();
				 
				// have to be more generic 
				String typeUri=UriCustomHelper.generateCardioUri(theme);
				 
				//more geeric 
				 model=mm.createModel("Cardio-"+theme);
				
				 Property themP=model.createProperty(UriCustomHelper.themeBase, theme);
				 

				  	//main uri 
				String uri= typeUri + itemDef.getName();
				String itemOid=item.getItemOID();
				//vta
				
				// create a data type property resource for the theme concepts
				Resource r=model.createResource(uri,OWL.DatatypeProperty);
					
					
					//finding type of data from Range 
					//If range exist check the type
				//TODO: extend for all data 
				
				String range=itemDef.getDataType().toString();//range type in string format 
					if(range.length()>0){
					if(range.contains("INTEGER")){
						r.addProperty(RDFS.range, XSD.integer);
				
					}else if(range.contains("FLOAT")){
						r.addProperty(RDFS.range, XSD.xfloat);		
					  }	else{//if none of these types then create with String 
						r.addProperty(RDFS.range, XSD.xstring);
					  }
					}	
							//pointing to Variable Def 
				Property based=model.createProperty(UriCustomHelper.metaBase,"#"+itemOid);
							
				//domain from CsvFile
				try { 
					
					String domain=CsvHelper.getDomain(itemOidName);

					if (null!= domain && !domain.isEmpty()) //Check if domain exist in lcdcMap
					 r.addProperty(RDFS.domain, domain);
						
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		
			   r.addProperty(RDFS.isDefinedBy, typeUri)
				.addProperty(RDFS.label, itemDef.getName())
				.addProperty(RDFS.comment, itemDef.getComment())
				.addProperty(DC.source, "cardio")
				.addProperty(DC.identifier, itemOid)
				.addProperty(Disco.basedOn, based)
				.addProperty(LcdcCore.themeId,themP);		
				
			//	model.add(stVariableId);
		     }//for item 
		  }//for itemDto  
	  }
	  
}
