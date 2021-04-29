/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import java.util.ArrayList;
import civitas.CivitasJuego;
/**
 *
 * @author javs
 */
public class TestP5 {
    public static void main(String[] args) {
        CivitasView vista = new CivitasView();
        Dado.createInstance(vista);
        Dado.getInstance().setDebug(true);
        CapturaNombres capturaNombres = new CapturaNombres(vista, true);
        ArrayList<String> nombres = new ArrayList<String>();
        for (String i : capturaNombres.getNombres()) {
            if (!i.equals("")) {
                nombres.add(i);
            }
        }
        for (String i : nombres) {
            System.out.println("XD " +  i);
        }
        CivitasJuego juego = new CivitasJuego(nombres);
        Controlador controlador = new Controlador(juego, vista);
        vista.setCivitasJuego(juego);
        controlador.juega();
       //vista.actualizarVista();
        
    }
}
