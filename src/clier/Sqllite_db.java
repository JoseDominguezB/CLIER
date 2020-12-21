/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author USSIRVING
 */
public class Sqllite_db {

    private String database = null;
    private Connection connection = null;

    public void setDatabase(String value) {
        database = value;
    }

    private void connect() {
        try {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:" + database);
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.err.println(e.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        connect();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS DEBUG \n"
                    + "(DEBUG_ID INTEGER PRIMARY KEY   AUTOINCREMENT,\n"
                    + " ARCHIVO		VARCHAR(255)    NOT NULL,\n"
                    + " DIFERENCIA		VARCHAR(255)    NOT NULL,\n"
                    + " FECHA_INICIAL		datetime    NOT NULL,\n"
                    + " FECHA_FINAL		datetime    NOT NULL,\n"
                    + " CAMADA		VARCHAR(50)    NOT NULL,\n"
                    + " DESCRIPCION		VARCHAR(255)    NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                stmt.close();
            } catch (Exception e) {
            }
            close();
        }
    }

    public void insert(String ARCHIVO,
            String DIFERENCIA,
            String FECHA_INICIAL,
            String FECHA_FINAL,
            String CAMADA,
            String DESCRIPCION) {
        connect();
        Statement stmt = null;
        try {
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            
            String sql = "INSERT INTO DEBUG (ARCHIVO, DIFERENCIA, FECHA_INICIAL, FECHA_FINAL, CAMADA, DESCRIPCION)\n"
                    + "VALUES ("
                    + "'" + ARCHIVO + "'" + ","
                    + "'" + DIFERENCIA + "'" + ","
                    + "'" + FECHA_INICIAL + "'" + ","
                    + "'" + FECHA_FINAL + "'" + ","
                    + "'" + CAMADA + "'" + ","
                    + "'" + DESCRIPCION + "'"
                    + ");";
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                stmt.close();
            } catch (Exception e) {
            }
            close();
        }
    }




    private void close() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
