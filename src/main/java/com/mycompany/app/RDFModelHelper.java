package com.mycompany.app;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.text.WordUtils;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionClinicalData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionCodeList;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionCodeListItem;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemDef;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionMetaDataVersion;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.RDFS;
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
			ODMcomplexTypeDefinitionMetaDataVersion meta = JaxBinder.catchMetaData(filePath);
	
	    HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDef = JaxBinder.catchItemDef(meta);
        ArrayList<ItemDetail> itemDtos = myJax.makeItemsObjects(clinicalData,meta);
  
        ///model codeList 
        
        HashMap<String, ODMcomplexTypeDefinitionCodeList> codeLists= JaxBinder.catchCodeList(meta);
        Collection<ODMcomplexTypeDefinitionCodeList>       codeList= codeLists.values();
      
        
      codeListRdf(codeList,mm);
     ObservationCustomModel.createObservation(mm,itemDtos,itemDef,meta);
      SliceCustomModel.sliceThemePhase(mm, itemDtos, itemDef, meta);
      VitalCustomModel.variableVital(itemDtos,itemDef,mm);
      VitalCustomModel.generateCardio(itemDtos,itemDef,mm);  
  	return mm;
	  
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

  
 
  
}
