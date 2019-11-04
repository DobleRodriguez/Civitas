require_relative 'jugador'
require_relative 'tablero'
require_relative 'mazo_sorpresas'
require_relative 'sorpresa'
require_relative 'tipo_sorpresa'
require_relative 'operaciones_juego'
require_relative 'gestor_estados'

module Civitas
  class CivitasJuego
    
    def initialize(nombres)
      @indice_jugador_actual = Dado.instance.quien_empieza(nombres.size)
      @gestor_estados = Gestor_estados.new
      @estado = @gestor_estados.estado_inicial
      @tablero
      @mazo = MazoSorpresas.new
      @jugadores = []
      nombres.each { |nombre| @jugadores << Jugador.new(nombre)}
      inicializar_tablero(@tablero)
      inicializar_mazo_sorpresas(@mazo)
    end

    def cancelar_hipoteca(ip)
      jugador_actual.cancelar_hipoteca(ip)
    end

    def comprar
      jugador_a = jugador_actual
      num_casilla_a = jugador_a.num_casilla_actual
      casilla = @tablero.casillas[num_casilla_a]
      titulo = casilla.titulo_propiedad
      res = jugador_a.comprar(titulo)
    end

    def construir_casa(ip)
      jugador_a = jugador_actual
      jugador_a.construir_casa(ip)
    end

    def construir_hotel(ip)
      jugador_a = jugador_actual
      jugador_a.construir_hotel(ip)
    end

    def final_del_juego
      fin = false
      i = 0
      @jugadores.each { |i| fin = true if i.en_bancarrota}
      fin
    end

    def casilla_actual
      jugador_a = jugador_actual
      @tablero.casillas[jugador_a.num_casilla_actual]
    end

    def jugador_actual
      @jugadores[@indice_jugador_actual]
    end

    def hipotecar(ip)
      jugador_actual.hipotecar(ip)
    end

    def info_jugador_texto
      jugador_actual.to_s
    end

    def salir_carcel_pagando
      jugador_actual.salir_carcel_pagando
    end

    def salir_carcel_tirando
      jugador_actual.salir_carcel_tirando
    end

    def siguiente_paso
      jugador_a = jugador_actual
      operacion = @gestor_estados.operaciones_permitidas(jugador_a, @estado)
      case operacion
      when OperacionesJuego::PASAR_TURNO
        pasar_turno
        @estado = siguiente_paso_completado(operacion)
      when OperacionesJuego::AVANZAR
        avanza_jugador
        @estado = siguiente_paso_completado(operacion)
      end
      operacion
    end

    def siguiente_paso_completado(operacion)
      @estado = @gestor_estados.siguiente_estado(jugador_actual, @estado, operacion)
    end

    def vender(ip)
      jugador_actual.vender(ip)
    end

    private

    def avanza_jugador
      jugador = jugador_actual
      posicion_actual = jugador.num_casilla_actual
      tirada = Dado.instance.tirar
      posicion_nueva = @tablero.nueva_posicion(posicion_actual, tirada)
      casilla = @tablero.casillas[posicion_nueva]
      contabilizar_pasos_por_salida(jugador)
      jugador.mover_a_casilla(posicion_nueva)
      casilla.recibe_jugador(@indice_jugador_actual, @jugadores)
      contabilizar_pasos_por_salida(jugador)
    end

    def contabilizar_pasos_por_salida(jugador_a)
      while @tablero.por_salida > 0
        jugador_a.pasa_por_salida
      end
    end

    def inicializar_mazo_sorpresas(tablero)
      @mazo.al_mazo(Sorpresa.new_evita_carcel(TipoSorpresa::SALIRCARCEL, @mazo))
      @mazo.al_mazo(Sorpresa.new_envia_carcel(TipoSorpresa::IRCARCEL, @tablero))
      @mazo.al_mazo(Sorpresa.new_envia_casilla(TipoSorpresa::IRCASILLA, @tablero, 0, "\"Envía a salida\""))
      @mazo.al_mazo(Sorpresa.new_envia_casilla(TipoSorpresa::IRCASILLA, @tablero, 10, "\"Envía al descanso\""))
      @mazo.al_mazo(Sorpresa.new_sorpresa(TipoSorpresa::PAGARCOBRAR, -13370, "\"Pierde 13370\""))
      @mazo.al_mazo(Sorpresa.new_sorpresa(TipoSorpresa::PAGARCOBRAR, 3000, "\"Recibe 3000\""))
      @mazo.al_mazo(Sorpresa.new_sorpresa(TipoSorpresa::PORCASAHOTEL, -700, "\"Pierde 700 por cada edificación\""))
      @mazo.al_mazo(Sorpresa.new_sorpresa(TipoSorpresa::PORCASAHOTEL, 420, "\"Recibe 420 por cada edificación\""))
      @mazo.al_mazo(Sorpresa.new_sorpresa(TipoSorpresa::PORJUGADOR, -1000, "\"Paga 1000 a cada jugador\""))
      @mazo.al_mazo(Sorpresa.new_sorpresa(TipoSorpresa::PORJUGADOR, 900, "\"Cada jugador le paga 900\""))
    end

    def inicializar_tablero(mazo)
      @tablero = Tablero.new(5)
      #@tablero.añade_juez
      @tablero.añade_casilla(Casilla.new_calle(TituloPropiedad.new("A", 10, 10, 10, 10, 10)))
      @tablero.añade_casilla(Casilla.new_calle(TituloPropiedad.new("B", 10, 10, 10, 10, 10)))
      @tablero.añade_casilla(Casilla.new_calle(TituloPropiedad.new("C", 10, 10, 10, 10, 10)))
      @tablero.añade_casilla(Casilla.new_sorpresa(@mazo, "S1"))
      @tablero.añade_casilla(Casilla.new_calle(TituloPropiedad.new("D", 10, 10, 10, 10, 10)))
      @tablero.añade_casilla(Casilla.new_calle(TituloPropiedad.new("E", 10, 10, 10, 10, 10)))
      @tablero.añade_casilla(Casilla.new_calle(TituloPropiedad.new("F", 10, 10, 10, 10, 10)))
      @tablero.añade_casilla(Casilla.new_calle(TituloPropiedad.new("G", 10, 10, 10, 10, 10)))
      @tablero.añade_casilla(Casilla.new_descanso("Descanso"))
      @tablero.añade_casilla(Casilla.new_calle(TituloPropiedad.new("H", 10, 10, 10, 10, 10)))
      @tablero.añade_casilla(Casilla.new_calle(TituloPropiedad.new("I", 10, 10, 10, 10, 10)))
      @tablero.añade_casilla(Casilla.new_sorpresa(@mazo, "S2"))
      @tablero.añade_casilla(Casilla.new_calle(TituloPropiedad.new("J", 10, 10, 10, 10, 10)))
      @tablero.añade_juez
      @tablero.añade_casilla(Casilla.new_calle(TituloPropiedad.new("K", 10, 10, 10, 10, 10)))
      @tablero.añade_casilla(Casilla.new_calle(TituloPropiedad.new("L", 10, 10, 10, 10, 10)))
      @tablero.añade_casilla(Casilla.new_impuesto(2500, "Impuesto"))
      @tablero.añade_casilla(Casilla.new_sorpresa(@mazo, "S3"))
    end

    def pasar_turno
      @indice_jugador_actual += 1
      @indice_jugador_actual = @indice_jugador_actual % @jugadores.size
    end

    def ranking
      @jugadores.sort
    end

  end
end
