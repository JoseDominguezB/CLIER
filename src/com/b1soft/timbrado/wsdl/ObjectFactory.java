
package com.b1soft.timbrado.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.b1soft.servidor.wsdl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Timbrar_QNAME = new QName("http://wsdl.servidor.b1soft.com/", "Timbrar");
    private final static QName _TimbrarResponse_QNAME = new QName("http://wsdl.servidor.b1soft.com/", "TimbrarResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.b1soft.servidor.wsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TimbrarResponse }
     * 
     */
    public TimbrarResponse createTimbrarResponse() {
        return new TimbrarResponse();
    }

    /**
     * Create an instance of {@link Timbrar }
     * 
     */
    public Timbrar createTimbrar() {
        return new Timbrar();
    }

    /**
     * Create an instance of {@link TimbradoCfdi }
     * 
     */
    public TimbradoCfdi createTimbradoCfdi() {
        return new TimbradoCfdi();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Timbrar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wsdl.servidor.b1soft.com/", name = "Timbrar")
    public JAXBElement<Timbrar> createTimbrar(Timbrar value) {
        return new JAXBElement<Timbrar>(_Timbrar_QNAME, Timbrar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimbrarResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://wsdl.servidor.b1soft.com/", name = "TimbrarResponse")
    public JAXBElement<TimbrarResponse> createTimbrarResponse(TimbrarResponse value) {
        return new JAXBElement<TimbrarResponse>(_TimbrarResponse_QNAME, TimbrarResponse.class, null, value);
    }

}
