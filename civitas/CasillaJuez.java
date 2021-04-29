package civitas;

import java.util.ArrayList;

public class CasillaJuez extends Casilla {

  private static int carcel;

  CasillaJuez(int numCasillaCarcel, String n) {
    super(n);
    carcel = numCasillaCarcel;
  }

  @Override
  void recibeJugador(int iactual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(iactual, todos)) {
      informe(iactual, todos);
      todos.get(iactual).encarcelar(carcel);
    }
  }

  @Override
  public String toString() {
    String s = super.toString();
    s += "\n  Casilla de tipo juez";
    return s;
  }
  
  @Override
  public String getTipo() {
      return "Juez";
  }
}