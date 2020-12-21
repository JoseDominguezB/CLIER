/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clier;

import com.net.wsdl.TimbradoCfdi;
import com.net.wsdl.TimbradoCfdiSoap;
import com.net.wsdl.TimbradoCfdi_Service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.xml.ws.BindingProvider;
import org.apache.commons.ssl.Base64;
import pdf.Pdf;

/**
 *
 * @author USSIRVING
 */
public class Cliente extends Thread {

    private boolean DEBUG_TIME = true;
    private boolean dispersor = true;
    private boolean pdf_constructor = true;
    /**/
    private String archivo_input;
    private String xml_sellado;
    private String renombrar;
    private String estructura_guardado;
    /**/
    private String camada;
    /**/
    private String CO_XSLT;
    private String ENTRADA;
    private String IDENTIFICADOR;
    private String PDF_CLASE;

    public void setDebugTime(boolean value) {
        DEBUG_TIME = value;
    }

    public void setDispersor(boolean value) {
        dispersor = value;
    }

    public void setPdfBuilder(boolean value) {
        pdf_constructor = value;
    }
    /**/

    public void setArchivoInput(String value) {
        archivo_input = value;
    }

    public void setXmlSellado(String value) {

        xml_sellado = value;
    }

    public void setRenombrar(String value) {
        renombrar = value;
    }

    public void setEstructuraGuardado(String value) {
        estructura_guardado = value;
    }

    public void setCoXslt(String value) {
        CO_XSLT = value;
    }

    public void setEntrada(String value) {
        ENTRADA = value;
    }

    public void setIdentificador(String value) {
        IDENTIFICADOR = value;
    }

    public void setPdfClase(String value) {
        PDF_CLASE = value;
    }

    public void setCamada(String value) {
        camada = value;
    }

    private void disponse() {
        DEBUG_TIME = true;
        dispersor = true;
        pdf_constructor = true;
        archivo_input = null;
        xml_sellado = null;
        renombrar = null;
        estructura_guardado = null;
        CO_XSLT = null;
        ENTRADA = null;
        IDENTIFICADOR = null;
        PDF_CLASE = null;
        camada = null;
    }

