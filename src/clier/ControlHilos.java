package clier;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USSIRVING
 */
public class ControlHilos {

    private int hilosTotal = 0;
    private int hilosUso = 0;
    private List<Cliente> clientes = new ArrayList<Cliente>();

    public void setHilos(int hilos) {
        hilosTotal = hilos;
    }
    
    public String getUsage(){
        return hilosUso + " de " + hilosTotal;
    }

    public void add(Cliente cliente) {
        clientes.add(cliente);
        hilosUso++;
    }

    public boolean continuar() {
        boolean salida = false;
        try {
            for (Cliente cliente : clientes) {
                if (!cliente.isAlive()) {
                    hilosUso--;
                    clientes.remove(cliente);
                    //System.out.println("DEAD\t" + cliente.getCamada() + "\t-> " + hilosUso + " de " + hilosTotal);
                    salida = true;
                } else {
                    //System.out.println("ALIVE\t" + cliente.getCamada() + "\t-> " + hilosUso + " de " + hilosTotal);
                }
            }
            if ((hilosUso < hilosTotal) && !salida) {
                salida = true;
            }
        } catch (Exception e) {
            //System.err.println("ERROR en verificacion de hilos -> " + e.getClass().getName() + ": " + e.toString());
            //e.printStackTrace();
        }
        return salida;
    }
}
