#encoding:utf-8
require_relative 'operaciones_juego'
require 'io/console'
require_relative 'salidas_carcel'
require_relative 'respuestas'
require_relative 'gestiones_inmobiliarias'

module Civitas

  class Vista_textual

    def initialize
      @i_gestion
      @i_propiedad
      @juegoModel
      @lista_respuestas = [Respuestas::NO, Respuestas::SI]
      @lista_salidas_carcel = [SalidasCarcel::PAGANDO, SalidasCarcel::TIRANDO]
      @lista_gestiones_inmobiliarias = [GestionesInmobiliarias::VENDER, GestionesInmobiliarias:: HIPOTECAR, GestionesInmobiliarias::CANCELAR_HIPOTECA, GestionesInmobiliarias::CONSTRUIR_CASA, GestionesInmobiliarias::CONSTRUIR_HOTEL, GestionesInmobiliarias::TERMINAR]
    end
    
    attr_reader :lista_respuestas, :lista_salidas_carcel, :lista_gestiones_inmobiliarias, i_gestion, i_propiedad

    def mostrar_estado(estado)
      puts estado
    end

    def pausa
      print "Pulsa una tecla"
      STDIN.getch
      print "\n"
    end

    def lee_entero(max,msg1,msg2)
      ok = false
      begin
        print msg1
        cadena = gets.chomp
        begin
          if (cadena =~ /\A\d+\Z/)
            numero = cadena.to_i
            ok = true
          else
            raise IOError
          end
        rescue IOError
          puts msg2
        end
        if (ok)
          if (numero >= max)
            ok = false
          end
        end
      end while (!ok)

      return numero
    end

    def menu(titulo,lista)
      tab = "  "
      puts titulo
      index = 0
      lista.each { |l|
        puts tab+index.to_s+"-"+l.to_s
        index += 1
      }

      opcion = lee_entero(lista.length,
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo")
      return opcion
    end
    
    def comprar
      lista = @lista_respuestas
      opcion = menu("¿Desea comprar la propiedad actual?", lista)
      opcion
    end

    def gestionar
      lista = @lista_gestiones_inmobiliarias
      @i_gestion = menu("¿Qué desea hacer con la propiedad?", lista)
      @i_propiedad = lee_entero(@juegoModel.jugador_actual.propiedades.size, "\n  Indique el índice de la propiedad que desea gestionar: ",
      "  Valor erróneo") if @i_gestion != 5
    end

    def mostrarSiguienteOperacion(operacion)
      puts operacion
    end

    def mostrarEventos
      while Diario.instance.eventos_pendientes
        puts Diario.instance.leer_evento
      end
    end

    def salir_carcel
      lista = @lista_salidas_carcel
      opcion = menu("Forma de salir de la cárcel: ", lista)
    end

    def setCivitasJuego(civitas)
         @juegoModel=civitas
         #self.actualizarVista
    end

    def actualizarVista
      puts "Jugador actual: 
      #{@juegoModel.jugador_actual.to_s}
Casilla actual: 
      #{@juegoModel.casilla_actual.to_s}
      "
    end
    
  end
end
