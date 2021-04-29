package civitas;

import java.util.ArrayList;

abstract class Sorpresa {

  String texto;

  abstract void aplicarAJugador(int actual, ArrayList<Jugador> todos);

  void informe(int actual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(actual, todos)) {
      Diario.getInstance().ocurreEvento("Se aplica sorpresa \"" + texto + "\" a jugador " + todos.get(actual).getNombre());
    }
  }

  public Boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos) {
    return (0 <= actual && actual<todos.size());
  }

  public String toString() {
    return texto;
  }

}
