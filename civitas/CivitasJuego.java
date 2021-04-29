package civitas;

import java.util.ArrayList;
import java.util.Collections;
import GUI.Dado;

public class CivitasJuego {

  private int indiceJugadorActual;
  private MazoSorpresas mazo;
  private EstadosJuego estado;
  private GestorEstados gestorEstados;
  private ArrayList<Jugador> jugadores;
  private Tablero tablero;

  private void avanzaJugador() {
    Jugador jugadorActual = getJugadorActual();
    int posicionActual = jugadorActual.getNumCasillaActual();
    int tirada = Dado.getInstance().tirar();
    int posicionNueva = tablero.nuevaPosicion(posicionActual, tirada);
    Casilla casilla = tablero.getCasilla(posicionNueva);
    this.contabilizarPasosPorSalida(jugadorActual);
    jugadorActual.moverACasilla(posicionNueva);
    casilla.recibeJugador(indiceJugadorActual, jugadores);
    this.contabilizarPasosPorSalida(jugadorActual);
  }

  public Boolean cancelarHipoteca(int ip) {
    return getJugadorActual().cancelarHipoteca(ip);
  }

  public CivitasJuego(ArrayList<String> nombres) {
    jugadores = new ArrayList<Jugador>();
    for(String i : nombres) {
        jugadores.add(new Jugador(i));
    }
    gestorEstados = new GestorEstados();
    estado = gestorEstados.estadoInicial();
    indiceJugadorActual = Dado.getInstance().quienEmpieza(jugadores.size());
    mazo = new MazoSorpresas();
    inicializarTablero(mazo);
    inicializarMazoSorpresas(tablero);
  }

  public Boolean comprar() {
    Jugador jugadorActual = getJugadorActual();
    CasillaCalle casilla = (CasillaCalle)getCasillaActual();
    TituloPropiedad titulo = casilla.getTituloPropiedad();
    Boolean res = jugadorActual.comprar(titulo);
    return res;
  }

  public Boolean construirCasa(int ip) {
    return getJugadorActual().construirCasa(ip);
  }

  public Boolean construirHotel(int ip) {
    return getJugadorActual().construirHotel(ip);
  }

  private void contabilizarPasosPorSalida(Jugador jugadorActual) {
    while (tablero.getPorSalida() > 0) {
      jugadorActual.pasaPorSalida();
    }
  }

  public Boolean finalDelJuego() {
    for (Jugador i : jugadores) {
      //if (i.enBancarrota()) {
      if (i.enBancarrota() ) {
        return true;
      }
    }
    return false;
  }

  public Casilla getCasillaActual() {
    return tablero.getCasilla(getJugadorActual().getNumCasillaActual());
  }

  public Jugador getJugadorActual() {
    return jugadores.get(indiceJugadorActual);
  }

  public Boolean hipotecar(int ip) {
    return getJugadorActual().hipotecar(ip);
  }

  public String infoJugadorTexto() {
    return getJugadorActual().toString();
  }

  private void inicializarMazoSorpresas(Tablero tablero) {
    //mazo.alMazo(new SorpresaIrCarcel(tablero));
    //mazo.alMazo(new SorpresaSalirCarcel(mazo));
    //mazo.alMazo(new SorpresaPagarCobrar(1000, "Recibe 1000$"));
    mazo.alMazo(new SorpresaPagarCobrar(-10000, "Paga 1000$"));
    //mazo.alMazo(new SorpresaIrACasilla(tablero, 10, "Va a Parking"));
    //mazo.alMazo(new SorpresaIrACasilla(tablero, 0, "Va a Salida"));
    //mazo.alMazo(new SorpresaPorCasaHotel(-800, "Paga impuestos de 800$ por cada construcción"));
    //mazo.alMazo(new SorpresaPorCasaHotel(300, "Recibe honorarios de 300$ por cada edificación"));
    //mazo.alMazo(new SorpresaPorJugador(-100, "Todos los jugadores reciben 100$ de ti"));
    //mazo.alMazo(new SorpresaPorJugador(50, "Todos los jugadores te pagan 50$"));
    //mazo.alMazo(new SorpresaConvertirJugador());
   // mazo.alMazo(new SorpresaConvertirJugador());
  }

  private void inicializarTablero(MazoSorpresas mazo) {
    tablero = new Tablero(5);        
    tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("A", 10000, 10, 10, 10)));
    // tablero.añadeJuez();
    tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("B", 10, 10, 10, 10)));
    tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("C", 10, 10, 10, 10)));
    tablero.añadeCasilla(new CasillaSorpresa(mazo, "S1"));
    tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("D", 10, 10, 10, 10)));
    tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("E", 10, 10, 10, 10)));
    tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("F", 10, 10, 10, 10)));
    tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("G", 10, 10, 10, 10)));
    tablero.añadeCasilla(new Casilla("Descanso"));
    tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("H", 10, 10, 10, 10)));
    tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("I", 10, 10, 10, 10)));
    tablero.añadeCasilla(new CasillaSorpresa(mazo, "S2"));
    tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("J", 10, 10, 10, 10)));
    tablero.añadeJuez();
    tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("K", 10, 10, 10, 10)));
    tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("L", 10, 10, 10, 10)));
    tablero.añadeCasilla(new CasillaImpuesto(2500.0f, "Impuesto")); // Si no cree que es cárcel
    tablero.añadeCasilla(new CasillaSorpresa(mazo, "S3"));
    //System.out.println("NUM CASILLA CARCEL = " + tablero.getCarcel());
  }

  private void pasarTurno() {
    indiceJugadorActual = (indiceJugadorActual+1)%jugadores.size();
  }

  public ArrayList<Jugador> ranking() { // Debería ser privado?
    Collections.sort(jugadores);
    return jugadores;
  }

  public Boolean salirCarcelPagando() {
    return getJugadorActual().salirCarcelPagando();
  }

  public Boolean salirCarcelTirando() {
    return getJugadorActual().salirCarcelTirando();
  }

  public OperacionesJuego siguientePaso() {
    Jugador jugadorActual = jugadores.get(indiceJugadorActual);
    OperacionesJuego operacion = gestorEstados.operacionesPermitidas(jugadorActual, estado);
    if (operacion == OperacionesJuego.PASAR_TURNO) {
      pasarTurno();
      siguientePasoCompletado(operacion);
    }
    else if (operacion == OperacionesJuego.AVANZAR) {
      avanzaJugador();
      siguientePasoCompletado(operacion);
    }
    return operacion;
  }

  public void siguientePasoCompletado(OperacionesJuego operacion) {
    estado = gestorEstados.siguienteEstado(getJugadorActual(), estado, operacion);
  }
  
  public Boolean vender(int ip) {
    return getJugadorActual().vender(ip);
  }

}
