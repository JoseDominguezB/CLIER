
package com.b1soft.timbrado.wsdl;

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
@WebService(name = "TimbradoCfdiWsdl", targetNamespace = "http://wsdl.servidor.b1soft.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface TimbradoCfdiWsdl {


    /**
     * 
     * @param notransaccion
     * @param xml
     * @param identificador
     * @return
     *     returns com.b1soft.servidor.wsdl.TimbradoCfdi
     */
    @WebMethod(operationName = "Timbrar")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "Timbrar", targetNamespace = "http://wsdl.servidor.b1soft.com/", className = "com.b1soft.servidor.wsdl.Timbrar")
    @ResponseWrapper(localName = "TimbrarResponse", targetNamespace = "http://wsdl.servidor.b1soft.com/", className = "com.b1soft.servidor.wsdl.TimbrarResponse")
    @Action(input = "http://wsdl.servidor.b1soft.com/TimbradoCfdiWsdl/TimbrarRequest", output = "http://wsdl.servidor.b1soft.com/TimbradoCfdiWsdl/TimbrarResponse")
    public TimbradoCfdi timbrar(
        @WebParam(name = "identificador", targetNamespace = "")
        String identificador,
        @WebParam(name = "Notransaccion", targetNamespace = "")
        String notransaccion,
        @WebParam(name = "xml", targetNamespace = "")
        String xml);

}
