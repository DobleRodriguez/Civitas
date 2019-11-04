require_relative 'tipo_sorpresa'
require_relative 'tipo_casilla'
require_relative 'titulo_propiedad'
require_relative 'mazo_sorpresas'

module Civitas
  class Casilla
    @@carcel

    def initialize(cantidad, num_casilla_carcel, mazo, nombre, titulo, tipo)
      @importe = cantidad
      @nombre = nombre
      @tipo = tipo
      @titulo_propiedad = titulo
      @mazo = mazo
      @@carcel = num_casilla_carcel if num_casilla_carcel != nil
    end

    attr_reader :nombre, :titulo_propiedad

    def self.new_descanso(nombre)
      new(nil, nil, nil, nombre, nil, TipoCasilla::DESCANSO)
    end

    def self.new_calle(titulo)
      new(nil, nil, nil, titulo.nombre, titulo, TipoCasilla::CALLE)
    end

    def self.new_impuesto(cantidad, nombre)
      new(cantidad, nil, nil, nombre, nil, TipoCasilla::IMPUESTO)
    end

    def self.new_juez(num_casilla_carcel, nombre)
      new(nil, num_casilla_carcel, nil, nombre, nil, TipoCasilla::JUEZ)
    end

    def self.new_sorpresa(mazo, nombre)
      new(nil, nil, mazo, nombre, nil, TipoCasilla::SORPRESA)
    end

    private_class_method :new

    def jugador_correcto(iactual, todos)
      iactual < todos.size
    end

    def recibe_jugador(iactual, todos)
      case @tipo
      when TipoCasilla::CALLE
        recibe_jugador_calle(iactual, todos)
      when TipoCasilla::IMPUESTO
        recibe_jugador_impuesto(iactual, todos)
      when TipoCasilla::JUEZ
        recibe_jugador_juez(iactual, todos)
      when TipoCasilla::SORPRESA
        recibe_jugador_sorpresa(iactual, todos)
      else
        informe(iactual, todos)
      end
    end

    def to_s
      "Nombre: #{@nombre}
      Tipo: #{@tipo}"
    end

    private

    def informe(iactual, todos)
      Diario.instance.ocurre_evento("#{todos[iactual].nombre} ha caído en 
      #{to_s}")
    end

    def recibe_jugador_calle(iactual, todos)
      if jugador_correcto(iactual, todos)
        informe(iactual, todos)
        jugador = todos[iactual]
        if !@titulo_propiedad.tiene_propietario
          jugador.puede_comprar_casilla
        else
          puts "Hola deberías tramitar el alquiler hermanazo"
          @titulo_propiedad.tramitar_alquiler(jugador)
        end
      end
    end

    def recibe_jugador_impuesto(iactual, todos)
      if jugador_correcto(iactual, todos)
        informe(iactual, todos)
        todos[iactual].paga_impuesto(@importe)
      end
    end

    def recibe_jugador_juez(iactual, todos)
      if jugador_correcto(iactual, todos)
        informe(iactual, todos)
        todos[iactual].encarcelar(@@carcel)
      end
    end

    def recibe_jugador_sorpresa(iactual, todos)
      if jugador_correcto(iactual, todos)
        sorpresa = @mazo.siguiente
        informe(iactual, todos)
        sorpresa.aplicar_a_jugador(iactual, todos)
      end
    end

  end
end
