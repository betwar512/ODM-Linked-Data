/**
 * 
 */
package com.mycompany.app;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionCodeList;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionCodeListItem;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionMetaDataVersion;

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
	  
	
	
}
