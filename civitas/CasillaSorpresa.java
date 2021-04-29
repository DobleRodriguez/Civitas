
package civitas;

import java.util.ArrayList;

public class CasillaSorpresa extends Casilla {

  private MazoSorpresas mazo;

  CasillaSorpresa(MazoSorpresas mazo, String n) {
    super(n);
    this.mazo = mazo;
  }

  @Override
  void recibeJugador(int iactual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(iactual, todos)) {
      informe(iactual, todos);
      mazo.siguiente().aplicarAJugador(iactual, todos);
    }
  }

  @Override
  public String toString() {
    String s = super.toString();
    s += "\n  Casilla de tipo sorpresa";
    return s;
  }
  
  @Override
  public String getTipo() {
      return "Sorpresa";
  }
}
