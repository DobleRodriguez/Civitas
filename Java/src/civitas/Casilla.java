package civitas;

import java.util.ArrayList;

public class Casilla {
    
  private String nombre ;
  private static int carcel;
  private float importe;
  private TipoCasilla tipo;
  private TituloPropiedad tituloPropiedad;
  private MazoSorpresas mazoSorpresas;

  Casilla(String n) {
    init();
    nombre = n;
    tipo = TipoCasilla.DESCANSO;
  }

  Casilla(TituloPropiedad titulo) {
    init();
    tituloPropiedad = titulo;
    nombre = titulo.getNombre();
    tipo = TipoCasilla.CALLE;
  }

  Casilla(float cantidad, String n) {
    init();
    importe = cantidad;
    nombre = n;
    tipo = TipoCasilla.IMPUESTO;
  }

  Casilla(int numCasillaCarcel, String n) {
    init();
    carcel = numCasillaCarcel;
    nombre = n; 
    tipo = TipoCasilla.JUEZ;
  }

  Casilla(MazoSorpresas mazo, String n) {
    init();  
    mazoSorpresas = mazo;
    nombre = n;
    tipo = TipoCasilla.SORPRESA;
  }

  public String getNombre() {
    return nombre;
  }

  TituloPropiedad getTituloPropiedad() {
    return tituloPropiedad;
  }
  
  private void informe(int iactual, ArrayList<Jugador> todos) {
    Diario.getInstance().ocurreEvento("El jugador " + todos.get(iactual).getNombre() + " ha caído en la casilla " + getNombre());
  }

  private void init() {
    nombre = "";
    importe = 0;
    tipo = null;
    tituloPropiedad = null;
    mazoSorpresas = null;
  }

  public Boolean jugadorCorrecto(int iactual, ArrayList<Jugador> todos) {
    return(0 <= iactual && iactual<todos.size());
  }

  void recibeJugador(int iactual, ArrayList<Jugador> todos) {
    switch (tipo) {
      case CALLE:
        recibeJugador_calle(iactual, todos);
        break;
      case IMPUESTO:
        recibeJugador_impuesto(iactual, todos);
        break;
      case JUEZ:
        recibeJugador_juez(iactual, todos);
        break;
      case SORPRESA:
        recibeJugador_sorpresa(iactual, todos);
        break;
      default:
        informe(iactual, todos);
        break;
    }
  }

  private void recibeJugador_calle(int iactual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(iactual, todos)) {
      informe(iactual, todos);
      Jugador jugador = todos.get(iactual);
      if (!tituloPropiedad.tienePropietario()) {
        jugador.puedeComprarCasilla();
      }
      else {
        tituloPropiedad.tramitarAlquiler(jugador);
      }
    }  
  }

  private void recibeJugador_impuesto(int iactual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(iactual, todos)) {
      informe(iactual, todos);
      todos.get(iactual).pagaImpuesto(importe);
    }
  }

  private void recibeJugador_juez(int iactual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(iactual, todos)) {
      informe(iactual, todos);
      //System.err.println("Estoy en casilla.recibe_juez. Carcel en "+carcel);
      todos.get(iactual).encarcelar(carcel);
    }
  }

  private void recibeJugador_sorpresa(int iactual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(iactual, todos)) {
      Sorpresa sorpresa = mazoSorpresas.siguiente();
      informe(iactual, todos);
      sorpresa.aplicarAJugador(iactual, todos);
    }
  }

  public String toString() {
    String s = 
      "\n  Nombre: " + nombre +
      "\n  Tipo de la casilla: "+ tipo; 
    return s;
  }
}
