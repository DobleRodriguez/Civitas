require 'singleton'

module Civitas
  class Dado
    include Singleton

    @@SALIDACARCEL = 5

    def initialize
      @random = Random.new
      @ultimo_resultado = 0
      @debug = false
    end

    attr_reader :ultimo_resultado

    def tirar
      @ultimo_resultado = @debug ? 1 : @random.rand(6) + 1
    end

    def salgo_de_la_carcel
      tirar == @@SALIDACARCEL
    end

    def quien_empieza(n)
      @random.rand(n)
    end

    def debug=(d)
      @debug = d
      s = d ? "Dado puesto a modo debug" : "Dado puesto a modo estándar"
      Diario.instance.ocurre_evento(s)
    end

  end
end
