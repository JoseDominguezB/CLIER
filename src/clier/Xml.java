package clier;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author pruphineas
 */
public class Xml {

    public static String buildXml(String xml, String sello, String certificado_pem, String certificado_serial) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setValidating(false);
            DocumentBuilder db = dbf.newDocumentBuilder();

            //Document doc = db.parse(new FileInputStream(xml));
            Document doc = xmlToDocument(xml);
            Element element = (Element) doc.getElementsByTagName("cfdi:Comprobante").item(0);
            try {
                element.getAttribute("fecha");
            } catch (Exception f) {
                Calendar calendar = Calendar.getInstance();
                calendar.getTime();

                String patron = "yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat formato = new SimpleDateFormat(patron);
                element.setAttribute("fecha", formato.format(calendar.getTime()).replaceAll(" ", "T"));
            }
            element.setAttribute("sello", sello);
            element.setAttribute("certificado", certificado_pem);
            element.setAttribute("noCertificado", certificado_serial);
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
        return xml;
    }

    public static String getRfc(String xml) throws Exception {
        String rfc = null;
        try {
            xml = xml.replaceAll(">", ">\n");
            String[] xmlLines = xml.split("\n");
            for (String line : xmlLines) {
                if (line.contains("<cfdi:Emisor") || line.contains("<Emisor")) {
                    rfc = line.substring(line.indexOf("rfc=") + 4, 13).replaceAll("\"", "");
                }
            }
            //xml = out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rfc;
    }

    public static Document xmlToDocument(String xml) {
        Document doc = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setValidating(false);
            DocumentBuilder db = dbf.newDocumentBuilder();

            doc = db.parse(new FileInputStream(new File(xml)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }

    public static String getOnlyNode(String xml, String node, String xmlns) {
        String exit = xml;
        int indexStart = xml.indexOf("<" + node);
        if (indexStart > 0) {
            int indexEnd = xml.indexOf("</" + node);
            exit = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
            exit += xml.substring(indexStart, indexEnd + node.length() + 3);
        }
        String xmlFinal = "";
        String[] pices = exit.replaceAll(">", ">\n").split("\n");
        for (String line : pices) {
            if (!line.equals("")) {
                //Nodo principal
                if (line.contains("<" + node) && !line.contains(xmlns)) {
                    line = line.replace("<" + node, "<" + node + " " + xmlns + "");
                }
                xmlFinal += !line.equals("") ? line.trim() + "\n" : "";
            }
        }
        return xmlFinal;
    }

    public static String getNodeLine(String xml, String node) {
        String exit = "";
        xml = xml.replaceAll("\n", "");
        xml = xml.replaceAll(">", ">\n");
        String[] lines = xml.split("\n");
        for (String line : lines) {
            if (line.contains(node)) {
                exit = line;
            }
        }
        exit = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + exit;
        return exit;
    }

    public String getNode(String node, String xml) {
        String exit = "";
        String node_end = "</" + node + ">";
        try {
            int index_start = xml.indexOf("<" + node);
            String new_node = xml.substring(index_start);
            int index_end = new_node.indexOf(node_end);
            int index_simple_end = new_node.indexOf(">");

            if (index_end > 0) {
                new_node = new_node.substring(0, index_end + node_end.length());
            } else if (index_simple_end > 0) {
                new_node = new_node.substring(0, index_simple_end) + ">";
            }
            exit = new_node;
        } catch (Exception e) {
        }
        return exit;
    }

    public String getAttribute(String attribute, String node) {
        String exit = "";
        try {
            int index_start = node.indexOf(attribute + "=");

            String new_attribute = node.substring(index_start);
            new_attribute = new_attribute.substring(attribute.length() + 2);
            new_attribute = new_attribute.substring(0, new_attribute.indexOf("\""));
            exit = new_attribute;
        } catch (Exception e) {
            System.out.println("\t\tError obteniendo Atributo");
        }
        return exit;
    }
}
