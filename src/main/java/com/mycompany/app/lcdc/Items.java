/**
 * @author Abbas.h.Safaie
 * 
 * */
package com.mycompany.app.lcdc;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;


//Item static class final 
public final class Items {

	

		//Base Uri for RDF Source
    protected static final String uri ="http://csiro.org/2015/01/lcdc-schema#";
    
    //Resource base
	 protected static final Resource resource( String local )
       { return ResourceFactory.createResource( uri + local ); }
	 
	 //Property base 
	  protected static final Property property( String local )
      { return ResourceFactory.createProperty( uri, local ); }
	
	  //Value
	 public static final Property value =property( "Value");	 
	 public static final Property itemOid =property("ItemOID");
	 public static final Property measurementUnitOID=property("MeasurementUnitOID");

	 
	 public String getUri(){
		 return uri;
	 }
	 
	private Items(){
		
		throw new AssertionError();
		
	}

}
