
package com.mycompany.app.lcdc;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

/**
 * @author Abbas.h.Safaie
 * Extended data cube Mediation
 *
 */
public final class ItemGroup {
	
	
	
	//Base Uri for RDF Source
    protected static final String uri ="http://csiro.org/2015/300/lcdc-schema#";
    
    //Resource base
	 protected static final Resource resource( String local )
       { return ResourceFactory.createResource( uri + local ); }
	 
	 //Property base 
	  protected static final Property property( String local )
      { return ResourceFactory.createProperty( uri, local ); }
	
	
	public static final Property  observationGroup=property("ObservationGroup");
	public static final Property  itemGroupOID=property("ItemGroupOID");
	public static final Property itemGroupRepeatKey=property("itemGroupRepeatKey");
	
	 public String getUri(){
		 return uri;
	 }
	 
	  private ItemGroup(){
		    //this prevents even the native class from 
		    //calling this  as well :
		    throw new AssertionError();
		  }


}
