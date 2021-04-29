package civitas;

import java.util.ArrayList;

public class TestP4 {

  public static void main(String[] args) {

    ArrayList<Jugador> jugadores = new ArrayList<>();
    jugadores.add(new Jugador("Dab"));
    TituloPropiedad titulo = new TituloPropiedad("Propiedad", 10, 10, 10, 10);
    jugadores.get(0).getPropiedades().add(titulo);
    SorpresaConvertirJugador s = new SorpresaConvertirJugador();
    s.aplicarAJugador(0, jugadores);
    //System.out.println(Diario.getInstance().leerEvento());
    System.out.println(jugadores.get(0));
    System.out.println(titulo.getPropietario());
  }
}