    public void run() {
        DebugTime dbg = new DebugTime();
        String xml = "";
        String detalleError = "";

        String entrada = ENTRADA + "entrada/";
        String procesando = ENTRADA + "procesando/";
        String temp = ENTRADA + "temp/";
        String log_path = ENTRADA + "log/history/";
        String log_path_debug = ENTRADA + "log/debug/";

        String cfdi = ENTRADA + "cfdi/";
        String pdf_path = ENTRADA + "pdf/";
        String error_file = ENTRADA + "error/" + archivo_input;

        Archivo generic = new Archivo();

        if (dispersor) {
            //<editor-fold defaultstate="collapsed" desc="Envio Dispersor">
            System.out.println("\t\t(" + camada + ") Enviando...");
            String fecha_inicio = dbg.getCalendar();
            try {
                Base64 b64 = new Base64();
                String xml_sellado_b64 = new String(b64.encode(xml_sellado.getBytes("UTF8")), "UTF8");
                TimbradoCfdi_Service service = new TimbradoCfdi_Service();
                TimbradoCfdiSoap port = service.getTimbradoCfdiSoap();
                /* */
                ((BindingProvider) port).getRequestContext().put("com.sun.xml.internal.ws.request.timeout", 1000 * Constantes.TIMEOUT * 1);
                /* */
                TimbradoCfdi timbrado = port.timbrar(IDENTIFICADOR, xml_sellado_b64);
                xml = timbrado.getXml() == null ? "" : timbrado.getXml();
                detalleError = timbrado.getDetalleError();
                String fecha_fin = dbg.getCalendar();
                if (DEBUG_TIME) {
                    dbg.save(log_path_debug, archivo_input, dbg.diferencia(fecha_fin, fecha_inicio), fecha_inicio, fecha_fin, camada, "Enviando");
                }
            } catch (Exception ex) {
                String fecha_fin = dbg.getCalendar();
                if (DEBUG_TIME) {
                    dbg.save(log_path_debug, archivo_input, dbg.diferencia(fecha_fin, fecha_inicio), fecha_inicio, fecha_fin, camada, "Enviando Error");
                }
                detalleError = "<h4>Error en servicio de timbrado</h4>" + ex.toString();
                ex.printStackTrace();
            }
            //</editor-fold>
        } else {
            //<editor-fold defaultstate="collapsed" desc="Envio a b1timbre">
            System.out.println("\t\t(" + camada + ") Enviando (b1timbre)...");
            String fecha_inicio = dbg.getCalendar();
            try {
                Base64 b64 = new Base64();
                String xml_sellado_b64 = new String(b64.encode(xml_sellado.getBytes("UTF8")), "UTF8");
                com.b1soft.servidor.wsdl.TimbradoCfdi_Service service = new com.b1soft.servidor.wsdl.TimbradoCfdi_Service();
                com.b1soft.servidor.wsdl.TimbradoCfdiWsdl port = service.getTimbradoCfdiWsdlPort();
                ((BindingProvider) port).getRequestContext().put("com.sun.xml.internal.ws.request.timeout", 1000 * Constantes.TIMEOUT * 1);
                /* */
                
                com.b1soft.servidor.wsdl.TimbradoCfdi timbrado = port.timbrar(IDENTIFICADOR, "id000", xml_sellado_b64);
                
                xml = timbrado.getXml() == null ? "" : timbrado.getXml();
                detalleError = timbrado.getDetalleError();
                String fecha_fin = dbg.getCalendar();
                if (DEBUG_TIME) {
                    dbg.save(log_path_debug, archivo_input, dbg.diferencia(fecha_fin, fecha_inicio), fecha_inicio, fecha_fin, camada, "Enviando");
                }
            } catch (Exception ex) {
                String fecha_fin = dbg.getCalendar();
                if (DEBUG_TIME) {
                    dbg.save(log_path_debug, archivo_input, dbg.diferencia(fecha_fin, fecha_inicio), fecha_inicio, fecha_fin, camada, "Enviando Error");
                }
                ex.printStackTrace();
                detalleError = "<h4>Error en servicio de timbrado(b1timbre)</h4>" + ex.toString();
            }
            //</editor-fold>
        }
        if (xml.contains("<?xml")) {
            //<editor-fold defaultstate="collapsed" desc="Guardado de XML">
                                /* - */
            String new_fileName = "";
            try {
                if (!renombrar.equals("") && renombrar != null) {
                    System.out.println("\t\t\t(" + camada + ") Renombrando archivo...\t");
                    String fecha_inicio = dbg.getCalendar();
                    List<String> renombre_parts = new ArrayList<String>();
                    if (renombrar.contains("/")) {
                        String[] renombre_parts_2 = renombrar.split("/");
                        renombre_parts.addAll(Arrays.asList(renombre_parts_2));
                    } else {
                        renombre_parts.add(renombrar);
                    }
                    for (String renombre_part : renombre_parts) {
                        String[] patron_rename = renombre_part.split("\t");
                        try {
                            Xml xml_obj = new Xml();
                            String node = xml_obj.getNode(patron_rename[0], xml);
                            new_fileName += xml_obj.getAttribute(patron_rename[1], node);
                            new_fileName += patron_rename[2];
                        } catch (Exception e) {
                        }
                    }
                    new_fileName = new_fileName.replaceAll("/", "-").replaceAll(":", "_");
                    String fecha_fin = dbg.getCalendar();
                    if (DEBUG_TIME) {
                        dbg.save(log_path_debug, archivo_input, dbg.diferencia(fecha_fin, fecha_inicio), fecha_inicio, fecha_fin, camada, "Renombrando");
                    }
                }
            } catch (Exception e) {
            }
            if (new_fileName.equals("")) {
                new_fileName = archivo_input.replaceAll(".xml", "").replaceAll(".XML", "").replaceAll(".txt", "").replaceAll(".TXT", "");
            }
            /* - */
            if (!estructura_guardado.equals("") && estructura_guardado != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.getTime();
                String patron = estructura_guardado;
                SimpleDateFormat formato = new SimpleDateFormat(patron);
                String timePath = formato.format(calendar.getTime());

                generic.checkPath(ENTRADA, timePath + "/cfdi/");
                generic.checkPath(ENTRADA, timePath + "/pdf/");

                cfdi = ENTRADA + timePath + "/cfdi/";
                pdf_path = ENTRADA + timePath + "/pdf/";
            }
            System.out.println("\t\t\t(" + camada + ") Documento Timbrado\t");
            generic.save(cfdi + new_fileName + ".xml", xml);
            /**/
            generic.delete(temp + archivo_input);
            generic.delete(procesando + archivo_input);
            //</editor-fold>
            if (pdf_constructor) {
                //Hilos
                //<editor-fold defaultstate="collapsed" desc="Construccion de PDF">
                Pdf pdf = new Pdf();
                pdf.setCadenaOriginal(CO_XSLT);
                pdf.setClase(PDF_CLASE);
                pdf.setUrlXml(cfdi + new_fileName + ".xml");
                pdf.setUrlPdf(pdf_path + new_fileName + ".pdf");
                pdf.setUuid(new_fileName);

                pdf.setDebugTime(DEBUG_TIME);
                pdf.setPath(log_path_debug);
                pdf.setArchivo(archivo_input);
                pdf.setCamada(camada);
                pdf.start();
                //</editor-fold>
            }
        } else {
            //<editor-fold defaultstate="collapsed" desc="ERROR la respuesta no es un XML">
            System.out.println("\t\t\t(" + camada + ") Error\t" + detalleError);
            Errormovable em = new Errormovable();
            if (em.isMovable(detalleError)) {
                //generic.save(error_file, detalleError);//Log
                generic.rename(procesando + archivo_input, error_file);
                generic.delete(temp + archivo_input);
            } else {
                System.out.println("\t\t\t(" + camada + ") Moviendo a entrada, para reprocesar...\t");
                /**/
                generic.rename(procesando + archivo_input, entrada + archivo_input);
                generic.delete(temp + archivo_input);
                /**/
            }
            if (!detalleError.contains("Error en servicio de timbrado")) {
                detalleError = "<h4>Error en servicio de timbrado</h4>" + detalleError;
            }
            generic.logError(log_path, archivo_input, detalleError);
            //</editor-fold>
        }
        disponse();
    }
}
