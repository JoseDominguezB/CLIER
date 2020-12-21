
package com.net.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para timbradoCfdi complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="timbradoCfdi">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="error" type="{http://www.w3.org/2001/XMLSchema}boolean" form="unqualified"/>
 *         &lt;element name="detalleError" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/>
 *         &lt;element name="xml" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "timbradoCfdi", namespace = "http://wsdl.servidor.b1soft.com/", propOrder = {
    "error",
    "detalleError",
    "xml"
})
public class TimbradoCfdi {

    @XmlElement(namespace = "")
    protected boolean error;
    @XmlElement(namespace = "")
    protected String detalleError;
    @XmlElement(namespace = "")
    protected String xml;

    /**
     * Obtiene el valor de la propiedad error.
     * 
     */
    public boolean isError() {
        return error;
    }

    /**
     * Define el valor de la propiedad error.
     * 
     */
    public void setError(boolean value) {
        this.error = value;
    }

    /**
     * Obtiene el valor de la propiedad detalleError.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetalleError() {
        return detalleError;
    }

    /**
     * Define el valor de la propiedad detalleError.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetalleError(String value) {
        this.detalleError = value;
    }

    /**
     * Obtiene el valor de la propiedad xml.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXml() {
        return xml;
    }

    /**
     * Define el valor de la propiedad xml.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXml(String value) {
        this.xml = value;
    }

}
