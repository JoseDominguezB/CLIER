package pdf;

import com.b1soft.plugins.pdf.interfaces.PdfInterface;
import com.b1soft.util.CargadorPlugins;
import java.util.ArrayList;
import java.util.List;

public class PluginsUpdater {

    private boolean correcto;
    private String mensaje;
    private List<String> listaDePluginsDisponibles;

    public PluginsUpdater() {
        init();
        actualizaRepositorioDePlugins();
    }

    private void init() {
        this.correcto = false;
        this.mensaje = "";
        this.listaDePluginsDisponibles = new ArrayList();
    }

    public final void actualizaRepositorioDePlugins() {
        this.correcto = CargadorPlugins.cargarPlugins();

        if (this.correcto) {
            try {
                PdfInterface[] pdfPlugins = CargadorPlugins.getPlugins();

                if (pdfPlugins.length > 0) {
                    for (PdfInterface pdfPlugin : pdfPlugins) {
                        this.listaDePluginsDisponibles.add(pdfPlugin.getClass().getCanonicalName());
                    }
                } else {
                    this.mensaje = "No existen plugins disponibles para generar los archivos PDF";
                    this.correcto = false;
                }
            } catch (Exception ex) {
                this.mensaje = ex.getMessage();
                this.correcto = false;
                return;
            }
        } else {
            this.mensaje = ("Los plugins encontrados no pudieron ser cargados, verifique la estructura de los archivos depositados en: " + CargadorPlugins.DIRECTORIO_PLUGINS);

            this.correcto = false;
        }

        this.mensaje = ("Se han cargado " + this.listaDePluginsDisponibles.size() + " plugins depositados en: " + CargadorPlugins.DIRECTORIO_PLUGINS);

        this.correcto = true;
    }

    public boolean isCorrecto() {
        return this.correcto;
    }

    public List<String> getListaDePluginsDisponibles() {
        return this.listaDePluginsDisponibles;
    }

    public String getMensaje() {
        return this.mensaje;
    }
}