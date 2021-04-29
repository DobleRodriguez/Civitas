package civitas;

import java.util.ArrayList;

public class Casilla {
    
  private String nombre ;

  Casilla(String n) {
    nombre = n;
  }

  public String getNombre() {
    return nombre;
  }

  void informe(int iactual, ArrayList<Jugador> todos) {
    Diario.getInstance().ocurreEvento("El jugador " + todos.get(iactual).getNombre() + " ha caído en la casilla " + getNombre());
  }

  public Boolean jugadorCorrecto(int iactual, ArrayList<Jugador> todos) {
    return(0 <= iactual && iactual<todos.size());
  }

  void recibeJugador(int iactual, ArrayList<Jugador> todos) {
    informe(iactual, todos);
  }
  
  public String toString() {
    String s = 
      "\n  Nombre: " + nombre;
    return s;
  }
  
  public String getTipo() {
      return "Descanso";
  }
}
