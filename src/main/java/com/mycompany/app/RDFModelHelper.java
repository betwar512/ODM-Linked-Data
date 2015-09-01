package com.mycompany.app;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionClinicalData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemDef;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionMetaDataVersion;


import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.TypeMapper;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;


import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;

import com.hp.hpl.jena.ontology.OntProperty;

import com.hp.hpl.jena.rdf.model.Literal;

import com.hp.hpl.jena.rdf.model.ModelFactory;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.OWL;

import com.hp.hpl.jena.vocabulary.RDFS;
import com.mycompany.app.lcdc.Odm;


/**
 * @author Abbas H Safaie
 * RDFModel /helper class to generate model and add specified properties to the model 
 * Main function CreateModel 
 *
 */
public class RDFModelHelper {


	   //================================================================================
    // Model
    //================================================================================

  
  //Grouping by SubjectKey
  public HashMap<String, List<ItemDetail>> groupBySubjectKey(ArrayList<ItemDetail> itemDetails){
	  
	  
	  HashMap<String, List<ItemDetail>> hashMap=new HashMap<String, List<ItemDetail>>();
	  
	  for(ItemDetail itemDetail:itemDetails){
		  
		  
		  if(!hashMap.containsKey(itemDetail.subjectKey)){
	          List<ItemDetail> list= new ArrayList<ItemDetail>();
	          list.add(itemDetail);
	      hashMap.put(itemDetail.subjectKey,list);
	      }
	      else
	          hashMap.get(itemDetail.subjectKey).add(itemDetail);
		  
	    }
		  
	  return hashMap;
	  }

  

		  //================================================================================
	   	// MetaData model   Test V.1.0
		//================================================================================
  
 
//Test Method to call 
  public OntModel ontoModelTest(){
	  
		//Model
		OntModel model=ModelFactory.createOntologyModel();
	

  	JaxBinder myJax=new JaxBinder();

  	ODMcomplexTypeDefinitionClinicalData clinicalData=
  			myJax.getClinicalData("src/main/java/odm1.3_clinical_ext_Full_study_extract_2015-05-22-162457368.xml");
  	
  
  	ODMcomplexTypeDefinitionMetaDataVersion meta =JaxBinder.catchMetaData("src/main/java/odm1.3_clinical_ext_Full_study_extract_2015-05-22-162457368.xml");
	
	HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDef=JaxBinder.catchItemDef(meta);

  	ArrayList<ItemDetail> itemDtos=myJax.makeItemsObjects(clinicalData,meta);

  //	metaDataRdf(itemDef,itemDtos,model);
  	
  //	completeRdf(itemDtos,model); 
  	ontModelTest(itemDef,itemDtos,model);
  	return model;
	  
  }
  
  
  
