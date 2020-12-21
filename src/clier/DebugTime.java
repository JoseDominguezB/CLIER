/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clier;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author USSIRVING
 */
public class DebugTime {

    public String getCalendar() {
        String exit = null;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.getTime();
            String patron_hms = "yyyy-MM-dd HH:mm:ss.SSS";
            SimpleDateFormat formato_hms = new SimpleDateFormat(patron_hms);
            exit = formato_hms.format(calendar.getTime());
        } catch (Exception e) {
        }
        return exit;
    }

    public String diferencia(String fecha_fin, String fecha_inicio) {
        String exit = "";
        try {
            Date date_inicio = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(fecha_inicio);
            Date date_fin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(fecha_fin);

            long msDif = (date_fin.getTime() - date_inicio.getTime());
            long segDif = (long) (msDif / (1000));
            long minDif = segDif / 60;
            long hoursDif = minDif / 60;

            if (segDif > 1) {
                msDif = msDif - (segDif * 1000);
            }
            if (minDif > 1) {
                segDif = segDif - (minDif * 60);
            }
            if (hoursDif > 1) {
                minDif = minDif - (hoursDif * 60);
            }

            exit = (hoursDif < 10 ? "0" + hoursDif : hoursDif) + ":"
                    + (minDif < 10 ? "0" + minDif : minDif) + ":"
                    + (segDif < 10 ? "0" + segDif : segDif) + "."
                    + msDif;

            exit = exit ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exit;
    }

    public void save(String path, String ARCHIVO, String DIFERENCIA, String FECHA_INICIAL, String FECHA_FINAL, String CAMADA, String DESCRIPCION) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.getTime();
            //String patron = "yyyy-MM-dd";
            String patron = "yyyy-MM";
            SimpleDateFormat formato = new SimpleDateFormat(patron);
            String dabadase = formato.format(calendar.getTime());
            Sqllite_db db = new Sqllite_db();
            db.setDatabase(path + dabadase + ".db");
            db.createTable();
            db.insert(ARCHIVO, DIFERENCIA, FECHA_INICIAL, FECHA_FINAL, CAMADA, DESCRIPCION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
