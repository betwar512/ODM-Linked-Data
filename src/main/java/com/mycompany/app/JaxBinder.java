package com.mycompany.app;
import java.io.File;
import java.util.List;
import javax.xml.bind.*;
import org.cdisc.ns.odm.v1.*;

public class JaxBinder {

	//itemDef
	public List<ODMcomplexTypeDefinitionItemDef> catchItemDef(ODMcomplexTypeDefinitionMetaDataVersion metaData){
		
		
	List<ODMcomplexTypeDefinitionItemDef> itemDef=metaData.getItemDef();
		
	
		return itemDef;
		
	}
	

	//returning metaData belong to study
	public ODMcomplexTypeDefinitionMetaDataVersion catchMetaData(List<ODMcomplexTypeDefinitionStudy> study){
		
		
		ODMcomplexTypeDefinitionMetaDataVersion metaData=study.get(0).getMetaDataVersion().get(0);
	
		return metaData;
	}
	
	
	
	//return Study belong to Xml file 
	public List<ODMcomplexTypeDefinitionStudy> catchStudy(String inputFile){
		
		if(inputFile!=null){
		try{
			
			org.cdisc.ns.odm.v1.ObjectFactory factory=new org.cdisc.ns.odm.v1.ObjectFactory();
			
			JAXBContext myJaxb=JAXBContext.newInstance
					(factory.getClass());
		    Unmarshaller u = 
		            myJaxb.createUnmarshaller();
		    org.cdisc.ns.odm.v1.ODM o=(ODM) u.unmarshal(new File(inputFile));
		    System.out.println("o created");
		   List<ODMcomplexTypeDefinitionStudy> study=o.getStudy();
		 	
		   study.get(0);
		   
		    return study;	    
		    }catch(JAXBException e){
		    	e.printStackTrace();    	
		    };
		}
	
		return null;	
	}

}

