require_relative 'diario'
require_relative 'tipo_sorpresa'

module Civitas
  class Sorpresa
    def initialize(tipo, tablero, valor, mazo, texto)
      @texto = texto
      @valor = valor
      @tablero = tablero
      @mazo = mazo
      @tipo = tipo
    end

    def self.new_envia_carcel(tipo, tablero)
      new(tipo, tablero, -1, nil, "Envía a la cárcel")
    end

    def self.new_envia_casilla(tipo, tablero, valor, texto)
      new(tipo, tablero, valor, nil, texto)
    end

    def self.new_evita_carcel(tipo, mazo)
      new(tipo, nil, -1, mazo, "Permite evitar la cárcel")
    end

    def self.new_sorpresa(tipo, valor, texto)
      new(tipo, nil, valor, nil, texto)
    end

    private_class_method :new

    def aplicar_a_jugador(actual, todos)
      case @tipo
      when TipoSorpresa::IRCASILLA
        aplicar_a_jugador_ir_a_casilla(actual, todos)
      when TipoSorpresa::IRCARCEL
        aplicar_a_jugador_ir_carcel(actual, todos)
      when TipoSorpresa::PAGARCOBRAR
        aplicar_a_jugador_pagar_cobrar(actual, todos)
      when TipoSorpresa::PORCASAHOTEL
        aplicar_a_jugador_por_casa_hotel(actual, todos)
      when TipoSorpresa::PORJUGADOR
        aplicar_a_jugador_por_jugador(actual, todos)
      when TipoSorpresa::SALIRCARCEL
        aplicar_a_jugador_salir_carcel(actual, todos)
      end
    end

    def jugador_correcto(actual, todos)
      actual < todos.size
    end

    def salir_del_mazo
      if @tipo == TipoSorpresa::SALIRCARCEL
        @mazo.inhabilitar_carta_especial(self)
      end
    end

    def to_s
      @texto
    end

    def usada
      if @tipo == TipoSorpresa::SALIRCARCEL
        @mazo.habilitar_carta_especial(self)
      end
    end
    
    private

    def aplicar_a_jugador_ir_a_casilla(actual, todos)
      if jugador_correcto(actual, todos)
        informe(actual, todos)
        casilla_actual = todos[actual].num_casilla_actual
        tirada = @tablero.calcular_tirada(casilla_actual, @valor)
        nueva_posicion = @tablero.nueva_posicion(casilla_actual, tirada)
        todos[actual].mover_a_casilla(nueva_posicion)
        @tablero.casillas[nueva_posicion].recibe_jugador(actual, todos)
      end
    end

    def aplicar_a_jugador_ir_carcel(actual, todos)
      if jugador_correcto(actual, todos)
        informe(actual, todos)
        todos[actual].encarcelar(@tablero.num_casilla_carcel)
      end
    end

    def aplicar_a_jugador_pagar_cobrar(actual, todos)
      if jugador_correcto(actual, todos)
        informe(actual, todos)
        jugador = todos[actual]
        jugador.saldo += @valor
      end
    end

    def aplicar_a_jugador_por_casa_hotel(actual, todos)
      if jugador_correcto(actual, todos)
        informe(actual, todos)
        jugador = todos[actual]
        jugador.saldo += @valor * jugador.cantidad_casas_hoteles
      end
    end

    def aplicar_a_jugador_por_jugador(actual, todos)
      if jugador_correcto(actual, todos)
        informe(actual, todos)
        sorpresa_1 = Sorpresa.new_sorpresa(TipoSorpresa::PAGARCOBRAR, @valor * -1, "\"Todos le pagan a #{todos[actual].nombre}\"")
        for i in 0..todos.size - 1
          sorpresa_1.aplicar_a_jugador(i, todos) if i != actual
        end
        sorpresa_2 = Sorpresa.new_sorpresa(TipoSorpresa::PAGARCOBRAR, @valor * (todos.size - 1), "\"#{todos[actual].nombre} recibe del resto de jugadores\"")
        sorpresa_2.aplicar_a_jugador(actual, todos)
      end
    end

    def aplicar_a_jugador_salir_carcel(actual, todos)
      if jugador_correcto(actual, todos)
        informe(actual, todos)
        salvoconducto = false
        for i in 0..todos.size-1
          salvoconducto = true if todos[i].tiene_salvoconducto
        end
        if !salvoconducto
          todos[actual].obtener_salvoconducto(self)
          salir_del_mazo
        end
      end
    end

    def informe(actual, todos)
      Diario.instance.ocurre_evento("Se le aplica la sorpresa #{@texto} al jugador #{todos[actual].nombre}")
    end

  end
end