  public void ontModelTest(HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDefs,ArrayList<ItemDetail> itemDtos,OntModel model){
	  
	  
	  
	  //================================================================================
   	// Annotation properties
	//================================================================================

//	  ###  http://creativecommons.org/ns#license
//		  cc:license rdf:type owl:AnnotationProperty .

	  OntProperty license=model.createAnnotationProperty("http://creativecommons.org/ns#license");
	 
	  license.addRDFType(OWL.AnnotationProperty);
		  //================================================================================
	   	//   Datatypes
		//================================================================================

		String baseDatatype="http://purl.org/sstats/lcdc/def/odm#";
		TypeMapper tm=TypeMapper.getInstance();
			
		RDFDatatype itemcode=tm.getSafeTypeByName(baseDatatype+"itemcode");
		tm.registerDatatype(itemcode);
			
		RDFDatatype itemgroupcode=tm.getSafeTypeByName(baseDatatype+"itemgroupcode");
		tm.registerDatatype(itemgroupcode);
	
			RDFDatatype variabledefinitioncode=tm.getSafeTypeByName(baseDatatype+"variabledefinitioncode");
		tm.registerDatatype(variabledefinitioncode);
		
		RDFDatatype formcode=tm.getSafeTypeByName(baseDatatype+"formcode");
		tm.registerDatatype(formcode);
		
		  //================================================================================
	   	//   Class
		//================================================================================
		OntClass varDef=model.createClass("http://purl.org/sstats/lcdc/def/core#VariableDefinition");
		varDef.addRDFType(OWL.Class);
		String baseUri="http://aehrc-ci.it.csiro.au/cardio/lcdc/id/variable-def";
	//	Individual itemNod=varDef.createIndividual(baseUri+"itemOid");
		
		
//	Resource re=model.createResource(baseUri,varDef);
	//re.addProperty(RDFS.label, ""); //lable

	//formOid
	// Literal valueForm=model.createTypedLiteral("FormOid",XSDDatatype.XSD);
	// Statement st=model.createStatement(re, formcode, valueForm);
	// Literal valueItemGroup=model.createTypedLiteral("ItemGroup",XSDDatatype.XSD);
	// Statement stItemGroup=model.createStatement(re, itemGroupOid, valueItemGroup);
	// Literal valueItem=model.createTypedLiteral("ItemOid",XSDDatatype.XSD);
	// Statement stItem=model.createStatement(re, itemOid, valueItem);
//	// Literal valuevarDef=model.createTypedLiteral("varDef",XSDDatatype.XSD);
//	 Statement stVarDef=model.createStatement(re, variableDefinitionKey, valuevarDef);
//	 Literal valueRepeat=model.createTypedLiteral("false",XSDDatatype.XSDboolean);
//	 Statement stRepeat=model.createStatement(re,repeating, valueRepeat);
//	 Literal valueDatattype=model.createTypedLiteral("false",XSDDatatype.XSD);
//	 Statement stDatetype=model.createStatement(re, dataType, valueDatattype);
//	// model.add(st);
//	// model.add(stItemGroup);
//	// model.add(stItem);
//	 model.add(stVarDef);
//	 model.add(stRepeat);
//	 model.add(stDatetype);
//	 re.addProperty(source, "cardio");
//	 re.addProperty(RDFS.comment, "this is hte comment");
//	 re.addProperty(RDFS.isDefinedBy, baseUri);
	 
	 
	 

	  for (ItemDetail itemDetail : itemDtos) {
	 
		  if(StringCustomHelper.vitalSeperate(itemDetail.itemGroupOid)){
		  
		  
	//  String definedBy=UriCustomHelper.rdfDefinition(itemDetail.itemGroupOid);
		List<ODMcomplexTypeDefinitionItemData> itemList=itemDetail.items;
		
		for (ODMcomplexTypeDefinitionItemData item : itemList) {  
			String itemOidName=item.getItemOID();
	     //	String itemOidval=item.getItemOID();	
	     	//Find itemDef belong to OpenClinica Item 
			ODMcomplexTypeDefinitionItemDef itemDef=itemDefs.get(itemOidName);
		//	List<ODMcomplexTypeDefinitionRangeCheck> listRange=itemDef.getRangeCheck();

	 		//Model		
			String defName="";
			defName=itemDef.getName();
			String mainUri=baseUri+ "#" + defName;
			Resource r=model.createResource(mainUri,varDef);
			r.addProperty(RDFS.label, itemDef.getName()); //lable
			 Literal valueForm=model.createTypedLiteral(itemDetail.formOid,formcode);
			 Literal valueItemGroup=model.createTypedLiteral(itemDetail.itemGroupOid,itemgroupcode);
			 Literal valueItem=model.createTypedLiteral(item.getItemOID(),itemcode);
			 //Literal valuevarDef=model.createTypedLiteral(itemDetail.g,XSDDatatype.XSD);
			// Statement stVarDef=model.createStatement(re, variableDefinitionKey, valuevarDef);
			 Literal valueRepeat=model.createTypedLiteral(itemDetail.repeating,XSDDatatype.XSDboolean);	
			 Literal valueDatatype=model.createTypedLiteral(itemDef.getDataType().toString().toLowerCase(),XSDDatatype.XSDstring);
			 
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
				 r.addProperty(DC.source,"cardio");
				 r.addProperty(RDFS.comment, itemDef.getComment());
				 r.addProperty(RDFS.isDefinedBy, baseUri);
				 
				 
		
		    }//i
		  }
	     }//For loop item		
	  }
}
