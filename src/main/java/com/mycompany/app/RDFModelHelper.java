package com.mycompany.app;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionClinicalData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionCodeList;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionCodeListItem;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemDef;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionMetaDataVersion;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.TypeMapper;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.XSD;
import com.mycompany.app.lcdc.Disco;
import com.mycompany.app.lcdc.LcdcCore;
import com.mycompany.app.lcdc.Obs;
import com.mycompany.app.lcdc.Odm;
import com.mycompany.app.lcdc.Skos;


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
	   	// MetaData model   Test V 2.0
		//================================================================================
  
 
//Test Method to call 
  public ModelMaker ontoModelTest(String filePath){
	  
		//Model
		//OntModel model=ModelFactory.createOntologyModel();
	

		
		PrefixMapping pf=PrefixMapping.Factory.create();

			pf.withDefaultMappings(PrefixMapping.Standard);
		
		pf.setNsPrefix("lcdcodm", Odm.getURI());
		pf.setNsPrefix("dc",  DC.getURI());	
		pf.setNsPrefix("lcdccore",LcdcCore.getURI());
		pf.setNsPrefix("disco",Disco.getURI());
		pf.setNsPrefix("cardiovitalsigns", "http://aehrc-ci.it.csiro.au/cardio/lcdc/vitalsigns/def/cardio-vitalsigns#");
		pf.setNsPrefix("lcdcobs", Obs.getURI());
		pf.setNsPrefix("skos", Skos.getURI());
		ModelFactory.setDefaultModelPrefixes(pf);
		
		     ModelMaker mm=ModelFactory.createMemModelMaker();
		
			JaxBinder myJax=new JaxBinder();
			ODMcomplexTypeDefinitionClinicalData clinicalData=
			myJax.getClinicalData(filePath);
			ODMcomplexTypeDefinitionMetaDataVersion meta =JaxBinder.catchMetaData(filePath);
	
	    HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDef=JaxBinder.catchItemDef(meta);
        ArrayList<ItemDetail> itemDtos=myJax.makeItemsObjects(clinicalData,meta);
  
        ///model codeList 
        
        HashMap<String, ODMcomplexTypeDefinitionCodeList> codeLists=JaxBinder.catchCodeList(meta);
        Collection<ODMcomplexTypeDefinitionCodeList>	codeList= codeLists.values();
      
        
        codeListRdf(codeList,mm);
      createObservation(mm,itemDtos,itemDef,meta);
  //	completeRdf(itemDtos,model); 
  	variableVital(itemDtos,itemDef,mm);
  	generateCardio(itemDtos,itemDef,mm);  
  	return mm;
	  
  }
  
  
  
  /*
   * Model to create vital-vitalSign
   * */
  public void generateCardio(ArrayList<ItemDetail> itemDtos
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
			  if(itemDto.isVital()){
					typeUri=baseUri+"vitalsigns/def/cardio-vitalsigns";
					theme="vitalsigns";
					model=mm.createModel("Cardio-Vital");;
				  }else if(itemDto.isBlood()){
					  model=mm.createModel("Cardio-Blood");
					  typeUri=baseUri+"blood/def/cardio-blood";
					  theme="blood";
				  }else if(itemDto.isMedic()){
					  model=mm.createModel("Cardio-Medication");
					  typeUri=baseUri+"/medic/def/cardio-medic";
					  theme="medication";
				  }
			  Property themP=model.createProperty("http://purl.org/sstats/lcdc/id/theme/", theme);
			  
	//  String definedBy=UriCustomHelper.rdfDefinition(itemDto.itemGroupOid);
		List<ODMcomplexTypeDefinitionItemData> itemList=itemDto.items;
		for (ODMcomplexTypeDefinitionItemData item : itemList) {  
			String itemOidName=item.getItemOID();
	     //	String itemOidval=item.getItemOID();	
	     	//Find itemDef belong to OpenClinica Item 
			ODMcomplexTypeDefinitionItemDef itemDef=itemDefs.get(itemOidName);
		//	List<ODMcomplexTypeDefinitionRangeCheck> listRange=itemDef.getRangeCheck();


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

		Property based=model.createProperty("http://purl.org/sstats/lcdc/def/cardio#",itemOid);
		
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
   *Decode value for codeList 
   *Return type String  
   * 
   *3 args :(String,String,ODMcomplexTypeDefinitionMetaDataVersion)
   * */
  
  public String codeListValue(String codeListOid,String code ,ODMcomplexTypeDefinitionMetaDataVersion metaData){
	  
	  String decode="";
	 HashMap<String,ODMcomplexTypeDefinitionCodeList> hashMap= JaxBinder.catchCodeList(metaData);  
	 ODMcomplexTypeDefinitionCodeList codeList=hashMap.get(codeListOid); 
	 List<ODMcomplexTypeDefinitionCodeListItem>  codeL=codeList.getCodeListItem();
	
	 	for(ODMcomplexTypeDefinitionCodeListItem item:codeL){		
	 		if(item.getCodedValue().contains(code)){
	 			String decodeVal=  item.getDecode().getTranslatedText().get(0).getValue();  
				//value of Decode Capitalized no white space
		decode=WordUtils.capitalizeFully(decodeVal).replaceAll("\\s","");		
	 			
	 		}		
	 	} 
	 	
	 return decode;
  }
  
  
  
  /*
   * Model Variable Vital 
   * separate models by dataType Vital Blood Medication
   * Void 
   * 
   * */
  public void variableVital
  (ArrayList<ItemDetail> itemDtos
		  ,HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDefs
		  ,ModelMaker mm){
	  Model model=null;
	 final String baseUri="http://aehrc-ci.it.csiro.au/cardio/lcdc/id/variable-def";

	  for (ItemDetail itemDto : itemDtos) { 
			String theme=""; 
			  if(itemDto.isVital()){
				model=mm.createModel("Variable-Vital");
					theme="vitalsigns";
					
				  }else if(itemDto.isBlood()){ 
					model=mm.createModel("Variable-Blood");
					  theme="blood";
			
				  }else if(itemDto.isMedic()){ 
				model=mm.createModel("Variable-Medication");
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
  
  /*
   * Generate RDf CodeList 
   * void method
   * version = 0.2.0
   * */
  public void codeListRdf(Collection<ODMcomplexTypeDefinitionCodeList> codeLists,ModelMaker mm){
	  Model model=mm.createModel("Code-List-all");
	  final String base_Uri="http://aehrc-ci.it.csiro.au/cardio/lcdc/clinical/vitalsigns/def/cardio-vitalsigns";
	  
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
		Resource codeResource =model.createResource(base_Uri+"#"+codeListName+oid+"-"+decodeValCap,Skos.Concept);
		codeResource.addProperty(DC.identifier,	item.getCodedValue());
		codeResource.addProperty(DC.description,decodeVal);
		codeResource.addProperty(RDFS.isDefinedBy,base_Uri);
		codeResource.addProperty(DC.source,"cardio");

		  }		  
	  }  
  }

  
  /*
   * Observation model 
   * version 1.0.0
   * */
  public void createObservation
  (ModelMaker mm,ArrayList<ItemDetail> itemDtos,HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDefs,ODMcomplexTypeDefinitionMetaDataVersion meta){
	  Model model=null;
	  final String cardioVitalSign="http://aehrc-ci.it.csiro.au/cardio/lcdc/vitalsigns/def/cardio-vitalsigns#";

	  for (ItemDetail itemDto : itemDtos) { 
		  	//ItemsDto typeData 
		  String theme="";
		  String baseUriType="http://purl.org/sstats/lcdc/id/";
		  String obsUri="";
		  if(itemDto.isVital()){
			theme="vitalsigns";
			obsUri="http://aehrc-ci.it.csiro.au/dataset/cardio/lcdc/20150713/theme/vitalsigns/phase/";
			model=mm.createModel("observations-VitalSigns");
		  }else if(itemDto.isBlood()){ 
			  theme="blood";
			  obsUri="http://aehrc-ci.it.csiro.au/dataset/cardio/lcdc/20150713/theme/blood/phase/";
			  model=mm.createModel("observations-Blood");
		  }else if(itemDto.isMedic()){ 
			  theme="medication";
			  obsUri="http://aehrc-ci.it.csiro.au/dataset/cardio/lcdc/20150713/theme/medication/phase/";
			  model=mm.createModel("observations-medication");
		  }
		  Property themeP=model.createProperty("http://purl.org/sstats/lcdc/id/theme/", theme);
	//  String definedBy=UriCustomHelper.rdfDefinition(itemDto.itemGroupOid);
		List<ODMcomplexTypeDefinitionItemData> itemList=itemDto.items;
		for (ODMcomplexTypeDefinitionItemData item : itemList) {  
			String itemOidName=item.getItemOID();
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
				String decode=codeListValue(codeListOid,item.getValue(),meta);
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
