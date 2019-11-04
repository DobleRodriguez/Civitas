require_relative 'operacion_inmobiliaria'

module Civitas
  class Controlador
    
    def initialize(juego, vista)
      @juego = juego
      @vista = vista
    end

    def juega
      @vista.setCivitasJuego(@juego)
      operacion = nil
      while !@juego.final_del_juego
        #puts "Cuánto estás ciclando ami"
        @vista.actualizarVista
        @vista.pausa
        #puts "A = #{operacion}"
        @vista.mostrarSiguienteOperacion(operacion = @juego.siguiente_paso)
        #puts "B = #{operacion}"
        if operacion != OperacionesJuego::PASAR_TURNO
          @vista.mostrarEventos
        end
        fin = @juego.final_del_juego
        if !fin
          case operacion
          when OperacionesJuego::COMPRAR
            seleccion = @vista.comprar
            @juego.comprar if @vista.lista_respuestas[seleccion] == Respuestas::SI
            operacion = @juego.siguiente_paso_completado(operacion)
          when OperacionesJuego::GESTIONAR
            @vista.gestionar
            gest = @vista.i_gestion
            ip = @vista.i_propiedad
            op_inmobiliaria = OperacionInmobiliaria.new(gest, ip)
            case @vista.lista_gestiones_inmobiliarias[gest]
            when GestionesInmobiliarias::CANCELAR_HIPOTECA
              @juego.cancelar_hipoteca(ip)
            when GestionesInmobiliarias::CONSTRUIR_CASA
              @juego.construir_casa(ip)
            when GestionesInmobiliarias::CONSTRUIR_HOTEL
              @juego.construir_hotel(ip)
            when GestionesInmobiliarias::HIPOTECAR
              @juego.hipotecar(ip)
            when GestionesInmobiliarias::VENDER
              @juego.vender(ip)
            when GestionesInmobiliarias::TERMINAR
              operacion = @juego.siguiente_paso_completado(operacion)
            end
          when OperacionesJuego::SALIR_CARCEL
            respuesta = @vista.salir_carcel
            case @vista.lista_salidas_carcel[respuesta]
            when SalidasCarcel::PAGANDO
              @juego.salir_carcel_pagando
            when SalidasCarcel::TIRANDO
              @juego.salir_carcel_tirando
            end
            @juego.siguiente_paso_completado(operacion)
          end
        end
      end
      puts "Juego terminado. El ránking de los jugadores es: "
      ranking = @juego.ranking
      ranking.each { |jugador| puts jugador.to_s }
    end

  end
end
