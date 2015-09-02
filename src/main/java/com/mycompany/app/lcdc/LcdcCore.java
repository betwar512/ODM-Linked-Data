package com.mycompany.app.lcdc;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.TypeMapper;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class LcdcCore {
    /** <p>The ontology model that holds the vocabulary terms</p> */
    private static OntModel m_model = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, null );
    private static TypeMapper m_tm=TypeMapper.getInstance(); 
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://purl.org/sstats/lcdc/def/core#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}

    public static final Resource NAMESPACE = m_model.createResource( NS );
    
    public static final OntClass varDefClass=m_model.createClass(NS + "VariableDefinition");
    
    public static final DatatypeProperty theme = m_model.createDatatypeProperty( NS + "theme" );
    
    public static final Property variableId = m_model.createProperty( NS + "variableId" );
    
    public static final RDFDatatype variablecode=m_tm.getSafeTypeByName(NS+"variablecode"); 
}
