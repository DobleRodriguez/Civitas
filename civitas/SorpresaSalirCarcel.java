package civitas;

import java.util.ArrayList;

public class SorpresaSalirCarcel extends Sorpresa {

  private MazoSorpresas mazoSorpresas;

  SorpresaSalirCarcel(MazoSorpresas mazo) {
    mazoSorpresas = mazo;
    texto = "Salvoconducto";
  }

  void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
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
  
  void salirDelMazo() {
    mazoSorpresas.inhabilitarCartaEspecial(this);
  }

  void usada() {
    mazoSorpresas.habilitarCartaEspecial(this);
  }

}
