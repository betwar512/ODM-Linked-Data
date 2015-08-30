package com.mycompany.app;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.cdisc.ns.odm.v1.DataType;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionClinicalData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemDef;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionMetaDataVersion;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionRangeCheck;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.AnnotationProperty;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.XSD;
import com.mycompany.app.lcdc.Lcdc;

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

  public HashMap<String,Model> sliceModels(){
		
		
		//Model
    	//Model model=ModelFactory.createDefaultModel();

    	JaxBinder jaXb=new JaxBinder();

    	ODMcomplexTypeDefinitionClinicalData clinicalData=
    			jaXb.getClinicalData("src/main/java/odm1.3_clinical_ext_Full_study_extract_2015-05-22-162457368.xml");
    	ArrayList<ItemDetail> itemDtos=jaXb.makeItemsObjects(clinicalData);
  
    	HashMap<String,Model> sliceModels=sliceRdf(itemDtos);
    	//completeRdf(itemDtos,model);
         return sliceModels;
	}
	
  
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

 
  //Slice RDF Model 
public HashMap<String,Model> sliceRdf(ArrayList<ItemDetail> itemDtos){
	//Create models 
	HashMap<String,Model> models=new HashMap<String,Model>();
	
	HashMap<String, List<ItemDetail>> groupItemDtos=groupBySubjectKey(itemDtos);
	
	for (Entry<String, List<ItemDetail>> entry : groupItemDtos.entrySet()) {
		
		Model sliceModel =ModelFactory.createDefaultModel();
		
		
	    List<ItemDetail> mapValue = entry.getValue();
	    for(ItemDetail itemDetail:mapValue){
	    
	    String itemRepeatKey="";
 		//Create base Uri 
 		String uri =UriCustomHelper.uriSlice(itemDetail);

 		List<ODMcomplexTypeDefinitionItemData> itemList=itemDetail.items;
 		String formOid=itemDetail.formOid;
 		String itemGroupOid=itemDetail.itemGroupOid;
 		String dataset=UriCustomHelper.datasetType(itemDetail.itemGroupOid);

 		if(	itemDetail.itemRepeatKey ==null){
 			
 			itemRepeatKey="";
 		}else{
 			
 			itemRepeatKey=itemDetail.itemRepeatKey;
 		}
 	
 		System.out.println(itemList.size());

 		for (ODMcomplexTypeDefinitionItemData item : itemList) {
 			String value="";
 			String messurmentUnit="";
 			if(item.getMeasurementUnitRef() != null)
 				messurmentUnit=item.getMeasurementUnitRef().toString();
 			String itemOid=item.getItemOID();
 			value=item.getValue();
 	
 			//Object Model to create comment 
 			CommentModel commentDto=new CommentModel();	
 			//CreateComment
 			commentDto.itemOid=itemOid;
 			commentDto.eventOid=itemDetail.eventOid;
 			commentDto.subjectKey=itemDetail.subjectKey;
 		 	commentDto.dataSet=dataset;
 		 	//Comment and Label
 	    	String comment=	StringCustomHelper.Comment(commentDto);
 		    String label=StringCustomHelper.label(itemOid);
 		
 			 sliceModel.createResource(uri+itemOid)
 			.addProperty(Lcdc.itemOid,itemOid)
 	    	.addProperty(Lcdc.itemGroupOID,itemGroupOid)
 	    	.addProperty(Lcdc.formOID,formOid)
 	    	.addProperty(Lcdc.itemGroupRepeatKey,itemRepeatKey)
 	    	.addProperty(Lcdc.measurementUnitOID, messurmentUnit)
 	    	.addProperty(Lcdc.value, value)
 	    	.addProperty(RDFS.label, label)
 	    	.addProperty(RDFS.comment, comment);
 	    	
 	  
 			}//For loop item
	    
 	
	    } 
	   
		models.put(entry.getKey(),sliceModel);
	}
	return models;
}
  
  
  //Create model with return Object ItemDtos
  public void completeRdf(ArrayList<ItemDetail> itemDtos,OntModel model){
	  
	 

	 for (ItemDetail itemDetail : itemDtos) {
	
		        String itemRepeatKey="";
		 		//Create base Uri 
		 		String uri =UriCustomHelper.uriDataset(itemDetail);

		 		List<ODMcomplexTypeDefinitionItemData> itemList=itemDetail.items;
		 //		String formOid=itemDetail.formOid;
		 	//	String itemGroupOid=itemDetail.itemGroupOid;
		 		String dataset=UriCustomHelper.datasetType(itemDetail.itemGroupOid);

		 		if(	itemDetail.itemRepeatKey ==null){
		 			
		 			itemRepeatKey="";
		 		}else{
		 			
		 			itemRepeatKey=itemDetail.itemRepeatKey;
		 		}
		 	
		 		System.out.println(itemList.size());
		
		 		for (ODMcomplexTypeDefinitionItemData item : itemList) {
		 			String value="";
		 			String messurmentUnit="";
		 			if(item.getMeasurementUnitRef() != null)
		 				messurmentUnit=item.getMeasurementUnitRef().getMeasurementUnitOID().toString();
		 			
		 	

		 			String itemOid=item.getItemOID();
		 			value=item.getValue();
		 	
		 			//Object Model to create comment 
		 			CommentModel commentDto=new CommentModel();	
		 			//CreateComment
		 			commentDto.itemOid=itemOid;
		 			commentDto.eventOid=itemDetail.eventOid;
		 			commentDto.subjectKey=itemDetail.subjectKey;
		 		 	commentDto.dataSet=dataset;
		 		 	//Comment and Label
		 	    	String comment=	StringCustomHelper.Comment(commentDto);
		 		    String label=StringCustomHelper.label(itemOid);
		 		
		 		 
		 		  // OntClass  testClass=model.createClass("");
		 		  
		 		   Resource r= model.createResource(uri+itemOid,OWL.Class);
		 		
		 		    r.addProperty(Lcdc.itemOid,itemOid)
		 	    	.addProperty(Lcdc.itemGroupRepeatKey,itemRepeatKey)
		 	    	.addProperty(Lcdc.measurementUnitOID, messurmentUnit)
		 	    	.addProperty(Lcdc.value, value)
		 	    	.addProperty(RDFS.label, label)
		 	    	.addProperty(RDFS.comment, comment);
		 		    
	 	
	 		
			//		 			 model.createResource(uri+itemOid)
			//		 			.addProperty(Lcdc.itemOid,itemOid)
			//		 	    	.addProperty(Lcdc.itemGroupOID,itemGroupOid)
			//		 	    	.addProperty(Lcdc.formOID,formOid)
			//		 	    	.addProperty(Lcdc.itemGroupRepeatKey,itemRepeatKey)
			//		 	    	.addProperty(Lcdc.measurementUnitOID, messurmentUnit)
			//		 	    	.addProperty(Lcdc.value, value)
			//		 	    	.addProperty(RDFS.label, label)
			//		 	    	.addProperty(RDFS.comment, comment);
		 	   
		 	}//For loop item
		 }//for loop itemDto 
	 }
	  

  
		  //================================================================================
	   	// MetaData model   Test V.1.0
		//================================================================================
  
  
  
  //MetaData Model 
  
  public void metaDataRdf(HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDefs,ArrayList<ItemDetail> itemDtos,OntModel model){
	  

	  for (ItemDetail itemDetail : itemDtos) {
			
	      
	 		//Create base Uri 
	 		String uri =UriCustomHelper.uriMetaData(itemDetail.itemGroupOid);
	 			
	 		String definedBy=UriCustomHelper.rdfDefinition(itemDetail.itemGroupOid);
	 		List<ODMcomplexTypeDefinitionItemData> itemList=itemDetail.items;
	 		
	 		for (ODMcomplexTypeDefinitionItemData item : itemList) {  
	 	     	String itemOid=item.getItemOID();	
	 	     	//Find itemDef belong to OpenClinica Item 
	 			ODMcomplexTypeDefinitionItemDef itemDef=itemDefs.get(itemOid);
	 			
	 			List<ODMcomplexTypeDefinitionRangeCheck> listRange=itemDef.getRangeCheck();
	
      	 		//Model		
	 		
	 			
	 			
	 			
	 		   OntResource r= model.createOntResource(uri+itemDef.getName()); 
	 		OntProperty pro=model.createOntProperty("testClass");
	 		
	 		
	 		pro.addRDFType(XSD.xfloat);
	 		r.addProperty(pro,"itemId");
	 		    r.addProperty(DC.identifier,itemOid)	   	 	    	
	 	    	.addProperty(RDFS.label, itemDef.getName())
	 	    	.addProperty(RDFS.comment,itemDef.getComment())
	 		    .addProperty(RDFS.isDefinedBy, definedBy);
	 		 
	 		    //Finding right dataType 
	 		    if(listRange.size()>0){  	
	 		    	DataType  type=itemDef.getDataType();
	 		    	if(type==DataType.FLOAT){  	
	 		    r.addProperty(RDFS.range,XSD.xfloat);
	 		    	}else if(type==DataType.INTEGER){
	 		    		r.addProperty(RDFS.range,XSD.integer);		    	
	 		    	}
	 		    }//i
	 	     }//For loop item		
	   	} 
  }
  
  

