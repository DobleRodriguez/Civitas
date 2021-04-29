package civitas;

import java.util.ArrayList;

public class SorpresaPagarCobrar extends Sorpresa {

  float monto;

  SorpresaPagarCobrar(float valor, String t) {
    monto = valor;
    texto = t;
  }

  void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(actual, todos)) {
      informe(actual, todos);
      todos.get(actual).recibe(monto);
    }
  }
  
}
