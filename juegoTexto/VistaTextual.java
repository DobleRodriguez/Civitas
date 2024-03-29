package juegoTexto;

import civitas.CivitasJuego;
import civitas.Diario;
import civitas.OperacionesJuego;
import civitas.SalidasCarcel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import civitas.Respuestas;

public class VistaTextual {
  
  private CivitasJuego juegoModel; 
  private int iGestion=-1;
  private int iPropiedad=-1;
  private static String separador = "=====================";
  
  private Scanner in;
  
  VistaTextual () {
    in = new Scanner (System.in);
  }
  
  void mostrarEstado(String estado) {
    System.out.println (estado);
  }
              
  void pausa() {
    System.out.print ("Pulsa una tecla");
    in.nextLine();
  }

  int leeEntero (int max, String msg1, String msg2) {
    Boolean ok;
    String cadena;
    int numero = -1;
    do {
      System.out.print (msg1);
      cadena = in.nextLine();
      try {  
        numero = Integer.parseInt(cadena);
        ok = true;
      } catch (NumberFormatException e) { // No se ha introducido un entero
        System.out.println (msg2);
        ok = false;  
      }
      if (ok && (numero < 0 || numero >= max)) {
        System.out.println (msg2);
        ok = false;
      }
    } while (!ok);

    return numero;
  }

  int menu (String titulo, ArrayList<String> lista) {
    String tab = "  ";
    int opcion;
    System.out.println (titulo);
    for (int i = 0; i < lista.size(); i++) {
      System.out.println (tab+i+"-"+lista.get(i));
    }

    opcion = leeEntero(lista.size(),
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo");
    return opcion;
  }

  SalidasCarcel salirCarcel() {
    int opcion = menu ("Elige la forma para intentar salir de la carcel",
      new ArrayList<> (Arrays.asList("Pagando","Tirando el dado")));
    return (SalidasCarcel.values()[opcion]);
  }

  Respuestas comprar() {
    int opcion = menu ("¿Desea comprar la propiedad actual?", 
      new ArrayList<>(Arrays.asList("No", "Sí")));
    return (Respuestas.values()[opcion]);
  }

  void gestionar () {
    iGestion = menu ("¿Qué gestión quiere realizar en sus propiedades?", 
      new ArrayList<> (Arrays.asList("Vender", "Hipotecar", "Cancelar hipoteca", "Construir una casa", "Construir un hotel", "Terminar")));
    if (iGestion != 5) {
      iPropiedad = leeEntero(juegoModel.getJugadorActual().getNumPropiedades(), "\n"+"  Indique el índice de la propiedad a gestionar: ", "  Valor erróneo"); 
    }
  }
  
  public int getGestion(){
    return iGestion;
  }
  
  public int getPropiedad(){
    return iPropiedad;
  }
    

  void mostrarSiguienteOperacion(OperacionesJuego operacion) {
    System.out.println(operacion);
  }

  void mostrarEventos() {
    while (Diario.getInstance().eventosPendientes()) {
      System.out.println(Diario.getInstance().leerEvento());
    }
  }
  
  public void setCivitasJuego(CivitasJuego civitas){ 
    juegoModel=civitas;
    //this.actualizarVista();
  }
  
  void actualizarVista(){
    System.out.println(separador);
    System.out.println("Jugador actual: " + juegoModel.getJugadorActual().toString() + "\n" +
    "\nCasilla actual: " + juegoModel.getCasillaActual().toString());
    System.out.println(separador);
  } 
}