//Test Method to call 
  public OntModel ontoModelTest(){
	  
		//Model
		OntModel model=ModelFactory.createOntologyModel();
	

  	JaxBinder myJax=new JaxBinder();

  	ODMcomplexTypeDefinitionClinicalData clinicalData=
  			myJax.getClinicalData("src/main/java/odm1.3_clinical_ext_Full_study_extract_2015-05-22-162457368.xml");
  	
  	
  	ODMcomplexTypeDefinitionMetaDataVersion meta =JaxBinder.catchMetaData("src/main/java/odm1.3_clinical_ext_Full_study_extract_2015-05-22-162457368.xml");
	
	HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDef=JaxBinder.catchItemDef(meta);

  	ArrayList<ItemDetail> itemDtos=myJax.makeItemsObjects(clinicalData);

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
	  
	  license.addRDFType(RDF.type);
	  license.addRDFType(OWL.AnnotationProperty);
	  
	  
	  
	  
//		  ###  http://purl.org/dc/elements/1.1/creator
//		  dc:creator rdf:type owl:AnnotationProperty .
OntProperty creator=model.createAnnotationProperty(" http://purl.org/dc/elements/1.1/creator");
	  
				creator.addRDFType(RDF.type);
				creator.addRDFType(OWL.AnnotationProperty);
	  
	  

//		  ###  http://purl.org/dc/elements/1.1/date
//		  dc:date rdf:type owl:AnnotationProperty .
				OntProperty date=model.createAnnotationProperty(" http://purl.org/dc/elements/1.1/date");
				  
				date.addRDFType(RDF.type);
				date.addRDFType(OWL.AnnotationProperty);
	  
//		  ###  http://purl.org/dc/elements/1.1/description
//
//		  dc:description rdf:type owl:AnnotationProperty .
				OntProperty description=model.createAnnotationProperty(" http://purl.org/dc/elements/1.1/description");
				  
				description.addRDFType(RDF.type);
				description.addRDFType(OWL.AnnotationProperty);
	  
//		  ###  http://purl.org/dc/elements/1.1/identifier
//
//		  dc:identifier rdf:type owl:AnnotationProperty .
		OntProperty identifier=model.createAnnotationProperty(" http://purl.org/dc/elements/1.1/description");
				  
		identifier.addRDFType(RDF.type);
		identifier.addRDFType(OWL.AnnotationProperty);
	  
//		  ###  http://purl.org/dc/elements/1.1/rights
//
//		  dc:rights rdf:type owl:AnnotationProperty .
		OntProperty rights=model.createAnnotationProperty(" http://purl.org/dc/elements/1.1/rights");
		  
		rights.addRDFType(RDF.type);
		rights.addRDFType(OWL.AnnotationProperty);
	  
//		  ###  http://purl.org/dc/elements/1.1/source
//		  dc:source rdf:type owl:AnnotationProperty .
		OntProperty source=model.createAnnotationProperty(" http://purl.org/dc/elements/1.1/source");
		  
		source.addRDFType(RDF.type);
		source.addRDFType(OWL.AnnotationProperty);
	  
//		  ###  http://purl.org/dc/elements/1.1/title
//		  dc:title rdf:type owl:AnnotationProperty .
		OntProperty title=model.createAnnotationProperty(" http://purl.org/dc/elements/1.1/title");
		  
		title.addRDFType(RDF.type);
		title.addRDFType(OWL.AnnotationProperty);
	  
//		  ###  http://purl.org/dc/terms/modified
//		  dct:modified rdf:type owl:AnnotationProperty .
		OntProperty modified=model.createAnnotationProperty("http://purl.org/dc/terms/modified");
		  
		modified.addRDFType(RDF.type);
		modified.addRDFType(OWL.AnnotationProperty);
	  
//		  ###  http://purl.org/linked-data/api/vocab#uriTemplate
//		  api:uriTemplate rdf:type owl:AnnotationProperty .
		OntProperty uriTemplate=model.createAnnotationProperty("http://purl.org/linked-data/api/vocab#uriTemplate");
		  
		uriTemplate.addRDFType(RDF.type);
		uriTemplate.addRDFType(OWL.AnnotationProperty);
	  
//		  ###  http://purl.org/sstats/lcdc/def/odm#dataType
//		  lcdcodm:dataType rdf:type owl:AnnotationProperty .
		OntProperty dataType=model.createAnnotationProperty("http://purl.org/sstats/lcdc/def/odm#dataType");
		  
		dataType.addRDFType(RDF.type);
		dataType.addRDFType(OWL.AnnotationProperty);
	  
//		  ###  http://purl.org/sstats/lcdc/def/odm#itemOid
//		  lcdcodm:itemOid rdf:type owl:AnnotationProperty .
		OntProperty itemOid=model.createAnnotationProperty("http://purl.org/sstats/lcdc/def/odm#itemOid");
		  
		itemOid.addRDFType(RDF.type);
		itemOid.addRDFType(OWL.AnnotationProperty);
	  
//		  ###  http://purl.org/sstats/lcdc/def/odm#repeating
//		  lcdcodm:repeating rdf:type owl:AnnotationProperty .
		OntProperty repeating=model.createAnnotationProperty("http://purl.org/sstats/lcdc/def/odm#repeating");
		  
		repeating.addRDFType(RDF.type);
		repeating.addRDFType(OWL.AnnotationProperty);
	  
//		  ###  http://purl.org/sstats/lcdc/def/core#variableDefinitionKey
//		  lcdccore:variableDefinitionKey rdf:type owl:AnnotationProperty .
		OntProperty variableDefinitionKey=model.createAnnotationProperty("http://purl.org/sstats/lcdc/def/core#variableDefinitionKey");
		  
		variableDefinitionKey.addRDFType(RDF.type);
		variableDefinitionKey.addRDFType(OWL.AnnotationProperty);
	  
//		  ###  http://purl.org/sstats/lcdc/def/odm#itemGroupOid
//		  lcdcodm:itemGroupOid rdf:type owl:AnnotationProperty .
		OntProperty itemGroupOid=model.createAnnotationProperty("http://purl.org/sstats/lcdc/def/odm#itemGroupOi");  
		itemGroupOid.addRDFType(RDF.type);
		itemGroupOid.addRDFType(OWL.AnnotationProperty);
	  
//		  ###  http://purl.org/sstats/lcdc/def/odm#formOid
//		  lcdcodm:formOid rdf:type owl:AnnotationProperty .
		OntProperty formOid=model.createAnnotationProperty("http://purl.org/sstats/lcdc/def/odm#formOid");  
		formOid.addRDFType(RDF.type);
		formOid.addRDFType(OWL.AnnotationProperty);
	  
		  //================================================================================
	   	//   Datatypes
		//================================================================================

		String baseDatatype="http://purl.org/sstats/lcdc/def/odm#";
//		###  http://purl.org/sstats/lcdc/def/odm#itemcode
//			lcdcodm:itemcode rdf:type rdfs:Datatype .
		DatatypeProperty itemcode= model.createDatatypeProperty(baseDatatype+"itemcode");
		itemcode.setRDFType(RDFS.Datatype);
		itemcode.addRDFType(RDF.type);
		
		
		
//			###  http://purl.org/sstats/lcdc/def/odm#itemgroupcode
//			lcdcodm:itemgroupcode rdf:type rdfs:Datatype .
		DatatypeProperty itemgroupcode= model.createDatatypeProperty(baseDatatype+"itemgroupcode");
		itemgroupcode.setRDFType(RDFS.Datatype);
		itemgroupcode.addRDFType(RDF.type);
		
//			###  http://purl.org/sstats/lcdc/def/odm#variabledefinitioncode
//			lcdcodm:variabledefinitioncode rdf:type rdfs:Datatype .
		DatatypeProperty variabledefinitioncode= model.createDatatypeProperty(baseDatatype+"variabledefinitioncode");

		variabledefinitioncode.setRDFType(RDFS.Datatype);
		variabledefinitioncode.addRDFType(RDF.type);
		
		
//			###  http://purl.org/sstats/lcdc/def/odm#formcode
//			lcdcodm:formcode rdf:type rdfs:Datatype 
		
		DatatypeProperty formcode= model.createDatatypeProperty(baseDatatype+"formcode");
		formcode.setRDFType(RDFS.Datatype);
		formcode.addRDFType(RDF.type);
		  //================================================================================
	   	//   Class
		//================================================================================

		
		OntClass varDef=model.createClass("http://purl.org/sstats/lcdc/def/core#VariableDefinition");
		varDef.setRDFType(OWL.Class);
		varDef.addRDFType(RDF.type);
		
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

			 Literal valueForm=model.createTypedLiteral(itemDetail.formOid,XSDDatatype.XSD);
			 Statement st=model.createStatement(r, formcode, valueForm);
			 Literal valueItemGroup=model.createTypedLiteral(itemDetail.itemGroupOid,XSDDatatype.XSD);
			 Statement stItemGroup=model.createStatement(r, itemGroupOid, valueItemGroup);
			 Literal valueItem=model.createTypedLiteral(item.getItemOID(),XSDDatatype.XSD);
			 Statement stItem=model.createStatement(r, itemOid, valueItem);
			 //Literal valuevarDef=model.createTypedLiteral(itemDetail.g,XSDDatatype.XSD);
			// Statement stVarDef=model.createStatement(re, variableDefinitionKey, valuevarDef);
			// Literal valueRepeat=model.createTypedLiteral(itemDetail.g,XSDDatatype.XSDboolean);
			// Statement stRepeat=model.createStatement(re,repeating, valueRepeat);
			// Literal valueDatattype=model.createTypedLiteral(itemDef,XSDDatatype.XSD);
		//	 Statement stDatetype=model.createStatement(re, dataType, valueDatattype);
		 
			 
			 	 model.add(st);
				 model.add(stItemGroup);
				 model.add(stItem);
				// model.add(stVarDef);
				
				 r.addProperty(source,"cardio");
				 r.addProperty(RDFS.comment, itemDef.getComment());
				 r.addProperty(RDFS.isDefinedBy, baseUri);
				 
				 
		
		    }//i
	     }//For loop item		
	  }
}
