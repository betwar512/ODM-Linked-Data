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
	   * Model to create vital-vitalSign
	   * */
	  public static void generateCardio(ArrayList<ItemDetail> itemDtos
			  ,HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDefs
			  ,ModelMaker mm){
		  
		  Model model=null;
	
		  for (ItemDetail itemDto : itemDtos) { 	

		//  String definedBy=UriCustomHelper.rdfDefinition(itemDto.itemGroupOid);
			List<ODMcomplexTypeDefinitionItemData> itemList=itemDto.items;
			for (ODMcomplexTypeDefinitionItemData item : itemList) {  
				String itemOidName=item.getItemOID();
		     //	String itemOidval=item.getItemOID();	
		     	//Find itemDef belong to OpenClinica Item 
				ODMcomplexTypeDefinitionItemDef itemDef=itemDefs.get(itemOidName);
			//	List<ODMcomplexTypeDefinitionRangeCheck> listRange=itemDef.getRangeCheck();


				String theme=StringCustomHelper.groupType(itemOidName).toLowerCase();
				 
				String typeUri=UriCustomHelper.cardioBase+theme+"/def/cardio-"+theme;
				 
				 model=mm.createModel("Cardio-"+theme);
				
				 Property themP=model.createProperty(UriCustomHelper.themeBase, theme);

				  	//main uri 
				String uri= typeUri +"#"+ itemDef.getName();
				String itemOid=item.getItemOID();
				//vital Resource
				Resource r=model.createResource(uri,OWL.DatatypeProperty);
		
					String range=itemDef.getDataType().toString();
					
					if(range.length()>0){
					if(range.contains("INTEGER")){
						r.addProperty(RDFS.range, XSD.integer);
				
					}else if(range.contains("FLOAT")){
						r.addProperty(RDFS.range, XSD.xfloat);		
					  }	
					}	
							//pointing to Variable Def 
				Property based=model.createProperty(UriCustomHelper.metaBase,"/"+itemOid);
				Property findings=model.createProperty("","Findings");
					
				try {
					String snomad =CsvHelper.getLastColumn(itemDef.getName());
					
					System.out.println(snomad);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		
				r.addProperty(RDFS.isDefinedBy, typeUri)
				.addProperty(RDFS.label, itemDef.getName())
				.addProperty(RDFS.comment, itemDef.getComment())
				.addProperty(RDFS.domain, findings)
				.addProperty(DC.source, "cardio")
				.addProperty(DC.identifier, itemOid)
				.addProperty(Disco.basedOn, based)
				.addProperty(LcdcCore.themeId,themP);		
				
			//	model.add(stVariableId);
		     }//for item 
		  }//for itemDto  
	  }
	  
}
