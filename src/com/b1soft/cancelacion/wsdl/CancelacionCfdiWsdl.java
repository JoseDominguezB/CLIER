
package com.b1soft.cancelacion.wsdl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebService(name = "CancelacionCfdiWsdl", targetNamespace = "http://servidor.b1soft.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface CancelacionCfdiWsdl {


    /**
     * 
     * @param xmlCancelacion
     * @param identificador
     * @return
     *     returns com.b1soft.cancelacion.wsdl.CancelacionCfdi
     */
    @WebMethod(operationName = "Cancelar")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "Cancelar", targetNamespace = "http://servidor.b1soft.com/", className = "com.b1soft.cancelacion.wsdl.Cancelar")
    @ResponseWrapper(localName = "CancelarResponse", targetNamespace = "http://servidor.b1soft.com/", className = "com.b1soft.cancelacion.wsdl.CancelarResponse")
    @Action(input = "http://servidor.b1soft.com/CancelacionCfdiWsdl/CancelarRequest", output = "http://servidor.b1soft.com/CancelacionCfdiWsdl/CancelarResponse")
    public CancelacionCfdi cancelar(
        @WebParam(name = "identificador", targetNamespace = "")
        String identificador,
        @WebParam(name = "xmlCancelacion", targetNamespace = "")
        String xmlCancelacion);

    /**
     * 
     * @param xmlCancelacion
     * @param passphrase
     * @param keystore
     * @param identificador
     * @return
     *     returns com.b1soft.cancelacion.wsdl.CancelacionCfdi
     */
    @WebMethod(operationName = "CancelarCfdi")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "CancelarCfdi", targetNamespace = "http://servidor.b1soft.com/", className = "com.b1soft.cancelacion.wsdl.CancelarCfdi")
    @ResponseWrapper(localName = "CancelarCfdiResponse", targetNamespace = "http://servidor.b1soft.com/", className = "com.b1soft.cancelacion.wsdl.CancelarCfdiResponse")
    @Action(input = "http://servidor.b1soft.com/CancelacionCfdiWsdl/CancelarCfdiRequest", output = "http://servidor.b1soft.com/CancelacionCfdiWsdl/CancelarCfdiResponse")
    public CancelacionCfdi cancelarCfdi(
        @WebParam(name = "identificador", targetNamespace = "")
        String identificador,
        @WebParam(name = "keystore", targetNamespace = "")
        byte[] keystore,
        @WebParam(name = "passphrase", targetNamespace = "")
        String passphrase,
        @WebParam(name = "xmlCancelacion", targetNamespace = "")
        String xmlCancelacion);

}
