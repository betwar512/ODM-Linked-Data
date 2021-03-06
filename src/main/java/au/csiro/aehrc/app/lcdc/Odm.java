package au.csiro.aehrc.app.lcdc;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.TypeMapper;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;



/**
 * Vocabulary definitions from file:C:\Users\abbas\Desktop\EclipseMarsWorkSpace\odm-project\1.1.1\src/main/java/odm.ttl 
 * @author Auto-generated by schemagen on 31 Aug 2015 23:40 
 * 
 * @author Designed by  hugo leroux
 * 
 	* @prefix cc: <http://creativecommons.org/ns#> .
			@prefix dc: <http://purl.org/dc/elements/1.1/> .
			@prefix dcterms: <http://purl.org/dc/terms/> .
			@prefix lcdcodm: <http://purl.org/sstats/lcdc/def/odm#> .
			@prefix owl: <http://www.w3.org/2002/07/owl#> .
			@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .
			@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .
			@prefix skos: <http://www.w3.org/2004/02/skos/core#> .
			@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .
			
			cc:license a owl:AnnotationProperty .
			
			dc:creator a owl:AnnotationProperty .
			
			dc:date a owl:AnnotationProperty .
			
			dc:description a owl:AnnotationProperty .
			
			dc:identifier a owl:AnnotationProperty .
			
			dc:rights a owl:AnnotationProperty .
			
			dc:source a owl:AnnotationProperty .
			
			dc:title a owl:AnnotationProperty .
			
			dcterms:modified a owl:AnnotationProperty .
			
			apivc:uriTemplate a owl:AnnotationProperty .
			
			<http://purl.org/sstats/lcdc/def/odm> a owl:Ontology ;
			    cc:license <http://creativecommons.org/licenses/by/3.0/au/> ;
			    dc:creator "Laurent Lefort, CSIRO"^^xsd:string ;
			    dc:date "2013-04-19" ;
			    dc:description "Linked Clinical Data Cube ODM  ontology" ;
			    dc:rights "Copyright 2013 CSIRO." ;
			    dc:title "Linked Clinical Data Cube ODM  ontology" ;
			    dcterms:modified "2013-04-26" ;
			    void:uriSpace "http://purl.org/sstats/lcdc/def/odm#" .
			
			lcdcodm:ItemOid a owl:DatatypeProperty ;
			    rdfs:label "item OID" ;
			    dc:description "The ID of the Item as defined in ODM." ;
			    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/odm" ;
			    rdfs:range lcdcodm:itemcode .
			
			lcdcodm:comment a owl:DatatypeProperty ;
			    rdfs:label "comment" ;
			    dc:description "A comment associated to the variable in ODM." ;
			    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/odm" ;
			    rdfs:range xsd:string .
			
			lcdcodm:dataType a owl:DatatypeProperty ;
			    rdfs:label "data type" ;
			    dc:description "The datatype of the item as defined in ODM." ;
			    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/odm" ;
			    rdfs:range xsd:string .
			
			lcdcodm:formOid a owl:DatatypeProperty ;
			    rdfs:label "form OID" ;
			    dc:description "The ID for a study event type, as defined in ODM." ;
			    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/odm" ;
			    rdfs:range lcdcodm:formcode .
			
			lcdcodm:formType a owl:DatatypeProperty ;
			    rdfs:label "form type" ;
			    dc:description "The Form name without the version" ;
			    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/odm" ;
			    rdfs:range xsd:string .
			
			lcdcodm:itemGroupOid a owl:DatatypeProperty ;
			    rdfs:label "item group OID" ;
			    dc:description "The ID of the Item Group Type used for the linked observations as defined in ODM." ;
			    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/odm" ;
			    rdfs:range lcdcodm:itemgroupcode .
			
			lcdcodm:itemtext a owl:DatatypeProperty ;
			    rdfs:label "item text" ;
			    dc:description "Text used in the Data Capture tool (ODM) to describe the item." ;
			    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/odm" ;
			    rdfs:range xsd:string .
			
			lcdcodm:nodeOid a owl:DatatypeProperty ;
			    rdfs:label "node OID" ;
			    dc:description "The ID for a node/study, as defined in ODM." ;
			    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/odm" ;
			    rdfs:range lcdcodm:nodecode .
			
			lcdcodm:repeating a owl:DatatypeProperty ;
			    rdfs:label "repeating" ;
			    dc:description "A flag to indicate that the item type this variable belongs to is used as a repeated item." ;
			    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/odm" ;
			    rdfs:range xsd:boolean .
			
			lcdcodm:sectiontext a owl:DatatypeProperty ;
			    rdfs:label "section text" ;
			    dc:description "Text used in the Data Capture tool (ODM) to describe the section the item belongs to." ;
			    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/odm" ;
			    rdfs:range xsd:string .
			
			lcdcodm:studyEventOid a owl:DatatypeProperty ;
			    rdfs:label "study event OID" ;
			    dc:description "The ID for a study event type, as defined in ODM." ;
			    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/odm" ;
			    rdfs:range lcdcodm:studyeventcode .
			
			lcdcodm:studyOID a owl:DatatypeProperty ;
			    rdfs:label "study OID" ;
			    dc:description "The ID for a Clinical data object, as defined in ODM." ;
			    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/odm" ;
			    rdfs:range lcdcodm:studycode .
			
			lcdcodm:subjectKey a owl:DatatypeProperty ;
			    rdfs:label "subject Key" ;
			    dc:description "The Key for a subject (subject), as defined in ODM." ;
			    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/odm" ;
			    rdfs:range lcdcodm:subjectcode .
			
			lcdcodm:formcode a rdfs:Datatype ;
			    rdfs:label "formcode" ;
			    dc:description "Datatype defining a formcode" ;
			    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/odm" .
			
			lcdcodm:itemcode a rdfs:Datatype ;
			    rdfs:label "itemcode" ;
			    dc:description "Datatype defining a itemcode" ;
			    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/odm" .
			
			lcdcodm:itemgroupcode a rdfs:Datatype ;
			    rdfs:label "itemgroupcode" ;
			    dc:description "Datatype defining a itemgroupcode" ;
			    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/odm" .
			
			lcdcodm:nodecode a rdfs:Datatype ;
			    rdfs:label "nodecode" ;
			    dc:description "Datatype defining a nodecode" ;
			    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/odm" .
			
			lcdcodm:studycode a rdfs:Datatype ;
			    rdfs:label "studycode" ;
			    dc:description "Datatype defining a studycode" ;
			    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/odm" .
			
			lcdcodm:studyeventcode a rdfs:Datatype ;
			    rdfs:label "studyeventcode" ;
			    dc:description "Datatype defining a studyeventcode" ;
			    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/odm" .
			
			lcdcodm:subjectcode a rdfs:Datatype ;
			    rdfs:label "subjectcode" ;
			    dc:description "Datatype defining a subjectcode" ;
			    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/odm" .
 * 
 */


  
