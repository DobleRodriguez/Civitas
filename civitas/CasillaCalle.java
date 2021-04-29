package civitas;

import java.util.ArrayList;

public class CasillaCalle extends Casilla {

  private TituloPropiedad tituloPropiedad;

  CasillaCalle(TituloPropiedad propiedad) {
    super(propiedad.getNombre());
    tituloPropiedad = propiedad;
  }

  @Override
  void recibeJugador(int iactual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(iactual, todos)) {
      informe(iactual, todos);
      Jugador jugador = todos.get(iactual);
      if (!tituloPropiedad.tienePropietario()) {
        jugador.puedeComprarCasilla();
      } else {
        tituloPropiedad.tramitarAlquiler(jugador);
      }
    }
  }  

  public TituloPropiedad getTituloPropiedad() {
    return tituloPropiedad;
  }

  @Override
  public String toString() {
    String s = super.toString();
    s += "\n  Casilla de tipo calle";
    return s;
  }
  
  @Override
  public String getTipo() {
      return "Calle";
  }
  
}