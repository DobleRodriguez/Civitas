require_relative 'civitas_juego'
require_relative 'vista_textual'
require_relative 'dado'
require_relative 'controlador'

#TODO: Arreglar visibilidad de métodos a partir de jugador.rb
module Civitas
  class JuegoTexto

    def initialize
      @vista = Vista_textual.new
      @juego = CivitasJuego.new(["Alejandro", "Brutus", "Camila", "Diana"])
      Dado.instance.debug = true
      @controlador = Controlador.new(@juego, @vista)
      @controlador.juega
    end
      
  end
end

Civitas::JuegoTexto.new
