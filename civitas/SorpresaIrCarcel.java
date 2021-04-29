package civitas;

import java.util.ArrayList;

public class SorpresaIrCarcel extends Sorpresa {

  Tablero tablero;

  SorpresaIrCarcel(Tablero tablero) {
    this.tablero = tablero;
    texto = "Ir a la cárcel";
  }

  void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(actual, todos)) {
      informe(actual, todos);
      todos.get(actual).encarcelar(tablero.getCarcel());
    }
  }
  
}
