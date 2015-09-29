package com.mycompany.app;

import java.io.FileNotFoundException;
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
import com.hp.hpl.jena.vocabulary.OWL2;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.mycompany.app.lcdc.LcdcCore;
import com.mycompany.app.lcdc.Odm;
import com.mycompany.app.lcdc.Skos;
import com.mycompany.app.lcdc.Snomed;

public class VariableCustomModel {

	
	  
	  /*
	   * Model Variable Vital 
	   * separate models by dataType Vital Blood Medication
	   * Void 
	   * 
	   * */
	  public static void generateVariable
	  (ArrayList<ItemDetail> itemDtos
			  ,HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDefs
			  ,ModelMaker mm){
		  //model for variable 
		  Model model=null;
		  //Model for Linkset
		  Model modelLs=null;

		  for (ItemDetail itemDto : itemDtos) { 
	
			List<ODMcomplexTypeDefinitionItemData> itemList=itemDto.items;
			for (ODMcomplexTypeDefinitionItemData item : itemList) {  

				String itemOidName=item.getItemOID();
				
				
				//Get them type data 
				String  theme=StringCustomHelper.groupType(itemOidName).toLowerCase();
				 model=mm.createModel("Variable-"+theme);
				  Property themP=model.createProperty(UriCustomHelper.themeBase, theme);
				

		     //	String itemOidval=item.getItemOID();	
		     	//Find itemDef belong to OpenClinica Item 
				ODMcomplexTypeDefinitionItemDef itemDef=itemDefs.get(itemOidName);
				String uri=UriCustomHelper.metaBase+ "#" + itemOidName;
				//itemDef.getCodeListRef();  //CodeList reference 
				Resource r=model.createResource(uri,LcdcCore.VariableDefinition);
							
					 Literal valueForm=model.createTypedLiteral(itemDto.formOid,Odm.formcode);
					 Literal valueItemGroup=model.createTypedLiteral(itemDto.itemGroupOid,Odm.itemgroupcode);
					 Literal valueItem=model.createTypedLiteral(item.getItemOID(),Odm.itemcode);
					 //Using TypeMapper because need to create TypeLitral 
				TypeMapper tm=TypeMapper.getInstance();
			        RDFDatatype rdfType=tm.getSafeTypeByName(LcdcCore.variabledefinitioncode.getURI());
			        
					 Literal valuevarDef=model.createTypedLiteral(itemDef.getName(),rdfType );
					 
					
					
					 Literal valueRepeat=model.createTypedLiteral(itemDto.repeating,XSDDatatype.XSDboolean);	
					 Literal valueDatatype=model.createTypedLiteral(itemDef.getDataType()
							 .toString()
							   .toLowerCase(),XSDDatatype.XSDstring);	 
					 
					 		 Statement stVarDef=model.createStatement(r, LcdcCore.variableDefinitionKey, valuevarDef);
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
					 r.addProperty(RDFS.isDefinedBy, UriCustomHelper.metaBase);
				   	 r.addProperty(RDFS.label, itemDef.getName()); //label
				   	 r.addProperty(LcdcCore.variableDefinitionKey,valuevarDef);
				   	 r.addProperty(LcdcCore.themeId, themP);
				   	 
				   	 
				   	 

					  //================================================================================
				   	//  adding named individual uri 
					//================================================================================
			  
			 
				   	 
				   	modelLs=mm.createModel("Linkset-"+theme);
				   	
				   	modelLs.setNsPrefix("snomed", Snomed.snomedUri);
				   	modelLs.setNsPrefix("snomedmod", Snomed.snomedmodUri);
				   	modelLs.setNsPrefix("amt", Snomed.amtUri);
				   	modelLs.setNsPrefix("skos",Skos.getURI());
				   	Resource linkSet=modelLs.createResource(uri);
				   	Resource namedIndv=null;
				   	
				   	String snomedUri="";
				   	try {
						 snomedUri=CsvHelper.getLastColumn(itemDef.getName()); //matched snomedUri 
						  namedIndv=modelLs.createResource(snomedUri,OWL2.NamedIndividual); //Resource nameIndivitual
							linkSet.addProperty(Skos.exactMatch, namedIndv); //add nameIndividual Resource to llinkSet Resource
						 
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				   	 
				   
				   	 namedIndv.addProperty(RDFS.label, itemDef.getName());
				   	 
				   	 
			    }//i
			  
		     }//For loop item		
		  }
}
