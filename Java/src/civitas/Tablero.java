package civitas;

import java.util.ArrayList;

public class Tablero {  
  
  private int numCasillaCarcel;
  private int porSalida;
  private Boolean tieneJuez;
  private ArrayList<Casilla> casillas;
  
  void añadeCasilla (Casilla casilla) {
    if (casillas.size() == numCasillaCarcel) {
      Casilla carcel = new Casilla("Cárcel");
      casillas.add(carcel);
    }
    casillas.add(casilla);
    if (casillas.size() == numCasillaCarcel) {
      Casilla carcel = new Casilla("Cárcel");
      casillas.add(carcel);
    }
  }

  void añadeJuez () {
    if (!tieneJuez) {
      // System.err.println("Estoy en tablero.añadeJuez. Carcel en "+getCarcel());
      Casilla juez = new Casilla(getCarcel(), "Juez");
      casillas.add(juez);
      tieneJuez = true;
    }
  }
  
  int calcularTirada(int origen, int destino) {
    int posicion = destino - origen;
    if (posicion < 0) 
    posicion += casillas.size();
    return posicion;
  }
  
  private Boolean correcto () {
    Boolean valido = false;
    if (casillas.size() > numCasillaCarcel && tieneJuez)
    valido = true;
    return valido;
  }
  
  private Boolean correcto (int numCasilla) {
    Boolean valido = false;
    if (correcto() && numCasilla <= casillas.size() && numCasilla >= 0)
      valido = true;
    return valido;
  }
  
  int getCarcel () {
    return numCasillaCarcel;
  }
  
  Casilla getCasilla (int numCasilla) {
    Casilla casilla = null;
    if (correcto(numCasilla))
      casilla = casillas.get(numCasilla);
    return casilla;
  } 
  
  public ArrayList<Casilla> getCasillas() {
    return casillas;
  }

  int getPorSalida () {
    int ret = porSalida;
    if (porSalida > 0)
      porSalida--;
    return ret;
  }

  int nuevaPosicion (int actual, int tirada) {
    int posicion = -1;
    if (correcto()) {
      posicion = (actual + tirada) % casillas.size();
      if (posicion != (actual + tirada)) 
        porSalida++;
    }
    return posicion;
  }

  public Tablero (int carcel) {
    numCasillaCarcel = 1;
    if (carcel>0)
    numCasillaCarcel = carcel;
    casillas = new ArrayList<>();
    Casilla Salida = new Casilla("Salida");
    casillas.add(0, Salida);
    porSalida = 0;
    tieneJuez = false;
  }

}
