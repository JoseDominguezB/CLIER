/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clier;

/**
 *
 * @author USSIRVING
 */
public class Errormovable {



    public boolean isMovable(String error) {
        boolean movable = true;
        try {
            if (error == null) {
                movable = false;
            } else if (error.equals("")) {
                movable = false;
            } else {
                for (String excepcion : Constantes.exceptions) {
                    if (error.contains(excepcion.trim())) {
                        movable = false;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("No se pudo verificar si se puede mover fichero a error, por default se movera");
        }
        return movable;
    }
}
