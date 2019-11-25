package civitas;

import java.util.Random;

public class Dado {

  private Random random;
  private int ultimoResultado;
  private Boolean debug;
  static final private Dado instance = new Dado();
  static final private int SalidaCarcel = 5;

  private Dado () {
    random = new Random();
    ultimoResultado = 0;
    debug = false;
  }
  
  int getUltimoResultado () {
    return ultimoResultado;
  }
  
  int tirar () {
    int valor = 1;
    if (!debug) 
    valor = random.nextInt(6)+1;
    ultimoResultado = valor;
    return valor;
  }
  
  Boolean salgoDeLaCarcel () {
    int valor = tirar();
    Boolean salir = false;
    if (valor >= SalidaCarcel) {
      salir = true;
    }
    return salir;
  }
  
  int quienEmpieza (int n) {
    return random.nextInt(n);
  }
  
  public void setDebug (Boolean d) {
    debug = d;
    String estado = "Debug = True";
    if (!debug)
    estado = "Debug = False";
    Diario.getInstance().ocurreEvento(estado);
  }

  public static Dado getInstance() {
    return instance;
  }
  
}
