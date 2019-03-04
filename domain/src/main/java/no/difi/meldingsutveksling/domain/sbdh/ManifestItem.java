//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.11.25 at 12:23:12 PM CET 
//


package no.difi.meldingsutveksling.domain.sbdh;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for ManifestItem complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ManifestItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MimeTypeQualifierCode" type="{http://www.unece.org/cefact/namespaces/StandardBusinessDocumentHeader}MimeTypeQualifier"/>
 *         &lt;element name="UniformResourceIdentifier" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LanguageCode" type="{http://www.unece.org/cefact/namespaces/StandardBusinessDocumentHeader}Language" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ManifestItem", propOrder = {
        "mimeTypeQualifierCode",
        "uniformResourceIdentifier",
        "description",
        "languageCode"
})
@Data
@Entity
@Table(name = "manifest_item")
public class ManifestItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    @XmlTransient
    private Long id;

    @XmlElement(name = "MimeTypeQualifierCode", required = true)
    protected String mimeTypeQualifierCode;
    @XmlElement(name = "UniformResourceIdentifier", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String uniformResourceIdentifier;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "LanguageCode")
    protected String languageCode;
}
