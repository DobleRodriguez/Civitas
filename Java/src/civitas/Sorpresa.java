package civitas;

import java.util.ArrayList;

public class Sorpresa {
  private String texto;
  private int valor;
  private TipoSorpresa tipo;
  private MazoSorpresas mazo;
  private Tablero tablero;

  void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
    switch(tipo) {
      case IRCASILLA:
        aplicarAJugador_irACasilla(actual, todos);
        break;
      case IRCARCEL:
        aplicarAJugador_irCarcel(actual, todos);
        break;
      case PAGARCOBRAR:
        aplicarAJugador_pagarCobrar(actual, todos);
        break;
      case PORCASAHOTEL:
        aplicarAJugador_porCasaHotel(actual, todos);
        break;
      case PORJUGADOR:
        aplicarAJugador_porJugador(actual, todos);
        break;
      case SALIRCARCEL:
        aplicarAJugador_salirCarcel(actual, todos);
    }
  }

  private void aplicarAJugador_irACasilla(int actual, ArrayList<Jugador> todos) {
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

  private void aplicarAJugador_irCarcel(int actual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(actual, todos)) {
      informe(actual, todos);
      todos.get(actual).encarcelar(tablero.getCarcel());
    }
  }

  private void aplicarAJugador_pagarCobrar(int actual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(actual, todos)) {
      informe(actual, todos);
      todos.get(actual).recibe(valor);
    }
  }

  private void aplicarAJugador_porCasaHotel(int actual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(actual, todos)) {
      Jugador jugador = todos.get(actual);
      informe(actual, todos);
      jugador.recibe(valor*jugador.cantidadCasasHoteles());
    }
  }

  private void aplicarAJugador_porJugador(int actual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(actual, todos)) {
      informe(actual, todos);
      Sorpresa s = new Sorpresa(TipoSorpresa.PAGARCOBRAR, -valor, "PagarCobrar a jugador " + todos.get(actual).getNombre());
      for (int i=0; i < todos.size() && i != actual; i++) {
        s.aplicarAJugador(i, todos);
      }
      Sorpresa s2 = new Sorpresa(TipoSorpresa.PAGARCOBRAR, valor*(todos.size()-1), "PagarCobrar del resto de jugadores");
      s2.aplicarAJugador(actual, todos);
    }
  }

  private void aplicarAJugador_salirCarcel(int actual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(actual, todos)) {
      informe(actual, todos);
      Boolean tienen = false;
      for (int i = 0; i<todos.size() && !tienen; i++) {
        tienen = todos.get(i).tieneSalvoconducto();
      }
      if (!tienen) {
        todos.get(actual).obtenerSalvoconducto(this);
        salirDelMazo();
      }
    }
  }

  private void informe(int actual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(actual, todos)) 
      Diario.getInstance().ocurreEvento("Se aplica sorpresa \"" + texto + "\" a jugador " + todos.get(actual).getNombre());
  }

  private void init() {
    valor = -1;
    tablero = null;
    mazo = null;
  }

  public Boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos) {
    return (0 <= actual && actual<todos.size());
  }

  void salirDelMazo() {
    if (tipo.equals(TipoSorpresa.SALIRCARCEL))
      mazo.inhabilitarCartaEspecial(this);
  }
  // Constructores

  Sorpresa(TipoSorpresa ti, Tablero tab) {
    init();
    tipo = ti;
    tablero = tab;
    texto = "Envía a la cárcel";
  }

  Sorpresa(TipoSorpresa ti, Tablero tab, int v, String t) {
    init();
    tipo = ti;
    tablero = tab;
    valor = v;
    texto = t;
    // Envía a otra casilla
  }

  Sorpresa(TipoSorpresa ti, int v, String t) {
    init();
    tipo = ti;
    valor = v;
    texto = t;
    // El resto
  }

  Sorpresa(TipoSorpresa ti, MazoSorpresas m) {
    init();
    tipo = ti;
    mazo = m;
    texto = "Salvoconducto";
    // Evita la cárcel
  }

  public String toString() {
    return texto;
  }

  void usada() {
    if (tipo.equals(TipoSorpresa.SALIRCARCEL)) {
      mazo.habilitarCartaEspecial(this);
    }
  }
  
}