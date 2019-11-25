require_relative 'casilla'

module Civitas
  class Tablero
    
    def initialize(num_casilla_carcel)
      @num_casilla_carcel = num_casilla_carcel >= 1 ? num_casilla_carcel : 1
      @casillas           = [Casilla.new_descanso("Salida")]
      @por_salida         = 0
      @tiene_juez         = false
    end
    
    attr_reader :num_casilla_carcel, :casillas

    def por_salida
      ret = @por_salida
      @por_salida -=1 if @por_salida > 0
      ret
    end

    def añade_casilla(casilla)
      @casillas << Casilla.new_descanso("Cárcel") if @casillas.size == @num_casilla_carcel
      @casillas << casilla
      @casillas << Casilla.new_descanso("Cárcel") if @casillas.size == @num_casilla_carcel
    end

    def añade_juez
      if !@tiene_juez
        @casillas << Casilla.new_juez(@num_casilla_carcel, "Juez")
        @tiene_juez = true
      end
    end

    def nueva_posicion(actual, tirada)
      result = 
        if !correcto
          -1
        else 
          posicion = (actual + tirada) % @casillas.size
          @por_salida += 1 if posicion != actual + tirada
          posicion
        end
    end

    def calcular_tirada(origen, destino)
      result = destino - origen
      result += @casillas.size if result < 0
      result
    end

    private

    def correcto(num_casilla = 0)
      (@casillas.size > @num_casilla_carcel) && @tiene_juez && (num_casilla < @casillas.size)  
    end

  end
end
