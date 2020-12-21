package clier;

import com.b1soft.Mapeador;
import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.TimeZone;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.commons.ssl.Base64;
import org.apache.commons.ssl.PKCS8Key;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import tools.Tools;

public class Clier {

    public static String ENTRADA = "timbrado/";
    public static String CANCELACION = "cancelacion/";
    public static String CANCELACION_METODO = "cfdi";
    public static String CO_XSLT = "";
    public static String IDENTIFICADOR = "";
    public static StreamSource SOURCE_XSL = null;
    public static Transformer transformer = null;
    private static String PDF = null;
    private static String PDF_CLASE = null;
    private static String DISPERSOR = null;
    private static String MAPEADOR = null;
    private static String SELLADOR = null;
    private static String certificate_str = "";
    private static String privatekey_str = "";
    private static String passphrase = "";
    private static String OPENSSL = "";
    private static String cancelador = "";
    private static String renombrar = "";
    private static String estructura_guardado = "";
    private static boolean DEBUG_TIME = false;
    private static int HILOS = 5;
    private static boolean PERMITIR_HILOS = false;
    
    // cadena original 3.3
    public static String CO_XSLT33 = "";
    public static StreamSource SOURCE_XSL33 = null;
    public static Transformer transformer33 = null;

    public static void main(String[] args) {
//        SimpleDateFormat formatoSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//        
//        Calendar calendar1 = Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"));
//        System.out.println("1. "+formatoSDF.format(calendar1.getTime()));
//        
//        Calendar calendar2 = new GregorianCalendar(TimeZone.getTimeZone("America/Mexico_City"));
//        System.out.println("2. "+formatoSDF.format(calendar2.getTime()));
//        
//        Calendar calendar3 = new GregorianCalendar();
//        TimeZone tz3 = TimeZone.getTimeZone("America/Mexico_City");
//        calendar3.setTimeZone(tz3);        
//        System.out.println("3. "+formatoSDF.format(calendar3.getTime()));
//        
//        Calendar calendar4 = Calendar.getInstance(TimeZone.getTimeZone("Mexico/General"));
//        System.out.println("4. "+formatoSDF.format(calendar4.getTime()));
//        
//        Calendar calendar5 = new GregorianCalendar(TimeZone.getTimeZone("Mexico/General"));
//        System.out.println("5. "+formatoSDF.format(calendar5.getTime()));
//        
//        Calendar calendar6 = new GregorianCalendar();
//        TimeZone tz6 = TimeZone.getTimeZone("Mexico/General");
//        calendar3.setTimeZone(tz6);        
//        System.out.println("6. "+formatoSDF.format(calendar3.getTime()));
//        
//        formatoSDF.setTimeZone(TimeZone.getTimeZone("GMT"));
//        System.out.println("7. "+formatoSDF.format(new Date()));
//        
//        try
//        {
//            Thread.sleep(100000);
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        System.exit(0);
        try {
            File prop = new File("clier.properties");
            Properties properties = new Properties();
            properties.load(new FileInputStream(prop.getAbsolutePath()));
            ENTRADA = properties.getProperty("timbrado");
            CANCELACION = properties.getProperty("cancelacion");
            CANCELACION_METODO = properties.getProperty("cancelacion_metodo");
            IDENTIFICADOR = properties.getProperty("identificador");
            CO_XSLT = properties.getProperty("xslt_cadena");
            DISPERSOR = properties.getProperty("dispersor");
            PDF = properties.getProperty("pdf");
            PDF_CLASE = properties.getProperty("pdf_clase");
            MAPEADOR = properties.getProperty("mapeador");
            SELLADOR = properties.getProperty("sellador");
            SOURCE_XSL = new StreamSource(new File(CO_XSLT));
            Constantes.WSDL = properties.getProperty("url_wsdl");
            Constantes.WSDL_CANCELACION = properties.getProperty("url_wsdl_cancel");
            TransformerFactory tFactory = TransformerFactory.newInstance();
            transformer = tFactory.newTransformer(SOURCE_XSL);
            certificate_str = properties.getProperty("certificate");
            privatekey_str = properties.getProperty("privatekey");
            passphrase = properties.getProperty("passphrase");
            OPENSSL = properties.getProperty("openssl");
            cancelador = properties.getProperty("cancelador");

            renombrar = properties.getProperty("renombrar");
            estructura_guardado = properties.getProperty("estructura_guardado");
            PERMITIR_HILOS = properties.getProperty("permitir_hilos") != null && properties.getProperty("permitir_hilos").equals("true") ? true : false;
            
            // para la cadena original 3.3
            CO_XSLT33 = properties.getProperty("xslt_cadena33");
            SOURCE_XSL33 = new StreamSource(new File(CO_XSLT33));
            transformer33 = tFactory.newTransformer(SOURCE_XSL33);
            
            try {
                Constantes.TIMEOUT = new Integer(properties.getProperty("timeout"));
                System.out.println("NOTICE -> Timeout configurado, " + Constantes.TIMEOUT + " seg");
            } catch (Exception e) {
                System.out.println("NOTICE -> Se usara Timeout por default, " + Constantes.TIMEOUT + " seg");
            }
            if(PERMITIR_HILOS)
            {
                try {
                    HILOS = new Integer(properties.getProperty("hilos"));
                    System.out.println("NOTICE -> Hilos configurados, " + HILOS + "");
                } catch (Exception e) {
                    System.out.println("NOTICE -> Se usaran Hilos por default, " + HILOS + "");
                }
            }
                

            DEBUG_TIME = properties.getProperty("debug_time") != null && properties.getProperty("debug_time").equals("true") ? true : false;

            if (DEBUG_TIME) {
                System.out.println("NOTICE -> Debug time activado");
            }
            
            System.out.println("PERMITIR_HILOS: "+PERMITIR_HILOS);
        } catch (Exception e) {
            System.out.println("Error al inicializar propiedades");
            e.printStackTrace();
            return;
        }

        try {
            String contenido = Archivo.getFileContent("exceptions.b1").trim();
            Constantes.exceptions = contenido.split("\n");
        } catch (Exception e) {
            System.out.println("Error al inicializar excepciones");
            e.printStackTrace();
            return;
        }
        while (true) {
            try {
                timbrado();
                if (cancelador != null && cancelador.equals("true")) {
                    cancela();
                }
                System.out.println("Esperando...");
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void cancela() throws InterruptedException {
        //<editor-fold defaultstate="collapsed" desc="Cancelacion">
        System.out.println("Buscando archivos de cancelacion...");
        if (CANCELACION != null) {
            String entrada = CANCELACION + "entrada/";
            String procesando = CANCELACION + "procesando/";
            String temp = CANCELACION + "temp/";
            String log_path = CANCELACION + "log/history/";
            String error_path = CANCELACION + "error/";
            String exito_path = CANCELACION + "exito/";
            Archivo generic = new Archivo();
            String[] archivos_input = generic.getFiles(entrada);
            int archivo_input_count = 0;
            if (archivos_input != null) {
                archivo_input_count++;
                int archivo_actual = 0;
                if (archivo_input_count == 1) {
                    System.out.println("Se encontraron " + archivos_input.length + " archivos de cancelacion.");
                }
                for (String archivo_input : archivos_input) {
                    boolean error = false;
                    String xml_cancelacion = "";
                    archivo_actual++;
                    System.out.println("\t(" + archivo_actual + " de " + archivos_input.length + ") " + archivo_input);
                    generic.rename(entrada + archivo_input, procesando + archivo_input);
                    try {
                        if (archivo_actual == 1) {
                            if (CANCELACION_METODO.equals("cfdi") || CANCELACION_METODO.equals("xml")) {
                                //Revisar existencia de PFX
                                File file = new File(certificate_str + ".pfx");
                                if (!file.exists()) {
                                    System.out.println("Construyendo PEM del KEY...");
                                    String file_bat = temp + file.getName() + ".bat";
                                    String bat_content = OPENSSL + " pkcs8 -inform DER -in " + privatekey_str + " -passin pass:" + passphrase + " -out " + privatekey_str + ".pem";
                                    generic.save(file_bat, bat_content);
//                                    System.out.println("bat_content:\n"+bat_content);
//                                    System.out.println("");
                                    try {
                                        Runtime.getRuntime().exec(file_bat);
                                    } catch (Exception e) {
                                        System.out.println("ERROR, no se pudo construir PEM de KEY");
                                        return;
                                    }
                                    Thread.sleep(1000);
                                    System.out.println("Construyendo PEM del CER...");
                                    bat_content = OPENSSL + " x509 -inform DER -outform PEM -in " + certificate_str + " -pubkey -out " + certificate_str + ".pem";
                                    generic.save(file_bat, bat_content);
//                                    System.out.println("bat_content:\n"+bat_content);
//                                    System.out.println("");
                                    try {
                                        Runtime.getRuntime().exec(file_bat);
                                    } catch (Exception e) {
                                        System.out.println("ERROR, no se pudo construir PEM de CER");
                                        return;
                                    }
                                    Thread.sleep(1000);
                                    
                                    System.out.println("Construyendo PFX...");
                                    bat_content = "" + OPENSSL + " pkcs12 -export -in "
                                            + certificate_str
                                            + ".pem -inkey " + privatekey_str
                                            + ".pem -out " + certificate_str + ".pfx"
                                            + " -password pass:" + passphrase;
                                    
                                    generic.save(file_bat, bat_content);
//                                    System.out.println("bat_content:\n"+bat_content);
//                                    System.out.println("");
                                    try {
                                        Runtime.getRuntime().exec(file_bat);
                                    } catch (Exception e) {
                                        System.out.println("ERROR, no se pudo construir pfx en pkcs12");
                                        return;
                                    }
                                    Thread.sleep(1000);
                                    generic.delete(file_bat);
                                }
                            }
                        }

                        if (CANCELACION_METODO.equals("cfdi")) {
                            System.out.println("\t\tObteniendo datos del CFDI...");
                            String xml_cfdi = Archivo.getFileContent(procesando + archivo_input);
                            Xml xml = new Xml();
                            String emisor = xml.getNode("cfdi:Emisor", xml_cfdi);
                            String tfd = xml.getNode("tfd:TimbreFiscalDigital", xml_cfdi);
                            //Get nodes
                            Calendar calendar = Calendar.getInstance();
                            calendar.getTime();

                            String patron = "yyyy-MM-dd HH:mm:ss";
                            SimpleDateFormat formato = new SimpleDateFormat(patron);
                            //get atributes
                            String uuid = xml.getAttribute("UUID", tfd);
                            String rfc = xml.getAttribute("rfc", emisor);
                            //String fecha = xml.getAttribute("FechaTimbrado", tfd);
                            String fecha = formato.format(calendar.getTime()).replaceAll(" ", "T");

                            xml_cancelacion = ""
                                    //+ "<?xml version=\"1.0\"?>"
                                    //+ "<CancelaCFD xmlns=\"http://cancelacfd.sat.gob.mx\">"
                                    + "<Cancelacion " + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" "
                                    + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " + "RfcEmisor=\"" + rfc + "\" " + "Fecha=\""
                                    + fecha + "\" "
                                    + "xmlns=\"http://cancelacfd.sat.gob.mx\">" + "<Folios><UUID>" + uuid + "</UUID></Folios>"
                                    + "</Cancelacion>"
                                    //+ "</CancelaCFD>"
                                    + "";
                            Archivo.save(temp + archivo_input, xml_cancelacion);
                            File file_pfx = new File(certificate_str + ".pfx");
                            if (file_pfx.exists()) {
                                try {
                                    XMLSigner xmlsigner = new XMLSigner();
                                    xmlsigner.setPassword(passphrase);
                                    xmlsigner.setPfxFile(certificate_str + ".pfx");
                                    xmlsigner.setXmlPreFile(temp + archivo_input);
                                    xmlsigner.setXmlPostFile(temp + "sign_" + archivo_input);
                                    xmlsigner.sign();
                                    xml_cancelacion = Archivo.getFileContent(temp + "sign_" + archivo_input);
                                } catch (Exception e) {
                                    //e.printStackTrace();
                                    System.out.println("\t\tERROR, No se pudo sellar el XML");
                                    error = true;
                                }
                            } else {
                                System.out.println("\t\tERROR, no se encontro PFX");
                                error = true;
                            }
                        } else if (CANCELACION_METODO.equals("xml")) {
                            System.out.println("\t\tSellando XML...");
                            File file_pfx = new File(certificate_str + ".pfx");
                            if (file_pfx.exists()) {
                                try {
                                    XMLSigner xmlsigner = new XMLSigner();
                                    xmlsigner.setPassword(passphrase);
                                    xmlsigner.setPfxFile(certificate_str + ".pfx");
                                    xmlsigner.setXmlPreFile(procesando + archivo_input);
                                    xmlsigner.setXmlPostFile(temp + "sign_" + archivo_input);
                                    xmlsigner.sign();
                                    xml_cancelacion = Archivo.getFileContent(temp + "sign_" + archivo_input);
                                    generic.delete(temp + "sign_" + archivo_input);
                                } catch (Exception e) {
                                    //e.printStackTrace();
                                    System.out.println("\t\tERROR, No se pudo sellar el XML");
                                    error = true;
                                }
                            } else {
                                System.out.println("\t\tERROR, no se encontro PFX");
                                error = true;
                            }
                        } else if (CANCELACION_METODO.equals("xml_sellado")) {
                            System.out.println("\t\tObteniendo XML sellado...");
                            xml_cancelacion = Archivo.getFileContent(procesando + archivo_input);
                        }
                        if (!error) {
                            if (DISPERSOR.equals("true")) {
                                System.out.println("\t\tEnviando XML de cancelacion...");
                            } else {
                                System.out.println("\t\tEnviando XML de cancelacion (b1timbre)...");
                                try {
                                    //Clean xml_cancelacion
                                    //System.out.println(xml_cancelacion);
                                    com.b1soft.cancelacion.wsdl.CancelacionCfdi_Service service = new com.b1soft.cancelacion.wsdl.CancelacionCfdi_Service();

                                    com.b1soft.cancelacion.wsdl.CancelacionCfdiWsdl port = service.getCancelacionCfdiWsdlPort();
                                    com.b1soft.cancelacion.wsdl.CancelacionCfdi cancelacion = port.cancelar(IDENTIFICADOR, xml_cancelacion);

                                    cancelacion.getCodigoCancelacion();
                                    if (cancelacion.isError()) {
                                        System.out.println("\t\tError");
                                        System.out.println("\t\t" + cancelacion.getResultadoCancelacion());

                                        generic.rename(procesando + archivo_input, error_path + archivo_input);
                                        generic.delete(temp + archivo_input);
                                        generic.logError(log_path, archivo_input, "<b>" + cancelacion.getCodigoCancelacion() + "</b>" + cancelacion.getResultadoCancelacion());
                                    } else {
                                        System.out.println("\t\tExito en solicitud");
                                        System.out.println("\t\t" + "\t" + cancelacion.getCodigoCancelacion() + "\t" + cancelacion.getResultadoCancelacion());
                                        generic.delete(temp + archivo_input);
                                        generic.delete(procesando + archivo_input);
                                        Archivo.save(exito_path + archivo_input, cancelacion.getCodigoCancelacion() + "\t" + cancelacion.getResultadoCancelacion());
                                    }
                                } catch (Exception ex) {
                                    System.out.println("\t\tError");
                                    ex.printStackTrace();
                                    generic.rename(procesando + archivo_input, entrada + archivo_input);
                                    generic.delete(temp + archivo_input);
                                    generic.logError(log_path, archivo_input, "<h4>Error en servicio de cancelacion</h4>" + ex.toString());
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("\t\tError");
                        e.printStackTrace();
                        generic.rename(procesando + archivo_input, entrada + archivo_input);
                        generic.delete(temp + archivo_input);
                        generic.logError(log_path, archivo_input, "<h4>Error en cancelacion</h4>" + e.toString());
                    }
                }
            }
        }
        //</editor-fold>
    }

    public static void timbrado() {
        System.out.println("Buscando archivos...");
        if (ENTRADA != null) {
            String entrada = ENTRADA + "entrada/";
            String procesando = ENTRADA + "procesando/";
            String temp = ENTRADA + "temp/";
            String log_path = ENTRADA + "log/history/";
            String log_path_debug = ENTRADA + "log/debug/";

            String cfdi = ENTRADA + "cfdi/";
            String pdf_path = ENTRADA + "pdf/";

            /**/
            Archivo generic = new Archivo();
            generic.checkPath(ENTRADA, "log/debug/");
            String[] archivos_input = generic.getFiles(entrada);
            int archivo_input_count = 0;
            if (archivos_input != null) {
                archivo_input_count++;

                PrivateKey PRIVATE_KEY = null;
                X509Certificate CERTIFICATE = null;
                String csd_serial = "";
                String csd_pem = "";

                if (archivo_input_count == 1) {
                    System.out.println("Se encontraron " + archivos_input.length + " archivos.");
                }


                int archivo_actual = 0;
                ControlHilos control = null;
                if(PERMITIR_HILOS)
                {
                    control = new ControlHilos();
                    control.setHilos(HILOS);
                }
                for (String archivo_input : archivos_input) {
                    //String reporte_debug_time = DEBUG_TIME ? archivo_input : "";
                    DebugTime dbg = new DebugTime();

                    boolean error = false;
                    String file_xml = procesando + archivo_input;
                    String error_file = ENTRADA + "error/" + archivo_input;
                    String xml_sellado = Archivo.getFileContent(entrada + archivo_input);
                    archivo_actual++;
                    String camada = archivo_actual + " de " + archivos_input.length;
                    System.out.println("\t(" + camada + ") " + archivo_input);
                    //Moviendo a procesando
                    generic.rename(entrada + archivo_input, procesando + archivo_input);
                    if (MAPEADOR.equals("true")) {
                        //<editor-fold defaultstate="collapsed" desc="Mapeador">
                        System.out.println("\t\t(" + camada + ") Mapeando...");
                        String fecha_inicio = dbg.getCalendar();
                        Mapeador mapa = new Mapeador();
                        mapa.setInput(procesando + archivo_input);
                        mapa.setOuput(temp + archivo_input);
                        mapa.mapea();
                        File file = new File(temp + archivo_input);
                        if (file.exists()) {
                            String fecha_fin = dbg.getCalendar();
                            if (DEBUG_TIME) {
                                dbg.save(log_path_debug, archivo_input, dbg.diferencia(fecha_fin, fecha_inicio), fecha_inicio, fecha_fin, camada, "Mapeando");
                            }
                            file_xml = temp + archivo_input;
                        } else {
                            String fecha_fin = dbg.getCalendar();
                            if (DEBUG_TIME) {
                                dbg.save(log_path_debug, archivo_input, dbg.diferencia(fecha_fin, fecha_inicio), fecha_inicio, fecha_fin, camada, "Mapeando (Error)");
                            }
                            System.out.println("\t(" + camada + ") Error -> No se encontro archivo mapeado");
                            error = true;
                            /**/
                            generic.rename(procesando + archivo_input, entrada + archivo_input);
                            generic.logError(log_path, archivo_input, "<h4>No se encontro archivo mapeado</h4>");
                            /**/
                        }
                        //</editor-fold>
                    }
                    if (!error) {
                        //Obteniendo versión de Comprobante
                        boolean version33 = getVersionComprobante(file_xml);
                        System.out.println("\t\tVersión de comprobante 3.3: "+ version33);
                        if (SELLADOR.equals("true")) {
                            if (archivo_actual == 1) {
                                //<editor-fold defaultstate="collapsed" desc="Obtener CSD">
                                System.out.println("\t\tObteniendo certificado de sello digital...");
                                String fecha_inicio = dbg.getCalendar();
                                try {
                                    PRIVATE_KEY = loadPKCS8PrivateKey(new FileInputStream(new File(privatekey_str)), passphrase);
                                    CERTIFICATE = loadX509Certificate(new FileInputStream(new File(certificate_str)));
                                    String numeroSerieCertX509 = CERTIFICATE.getSerialNumber().toString(16);
                                    for (int i = 0; i < numeroSerieCertX509.length(); i++) {
                                        if (i % 2 != 0) {
                                            csd_serial += numeroSerieCertX509.charAt(i);
                                        }
                                    }
                                    Base64 b64 = new Base64(-1);
                                    csd_pem = b64.encodeToString(CERTIFICATE.getEncoded());
                                    error = false;
                                    String fecha_fin = dbg.getCalendar();
                                } catch (Exception e) {
                                    String fecha_fin = dbg.getCalendar();
                                    if (DEBUG_TIME) {
                                        dbg.save(log_path_debug, archivo_input, dbg.diferencia(fecha_fin, fecha_inicio), fecha_inicio, fecha_fin, camada, "Obteniendo CSD Error");
                                    }
                                    error = true;
                                    System.out.println("No se pudo configurar datos de emisor\n" + e.toString());
                                    /**/
                                    generic.rename(procesando + archivo_input, entrada + archivo_input);
                                    generic.logError(log_path, archivo_input, "<h4>No se pudo configurar datos de emisor</h4> " + e.toString());
                                    e.printStackTrace();
                                    /**/
                                }
                                //</editor-fold>
                            }
                            if (!error) {
                                //<editor-fold defaultstate="collapsed" desc="Sellar">
                                System.out.println("\t\t(" + camada + ") Sellando...");
                                String fecha_inicio = dbg.getCalendar();
                                try {
                                    xml_sellado = buildXml(file_xml, csd_pem, csd_serial, PRIVATE_KEY);
                                    
                                    String xml_in_line = xml_sellado.replace("\t", "").replace("\r", "").replace("\n", "");
                                    int badXml = xml_in_line.indexOf("<cfdi:RegimenFiscal/></cfdi:Emisor><cfdi:Receptor/><cfdi:Conceptos/><cfdi:Impuestos><cfdi:Retenciones><cfdi:Retencion/></cfdi:Retenciones></cfdi:Impuestos>");
                                    if (badXml > 0) {
                                        String fecha_fin = dbg.getCalendar();
                                        if (DEBUG_TIME) {
                                            dbg.save(log_path_debug, archivo_input, dbg.diferencia(fecha_fin, fecha_inicio), fecha_inicio, fecha_fin, camada, "Sellando Error");
                                        }
                                        //Xml mal mapeado
                                        System.out.println("\t\t\t(" + camada + ") WARNING, No se pudo mapear correctamente\t");
                                        System.out.println("\t\t\t(" + camada + ") Moviendo a entrada, para reprocesar...\t");
                                        generic.rename(procesando + archivo_input, entrada + archivo_input);
                                        generic.logError(log_path, archivo_input, "<h4>WARNING, No se pudo mapear correctamente</h4>");
                                        error = true;
                                    } else {
                                        String fecha_fin = dbg.getCalendar();
                                        if (DEBUG_TIME) {
                                            dbg.save(log_path_debug, archivo_input, dbg.diferencia(fecha_fin, fecha_inicio), fecha_inicio, fecha_fin, camada, "Sellando");
                                        }
                                        generic.save(temp + archivo_input, xml_sellado);
                                    }
                                } catch (Exception e) {
                                    String fecha_fin = dbg.getCalendar();
                                    if (DEBUG_TIME) {
                                        dbg.save(log_path_debug, archivo_input, dbg.diferencia(fecha_fin, fecha_inicio), fecha_inicio, fecha_fin, camada, "Sellando Error");
                                    }
                                    error = true;
                                    System.out.println("\t\t(" + camada + ") Error sellando " + e.toString());
                                    /**/
                                    generic.rename(procesando + archivo_input, entrada + archivo_input);
                                    generic.logError(log_path, archivo_input, "<h4>Error sellando</h4>" + e.toString());
                                    /**/
                                }
                                //</editor-fold>
                            }
                        }
                        if (!error) {
                            /**/
                            Cliente cliente = new Cliente();
                            cliente.setDebugTime(DEBUG_TIME);
                            cliente.setDispersor(DISPERSOR.equals("true") ? true : false);
                            cliente.setPdfBuilder(PDF.equals("true") ? true : false);
                            cliente.setArchivoInput(archivo_input);
                            cliente.setXmlSellado(xml_sellado);
                            cliente.setRenombrar(renombrar);
                            cliente.setEstructuraGuardado(estructura_guardado);
                            cliente.setCoXslt(CO_XSLT);
                            cliente.setEntrada(ENTRADA);
                            cliente.setIdentificador(IDENTIFICADOR);
                            cliente.setPdfClase(PDF_CLASE);
                            cliente.setCamada(camada);
                            if(PERMITIR_HILOS)
                            {
                                control.add(cliente);
                                cliente.start();
                            }
                            else
                            {
                                cliente.run();
                            }

                            
                            /**/
                        }
                    }
                    /**/
                    if(PERMITIR_HILOS)
                    {
                        while (true) {
                            try {
                                if (control.continuar()) {
                                    break;
                                } else {
                                    System.out.println("Esperando hilos disponibles(" + control.getUsage() + ")...");
                                    Thread.sleep(1000);
                                }
                            } catch (Exception e) {
                            }
                        }
                    }                        
                    /**/
                }

            }
        } else {
            System.out.println("\tError -> Los directorios de entrada y salida no estan parametrizados");
        }
    }

    private static PrivateKey loadPKCS8PrivateKey(InputStream in, String passwd) throws Exception {
        byte[] decrypted = (passwd != null) ? getBytes(in, passwd.toCharArray()) : getBytes(in);
        PKCS8EncodedKeySpec keysp = new PKCS8EncodedKeySpec(decrypted);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keysp);
    }

    private static X509Certificate loadX509Certificate(InputStream in) throws Exception {
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        return (X509Certificate) factory.generateCertificate(in);
    }

    private static byte[] getBytes(InputStream in) throws Exception {
        try {
            byte[] buffer = null;
            in.read(buffer);
            return buffer;
        } finally {
            in.close();
        }
    }

    private static byte[] getBytes(InputStream in, char[] passwd) throws Exception {
        try {
            PKCS8Key pkcs8 = new PKCS8Key(in, passwd);
            return pkcs8.getDecryptedBytes();
        } finally {
            in.close();
        }
    }
    
    public static String getCadenaOriginal(String xmlString, Transformer trans) {
        try {
            StreamSource sourceXML = new StreamSource(new StringReader(xmlString));
            OutputStream output = new ByteArrayOutputStream();
            trans.transform(sourceXML, new StreamResult(output));
            String devolver = new String(((ByteArrayOutputStream) output).toByteArray(), "UTF-8");
            output.close();
            return devolver;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String sellar(PrivateKey PRIVATE_KEY, String cadenaoriginal) {
        String sello = null;
        try {
            Signature sig = Signature.getInstance("SHA1withRSA");
            sig.initSign(PRIVATE_KEY);
            sig.update(cadenaoriginal.getBytes("UTF-8"));
            byte[] signed = sig.sign();
            Base64 b64 = new Base64(-1);
            sello = b64.encodeToString(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sello;
    }
    
    public static String sellarSHA256(PrivateKey PRIVATE_KEY, String cadenaoriginal) {
        String sello = null;
        try {
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initSign(PRIVATE_KEY);
            sig.update(cadenaoriginal.getBytes("UTF-8"));
            byte[] signed = sig.sign();
            Base64 b64 = new Base64(-1);
            sello = b64.encodeToString(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sello;
    }

    public static String buildXml(String file_xml_sin_sello, String pem_certificate, String serial, PrivateKey PRIVATE_KEY) {
        try {
            String cadenaoriginal = "";
            String sello = "";
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setValidating(false);
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new FileInputStream(new File(file_xml_sin_sello)));

            Element element = (Element) doc.getElementsByTagName("cfdi:Comprobante").item(0);
            if(element==null)
                return null;
            
            String version = element.getAttribute("Version");
            if(version!=null && !version.isEmpty() && version.equals("3.3"))
            {
                if(!element.hasAttribute("Fecha"))
                {
                    Calendar calendar = Calendar.getInstance();
                    calendar.getTime();
                    String patron = "yyyy-MM-dd HH:mm:ss";
                    SimpleDateFormat formato = new SimpleDateFormat(patron);                    
                    element.setAttribute("Fecha", formato.format(calendar.getTime()).replaceAll(" ", "T"));
                }
                element.setAttribute("Certificado", pem_certificate);
                element.setAttribute("NoCertificado", serial);
                
                cadenaoriginal = getCadenaOriginal(getXML(doc), transformer33);
                sello = sellarSHA256(PRIVATE_KEY, cadenaoriginal);
                element.setAttribute("Sello", sello);
            }
            else
            {
                if(!element.hasAttribute("fecha"))
                {
                    Calendar calendar = Calendar.getInstance();
                    calendar.getTime();
                    String patron = "yyyy-MM-dd HH:mm:ss";
                    SimpleDateFormat formato = new SimpleDateFormat(patron);                    
                    element.setAttribute("fecha", formato.format(calendar.getTime()).replaceAll(" ", "T"));
                }
                element.setAttribute("certificado", pem_certificate);
                element.setAttribute("noCertificado", serial);
                
                cadenaoriginal = getCadenaOriginal(getXML(doc), transformer);
                sello = sellar(PRIVATE_KEY, cadenaoriginal);
                element.setAttribute("sello", sello);
            }
            
            System.out.println("\t\t\tcadenaoriginal: "+ cadenaoriginal);
            System.out.println("\t\t\tsello: "+ sello);
            
            return getXML(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String getXML(Document doc) throws Exception {
        String xml = null;
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer out = new StringWriter();
        tf.transform(new DOMSource(doc), new StreamResult(out));
        xml = out.toString();
        out .close();
        return xml;
    }
    
    public static boolean getVersionComprobante(String filename)
    {
        try
        {
            String xml = Tools.getArchivoContenido(filename);
            
            DocumentBuilderFactory dbFactory = dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new StringReader(xml)));
            doc.getDocumentElement().normalize();
            dbFactory.setNamespaceAware(true);
            
            if(doc==null) return false;
            Element comprobante = doc.getDocumentElement();
            if(comprobante==null) return false;

            if( !comprobante.getAttribute("Version").equals("3.3") )
            {
                return false;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
            
        return true;
    }
}
