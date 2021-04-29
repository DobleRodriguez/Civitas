package civitas;

import java.util.ArrayList;

public class CasillaImpuesto extends Casilla {

  private float monto;

  CasillaImpuesto(float valor, String n) {
    super(n);
    monto = valor;
  }

  @Override
  void recibeJugador(int iactual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(iactual, todos)) {
      informe(iactual, todos);
      todos.get(iactual).pagaImpuesto(monto);
    }
  }

  @Override
  public String toString() {
    String s = super.toString();
    s += "\n  Casilla de tipo impuesto";
    return s;
  }
  
  @Override
  public String getTipo() {
      return "Impuesto";
  }
}
