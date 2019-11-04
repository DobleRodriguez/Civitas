require_relative 'dado'
require_relative 'diario'
require_relative 'estados_juego'
require_relative 'tipo_sorpresa'
require_relative 'tipo_casilla'
require_relative 'mazo_sorpresas'
require_relative 'tablero'
require_relative 'sorpresa'

module Civitas
  puts Diario.instance.object_id
  array = [0, 0, 0, 0]
  for i in 0..99
    array[Dado.instance.quien_empieza(4)] += 1
  end
  array.each { |elem| puts elem }
  Dado.instance.debug = true
  for i in 0..4
    puts Dado.instance.tirar
  end
  Dado.instance.debug = false
  for i in 0..4
    puts Dado.instance.tirar
  end
  puts Dado.instance.ultimo_resultado
  puts Dado.instance.salgo_de_la_carcel
  puts Estados_juego::DESPUES_AVANZAR
  puts Tipo_sorpresa::IRCARCEL
  puts Tipo_casilla::CALLE
  mazo = MazoSorpresas.new
  s1 = Sorpresa.new
  s2 = Sorpresa.new
  puts mazo.al_mazo(s1)
  puts mazo.al_mazo(s2)
  puts mazo.siguiente
  mazo.inhabilitar_carta_especial(s2)
  mazo.habilitar_carta_especial(s2)
  while Diario.instance.eventos_pendientes
    puts Diario.instance.leer_evento
  end
  tablero = Tablero.new(2)
  tablero.añade_casilla(Casilla.new("A"))
  tablero.añade_casilla(Casilla.new("B"))
  tablero.casillas.each { |i| puts i.nombre }
  tablero.añade_juez
  #puts tablero.calcular_tirada(0, Dado.instance.tirar)
  puts tablero.nueva_posicion(0, tablero.calcular_tirada(0, Dado.instance.tirar))
  puts Dado.instance.ultimo_resultado
end
