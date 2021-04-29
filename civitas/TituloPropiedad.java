package civitas;

public class TituloPropiedad {
  private float alquilerBase;
  private static final float factorInteresHipoteca = 1.1f;
  private float factorRevalorizacion;
  private float hipotecaBase;
  private Boolean hipotecado;
  private String nombre;
  private int numCasas;
  private int numHoteles;
  private float precioCompra;
  private float precioEdificar;
  private Jugador propietario;
  
  void actualizaPropietarioPorConversion(Jugador jugador) {
      propietario = jugador;
  }
  
  Boolean cancelarHipoteca(Jugador jugador) {
    Boolean result = false;
    if (hipotecado) {
      if (esEsteElPropietario(jugador)) {
        propietario.paga(getImporteCancelarHipoteca());
        hipotecado = false;
        result = true;
      }
    }
    return result;
  }
  
  int cantidadCasasHoteles() {
    int casashoteles = numCasas + numHoteles;
    return casashoteles;
  }
  
  Boolean comprar(Jugador jugador) {
    Boolean result = false;
    if (!tienePropietario()) {
      propietario = jugador;
      result = true;
      jugador.paga(precioCompra);
    }
    return result;
  }

  Boolean construirCasa(Jugador jugador) {
    Boolean casa = false;
    if (esEsteElPropietario(jugador)) {
      jugador.paga(precioEdificar);
      numCasas++;
      casa = true;
    } 
    return casa;
  }

  Boolean construirHotel(Jugador jugador) {
    Boolean result = false;
    if (esEsteElPropietario(jugador)) {
      propietario.paga(precioEdificar);
      numHoteles++;
      result = true;
    }
    return result;
  }

  Boolean derruirCasas(int n, Jugador jugador) {
    Boolean derruida = false;
    if (esEsteElPropietario(jugador) && numCasas >= n) {
      numCasas -= n;
      derruida = true;
    }
    return derruida;
  }
  
  private Boolean esEsteElPropietario (Jugador jugador) {
    return (propietario.equals(jugador));
  }

  public Boolean getHipotecado() {
    return hipotecado;
  }
  
  float getImporteCancelarHipoteca() {
    float importeCancelar = getImporteHipoteca() *factorInteresHipoteca;
    return importeCancelar;
  }
  
  public float getImporteHipoteca() {
    float cantidadRecibida = hipotecaBase * (1 + (numCasas * 0.5f) + (numHoteles*2.5f));
    return cantidadRecibida;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public int getNumCasas() {
    return numCasas;
  }
  
  public int getNumHoteles() {
      return numHoteles;
  }
  
  public float getPrecioAlquiler() {
    float precioAlquiler = 0;
    if (!hipotecado && !propietarioEncarcelado()) {
      precioAlquiler = alquilerBase*(1+(numCasas*0.5f)+(numHoteles*2.5f));
    }
    return precioAlquiler;
  }
  
  public float getPrecioCompra() {
    return precioCompra;
  }

  public float getPrecioEdificar() {
    return precioEdificar;
  }
  
  public float getPrecioVenta() {
    float precioVenta = getPrecioCompra() + getPrecioEdificar() * (numCasas + numHoteles) * factorRevalorizacion;
    return precioVenta;
  }
  
  public Jugador getPropietario() {
    return propietario;
  }
  
  Boolean hipotecar(Jugador jugador) {
    Boolean salida  = false;
    if (!hipotecado && esEsteElPropietario(jugador)) {
      salida = propietario.recibe(getImporteHipoteca());
      hipotecado = true;
    }
    return salida;
  }

  private Boolean propietarioEncarcelado() {
    Boolean encarcelado = false;
    if (propietario != null && propietario.isEncarcelado())
      encarcelado = true;
    return encarcelado;
  }

  public Boolean tienePropietario() {
    // System.out.println(propietario);
    return (propietario != null);
  }

  TituloPropiedad (String nom, float ab, float hb, float pc, float pe) {
    nombre = nom;
    alquilerBase = ab;
    hipotecaBase = hb;
    precioCompra = pc;
    precioEdificar = pe;
    numCasas = 0;
    numHoteles = 0;
    hipotecado = false;
    propietario = null;
  }                    
  
  public String toString() {
    String s = String.format(
    "\n    Nombre: %s" +
    ", N° casas: %d" +
    ", N° hoteles: %d" +
    "  y " + (hipotecado ? "" : "no ") + "se encuentra hipotecada",
    nombre, numCasas, numHoteles);
    return s;
  }

  void tramitarAlquiler(Jugador jugador) {
    if (tienePropietario()) {
      if (!esEsteElPropietario(jugador)) {
        float precio = getPrecioAlquiler();
        jugador.pagaAlquiler(precio);
        propietario.recibe(precio);
      }
    }
  }

  Boolean vender(Jugador jugador) { 
    Boolean venta = false;
    if (tienePropietario() && esEsteElPropietario(jugador)) {
      propietario.recibe(getPrecioVenta());
      propietario = null;
      venta = true;
    }
    return venta;
  }
  
}
    