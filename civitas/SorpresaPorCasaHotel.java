package civitas;

import java.util.ArrayList;

public class SorpresaPorCasaHotel extends Sorpresa {

  float monto;

  SorpresaPorCasaHotel(float valor, String t) {
    monto = valor;
    texto = t;
  }

  void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(actual, todos)) {
      Jugador jugador = todos.get(actual);
      informe(actual, todos);
      jugador.recibe(monto*jugador.cantidadCasasHoteles());
    }
  }
  
}
