/**
 * @author Abbas.H.Safaie
 * 
 */
package au.csiro.aehrc.jenaModels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemDef;
import au.csiro.aehrc.CsvHelper;
import au.csiro.aehrc.StringCustomHelper;
import au.csiro.aehrc.UriCustomHelper;
import au.csiro.aehrc.typeChecker;
import au.csiro.aehrc.app.lcdc.Disco;
import au.csiro.aehrc.app.lcdc.LcdcCore;
import au.csiro.aehrc.utils.ItemDetail;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDFS;

/**
 * @author Abbas H Safaie
 *
 */
public class CardioCustomModel {

	
	
	  /*
	   * Cardio model 
	   * Model name : Cardio-'rest of the name captures from theme'
	   * 
	   * 
	   * */
	  public static void generateCardio(ArrayList<ItemDetail> itemDtos
			  ,HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDefs
			  ,ModelMaker mm,CsvHelper csvHlp){
		  
		  Model model=null;
		 
		  for (ItemDetail itemDto : itemDtos) { 	
			List<ODMcomplexTypeDefinitionItemData> itemList=itemDto.items;
			for (ODMcomplexTypeDefinitionItemData item : itemList) {  
				String itemOidName=item.getItemOID();	
		     	//Find itemDef belong to OpenClinica Item 
				ODMcomplexTypeDefinitionItemDef itemDef=itemDefs.get(itemOidName);
					/*
					 * gets the theme from the item name
					 * ideally, the theme should be passed in
					 * 
					 * */
				String theme=csvHlp.getTheme(itemOidName);
	
				// have to be more generic:Changed
				String typeUri=UriCustomHelper.generateCardioUri(theme);
				 String modelName=StringCustomHelper.modelName(StringCustomHelper.getCardiomodel(), theme);
				//more geeric 
				 model=mm.createModel(modelName);	
				 Property themP=model.createProperty(UriCustomHelper.getThemebase(), theme);
				  	//main uri 
				String uri= typeUri + itemDef.getName().toLowerCase();
				String itemOid=item.getItemOID();	
				// create a data type property resource for the theme concepts
				Resource r=model.createResource(uri,OWL.DatatypeProperty);
			    	String range=itemDef.getDataType().toString();//range type in string format				
						Resource typeRangeResource=typeChecker.rangeResourcetype(range);
						r.addProperty(RDFS.range, typeRangeResource);

				//pointing to Variable Def 
				Property based=model.createProperty(UriCustomHelper.generateVariableDef(itemOid));					
				//domain from CsvFile	
					String domain=csvHlp.getDomain(itemOidName);

					if (null!= domain && !domain.isEmpty()) //Check if domain exist in lcdcMap
					 r.addProperty(RDFS.domain, domain);
						
			
				
		
			   r.addProperty(RDFS.isDefinedBy, typeUri)
				.addProperty(RDFS.label, itemDef.getName())
				.addProperty(RDFS.comment, itemDef.getComment())
				.addProperty(DC.source, "cardio")
				.addProperty(DC.identifier, itemOid)
				.addProperty(Disco.basedOn, based)
				.addProperty(LcdcCore.themeId,themP);		
				
			//	model.add(stVariableId);
		     }//for item 
		  }//for itemDto  
	  }
	  
}
