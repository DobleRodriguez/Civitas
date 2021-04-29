package civitas;

import java.util.ArrayList;
import java.util.Collections;

public class MazoSorpresas {

  private Boolean barajada;
  private Boolean debug;
  private Sorpresa ultimaSorpresa;
  private int usadas;
  private ArrayList<Sorpresa> sorpresas;
  private ArrayList<Sorpresa> cartasEspeciales;

  void alMazo (Sorpresa s) {
    if (!barajada) {
      sorpresas.add(s);
    }
  }

  Sorpresa getUltimaSorpresa() {
    return ultimaSorpresa;
  }

  void habilitarCartaEspecial (Sorpresa sorpresa) {
    int ubicEspecial = cartasEspeciales.indexOf(sorpresa);
    if (ubicEspecial > -1) {
      Sorpresa aDevolver = cartasEspeciales.remove(ubicEspecial);
      sorpresas.add(aDevolver);
      Diario.getInstance().ocurreEvento("Se devuelve una sopresa al mazo");
    }
  }
  
  void inhabilitarCartaEspecial (Sorpresa sorpresa) {
    int ubicSorpresa = sorpresas.indexOf(sorpresa);
    if (ubicSorpresa > -1) {
      Sorpresa aRemover = sorpresas.remove(ubicSorpresa);
      cartasEspeciales.add(aRemover);
      Diario.getInstance().ocurreEvento("Se añade una sorpresa a cartasEspeciales");
    }
  }

  private void init () {
    sorpresas = new ArrayList<>();
    cartasEspeciales = new ArrayList<>();
    barajada = false;
    usadas = 0;
  }

  MazoSorpresas (Boolean b) {
    debug = b;
    init();
    if (debug) {
      Diario.getInstance().ocurreEvento("Debug = True");
    }
  }

  MazoSorpresas () {
      debug = false;
      init();
  }

  Sorpresa siguiente () {
    if (!barajada || usadas == sorpresas.size()) {
      if (!debug) {
        Collections.shuffle(sorpresas);
        usadas = 0;
        barajada = true;
      }
    }
    usadas++;
    ultimaSorpresa = sorpresas.remove(0);
    sorpresas.add(ultimaSorpresa);
    return ultimaSorpresa;
  }

}
