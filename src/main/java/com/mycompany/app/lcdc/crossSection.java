package com.mycompany.app.lcdc;
/* CVS $Id: $ */

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Vocabulary definitions from /Users/betwar-mac/desktop/workspacejava/ttl/cross-section.ttl 
 * @author Auto-generated by schemagen on 25 Oct 2015 12:40 
 */
public class crossSection {
    /** <p>The RDF model that holds the vocabulary terms</p> */
    private static Model m_model = ModelFactory.createDefaultModel();
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://purl.org/sstats/lcdc/def/cross-section#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
    
    public static final Resource GenderSection = m_model.createResource( "http://purl.org/sstats/lcdc/def/cross-section#GenderSection" );
    
    public static final Resource NodeSection = m_model.createResource( "http://purl.org/sstats/lcdc/def/cross-section#NodeSection" );
    
    public static final Resource ObsSection = m_model.createResource( "http://purl.org/sstats/lcdc/def/cross-section#ObsSection" );
    
    public static final Resource Section = m_model.createResource( "http://purl.org/sstats/lcdc/def/cross-section#Section" );
    
    public static final Resource SubjectSection = m_model.createResource( "http://purl.org/sstats/lcdc/def/cross-section#SubjectSection" );
    
    public static final Resource ThemeSection = m_model.createResource( "http://purl.org/sstats/lcdc/def/cross-section#ThemeSection" );
    
}