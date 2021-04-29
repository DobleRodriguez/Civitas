package juegoTexto;

import civitas.CivitasJuego;
import java.util.ArrayList;
import GUI.Dado;
import java.util.Scanner;

class JuegoTexto {
  public static void main(String[] args) {
    VistaTextual vista = new VistaTextual();
    int nroJugadores = vista.leeEntero(4, "Indique el número de jugadores: ", "Valor incorrecto. Solo pueden jugar hasta 4 jugadores");
    
    ArrayList<String> nombres = new ArrayList<>(nroJugadores);
    System.out.println("Introduce el nombre de los jugadores: ");
    Scanner in = new Scanner(System.in);
    for (int i = 0; i < nroJugadores; i++) {
      nombres.add(in.nextLine());
    }
    // in.close();
    // nombres.add("Alejandro");
    // nombres.add("Brutus");
    // nombres.add("Camila");
    // nombres.add("Diana");
    CivitasJuego juego = new CivitasJuego(nombres);
    Dado.getInstance().setDebug(true);
    Controlador controlador = new Controlador(juego, vista);
    controlador.juega();
    in.close();
  }
}
