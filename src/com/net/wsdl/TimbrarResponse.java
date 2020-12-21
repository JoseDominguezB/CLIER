
package com.net.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TimbrarResult" type="{http://wsdl.servidor.b1soft.com/}timbradoCfdi" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "timbrarResult"
})
@XmlRootElement(name = "TimbrarResponse")
public class TimbrarResponse {

    @XmlElement(name = "TimbrarResult")
    protected TimbradoCfdi timbrarResult;

    /**
     * Obtiene el valor de la propiedad timbrarResult.
     * 
     * @return
     *     possible object is
     *     {@link TimbradoCfdi }
     *     
     */
    public TimbradoCfdi getTimbrarResult() {
        return timbrarResult;
    }

    /**
     * Define el valor de la propiedad timbrarResult.
     * 
     * @param value
     *     allowed object is
     *     {@link TimbradoCfdi }
     *     
     */
    public void setTimbrarResult(TimbradoCfdi value) {
        this.timbrarResult = value;
    }

}
