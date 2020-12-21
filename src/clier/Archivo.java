/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Administrador
 */
public class Archivo {

    public static String path = "xml/";
    public static String filename = "file.xml";
    public static String content = "";

    public void save() {
        try {
            FileOutputStream xmlFile = new FileOutputStream(new File(path + filename));
            byte[] byteArr = content.getBytes("UTF-8");
            xmlFile.write(byteArr);
            xmlFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkPath(String staticPath, String checkPath) {
        try {
            String path = staticPath;
            String[] pathParts = checkPath.split("/");
            for (String pathPart : pathParts) {
                path += "/" + pathPart;
                File generic = new File(path);
                if (!generic.exists()) {
                    generic.mkdir();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save(String filename, String content) {
        try {
            FileOutputStream xmlFile = new FileOutputStream(new File(filename));
            byte[] byteArr = content.getBytes("UTF-8");
            xmlFile.write(byteArr);
            xmlFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void append(String filename, String content) {
        try {
            FileWriter fstream = new FileWriter(filename, true);
            BufferedWriter fbw = new BufferedWriter(fstream);
            fbw.write(content);
            fbw.newLine();
            fbw.close();
        } catch (Exception e) {
        }
    }
    
    private String getMes(String mes){
        String month = "Enero";
        if(mes.equals("02")){
            month = "Febrero";
        } else if(mes.equals("03")){
            month = "Marzo";
        } else if(mes.equals("04")){
            month = "Abril";
        } else if(mes.equals("05")){
            month = "Mayo";
        } else if(mes.equals("06")){
            month = "Junio";
        } else if(mes.equals("07")){
            month = "Julio";
        } else if(mes.equals("08")){
            month = "Agosto";
        } else if(mes.equals("09")){
            month = "Septiembre";
        } else if(mes.equals("10")){
            month = "Octubre";
        } else if(mes.equals("11")){
            month = "Noviembre";
        } else if(mes.equals("12")){
            month = "Diciembre";
        }
        
        return month;
    }

    public void logDebugTime(String path, String nombre, String log) {
        try {
            Calendar calendar = Calendar.getInstance();
            //String patron = "yyyy-MM-dd";
            String patron = "yyyy-MM";
            SimpleDateFormat formato = new SimpleDateFormat(patron);
            String filename = formato.format(calendar.getTime());
            append(path + filename + ".log", log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void logError(String path, String nombre, String error) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.getTime();
            //String patron = "yyyy-MM-dd";
            String patron = "yyyy-MM";
            String patron_hms = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat formato = new SimpleDateFormat(patron);
            SimpleDateFormat formato_hms = new SimpleDateFormat(patron_hms);
            String filename = formato.format(calendar.getTime());
            File file = new File(path + "history.html");
            if (!file.exists()) {
                save(path + "history.html", "<link href=\"../lib/errores.css\" rel=\"stylesheet\" type=\"text/css\">");
            }
            file = new File(path + filename + ".html");
            if (!file.exists()) {
                save(path + filename + ".html", "<link href=\"../lib/errores.css\" rel=\"stylesheet\" type=\"text/css\">");
                save(path + "errores.html", "<link href=\"../lib/errores.css\" rel=\"stylesheet\" type=\"text/css\">");
                String ano = "2XXX";
                String mes = "XX";
                try {
                    ano = filename.substring(0,4);
                    mes = filename.substring(5);
                } catch (Exception e) {
                }
                append(path + "history.html", "<a href=\"" + filename + ".html\"><li class=\"history\">" + getMes(mes) + " " + ano + "</li></a>");
            }


            String contenido = "<table width=\"95%\" border=\"1\"><tr>"
                    + "<td class=\"fecha\">" + formato_hms.format(calendar.getTime()) + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td valign=\"top\" class=\"error\">"
                    + "<div class=\"nombre\">" + nombre + "</div>"
                    + "<div class=\"detalle_error_box\">"
                    + "<div class=\"detalle_error\">" + error + "</div>"
                    + "</div>"
                    + "</td>"
                    + "</tr>"
                    + "</table>\n";
            append(path + filename + ".html", contenido);
            append(path + "errores.html", contenido);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logErrorOld(String path, String content) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.getTime();
            String patron = "yyyy-MM-dd";
            String patron_hms = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat formato = new SimpleDateFormat(patron);
            SimpleDateFormat formato_hms = new SimpleDateFormat(patron_hms);
            File file = new File(path + formato.format(calendar.getTime()) + ".log");
            if (!file.exists()) {
                save(path + formato.format(calendar.getTime()) + ".log", "");
            }
            String contenido = formato_hms.format(calendar.getTime()) + "\t" + content;
            append(path + formato.format(calendar.getTime()) + ".log", contenido);
        } catch (Exception e) {
        }
    }

    public static String getFileContent(String filename) {
        String content = "";
        try {
            File file = new File(filename);
            byte[] buffer = null;
            FileInputStream f = null;
            buffer = new byte[(int) file.length()];
            f = new FileInputStream(file);
            f.read(buffer);
            content = new String(buffer, "UTF-8");
            f.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public static String getSimplename(String filename) {
        return filename.substring(0, (filename.length() > 4 ? filename.length() - 4 : filename.length()));
    }

    public String[] getFiles(String path) {
        String[] files = null;
        try {
            File dir = new File(path);
            if (dir.exists()) {
                files = dir.list();
            } else {
                System.out.println("\tNo existe la carpeta " + path);
            }
        } catch (Exception e) {
            System.out.println("\t\tError en obtener archivos de " + path);
            e.printStackTrace();
        }
        return files;
    }

    public void rename(String filename_old, String filename_new) {
        try {
            File archivo_old = new File(filename_old);
            if (!archivo_old.canWrite()) {
                //System.out.println("\tNo se puede escribir");
            }
            File archivo_new = new File(filename_new);
            if (!archivo_old.renameTo(archivo_new)) {
                //System.out.println("\tNo se pudo mover archivo");
                copyFileUsingStream(archivo_old, archivo_new);
                if (!archivo_old.delete()) {
                    //System.out.println("\tNo se pudo eliminar archivo");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rename_old(String filename_old, String filename_new) {
        try {
            File archivo_old = new File(filename_old);
            File archivo_new = new File(filename_new);
            copyFileUsingStream(archivo_old, archivo_new);
        } catch (Exception e) {
        }
    }

    public void delete(String filename) {
        try {
            File archivo = new File(filename);
            archivo.canWrite();
            archivo.delete();
        } catch (Exception e) {
        }
    }

    private void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            int size = new Integer((int) source.length());
            byte[] buffer = new byte[size];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }
}
