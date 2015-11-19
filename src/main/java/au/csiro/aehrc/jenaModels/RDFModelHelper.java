package au.csiro.aehrc.jenaModels;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionClinicalData;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemDef;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionMetaDataVersion;

import au.csiro.aehrc.CsvHelper;
import au.csiro.aehrc.JaxBinder;
import au.csiro.aehrc.app.lcdc.Cardioblood;
import au.csiro.aehrc.app.lcdc.Cardiomedic;
import au.csiro.aehrc.app.lcdc.Cardiovitalsigns;
import au.csiro.aehrc.app.lcdc.Disco;
import au.csiro.aehrc.app.lcdc.LcdcCore;
import au.csiro.aehrc.app.lcdc.Obs;
import au.csiro.aehrc.app.lcdc.Odm;
import au.csiro.aehrc.app.lcdc.Qb;
import au.csiro.aehrc.app.lcdc.Skos;
import au.csiro.aehrc.utils.ItemDetail;

import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.vocabulary.DC;


/**
 * @author Abbas H Safaie
 * RDFModel /helper class to generate model and add specified properties to the model 
 * Main function CreateModel 
 *
 */
public class RDFModelHelper {


  
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
	   	//  model   Test V 4.0
		//================================================================================
  
 
//Test Method to call 
  public ModelMaker modelHandler(String filePath){

		PrefixMapping pf=PrefixMapping.Factory.create();
			//Default mapping for Models ,Added here to set modelFactory prefix for all the models 
		pf.withDefaultMappings(PrefixMapping.Standard);
		pf.setNsPrefix("lcdcodm", Odm.getURI());
		pf.setNsPrefix("dc",  DC.getURI());	
		pf.setNsPrefix("lcdccore",LcdcCore.getURI());
		pf.setNsPrefix("disco",Disco.getURI());
		pf.setNsPrefix("lcdcobs", Obs.getURI());
		pf.setNsPrefix("skos", Skos.getURI());
		pf.setNsPrefix("qb", Qb.getURI());
		pf.setNsPrefix("cardiovitalsigns",Cardiovitalsigns.getURI() );
		pf.setNsPrefix("cardiomedic",Cardiomedic.getURI());
		pf.setNsPrefix("cardioblood",Cardioblood.getURI());
		
		ModelFactory.setDefaultModelPrefixes(pf);
		
	 ModelMaker mm=ModelFactory.createMemModelMaker();
		
		     	mm.createDefaultModel();
		    //unmarshal binding 
			JaxBinder myJax=new JaxBinder(filePath);
			ODMcomplexTypeDefinitionClinicalData clinicalData=
			myJax.getClinicalData();
			ODMcomplexTypeDefinitionMetaDataVersion meta = myJax.catchMetaData();
	
			HashMap<String,ODMcomplexTypeDefinitionItemDef> itemDef = JaxBinder.catchItemDef(meta);
			ArrayList<ItemDetail> itemDtos = myJax.makeItemsObjects(clinicalData,meta);
				CsvHelper csvHlp=null;
				try {
					csvHlp = new CsvHelper();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
     	//Cardio Variables 
        VariableCustomModel.generateVariable(itemDtos,itemDef,mm,csvHlp);
        CardioCustomModel.generateCardio(itemDtos,itemDef,mm,csvHlp);  
      
      //Observation Models 
      ObservationCustomModel.generateObservation(mm,itemDtos,itemDef,meta,csvHlp);
      //Slice Models 
      SliceCustomModel.sliceBySubject(mm, itemDtos, itemDef, meta,csvHlp);
      SliceCustomModel.sliceByTheme( mm,  itemDtos, itemDef, meta,csvHlp);
      SliceCustomModel.sliceByPhase( mm,  itemDtos, itemDef, meta,csvHlp);
      
  	return mm;
	  
  }
}
