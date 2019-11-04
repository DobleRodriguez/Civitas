module Civitas
  class TituloPropiedad

    @@FACTOR_INTERES_HIPOTECA = 1.1
    
    def initialize(nombre, alquiler_base, factor_revalorizacion, hipoteca_base, precio_compra, precio_edificar)
      @alquiler_base = alquiler_base
      @factor_revalorizacion = factor_revalorizacion
      @hipoteca_base = hipoteca_base
      @hipotecado = false
      @nombre = nombre
      @num_casas = 0
      @num_hoteles = 0
      @precio_compra = precio_compra
      @precio_edificar = precio_edificar
      @propietario = nil
    end
    
    attr_reader :nombre, :num_casas, :num_hoteles, :precio_compra, :precio_edificar, :propietario
    
    def actualiza_propietario_por_conversion(jugador)
      @propietario = jugador
    end

    def cancelar_hipoteca(jugador)
      result = false
      if @hipotecado
        if es_este_el_propietario(jugador)
          @propietario.paga(importe_cancelar_hipoteca)
          @hipotecado = false
          result = true
        end
      end
      result
    end

    def cantidad_casas_hoteles
      @num_casas + @num_hoteles
    end

    def comprar(jugador)
      result = false
      if !tiene_propietario
        @propietario = jugador
        result = true
        @propietario.paga(precio_compra)
      end
      result
    end

    def construir_casa(jugador)
      result = false
      if es_este_el_propietario(jugador)
        @propietario.paga(@precio_edificar)
        @num_casas += 1
        result = true
      end
      result
    end

    def construir_hotel(jugador)
      result = false
      if es_este_el_propietario(jugador)
        @propietario.paga(@precio_edificar)
        @num_hoteles += 1
        result = true
      end
      result
    end

    def derruir_casas(n, jugador)
      @num_casas -= n if es_este_el_propietario(jugador) && @num_casas >= n
    end

    def hipotecar(jugador)
      salida = false
      if !@hipotecado && es_este_el_propietario(jugador)
        puts "Hola baby soy yo again la hipoteca que voy a recibir es #{@hipoteca_base}"
        @propietario.recibe(@hipoteca_base)
        @hipotecado = true
        salida = true
      end
      salida #Necesario?
    end

    def importe_cancelar_hipoteca
      @hipoteca_base * @@FACTOR_INTERES_HIPOTECA
    end

    def tiene_propietario
      @propietario != nil
    end
      
    def to_s
      "Nombre: #{@nombre}, Número de casas: #{@num_casas}, Número de hoteles: #{@num_hoteles} y #{@hipotecado ? "" : "no "}se encuentra hipotecada."
=begin
      Alquiler base: #{@alquiler_base}
      Hipoteca base: #{@hipoteca_base}
      Precio de compra: #{@precio_compra}
      Precio por edificar: #{@precio_edificar}
      Factor de revalorización: #{@factor_revalorizacion}
      Factor de interes por hipotecar: #{@@FACTOR_INTERES_HIPOTECA}
      Propietario: #{@propietario}
=end
    end

    def tramitar_alquiler(jugador)
      if tiene_propietario
        if !es_este_el_propietario(jugador)
          precio = precio_alquiler
          puts "El alquiler te cuesta #{precio}"
          jugador.paga_alquiler(precio)
          @propietario.recibe(precio)
        end
      end
    end

    def vender(jugador)
      if es_este_el_propietario(jugador) && !@hipotecado
        @propietario.recibe(precio_venta)
        @propietario = nil
        @num_casas = 0
        @num_hoteles = 0
      end
    end

    private

    def es_este_el_propietario(jugador)
      jugador == @propietario
    end

    attr_reader :hipoteca_base

    def precio_alquiler
      (@hipotecado || propietario_encarcelado) ? 0 : @alquiler_base * (1 + (@num_casas * 0.5) + (@num_hoteles * 2.5))
    end

    def precio_venta
      @precio_compra + @precio_edificar * cantidad_casas_hoteles * @factor_revalorizacion
    end

    def propietario_encarcelado
      @propietario.encarcelado
    end

  end
end
