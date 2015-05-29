package com.mycompany.app;
import java.util.List;

import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionMetaDataVersion;
import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionStudy;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	// TODO Auto-generated method stub

    	JaxBinder myJax=new JaxBinder();
    	
    	List<ODMcomplexTypeDefinitionStudy> study=myJax.catchStudy("src/main/java/odm1.3_clinical_ext_InitialExtract_2015-04-15-101034016.xml");	
    	ODMcomplexTypeDefinitionMetaDataVersion metaData=myJax.catchMetaData(study);
    	
    	
    }
}
