package pdf;

import clier.DebugTime;
import com.b1soft.plugins.pdf.interfaces.PdfInterface;
import com.b1soft.util.XmlReader;
import java.io.File;

public class Pdf extends Thread {

    private String clase_pdf;
    private String url_xml;
    private String url_pdf;
    private String cadena_original;
    private String uuid;
    private boolean DEBUG_TIME = false;
    private String log_path_debug, archivo_input, camada;

    public void setClase(String value) {
        clase_pdf = value;
    }

    public void setUrlXml(String value) {
        url_xml = value;
    }

    public void setUrlPdf(String value) {
        url_pdf = value;
    }

    public void setCadenaOriginal(String value) {
        cadena_original = value;
    }

    public void setUuid(String value) {
        uuid = value;
    }

    public void setDebugTime(boolean value) {
        DEBUG_TIME = value;
    }
    
    public void setPath(String value) {
        log_path_debug = value;
    }
    public void setArchivo(String value) {
        archivo_input = value;
    }
    public void setCamada(String value) {
        camada = value;
    }

    public void run() {
        System.out.println("\t\t\tContruyendo PDF para: " + archivo_input + "...");
        DebugTime dbg = new DebugTime();
        String fecha_inicio = dbg.getCalendar();
        try {
            Builder generator;
            PdfInterface pdf;
            generator = new Builder(clase_pdf);
            pdf = generator.getPdf();
            XmlReader xmlReader = new XmlReader(new File(url_xml));
            pdf.setXmlReader(xmlReader, new File(cadena_original));
            pdf.guardaDocumento(url_pdf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sysoutprint = "Construccion de PDF Error";
        System.out.println("\t\t\tFin Contruccion PDF para: " + archivo_input);
        File file = new File(url_pdf);
        if (file.exists()) {
            sysoutprint = "Construccion de PDF";
        }
        System.out.println("\t\t\t" + sysoutprint);
        String fecha_fin = dbg.getCalendar();
        if (DEBUG_TIME) {
            dbg.save(log_path_debug, archivo_input, dbg.diferencia(fecha_fin, fecha_inicio), fecha_inicio, fecha_fin, camada, sysoutprint);
        }
    }
}