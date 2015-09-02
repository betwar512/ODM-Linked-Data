package com.mycompany.app.lcdc;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.TypeMapper;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

public class Disco {

	
	
	
	/** <p>The ontology model that holds the vocabulary terms</p> */
    private static OntModel m_model = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, null );
    private static TypeMapper m_tm=TypeMapper.getInstance();  
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://rdf-vocabulary.ddialliance.org/discovery#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
   
    public static final DatatypeProperty basedOn = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/odm#basedOn" );
    
    
   public static final  RDFDatatype itemcode=m_tm.getSafeTypeByName("itemcode");
   	
		
}
