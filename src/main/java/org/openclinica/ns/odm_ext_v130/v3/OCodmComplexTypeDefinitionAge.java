//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.07.29 at 01:44:17 PM AEST 
//


package org.openclinica.ns.odm_ext_v130.v3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for OCodmComplexTypeDefinition-Age complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OCodmComplexTypeDefinition-Age">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.cdisc.org/ns/odm/v1.3>text">
 *       &lt;attGroup ref="{http://www.openclinica.org/ns/odm_ext_v130/v3.1}AgeAttributeDefinition"/>
 *       &lt;attGroup ref="{http://www.openclinica.org/ns/odm_ext_v130/v3.1}AgeAttributeExtension"/>
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OCodmComplexTypeDefinition-Age", propOrder = {
    "value"
})
public class OCodmComplexTypeDefinitionAge {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "MinimumAge")
    protected String minimumAge;
    @XmlAttribute(name = "MaximumAge")
    protected String maximumAge;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the minimumAge property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMinimumAge() {
        return minimumAge;
    }

    /**
     * Sets the value of the minimumAge property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinimumAge(String value) {
        this.minimumAge = value;
    }

    /**
     * Gets the value of the maximumAge property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaximumAge() {
        return maximumAge;
    }

    /**
     * Sets the value of the maximumAge property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaximumAge(String value) {
        this.maximumAge = value;
    }

}
