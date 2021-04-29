package juegoTexto;

import civitas.CivitasJuego;
import civitas.GestionesInmobiliarias;
import civitas.OperacionInmobiliaria;
import civitas.OperacionesJuego;
import civitas.Respuestas;
import civitas.SalidasCarcel;

public class Controlador {

  private CivitasJuego juego;
  private VistaTextual vista;

  Controlador(CivitasJuego juego, VistaTextual vista) {
    this.juego = juego;
    this.vista = vista;
  }

  void juega() {
    vista.setCivitasJuego(juego);
    while (!juego.finalDelJuego()) {
      vista.actualizarVista();
      vista.pausa();
      OperacionesJuego sigPaso = juego.siguientePaso();
      vista.mostrarSiguienteOperacion(sigPaso);
      if (sigPaso != OperacionesJuego.PASAR_TURNO) {
        vista.mostrarEventos();
      }
      if (!juego.finalDelJuego()) {
        switch (sigPaso) {
          case COMPRAR:
            if (vista.comprar() == Respuestas.SI) {
              juego.comprar();
            }
            juego.siguientePasoCompletado(sigPaso);
            break;
          case GESTIONAR:
            vista.gestionar();
            GestionesInmobiliarias gestion = GestionesInmobiliarias.values()[vista.getGestion()];
            int ip = vista.getPropiedad();
            OperacionInmobiliaria operacion = new OperacionInmobiliaria(gestion, ip);
            switch (gestion) {
              case VENDER:
                juego.vender(ip);
                break;
              case HIPOTECAR:
                juego.hipotecar(ip);
                break;
              case CANCELAR_HIPOTECA:
                juego.cancelarHipoteca(ip);
                break;
              case CONSTRUIR_CASA:
                juego.construirCasa(ip);
                break;
              case CONSTRUIR_HOTEL:
                juego.construirHotel(ip);
                break;
              case TERMINAR:
                juego.siguientePasoCompletado(sigPaso);
                break;
            }
           break;	
          case SALIR_CARCEL:
            SalidasCarcel modo_salida = vista.salirCarcel();
            switch (modo_salida) {
              case PAGANDO:
                juego.salirCarcelPagando();
                break;
              case TIRANDO:
                juego.salirCarcelTirando();
                break;
            }
            juego.siguientePasoCompletado(sigPaso);
            break;
          default:
            break;
        }
      }
    }
    System.out.println("Juego terminado. El ránking es: ");
    System.out.println(juego.ranking());
  }
} 