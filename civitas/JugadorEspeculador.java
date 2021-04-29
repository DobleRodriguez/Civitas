package civitas;

public class JugadorEspeculador extends Jugador {

  private static final int FactorEspeculador = 2;
  private static final int Fianza = 300;

  JugadorEspeculador(Jugador jugador) {
    super(jugador);
    for (TituloPropiedad propiedad : getPropiedades()) {
      propiedad.actualizaPropietarioPorConversion(this);
    }
  }

  @Override
  int getCasasMax() {
    return super.getCasasMax() * FactorEspeculador;
  }

  @Override
  int getHotelesMax() {
    return super.getHotelesMax() * FactorEspeculador;
  }

  @Override
  Boolean pagaImpuesto(float cantidad) {
    return super.pagaImpuesto(cantidad / FactorEspeculador);
  }

  @Override
  Boolean encarcelar(int numCasillaCarcel) {
    Boolean encarcelado;
    if (getSaldo() >= Fianza) {
      paga(Fianza);
      Diario.getInstance().ocurreEvento("El jugador " + getNombre() + " paga fianza para evitar la cárcel");
      encarcelado = false;
    } else {
      encarcelado = super.encarcelar(numCasillaCarcel);
    }
    return encarcelado;
  }

  @Override
  public String toString() {
    String s = super.toString();
    s += "\n  El jugador es especulador";
    return s;
  }
}