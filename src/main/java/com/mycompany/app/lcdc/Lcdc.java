package com.mycompany.app.lcdc;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

public class Lcdc {

	
	
	

	//Base Uri for RDF Source
protected static final String uri ="http://csiro.org/2015/01/lcdc-schema#";

//Resource base
 protected static final Resource resource( String local )
   { return ResourceFactory.createResource( uri + local ); }
 
 //Property base 
  protected static final Property property( String local )
  { return ResourceFactory.createProperty( uri, local ); }

  //Top level 
  public static final Property subjectKey=property("SubjectKey");
  public static final Property eventOID=property("EventOID");
  public static final Property formOID=property("FormOID");
  
  
  //Item
 public static final Property value =property( "Value");	 
 public static final Property itemOid =property("ItemOID");
 public static final Property measurementUnitOID=property("MeasurementUnitOID");
 //ItemGroup
	public static final Property  observationGroup=property("ObservationGroup");
	public static final Property  itemGroupOID=property("ItemGroupOID");
	public static final Property itemGroupRepeatKey=property("itemGroupRepeatKey");
 
 public String getUri(){
	 return uri;
 }
 
private Lcdc(){
	
	throw new AssertionError();
	
}
}