public class Odm {
    /** <p>The ontology model that holds the vocabulary terms</p> */
    private static OntModel m_model = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, null );
    private static TypeMapper m_tm=TypeMapper.getInstance(); 
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://purl.org/sstats/lcdc/def/odm#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
   
    public static final DatatypeProperty ItemOid = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/odm#ItemOid" );
   
    public static final DatatypeProperty comment = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/odm#comment" );
    
    public static final DatatypeProperty dataType = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/odm#dataType" );
    
    public static final DatatypeProperty formOid = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/odm#formOid" );
    
    public static final DatatypeProperty formType = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/odm#formType" );
    
    public static final DatatypeProperty itemGroupOid = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/odm#itemGroupOid" );
    
    public static final DatatypeProperty itemtext = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/odm#itemtext" );
    
    public static final DatatypeProperty nodeOid = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/odm#nodeOid" );
    
    public static final DatatypeProperty repeating = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/odm#repeating" );
    
    public static final DatatypeProperty sectiontext = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/odm#sectiontext" );
    
    public static final DatatypeProperty studyEventOid = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/odm#studyEventOid" );
    
    public static final DatatypeProperty studyOID = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/odm#studyOID" );
    
    public static final DatatypeProperty subjectKey = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/odm#subjectKey" );

	public static final RDFDatatype itemcode = m_tm.getSafeTypeByName(NS+"itemcode");
	
    public static final RDFDatatype itemgroupcode=m_tm.getSafeTypeByName(NS +"itemgroupcode");
    
    public static final RDFDatatype variabledefinitioncode=m_tm.getSafeTypeByName(NS+"variabledefinitioncode");
    
    public static final RDFDatatype formcode=m_tm.getSafeTypeByName(NS +"formcode");
}
