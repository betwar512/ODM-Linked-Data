package au.csiro.aehrc.app.lcdc;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import com.hp.hpl.jena.rdf.model.Resource;


import com.hp.hpl.jena.ontology.*;

/**
 * Vocabulary definitions from core.ttl
 * @author Abbas.h.Safaie by schemagen on 08 Sep 2015 01:02
 * 
 * @author Designed by  hugo leroux
 * 
 * Full ontology 
 * @prefix apivc: <http://purl.org/linked-data/api/vocab#> .
		@prefix cc: <http://creativecommons.org/ns#> .
		@prefix dc: <http://purl.org/dc/selements/1.1/> .
		@prefix dcterms: <http://purl.org/dc/terms/> .
		@prefix disco: <http://rdf-vocabulary.ddialliance.org/discovery#> .
		@prefix interval: <http://reference.data.gov.uk/def/intervals/> .
		@prefix lcdccore: <http://purl.org/sstats/lcdc/def/core#> .
		@prefix owl: <http://www.w3.org/2002/07/owl#> .
		@prefix qb: <http://purl.org/linked-data/cube#> .
		@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .
		@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .
		@prefix skos: <http://www.w3.org/2004/02/skos/core#> .
		@prefix void: <http://rdfs.org/ns/void#> .
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
		
		<http://purl.org/sstats/lcdc/def/core> a owl:Ontology ;
		    cc:license <http://creativecommons.org/licenses/by/3.0/au/> ;
		    dc:creator "Hugo Leroux, The Australian E-Health Research Centre, CSIRO"^^xsd:string ;
		    dc:date "2015-07-09" ;
		    dc:description "Linked Clinical Data Cube Core ontology" ;
		    dc:rights "Copyright 2013 CSIRO." ;
		    dc:title "Linked Clinical Data Cube Core ontology" ;
		    dcterms:modified "2015-07-09" ;
		    void:uriSpace "http://purl.org/sstats/lcdc/def/core#" .
		
		lcdccore:Node a owl:Class ;
		    rdfs:label "Node" ;
		    dc:description "A node for a study" ;
		    apivc:uriTemplate "http://purl.org/sstats/{linked}/id/node/{node}" ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" .
		
		lcdccore:Phase a owl:Class ;
		    rdfs:label "Phase" ;
		    dc:description "A phase of a study" ;
		    apivc:uriTemplate "http://purl.org/sstats/{linked}/id/phase/{phase}" ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" ;
		    rdfs:subClassOf [ a owl:Restriction ;
		            owl:onProperty lcdccore:phaseInterval ;
		            owl:someValuesFrom interval:CalendarInterval ],
		        disco:LogicalDataSet .
		
		lcdccore:Questionnaire a owl:Class ;
		    rdfs:label "Questionnaire" ;
		    dc:description "Placeholder for question text." ;
		    apivc:uriTemplate "http://purl.org/sstats/{linked}/id/questionnaire/{questionnaire}" ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" ;
		    rdfs:subClassOf [ a owl:Restriction ;
		            owl:onProperty disco:question ;
		            owl:someValuesFrom lcdccore:Question ],
		        disco:Questionnaire .
		
		lcdccore:Study a owl:Class ;
		    rdfs:label "Study" ;
		    dc:description "A sub-part of a longitudinal study or clinical trial" ;
		    apivc:uriTemplate "http://purl.org/sstats/{linked}/id/study/{study}" ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" ;
		    rdfs:subClassOf [ a owl:Restriction ;
		            owl:onProperty disco:inGroup ;
		            owl:someValuesFrom lcdccore:StudyGroup ],
		        [ a owl:Restriction ;
		            owl:onProperty lcdccore:studyInterval ;
		            owl:someValuesFrom interval:CalendarInterval ],
		        [ a owl:Restriction ;
		            owl:onProperty disco:instrument ;
		            owl:someValuesFrom disco:Instrument ],
		        disco:Study .
		
		lcdccore:Subject a owl:Class ;
		    rdfs:label "Subject" ;
		    dc:description "A subject to a study" ;
		    apivc:uriTemplate "http://purl.org/sstats/{linked}/id/subject/{subject}" ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" .
		
		lcdccore:Variable a owl:Class ;
		    rdfs:label "variable ID" ;
		    dc:description "The type of a data item associated to an observation as encoded in ODM. Also a qb:ComponentProperty in QB context." ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" ;
		    rdfs:subClassOf [ a owl:Restriction ;
		            owl:onProperty disco:basedOn ;
		            owl:someValuesFrom lcdccore:VariableDefinition ],
		        [ a owl:Restriction ;
		            owl:onProperty disco:concept ;
		            owl:someValuesFrom skos:Concept ],
		        [ a owl:Restriction ;
		            owl:onProperty disco:question ;
		            owl:someValuesFrom lcdccore:Question ],
		        qb:ComponentProperty,
		        disco:Variable .
		
		lcdccore:gender a owl:DatatypeProperty ;
		    rdfs:label "gender" ;
		    dc:description "The gender of the subject" ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" ;
		    rdfs:range xsd:string .
		
		lcdccore:node a owl:ObjectProperty ;
		    rdfs:label "node" ;
		    dc:description "The identification key for the subject subject of the observation..",
		        "The node for this slice." ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" .
		
		lcdccore:nodeKey a owl:DatatypeProperty ;
		    rdfs:label "node key" ;
		    dc:description "The identification key for the node.",
		        "The identification key for this node of the study, if available." ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" ;
		    rdfs:range lcdccore:nodecode .
		
		lcdccore:obsDate a owl:DatatypeProperty ;
		    rdfs:label "obs date" ;
		    dc:description "The date of collection of the observation data," ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" ;
		    rdfs:range xsd:date .
		
		lcdccore:obsInterval a owl:ObjectProperty ;
		    rdfs:label "obs interval" ;
		    dc:description "The interval associated with the observation data, during which the data has been sampled." ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" .
		
		lcdccore:obsTime a owl:DatatypeProperty ;
		    rdfs:label "obs time" ;
		    dc:description "The time of collection of the observation data," ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" ;
		    rdfs:range xsd:string .
		
		lcdccore:phase a owl:ObjectProperty ;
		    rdfs:label "phase" ;
		    dc:description "The phase of the study for this slice." ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" .
		
		lcdccore:phaseKey a owl:DatatypeProperty ;
		    rdfs:label "phase key" ;
		    dc:description "The identification key for this phase of the study, if available." ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" ;
		    rdfs:range lcdccore:phasecode .
		
		lcdccore:questionKey a owl:DatatypeProperty ;
		    rdfs:label "question key" ;
		    dc:description "The ID of the Item as defined in ODM." ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" ;
		    rdfs:range lcdccore:questioncode .
		
		lcdccore:questionnaireKey a owl:DatatypeProperty ;
		    rdfs:label "questionnaire key" ;
		    dc:description "The ID of the Item as defined in ODM." ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" ;
		    rdfs:range lcdccore:questionnairecode .
		
		lcdccore:study a owl:ObjectProperty ;
		    rdfs:label "study" ;
		    dc:description "The associated Study" ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" .
		
		lcdccore:studyKey a owl:DatatypeProperty ;
		    rdfs:label "study key" ;
		    dc:description "The study Identification key" ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" ;
		    rdfs:range lcdccore:studycode .
		
		lcdccore:subject a owl:ObjectProperty ;
		    rdfs:label "subject" ;
		    dc:description "The subject for this slice.",
		        "The subject of the observation." ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" .
		
		lcdccore:subjectKey a owl:DatatypeProperty ;
		    rdfs:label "subject Key" ;
		    dc:description "The Key for a subject (subject), as defined in LCDC. Can be different from the key used in ODM to allow for privacy preserving operations." ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" ;
		    rdfs:range lcdccore:subjectcode .
		
		lcdccore:themeId a owl:DatatypeProperty ;
		    rdfs:label "theme ID" ;
		    dc:description "The theme code" ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" ;
		    rdfs:range lcdccore:themecode .
		
		lcdccore:variableDefinitionKey a owl:DatatypeProperty ;
		    rdfs:label "variable definition key" ;
		    dc:description "The ID of the Item as defined in ODM." ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" ;
		    rdfs:range lcdccore:variabledefinitioncode .
		
		lcdccore:variableId a owl:DatatypeProperty ;
		    rdfs:label "variable ID" ;
		    dc:description "The ID of the Item as defined in ODM." ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" ;
		    rdfs:range lcdccore:variablecode .
		
		qb:ComponentProperty a owl:Class .
		
		qb:DataSet a owl:Class .
		
		qb:dataSet a owl:ObjectProperty .
		
		qb:observation a owl:ObjectProperty .
		
		lcdccore:DataFile a owl:Class ;
		    rdfs:label "Data File" ;
		    dc:description "A study data product following a specified protocol." ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" ;
		    rdfs:subClassOf disco:DataFile .
		
		lcdccore:Protocol a owl:Class .
		
		lcdccore:StudyGroup a owl:Class ;
		    rdfs:label "Study Group" ;
		    dc:description "A Longitudinal study or Clinical trial" ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" ;
		    rdfs:subClassOf disco:StudyGroup .
		
		lcdccore:nodecode a rdfs:Datatype ;
		    rdfs:label "nodecode" ;
		    dc:description "Datatype defining a nodecode" ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" .
		
		lcdccore:phaseInterval a owl:ObjectProperty ;
		    rdfs:label "phase Interval" ;
		    dc:description "The period corresponding to this phase, if available." ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" .
		
		lcdccore:phasecode a rdfs:Datatype ;
		    rdfs:label "phasecode" ;
		    dc:description "Datatype defining a phasecode" ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" .
		
		lcdccore:protocol a owl:ObjectProperty ;
		    rdfs:label "protocol" ;
		    dc:description "The associated protocol." ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" .
		
		lcdccore:questioncode a rdfs:Datatype ;
		    rdfs:label "questioncode" ;
		    dc:description "Datatype defining a questioncode" ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" .
		
		lcdccore:questionnairecode a rdfs:Datatype ;
		    rdfs:label "questionnairecode" ;
		    dc:description "Datatype defining a questionnairecode" ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" .
		
		lcdccore:studyInterval a owl:ObjectProperty ;
		    rdfs:label "study interval" ;
		    dc:description "The temporal information attached to the study." ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" .
		
		lcdccore:studycode a rdfs:Datatype ;
		    rdfs:label "studycode" ;
		    dc:description "Datatype defining a studycode" ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" .
		
		lcdccore:subjectcode a rdfs:Datatype ;
		    rdfs:label "subjectcode" ;
		    dc:description "Datatype defining a subjectcode" ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" .
			
		lcdccore:themecode a rdfs:Datatype ;
		    rdfs:label "themecode" ;
		    dc:description "Datatype defining a themecode" ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" .
		
		lcdccore:variablecode a rdfs:Datatype ;
		    rdfs:label "variablecode" ;
		    dc:description "Datatype defining a variablecode" ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" .
		
		lcdccore:variabledefinitioncode a rdfs:Datatype ;
		    rdfs:label "variabledefinitioncode" ;
		    dc:description "Datatype defining a variabledefinitioncode" ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" .
		
		disco:DataFile a owl:Class .
		
		disco:Instrument a owl:Class .
		
		disco:Question a owl:Class .
		
		disco:Questionnaire a owl:Class .
		
		disco:Study a owl:Class .
		
		disco:StudyGroup a owl:Class .
		
		disco:Variable a owl:Class .
		
		disco:VariableDefinition a owl:Class .
		
		disco:aggregation a owl:ObjectProperty .
		
		disco:basedOn a owl:ObjectProperty .
		
		disco:dataFile a owl:ObjectProperty .
		
		disco:datasetTheme a owl:ObjectProperty .
		
		disco:inGroup a owl:ObjectProperty .
		
		disco:instrument a owl:ObjectProperty .
		
		disco:product a owl:ObjectProperty .
		
		disco:studyUniverse a owl:ObjectProperty .
		
		disco:universe a owl:ObjectProperty .
		
		void:Dataset a owl:Class .
		
		lcdccore:Question a owl:Class ;
		    rdfs:label "Question" ;
		    dc:description "Placeholder for question text." ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" ;
		    rdfs:subClassOf [ a owl:Restriction ;
		            owl:onProperty disco:concept ;
		            owl:someValuesFrom skos:Concept ],
		        disco:Question .
		
		lcdccore:VariableDefinition a owl:Class ;
		    rdfs:label "Variable Definition" ;
		    dc:description "Study-independent, reusable parts of Variables." ;
		    apivc:uriTemplate "http://purl.org/sstats/{linked}/id/variabledef/{variabledef}" ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" ;
		    rdfs:subClassOf 
		        [ a owl:Restriction ;
		            owl:onProperty disco:concept ;
		            owl:someValuesFrom skos:Concept ],
		        disco:VariableDefinition .
		
		disco:LogicalDataSet a owl:Class .
		
		disco:Universe a owl:Class .
		
		disco:question a owl:ObjectProperty .
		
		lcdccore:Theme a owl:Class ;
		    rdfs:label "Theme" ;
		    dc:description "A domain covered by the collected data." ;
		    apivc:uriTemplate "http://purl.org/sstats/{linked}/id/theme/{theme}" ;
		    rdfs:isDefinedBy "http://purl.org/sstats/lcdc/def/core" ;
		    rdfs:subClassOf [ a owl:Restriction ;
		            owl:onProperty disco:concept ;
		            owl:someValuesFrom skos:Concept ],
		        disco:Universe .
		interval:CalendarInterval a owl:Class .
		disco:concept a owl:ObjectProperty .
		skos:Concept a owl:Class .
 */
