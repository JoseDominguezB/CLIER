package pdf;

import com.b1soft.plugins.pdf.interfaces.PdfInterface;

public class Builder {

    private String claseDelPluginPdf;
    private String mensaje;
    private boolean correcto;
    private PdfInterface pdf;

    public Builder(String claseDelPluginPdf)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.claseDelPluginPdf = claseDelPluginPdf;
        init();
    }

    private void init()
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Object classLoaded = Class.forName(this.claseDelPluginPdf).newInstance();
        try {
            this.pdf = ((PdfInterface) classLoaded);
        } catch (ClassCastException cce) {
            this.mensaje = ("La implementacion de la clase del plugin: " + this.claseDelPluginPdf + " no tiene una " + "correcta implementacion de la interfaz: " + PdfInterface.class.getCanonicalName() + "\n" + cce.getMessage());

            this.correcto = false;
        }

        this.correcto = true;
        this.mensaje = ("El plugin: " + this.claseDelPluginPdf + " se ha cargado exitosamente");
    }

    public void changeClaseDelPluginPdf(String claseDelPluginPdf)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.claseDelPluginPdf = claseDelPluginPdf;
        init();
    }

    public String getClaseDelPluginPdf() {
        return this.claseDelPluginPdf;
    }

    public boolean isCorrecto() {
        return this.correcto;
    }

    public String getMensaje() {
        return this.mensaje;
    }

    public PdfInterface getPdf() {
        return this.pdf;
    }
}