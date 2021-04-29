package civitas;

import java.util.ArrayList;

public class SorpresaIrACasilla extends Sorpresa {

  private Tablero tablero;
  private int valor;

  SorpresaIrACasilla(Tablero tab, int v, String t) {
    tablero = tab;
    valor = v;
    texto = t;
  }

  void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(actual, todos)) {
      informe(actual, todos);
      Jugador jugador = todos.get(actual);
      int casillaActual = jugador.getNumCasillaActual();
      int tirada = tablero.calcularTirada(casillaActual, valor);
      int nuevaPosicion = tablero.nuevaPosicion(casillaActual, tirada);
      jugador.moverACasilla(nuevaPosicion);
      tablero.getCasilla(valor).recibeJugador(actual, todos);
    }
  }

}
