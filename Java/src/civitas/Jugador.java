package civitas;

import java.util.ArrayList;

public class Jugador implements Comparable<Jugador> {

  protected static final int CasasMax = 4;
  protected static final int CasasPorHotel = 4;
  protected Boolean encarcelado;
  protected static final int HotelesMax = 4;
  private String nombre;
  private int numCasillaActual;
  protected static final float PasoPorSalida = 1000;
  protected static final float PrecioLibertad = 200;
  private Boolean puedeComprar;
  private float saldo;
  private static final float SaldoInicial = 7500;
  private ArrayList<TituloPropiedad> propiedades;
  private Sorpresa salvoconducto;

  Boolean cancelarHipoteca(int ip) {
    Boolean result = false;
    if (encarcelado) {
      return result;
    }
    if (existeLaPropiedad(ip)) {
      TituloPropiedad propiedad = propiedades.get(ip);
      float cantidad = propiedad.getImporteCancelarHipoteca();
      Boolean puedoGastar = this.puedoGastar(cantidad);
      if (puedoGastar) {
        result = propiedad.cancelarHipoteca(this);
      }
      if (result) {
        Diario.getInstance().ocurreEvento(String.format("El jugador %s cancela la hipoteca de la propiedad %d", nombre, ip));
      }
    }
    return result;
  }

  int cantidadCasasHoteles() {
    int cantidad = 0;
    for (int i = 0; i<propiedades.size(); i++) {
      cantidad += propiedades.get(i).cantidadCasasHoteles();
    }
    return cantidad;
  }

  public int compareTo(Jugador otro) {
    return Float.compare(otro.saldo, saldo);
  }

  Boolean comprar (TituloPropiedad titulo) {
    Boolean result = false;
    if (encarcelado){
      return result;
    }
    if (puedeComprar) {
      float precio = titulo.getPrecioCompra();
      if (puedoGastar(precio)) {
        result = titulo.comprar(this);
        if (result) {
          propiedades.add(titulo);
          Diario.getInstance().ocurreEvento(String.format("El jugador %s compra la propiedad %s", nombre, titulo.getNombre()));
        }
      }
      puedeComprar = false;
    }
    return result;
  }

  Boolean construirCasa(int ip) {
    Boolean result = false;
    if (encarcelado) {
        return result;
    }
    Boolean existe = existeLaPropiedad(ip);
    if (existe) {
      TituloPropiedad propiedad = propiedades.get(ip);
      Boolean puedoEdificarCasa = puedoEdificarCasa(propiedad);
      float precio = propiedad.getPrecioEdificar();
      if (puedoGastar(precio) && propiedad.getNumCasas() < getCasasMax()) {
        puedoEdificarCasa = true;
      }
      if (puedoEdificarCasa) {
        result = propiedad.construirCasa(this);
        if (result) {
          Diario.getInstance().ocurreEvento(String.format("El jugador %s construye casa en la propiedad %d", nombre, ip));
        }
      }
    }
    return result;
  }

  Boolean construirHotel(int ip) {
    Boolean result = false;
    if (encarcelado) {
      return result;
    }
    if (existeLaPropiedad(ip)) {
      TituloPropiedad propiedad = propiedades.get(ip);
      Boolean puedoEdificarHotel = puedoEdificarHotel(propiedad);
      if (puedoEdificarHotel) {
        result = propiedad.construirHotel(this);
        int casasPorHotel = getCasasPorHotel();
        propiedad.derruirCasas(casasPorHotel, this);
      }
      Diario.getInstance().ocurreEvento(String.format("El jugador %s construye hotel en la propiedad %d", nombre, ip));
    }
    return result;
  }

  protected Boolean debeSerEncarcelado() {
    Boolean debe = false;
    if (!encarcelado) {
      if (tieneSalvoconducto()) {
        perderSalvoconducto();
        Diario.getInstance().ocurreEvento("El jugador " + nombre + " se libra de la cárcel");
      }
      else
        debe = true;
    }
    return debe;
  }

  Boolean enBancarrota() {
    return saldo < 0;
  }

  Boolean encarcelar(int numCasillaCarcel) {
    // System.err.println("Estoy en jugador.encarcelar. Carcel en "+numCasillaCarcel);
    if (debeSerEncarcelado()) {
      moverACasilla(numCasillaCarcel);
      encarcelado = true;
      Diario.getInstance().ocurreEvento("El jugador " + nombre + " se encarcela");
    }
    return encarcelado;
  }

  private Boolean existeLaPropiedad(int ip) {
    return ip < propiedades.size() && ip >= 0;
  }

  private int getCasasMax() {
    return CasasMax;
  }

  int getCasasPorHotel() {
    return CasasPorHotel;
  }

  private int getHotelesMax() {
    return HotelesMax;
  }

  protected String getNombre() {
    return nombre;
  }
  
  int getNumCasillaActual() {
    return numCasillaActual;
  }

  private float getPrecioLibertad() {
    return PrecioLibertad;
  }

  private float getPremioPasoSalida() {
    return PasoPorSalida;
  }

  protected ArrayList<TituloPropiedad> getPropiedades() {
    return propiedades;
  }

  Boolean getPuedeComprar() {
    return puedeComprar;
  }

  protected float getSaldo() {
    return saldo;
  }

  Boolean hipotecar(int ip) {
    Boolean result = false;
    if (encarcelado) {
      return result;
    }
    if (existeLaPropiedad(ip)) {
      TituloPropiedad propiedad = propiedades.get(ip);
      result = propiedad.hipotecar(this);
    }
    if (result) {
      Diario.getInstance().ocurreEvento(String.format("El jugador %s hipoteca la propiedad %d", nombre, ip));
    }
    return result;
  }

  public Boolean isEncarcelado() {
    return encarcelado;
  }

