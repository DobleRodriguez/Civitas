package civitas;

import java.util.ArrayList;

public class SorpresaConvertirJugador extends Sorpresa {

  SorpresaConvertirJugador() {
    texto = "Convierte a jugador especulador";
  }

  void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(actual, todos)) {
      Jugador jugador = todos.remove(actual);
      JugadorEspeculador jugadorEspeculador = new JugadorEspeculador(jugador);
      todos.add(actual, jugadorEspeculador);
    }
  }

}