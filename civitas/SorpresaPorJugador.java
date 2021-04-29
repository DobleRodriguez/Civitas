package civitas;

import java.util.ArrayList;

public class SorpresaPorJugador extends Sorpresa {

  float monto;

  SorpresaPorJugador(float valor, String t) {
    monto = valor;
    texto = t;
  }

  void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(actual, todos)) {
      informe(actual, todos);
      Sorpresa s = new SorpresaPagarCobrar(-monto, "PagarCobrar a jugador " + todos.get(actual).getNombre());
      for (int i=0; i < todos.size() && i != actual; i++) {
        s.aplicarAJugador(i, todos);
      }
      Sorpresa s2 = new SorpresaPagarCobrar(monto*(todos.size()-1), "PagarCobrar del resto de jugadores");
      s2.aplicarAJugador(actual, todos);
    }
  }
  
}
