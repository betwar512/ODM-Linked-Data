package com.mycompany.app;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.text.WordUtils;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionClinicalData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionCodeList;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionCodeListItem;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemDef;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionMetaDataVersion;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionSubjectData;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.TypeMapper;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ModelFactory;
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
  public HashMap<String, List<ItemDetail>> groupBySubjectKey(ArrayList<ItemDetail> itemDtos){
	  
	  
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

  

		  //================================================================================
	   	// MetaData model   Test V.1.0
		//================================================================================
  
 
//Test Method to call 
  public OntModel ontoModelTest(){
	  
		//Model
		OntModel model=ModelFactory.createOntologyModel();
		
		    model.setNsPrefix( "lcdcodm", "http://purl.org/sstats/lcdc/def/odm#" );
			model.setNsPrefix("dc",  DC.getURI());
			model.setNsPrefix("lcdccore", "http://purl.org/sstats/lcdc/def/core#");
			model.setNsPrefix("disco","http://rdf-vocabulary.ddialliance.org/discovery#");
			model.setNsPrefix("cardiovitalsigns", "http://aehrc-ci.it.csiro.au/cardio/lcdc/vitalsigns/def/cardio-vitalsigns#");
			
			JaxBinder myJax=new JaxBinder();
			ODMcomplexTypeDefinitionClinicalData clinicalData=
			myJax.getClinicalData("src/main/java/odm1.3_clinical_ext_Full_study_extract_2015-05-22-162457368.xml");
			ODMcomplexTypeDefinitionMetaDataVersion meta =JaxBinder.catchMetaData("src/main/java/odm1.3_clinical_ext_Full_study_extract_2015-05-22-162457368.xml");
	
	    HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDef=JaxBinder.catchItemDef(meta);
        ArrayList<ItemDetail> itemDtos=myJax.makeItemsObjects(clinicalData,meta);
  
        ///model codeList 
        
        List<ODMcomplexTypeDefinitionCodeList> codeLists=JaxBinder.catchCodeList(meta);
        
        codeListRdf(codeLists,model);
        
  //	completeRdf(itemDtos,model); 
  //	cardioVital(itemDtos,itemDef,model);
  //	vitalVitalSign(itemDtos,itemDef,model);  
  	return model;
	  
  }
  
  
  
  /*
   * OntModel to create vital-vitalSign
   * */
  public void vitalVitalSign(ArrayList<ItemDetail> itemDtos
		  ,HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDefs
		  ,OntModel model){
	  
	  final String baseUri="http://aehrc-ci.it.csiro.au/cardio/lcdc/clinical";
	  
		 final String metaDataUri="http://aehrc-ci.it.csiro.au/cardio/lcdc/id/variable-def#";
	
		final String cardioVitalSign="http://aehrc-ci.it.csiro.au/cardio/lcdc/vitalsigns/def/cardio-vitalsigns#";
	//	varDefClass.addRDFType(OWL.Class);
		
	  for (ItemDetail itemDto : itemDtos) { 	
	//  String definedBy=UriCustomHelper.rdfDefinition(itemDto.itemGroupOid);
		List<ODMcomplexTypeDefinitionItemData> itemList=itemDto.items;
		for (ODMcomplexTypeDefinitionItemData item : itemList) {  
			String itemOidName=item.getItemOID();
	     //	String itemOidval=item.getItemOID();	
	     	//Find itemDef belong to OpenClinica Item 
			ODMcomplexTypeDefinitionItemDef itemDef=itemDefs.get(itemOidName);
		//	List<ODMcomplexTypeDefinitionRangeCheck> listRange=itemDef.getRangeCheck();
			String typeUri="";
			String theme="";
			//Create observation uri 
			String obsUri="";
		  	//ItemsDto typeData 
			  if(itemDto.isVital()){
				typeUri=baseUri+"vitalsigns/def/cardio-vitalsigns";
				theme="http://purl.org/sstats/lcdc/id/theme/vitalsigns";	
				obsUri="http://aehrc-ci.it.csiro.au/dataset/cardio/lcdc/20150713/theme/vitalsigns/phase/";
			  }else if(itemDto.isBlood()){
				  typeUri=baseUri+"blood/def/cardio-blood";
				  theme="http://purl.org/sstats/lcdc/id/theme/blood";
				  obsUri="http://aehrc-ci.it.csiro.au/dataset/cardio/lcdc/20150713/theme/blood/phase/";
			  }else if(itemDto.isMedic()){
				  typeUri=baseUri+"/medic/def/cardio-medic";
				  theme="http://purl.org/sstats/lcdc/id/theme/medication";
				  obsUri="http://aehrc-ci.it.csiro.au/dataset/cardio/lcdc/20150713/theme/medication/phase/";
			  }
			  String obsPhase=obsUri+ itemDto.eventOid+"/subject/"+itemDto.subjectKey;

			  	//main uri 
			String uri= typeUri +"#"+ itemDef.getName();
			String itemOid=item.getItemOID();
			//vital Resource
			Resource r=model.createResource(uri,OWL.DatatypeProperty);
			//Observation resource
			Resource obs=model.createResource(obsPhase,OWL.Class);
			//Obs value
			Literal value=null;
			//if not empty 
			Property pr=model.createProperty(cardioVitalSign+itemDef.getName());
			if(!itemDef.getRangeCheck().isEmpty()){
				String range=itemDef.getDataType().toString();
				if(range.contains("INTEGER")){
				value=model.createTypedLiteral(item.getValue(),XSDDatatype.XSDunsignedInt);
					r.addProperty(RDFS.range, XSD.integer);
				}else if(range.contains("FLOAT")){
					r.addProperty(RDFS.range, XSD.xfloat);
					value=model.createTypedLiteral(item.getValue(),XSDDatatype.XSDfloat);
				}else{
					r.addProperty(RDFS.range, XSD.xstring);
					value=model.createTypedLiteral(item.getValue());
				}
				
					
				Statement stVal=model.createStatement(obs,pr,value);
				model.add(stVal);
			}else{
				
				obs.addProperty(pr,item.getValue());
				
			}
			
			Literal variableId=model.createTypedLiteral(itemOid, LcdcCore.variablecode);
			Statement stVariableId=model.createStatement(r, LcdcCore.variableId, variableId);
		
					//rdfs:range xsd:float .
			r.addProperty(RDFS.isDefinedBy, typeUri)
			.addProperty(RDFS.label, itemDef.getName())
			.addProperty(RDFS.comment, itemDef.getComment())
			.addProperty(RDFS.domain, ":Finding")
			.addProperty(DC.source, "cardio")
			.addProperty(DC.identifier, itemOid)
			.addProperty(Disco.basedOn, metaDataUri+itemOid)
			.addProperty(LcdcCore.theme,theme);
			
			model.add(stVariableId);
		
			
			
			

	     }//for item 
	  }//for itemDto
	  
	  
  }
  

  /*
   * OntModel to create Cardio-vitalSign
   * */
  public void cardioVital
  (ArrayList<ItemDetail> itemDtos
		  ,HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDefs
		  ,OntModel model){
	  
	 final String baseUri="http://aehrc-ci.it.csiro.au/cardio/lcdc/id/variable-def";
	  
		 

//		RDFDatatype itemgroupcode=tm.getSafeTypeByName(baseDatatype+"itemgroupcode");
//		tm.registerDatatype(itemgroupcode);

	  for (ItemDetail itemDto : itemDtos) { 
		
	//  String definedBy=UriCustomHelper.rdfDefinition(itemDto.itemGroupOid);
		List<ODMcomplexTypeDefinitionItemData> itemList=itemDto.items;
		for (ODMcomplexTypeDefinitionItemData item : itemList) {  
			String itemOidName=item.getItemOID();
	     //	String itemOidval=item.getItemOID();	
	     	//Find itemDef belong to OpenClinica Item 
			ODMcomplexTypeDefinitionItemDef itemDef=itemDefs.get(itemOidName);
			String uri=baseUri+ "#" + itemOidName;
			
			Resource r=model.createResource(uri,LcdcCore.varDefClass);
						
				 Literal valueForm=model.createTypedLiteral(itemDto.formOid,Odm.formcode);
				 Literal valueItemGroup=model.createTypedLiteral(itemDto.itemGroupOid,Odm.itemgroupcode);
				 Literal valueItem=model.createTypedLiteral(item.getItemOID(),Odm.itemcode);
				 
				 //Literal valuevarDef=model.createTypedLiteral(itemDto.g,XSDDatatype.XSD);
				// Statement stVarDef=model.createStatement(re, variableDefinitionKey, valuevarDef);
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
				 r.addProperty(DC.source,"cardio");
				 r.addProperty(RDFS.comment, itemDef.getComment());
				 r.addProperty(RDFS.isDefinedBy, baseUri);
			   	 r.addProperty(RDFS.label, itemDef.getName()); //label
		    }//i
		  
	     }//For loop item		
	  }
  
  /*
   * Generate RDf CodeList 
   * void method
   * version = 0.1.3
   * */
  public void codeListRdf(List<ODMcomplexTypeDefinitionCodeList> codeLists,OntModel model){
	  
	  final String base_Uri="http://aehrc-ci.it.csiro.au/cardio/lcdc/clinical/vitalsigns/def/cardio-vitalsigns#";
	  for(ODMcomplexTypeDefinitionCodeList codeList:codeLists)
	  {
		  List<ODMcomplexTypeDefinitionCodeListItem>  codeL=codeList.getCodeListItem();
		  
		 //number belong to codeList 
		String codeListName=codeList.getName().replaceAll("\\s","");
		 String oids[]=codeList.getOID().split("_");
		 String oid=oids[oids.length-1];
		 
		 //Each code inside codeList 
		  for(ODMcomplexTypeDefinitionCodeListItem item:codeL){	  
			String decodeVal=  item.getDecode().getTranslatedText().get(0).getValue();
			  

				//value of Decode Capitalized no white space
		String decodeValCap=WordUtils.capitalizeFully(decodeVal).replaceAll("\\s","");
		  
		//Resource and properties 
		Resource codeResource =model.createResource(base_Uri+codeListName+oid+"-"+decodeValCap);
		codeResource.addProperty(DC.identifier,	item.getCodedValue());
		codeResource.addProperty(DC.description,decodeVal);
		codeResource.addProperty(RDFS.isDefinedBy,base_Uri);
		codeResource.addProperty(DC.source,"cardio");
	
		  }
		  
	  }
	  
	  
	  
  }
  
  
  
  
  
  
  
  
  
  
  
  /*
   * Observation model 
   * version 0.0.1
   * */
  public void createObservation(ArrayList<ItemDetail> idto){
	  
	  
	  HashMap<String,List<ItemDetail>> groupSubject=groupBySubjectKey(idto);
	  
	  Iterator<Entry<String, List<ItemDetail>>> it=groupSubject.entrySet().iterator();
	  while(it.hasNext()){
		  
		Entry<String,List<ItemDetail>> o=it.next();
		
		 List<ItemDetail> itemDtos=o.getValue();
		
		 
		 //add Model Define ontology add value for eachdataset 
		 
		 
		 
		 
		 
		
		  
	  }
	  
	  
	  
	  
  }
  
  
  
  
}
