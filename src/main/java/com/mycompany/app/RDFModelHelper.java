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
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ModelFactory;
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
			
			
			JaxBinder myJax=new JaxBinder();
			ODMcomplexTypeDefinitionClinicalData clinicalData=
			myJax.getClinicalData("src/main/java/odm1.3_clinical_ext_Full_study_extract_2015-05-22-162457368.xml");
			ODMcomplexTypeDefinitionMetaDataVersion meta =JaxBinder.catchMetaData("src/main/java/odm1.3_clinical_ext_Full_study_extract_2015-05-22-162457368.xml");
	
	    HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDef=JaxBinder.catchItemDef(meta);
        ArrayList<ItemDetail> itemDtos=myJax.makeItemsObjects(clinicalData,meta);
  
  //	completeRdf(itemDtos,model); 
  //	cardioVital(itemDtos,itemDef,model);
  	vitalVitalSign(itemDtos,itemDef,model);
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
		  	//ItemsDto typeData 
			  if(itemDto.isVital()){
				typeUri=baseUri+"vitalsigns/def/cardio-vitalsigns";
				theme="http://purl.org/sstats/lcdc/id/theme/vitalsigns";
			  }else if(itemDto.isBlood()){
				  typeUri=baseUri+"blood/def/cardio-blood";
				  theme="http://purl.org/sstats/lcdc/id/theme/blood";
			  }else if(itemDto.isMedic()){
				  typeUri=baseUri+"/medic/def/cardio-medic";
				  theme="http://purl.org/sstats/lcdc/id/theme/medication";
			  }
			  
			  
			
				
				
			  	//main uri 
			String uri= typeUri +"#"+ itemDef.getName();
			String itemOid=item.getItemOID();
			Resource r=model.createResource(uri,OWL.DatatypeProperty);
			
			if(!itemDef.getRangeCheck().isEmpty()){
				String range=itemDef.getDataType().toString();
				if(range.contains("INTEGER")){
					r.addProperty(RDFS.range, XSD.integer);
				}else if(range.contains("FLOAT")){
					r.addProperty(RDFS.range, XSD.xfloat);
				}
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
}
