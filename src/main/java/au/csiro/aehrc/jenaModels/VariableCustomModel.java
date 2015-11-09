package au.csiro.aehrc.jenaModels;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemDef;

import au.csiro.aehrc.CsvHelper;
import au.csiro.aehrc.ItemDetail;
import au.csiro.aehrc.StringCustomHelper;
import au.csiro.aehrc.UriCustomHelper;
import au.csiro.aehrc.app.lcdc.LcdcCore;
import au.csiro.aehrc.app.lcdc.Odm;
import au.csiro.aehrc.app.lcdc.Skos;
import au.csiro.aehrc.app.lcdc.Snomed;

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

public class VariableCustomModel {

	
	  
	  /*
	   * Model Variable Vital 
	   * Model Variable ,Model Linkset 
	   * 
	   * */
	  public static void generateVariable
	  (ArrayList<ItemDetail> itemDtos
			  ,HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDefs
			  ,ModelMaker mm){
		  //model for variable 
		  Model model=null;
		

		  for (ItemDetail itemDto : itemDtos) { 
	
			List<ODMcomplexTypeDefinitionItemData> itemList=itemDto.items;
			for (ODMcomplexTypeDefinitionItemData item : itemList) {  

				String itemOidName=item.getItemOID();
				
				//Find itemDef belong to OpenClinica Item 
				ODMcomplexTypeDefinitionItemDef itemDef=itemDefs.get(itemOidName);
				//Get theme Mapped CSV file  
				
				
				String theme="";
				try {
					theme=CsvHelper.getTheme(itemOidName);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//=StringCustomHelper.groupType(itemOidName).toLowerCase();
				String modelName=StringCustomHelper.modelName(StringCustomHelper.getVariablemodel(),theme);
				 model=mm.createModel(modelName);
				 
				  Property themP=model.createProperty(UriCustomHelper.getThemebase(), theme);
				
		     	
				String uri=UriCustomHelper.generateVariableDef(itemOidName);
				
				Resource r=model.createResource(uri,LcdcCore.VariableDefinition);
							
					 Literal valueForm		=model.createTypedLiteral(itemDto.formOid,Odm.formcode);
					 Literal valueItemGroup	=model.createTypedLiteral(itemDto.itemGroupOid,Odm.itemgroupcode);
					 Literal valueItem		=model.createTypedLiteral(item.getItemOID(),Odm.itemcode);
					 Literal valuevarDef	=model.createTypedLiteral(itemDef.getName(),LcdcCore.variabledefinitioncode.getURI());
					 Literal valueRepeat	=model.createTypedLiteral(itemDto.repeating,XSDDatatype.XSDboolean);	
					 Literal valueDatatype	=model.createTypedLiteral(itemDef.getDataType()
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
					 r.addProperty(RDFS.isDefinedBy, UriCustomHelper.getMetabase());
				   	 r.addProperty(RDFS.label, itemDef.getName()); //label
				   	 r.addProperty(LcdcCore.variableDefinitionKey,valuevarDef);
				   	 r.addProperty(LcdcCore.themeId, themP);
				   	 
				   	 
				   	 

					  //================================================================================
				   	//  Model LinkSet 
					//================================================================================
			  
			 
				   	linkSet(uri,itemOidName,theme,mm);

  	 
			    }//i
			  
		     }//For loop item		
		  }
	  
	  
	  /*
	   * Model Linkset 
	   * @private
	   * input: String uri,String Odm.itemOid,String item.theme,ModelMaker Jena.ModelMaker 
	   * Gets called inside variable generator method ,(Having similar object pattern)
	   * 
	   * */
	  private static void linkSet(String uri,String itemOid,String theme,ModelMaker mm){
		String modelName=StringCustomHelper.modelName(StringCustomHelper.getLinksetmodel(), theme);
		  Model  modelLs=mm.createModel(modelName);
		   	String snomedUri="";
		   	String extLabel="";
		   	
		   	//Prefix setting for LinkSet file 
		   	modelLs.setNsPrefix("snomed", Snomed.snomedUri);
		   	modelLs.setNsPrefix("snomedmod", Snomed.snomedmodUri);
		   	modelLs.setNsPrefix("amt", Snomed.amtUri);
		   	modelLs.setNsPrefix("skos",Skos.getURI());
		   	//Resource linkSet 
		   	Resource linkSet=modelLs.createResource(uri);
		
		   	try {
				 snomedUri=CsvHelper.getSnomedUri(itemOid); //matched snomedUri 
				 extLabel = CsvHelper.getLabel(itemOid);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   	if(snomedUri.length()>0){ //Check if snomed uri exist in lcdcMapping
		  	Resource  namedIndv=modelLs.createResource(snomedUri,OWL2.NamedIndividual); //Resource nameIndivitual
				linkSet.addProperty(RDFS.subPropertyOf, namedIndv); //add nameIndividual Resource to llinkSet Resource
			   	 namedIndv.addProperty(RDFS.label,extLabel);
		  
	    }
	  }
	  
	  
	  
}