  Jugador(String n) {
    encarcelado = false;
    nombre = n;
    numCasillaActual = 0;
    saldo = SaldoInicial;
    puedeComprar = true;
    salvoconducto = null;
    propiedades = new ArrayList<TituloPropiedad>();
  } 

  protected Jugador(Jugador otro) {
    encarcelado = otro.encarcelado;
    nombre = otro.nombre;
    numCasillaActual = otro.numCasillaActual;
    saldo = otro.saldo;
    puedeComprar = otro.puedeComprar;
    salvoconducto = otro.salvoconducto;
    propiedades = new ArrayList<TituloPropiedad>();
    for (int i=0; i<otro.propiedades.size(); i++) {
      propiedades.add(otro.propiedades.get(i));
    }
  }

  Boolean modificarSaldo(float cantidad) {
    saldo += cantidad;
    Diario.getInstance().ocurreEvento("El jugador " + getNombre() + (cantidad >= 0 ? " recibe " : " paga ") + Math.abs(cantidad));
    return true;
  }

  Boolean moverACasilla(int numCasilla) {
    Boolean mueve = false;
    if (!encarcelado) {
      numCasillaActual = numCasilla;
      puedeComprar = false;
      Diario.getInstance().ocurreEvento("Se movió al jugador " + nombre + " a la casilla " + numCasilla);
      mueve = true;
    }
    return mueve;
  }

  Boolean obtenerSalvoconducto(Sorpresa sorpresa) {
    Boolean obtiene = false;
    if (!encarcelado) {
      salvoconducto = sorpresa;
      obtiene = true;
    }
    return obtiene;
  }

  Boolean paga(float cantidad) {
    return modificarSaldo(-cantidad);
  }

  Boolean pagaAlquiler(float cantidad) {
    Boolean paga = false;
    if (!encarcelado) {
      paga = paga(cantidad);
    }
    return paga;
  }

  Boolean pagaImpuesto(float cantidad) {
    Boolean paga = false;
    if (!encarcelado) {
      paga = paga(cantidad);
    }
    return paga;
  }
  
  Boolean pasaPorSalida() {
    modificarSaldo(getPremioPasoSalida());
    Diario.getInstance().ocurreEvento("Pasa por salida el jugador "+nombre);
    return true;
  }

  private void perderSalvoconducto() {
    salvoconducto.usada();
    salvoconducto = null;
  }

  Boolean puedeComprarCasilla() {
    if (encarcelado)
      puedeComprar = false;
    else
      puedeComprar = true;
    return puedeComprar;
  }

  private Boolean puedeSalirCarcelPagando() {
    return saldo >= getPrecioLibertad();
  }

  private Boolean puedoEdificarCasa(TituloPropiedad propiedad) {
    Boolean puede = false;
    if (!encarcelado) {
      Boolean esLaPropiedad = false;
      for (int i=0; i<propiedades.size() && !esLaPropiedad; i++) {
        esLaPropiedad = (propiedades.get(i).equals(propiedad));
      } 
      if (esLaPropiedad && propiedad.getNumCasas() < CasasMax && saldo >= propiedad.getPrecioEdificar()) {
        puede = true;
      }
    }
    return puede;
  }

  private Boolean puedoEdificarHotel(TituloPropiedad propiedad) {
    Boolean puedoEdificarHotel = false;
    float precio = propiedad.getPrecioEdificar();
    if (puedoGastar(precio)) {
      if (propiedad.getNumHoteles() < getHotelesMax()) {
        if (propiedad.getNumCasas() >= getCasasPorHotel()) {
          puedoEdificarHotel = true;
        }
      }
    }
    return puedoEdificarHotel;
  }

  private Boolean puedoGastar(float precio) {
    Boolean puede = false;
    if (!encarcelado) {
      puede = saldo >= precio;
    }
    return puede;
  }

  Boolean recibe(float cantidad) {
    Boolean recibir = false;
    if (!encarcelado) {
      recibir = modificarSaldo(cantidad);
    }
    return recibir;
  }

  Boolean salirCarcelPagando() {
    Boolean sale = false;
    if (encarcelado && puedeSalirCarcelPagando()) {
      paga(getPrecioLibertad());
      encarcelado = false;
      Diario.getInstance().ocurreEvento("Jugador "+nombre+" paga para salir de la cárcel");
      sale = true;
    }
    return sale;
  }

  Boolean salirCarcelTirando() {
    Boolean tirada = Dado.getInstance().salgoDeLaCarcel();
    if (tirada) {
      encarcelado = false;
      Diario.getInstance().ocurreEvento("El jugador "+nombre+" tira para salir de la cárcel");
    }
    return tirada;
  }

  Boolean tieneAlgoQueGestionar() {
    return !propiedades.isEmpty();
  }

  Boolean tieneSalvoconducto() {
    return (salvoconducto != null);
  }

  public String toString() {
    String s = String.format(
      "\n\n  Nombre: %s" +
      "\n  Casilla actual: %d" +
      (encarcelado ? "\n  S" : "\n  No s") + "e encuentra encarcelado" +
      "\n  Saldo: %f" +
      (tieneSalvoconducto() ? "\n  T" : "\n  No t") + "iene salvoconducto" +
      "\n  Propiedades: %s", nombre, numCasillaActual, saldo, propiedades.toString());
    return s;
  }

  Boolean vender(int ip) {
    Boolean puede = false;
    if (!encarcelado && existeLaPropiedad(ip)) {
      if (propiedades.get(ip).vender(this)) {
        propiedades.remove(ip);
        Diario.getInstance().ocurreEvento("Jugador "+nombre+" vende propiedad");
        puede = true;
      }
    }
    return puede;
  }

  public int getNumPropiedades() { // HOLA??????? 
    return propiedades.size();
  }
}