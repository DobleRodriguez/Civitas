# encoding:utf-8
require_relative 'civitas_juego'
require_relative 'vista_textual'
require_relative 'dado'
require_relative 'controlador'

#TODO: Arreglar visibilidad de métodos a partir de jugador.rb
module Civitas
  class JuegoTexto

    def initialize
      @vista = Vista_textual.new
      nro_jugadores = @vista.lee_entero(4, "Indique el número de jugadores: ", "Valor incorrecto. Solo pueden jugar hasta 4 jugadores")
      nombres = []
      puts "Indique el nombre de los jugadores: "
      for i in 0..nro_jugadores-1
        nombres << gets.chomp
      end
      @juego = CivitasJuego.new(nombres)
      #(["Alejandro", "Brutus", "Camila", "Diana"])
      Dado.instance.debug = true
      @controlador = Controlador.new(@juego, @vista)
      @controlador.juega
    end
      
  end
end

Civitas::JuegoTexto.new
