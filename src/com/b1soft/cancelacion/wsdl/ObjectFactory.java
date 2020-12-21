
package com.b1soft.cancelacion.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.b1soft.cancelacion.wsdl package. 
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

    private final static QName _Cancelar_QNAME = new QName("http://servidor.b1soft.com/", "Cancelar");
    private final static QName _CancelarCfdiResponse_QNAME = new QName("http://servidor.b1soft.com/", "CancelarCfdiResponse");
    private final static QName _CancelarResponse_QNAME = new QName("http://servidor.b1soft.com/", "CancelarResponse");
    private final static QName _CancelarCfdi_QNAME = new QName("http://servidor.b1soft.com/", "CancelarCfdi");
    private final static QName _CancelarCfdiKeystore_QNAME = new QName("", "keystore");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.b1soft.cancelacion.wsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CancelarCfdi }
     * 
     */
    public CancelarCfdi createCancelarCfdi() {
        return new CancelarCfdi();
    }

    /**
     * Create an instance of {@link Cancelar }
     * 
     */
    public Cancelar createCancelar() {
        return new Cancelar();
    }

    /**
     * Create an instance of {@link CancelarCfdiResponse }
     * 
     */
    public CancelarCfdiResponse createCancelarCfdiResponse() {
        return new CancelarCfdiResponse();
    }

    /**
     * Create an instance of {@link CancelarResponse }
     * 
     */
    public CancelarResponse createCancelarResponse() {
        return new CancelarResponse();
    }

    /**
     * Create an instance of {@link CancelacionCfdi }
     * 
     */
    public CancelacionCfdi createCancelacionCfdi() {
        return new CancelacionCfdi();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Cancelar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servidor.b1soft.com/", name = "Cancelar")
    public JAXBElement<Cancelar> createCancelar(Cancelar value) {
        return new JAXBElement<Cancelar>(_Cancelar_QNAME, Cancelar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelarCfdiResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servidor.b1soft.com/", name = "CancelarCfdiResponse")
    public JAXBElement<CancelarCfdiResponse> createCancelarCfdiResponse(CancelarCfdiResponse value) {
        return new JAXBElement<CancelarCfdiResponse>(_CancelarCfdiResponse_QNAME, CancelarCfdiResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelarResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servidor.b1soft.com/", name = "CancelarResponse")
    public JAXBElement<CancelarResponse> createCancelarResponse(CancelarResponse value) {
        return new JAXBElement<CancelarResponse>(_CancelarResponse_QNAME, CancelarResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelarCfdi }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servidor.b1soft.com/", name = "CancelarCfdi")
    public JAXBElement<CancelarCfdi> createCancelarCfdi(CancelarCfdi value) {
        return new JAXBElement<CancelarCfdi>(_CancelarCfdi_QNAME, CancelarCfdi.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "keystore", scope = CancelarCfdi.class)
    public JAXBElement<byte[]> createCancelarCfdiKeystore(byte[] value) {
        return new JAXBElement<byte[]>(_CancelarCfdiKeystore_QNAME, byte[].class, CancelarCfdi.class, ((byte[]) value));
    }

}