public class LcdcCore {
    /** <p>The ontology model that holds the vocabulary terms</p> */
    private static OntModel m_model = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, null );

    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://purl.org/sstats/lcdc/def/core#";

    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}

    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );

    public static final ObjectProperty node = m_model.createObjectProperty( "http://purl.org/sstats/lcdc/def/core#node"
);

    public static final ObjectProperty obsInterval = m_model.createObjectProperty( "http://purl.org/sstats/lcdc/def/core#obsInterval" );

    public static final ObjectProperty phase = m_model.createObjectProperty( "http://purl.org/sstats/lcdc/def/core#phase" );
    
    public static final ObjectProperty theme = m_model.createObjectProperty( "http://purl.org/sstats/lcdc/def/core#theme" );

    public static final ObjectProperty phaseInterval = m_model.createObjectProperty( "http://purl.org/sstats/lcdc/def/core#phaseInterval" );

    public static final ObjectProperty protocol = m_model.createObjectProperty( "http://purl.org/sstats/lcdc/def/core#protocol" );

    public static final ObjectProperty study = m_model.createObjectProperty( "http://purl.org/sstats/lcdc/def/core#study" );

    public static final ObjectProperty studyInterval = m_model.createObjectProperty( "http://purl.org/sstats/lcdc/def/core#studyInterval" );

    public static final ObjectProperty subject = m_model.createObjectProperty( "http://purl.org/sstats/lcdc/def/core#subject" );

    public static final DatatypeProperty gender = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/core#gender" );

    public static final DatatypeProperty nodeKey = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/core#nodeKey" );

    public static final DatatypeProperty obsDate = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/core#obsDate" );

    public static final DatatypeProperty obsTime = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/core#obsTime" );

    public static final DatatypeProperty phaseKey = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/core#phaseKey" );

    public static final DatatypeProperty questionKey = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/core#questionKey" );

    public static final DatatypeProperty questionnaireKey = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/core#questionnaireKey" );

    public static final DatatypeProperty studyKey = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/core#studyKey" );

    public static final DatatypeProperty subjectKey = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/core#subjectKey" );

    public static final DatatypeProperty themeId = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/core#themeId" );
    
    
    //Edit by A H Safaie
    public static final DatatypeProperty themeKey = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/core#themeKey" );

    public static final DatatypeProperty variableDefinitionKey = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/core#variableDefinitionKey" );

    public static final DatatypeProperty variableId = m_model.createDatatypeProperty( "http://purl.org/sstats/lcdc/def/core#variableId" );

    public static final OntClass DataFile = m_model.createClass( "http://purl.org/sstats/lcdc/def/core#DataFile" );

    public static final OntClass Node = m_model.createClass( "http://purl.org/sstats/lcdc/def/core#Node" );

    public static final OntClass Phase = m_model.createClass( "http://purl.org/sstats/lcdc/def/core#Phase" );

    public static final OntClass Protocol = m_model.createClass( "http://purl.org/sstats/lcdc/def/core#Protocol" );

    public static final OntClass Question = m_model.createClass( "http://purl.org/sstats/lcdc/def/core#Question" );

    public static final OntClass Questionnaire = m_model.createClass( "http://purl.org/sstats/lcdc/def/core#Questionnaire" );

    public static final OntClass Study = m_model.createClass( "http://purl.org/sstats/lcdc/def/core#Study" );

    public static final OntClass StudyGroup = m_model.createClass( "http://purl.org/sstats/lcdc/def/core#StudyGroup" );

    public static final OntClass Subject = m_model.createClass( "http://purl.org/sstats/lcdc/def/core#Subject" );

    public static final OntClass Theme = m_model.createClass( "http://purl.org/sstats/lcdc/def/core#Theme" );

    public static final OntClass Variable = m_model.createClass( "http://purl.org/sstats/lcdc/def/core#Variable" );

    public static final OntClass VariableDefinition = m_model.createClass( "http://purl.org/sstats/lcdc/def/core#VariableDefinition" );

    public static final Resource nodecode = m_model.createResource( "http://purl.org/sstats/lcdc/def/core#nodecode" );

    public static final Resource phasecode = m_model.createResource( "http://purl.org/sstats/lcdc/def/core#phasecode" );


    public static final Resource questioncode = m_model.createResource( "http://purl.org/sstats/lcdc/def/core#questioncode" );

    public static final Resource questionnairecode = m_model.createResource( "http://purl.org/sstats/lcdc/def/core#questionnairecode" );

    public static final Resource studycode = m_model.createResource( "http://purl.org/sstats/lcdc/def/core#studycode" );


    public static final Resource subjectcode = m_model.createResource( "http://purl.org/sstats/lcdc/def/core#subjectcode" );

    public static final Resource themecode = m_model.createResource( "http://purl.org/sstats/lcdc/def/core#themecode" );


    public static final Resource variablecode = m_model.createResource( "http://purl.org/sstats/lcdc/def/core#variablecode" );

    public static final Resource variabledefinitioncode = m_model.createResource( "http://purl.org/sstats/lcdc/def/core#variabledefinitioncode" );

}