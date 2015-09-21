package com.mycompany.app;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemDef;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.TypeMapper;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.XSD;
import com.mycompany.app.lcdc.Disco;
import com.mycompany.app.lcdc.LcdcCore;
import com.mycompany.app.lcdc.Odm;

public class ModelTester {

	/**
	 * 
	 */


	

	/**
	 * @author Abbas H Safaie
	 *
	 */
	


		  /*
		   * Model to create vital-vitalSign
		   * */
		  public static void generateCardio(ArrayList<ItemDetail> itemDtos
				  ,HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDefs
				  ,ModelMaker mm){
			  
			  Model model=null;
			  final String baseUri="http://aehrc-ci.it.csiro.au/cardio/lcdc/clinical";	  
			//  final String metaDataUri="http://aehrc-ci.it.csiro.au/cardio/lcdc/id/variable-def#";
			//  final String cardioVitalSign="http://aehrc-ci.it.csiro.au/cardio/lcdc/vitalsigns/def/cardio-vitalsigns#";
			  

			  for (ItemDetail itemDto : itemDtos) { 	
					String typeUri="";
					String theme="";
					//Create observation uri 
				  	//ItemsDto typeData 
//					  if(itemDto.isVital()){
//							typeUri=baseUri+"vitalsigns/def/cardio-vitalsigns";
//							theme="vitalsigns";
//							model=mm.createModel(ModelNames.CARDIO_VITAL);;
//						  }else if(itemDto.isBlood()){
//							  model=mm.createModel(ModelNames.CARDIO_BLOOD);
//							  typeUri=baseUri+"blood/def/cardio-blood";
//							  theme="blood";
//						  }else if(itemDto.isMedic()){
//							  model=mm.createModel(ModelNames.CARDIO_MEIDC);
//							  typeUri=baseUri+"/medic/def/cardio-medic";
//							  theme="medication";
//						  }
//					  
					  
					  
		
					  
					  
			//  String definedBy=UriCustomHelper.rdfDefinition(itemDto.itemGroupOid);
				List<ODMcomplexTypeDefinitionItemData> itemList=itemDto.items;
				for (ODMcomplexTypeDefinitionItemData item : itemList) {  
					String itemOidName=item.getItemOID();
			     //	String itemOidval=item.getItemOID();	
			     	//Find itemDef belong to OpenClinica Item 
					ODMcomplexTypeDefinitionItemDef itemDef=itemDefs.get(itemOidName);
				//	List<ODMcomplexTypeDefinitionRangeCheck> listRange=itemDef.getRangeCheck();

					
					
					
					//############################################################################
					//Generically decide what to do with model 
					//############################################################################
					
					
					
					 theme=StringCustomHelper.groupType(itemOidName).toLowerCase();
					 
					 typeUri=baseUri+theme+"/def/cardio-"+theme;
					 
					 model=mm.createModel("Cardio-"+theme);
					
					Property themP=model.createProperty("http://purl.org/sstats/lcdc/id/theme/", theme);
					
					

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
						Property based=model.createProperty("http://aehrc-ci.it.csiro.au/cardio/lcdc/id/variable-def#",itemOid);
				
					r.addProperty(RDFS.isDefinedBy, typeUri)
					.addProperty(RDFS.label, itemDef.getName())
					.addProperty(RDFS.comment, itemDef.getComment())
					.addProperty(RDFS.domain, ":Finding")
					.addProperty(DC.source, "cardio")
					.addProperty(DC.identifier, itemOid)
					.addProperty(Disco.basedOn, based)
					.addProperty(LcdcCore.themeId,themP);		
					
				//	model.add(stVariableId);
			     }//for item 
			  }//for itemDto  
		  }
		  

		  
		  /*
		   * Model Variable Vital 
		   * separate models by dataType Vital Blood Medication
		   * Void 
		   * 
		   * */
		  public static void variableVital
		  (ArrayList<ItemDetail> itemDtos
				  ,HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDefs
				  ,ModelMaker mm){
			  Model model=null;
			 final String baseUri="http://aehrc-ci.it.csiro.au/cardio/lcdc/id/variable-def";

			  for (ItemDetail itemDto : itemDtos) { 
					String theme=""; 
					  if(itemDto.isVital()){
						model=mm.createModel(ModelNames.Variable_VITAL);
							theme="vitalsigns";
							
						  }else if(itemDto.isBlood()){ 
							model=mm.createModel(ModelNames.Variable_BLOOD);
							  theme="blood";
					
						  }else if(itemDto.isMedic()){ 
						model=mm.createModel(ModelNames.Variable_MEIDC);
							  theme="medication";
						  }
					  Property themP=model.createProperty("http://purl.org/sstats/lcdc/id/theme/", theme);
				List<ODMcomplexTypeDefinitionItemData> itemList=itemDto.items;
				for (ODMcomplexTypeDefinitionItemData item : itemList) {  

					String itemOidName=item.getItemOID();
			     //	String itemOidval=item.getItemOID();	
			     	//Find itemDef belong to OpenClinica Item 
					ODMcomplexTypeDefinitionItemDef itemDef=itemDefs.get(itemOidName);
					String uri=baseUri+ "#" + itemOidName;
					//itemDef.getCodeListRef();  //CodeList reference 
					Resource r=model.createResource(uri,LcdcCore.VariableDefinition);
								
						 Literal valueForm=model.createTypedLiteral(itemDto.formOid,Odm.formcode);
						 Literal valueItemGroup=model.createTypedLiteral(itemDto.itemGroupOid,Odm.itemgroupcode);
						 Literal valueItem=model.createTypedLiteral(item.getItemOID(),Odm.itemcode);
						 //Using TypeMapper because need to create TypeLitral 
					TypeMapper tm=TypeMapper.getInstance();
				        RDFDatatype rdfType=tm.getSafeTypeByName(LcdcCore.variabledefinitioncode.getURI());
				        
						 Literal valuevarDef=model.createTypedLiteral(itemDef.getName(),rdfType );
						 
						Statement stVarDef=model.createStatement(r, LcdcCore.variableDefinitionKey, valuevarDef);
						 r.addProperty(LcdcCore.variableDefinitionKey,valuevarDef);
						 Literal valueRepeat=model.createTypedLiteral(itemDto.repeating,XSDDatatype.XSDboolean);	
						 Literal valueDatatype=model.createTypedLiteral(itemDef.getDataType()
								 .toString()
								   .toLowerCase(),XSDDatatype.XSDstring);	 
						 		 Statement stDatetype=model.createStatement(r, Odm.dataType, valueDatatype);
								 Statement stRepeat=model.createStatement(r,Odm.repeating, valueRepeat);
								 Statement stItem=model.createStatement(r, Odm.ItemOid, valueItem);
								 Statement stItemGroup=model.createStatement(r, Odm.itemGroupOid, valueItemGroup);
								 Statement st=model.createStatement(r,Odm.formOid , valueForm);
								 
					 	 model.add(st);
						 model.add(stItemGroup);
						 model.add(stItem);
						 model.add(stDatetype);
					     model.add(stRepeat);
					     model.add(stVarDef);
						 r.addProperty(DC.source,"cardio");
						 r.addProperty(RDFS.comment, itemDef.getComment());
						 r.addProperty(RDFS.isDefinedBy, baseUri);
					   	 r.addProperty(RDFS.label, itemDef.getName()); //label
					   	 r.addProperty(LcdcCore.themeId, themP);
				    }//i
				  
			     }//For loop item		
			  }
		  
	

}
