/**
 * @author Abbas.H.Safaie
 */
package au.csiro.aehrc;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.text.WordUtils;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionCodeList;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionCodeListItem;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionMetaDataVersion;

import au.csiro.aehrc.app.lcdc.Skos;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

/**
 * @author Abbas H Safaie
 *
 */
public class CodeListDecoder {

	
	  /*
	   *Decode value for codeList 
	   *Return type String  
	   * 
	   *3 args :(String,String,ODMcomplexTypeDefinitionMetaDataVersion)
	   * */
	public static String codeListValue(String codeListOid,String code ,ODMcomplexTypeDefinitionMetaDataVersion metaData){
		  
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
	 * codelist property with decoder
	 * return: String 
	 * args : String codeListOid,String itemDefName,String decode
	 * */
	public static String codeListProperty(String codeListOid,String itemDefName,String decode){
		
		String coCl[]=codeListOid.split("_");
		String codeNumber=coCl[coCl.length-1];
		String property=itemDefName+codeNumber+"-"+decode;
		return property;
	}
	
	/*
   * Generate RDf CodeList 
   * void method
   * version = 0.5.0
   * 
   * Adding codeList to relevant Model 
   * 
   * Input : Base_Uri for resource , codeList id , ODM meta data , model 
   * */
	
	  public static void codeListRdf(String base_Uri,String codeListOid,ODMcomplexTypeDefinitionMetaDataVersion metaData,Model model){
	
		//  final String base_Uri="http://aehrc-ci.it.csiro.au/cardio/lcdc/clinical/vitalsigns/def/cardio-vitalsigns";  
		  HashMap<String,ODMcomplexTypeDefinitionCodeList> hashMap= JaxBinder.catchCodeList(metaData);  
			 ODMcomplexTypeDefinitionCodeList codeList=hashMap.get(codeListOid); 
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
			Resource codeResource =model.createResource(base_Uri+codeListName+oid+"-"+decodeValCap,OWL.Class);	
			codeResource.addProperty(RDF.type,Skos.Concept);
			codeResource.addProperty(DC.identifier,	item.getCodedValue());
			codeResource.addProperty(DC.description,decodeVal);
			codeResource.addProperty(RDFS.isDefinedBy,base_Uri);
			
			//TODO: must replace this by a static variable stating the study short name
			codeResource.addProperty(DC.source,"cardio");

			  }		  
		  }  
	  
	
	
	
	
}
