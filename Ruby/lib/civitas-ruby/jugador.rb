require_relative 'diario'
module Civitas
  class Jugador
    @@Casas_max = 4
    @@Casas_por_hotel = 4
    @@Hoteles_max = 4
    @@Paso_por_salida = 1000
    @@Precio_libertad = 200
    @@Saldo_inicial = 7500
    
    def initialize(nombre)
      @encarcelado = false
      @nombre = nombre
      @num_casilla_actual = 0
      @puede_comprar
      @saldo = @@Saldo_inicial
      @propiedades = []
      @salvoconducto = nil
    end
    
    attr_reader :num_casilla_carcel, :num_casilla_actual, :puede_comprar, :encarcelado
    attr_reader :nombre
    attr_reader :propiedades, :saldo
    
    attr_writer :saldo


    def cancelar_hipoteca(ip)
      result = false
      if encarcelado
        result
      else
        if existe_la_propiedad(ip)
          propiedad = @propiedades[ip]
          cantidad = propiedad.importe_cancelar_hipoteca
          if puedo_gastar(cantidad)
            result = propiedad.cancelar_hipoteca(self)
            if result
              Diario.instance.ocurre_evento("El jugador #{nombre} cancela la hipoteca de la propiedad #{ip}")
            end
          end
        end
      end
      result
    end

    def cantidad_casas_hoteles
      i = 0
      @propiedades.each { |propiedad| i += (propiedad.num_casas + propiedad.num_hoteles)}
      i
    end

    def comprar(titulo)
      result = false
      if @encarcelado
        result
      else
        if @puede_comprar
          precio = titulo.precio_compra
          if puedo_gastar(precio)
            result = titulo.comprar(self)
            if result
              @propiedades << titulo
              Diario.instance.ocurre_evento("El jugador #{@nombre} compra la propiedad #{titulo.nombre}")
            end
            @puede_comprar = false
          end
        end
      end
      result
    end

    def construir_casa(ip)
      result = false
      if encarcelado
        result
      else
        existe = existe_la_propiedad(ip)
        if existe
          propiedad = @propiedades[ip]
          puedo_casa = puedo_edificar_casa(propiedad)
          if puedo_casa
            result = propiedad.construir_casa(self)
            if result
              Diario.instance.ocurre_evento("El jugador #{@nombre} construye casa en la propiedad #{ip}")
            end
          end
        end
      end
      result
    end

    def construir_hotel(ip)
      result = false
      if @encarcelado
        result
      else
        if existe_la_propiedad(ip)
          propiedad = @propiedades[ip]
          puedo_hotel = puedo_edificar_hotel(propiedad)
          if puedo_hotel
            result = propiedad.construir_hotel(self)
            casas_hotel = @@Casas_por_hotel
            propiedad.derruir_casas(casas_hotel, self)
            Diario.instance.ocurre_evento("El jugador #{@nombre} construye hotel en la propiedad #{ip}")
          end
        end
      end
      result
    end

    def en_bancarrota
      @saldo < 0
    end

    def encarcelar(num_casilla_carcel)
      if debe_ser_encarcelado
        mover_a_casilla(num_casilla_carcel)
        @encarcelado = true
        Diario.instance.ocurre_evento("#{@nombre} ha sido encarcelado")
      end
      @encarcelado
    end

    def self.Casas_por_hotel
      @@Casas_por_hotel
    end

    def hipotecar(ip)
      result = false
      if @encarcelado
        result
      else
        if existe_la_propiedad(ip)
          propiedad = @propiedades[ip]
          result = propiedad.hipotecar(self)
        end
        if result
          Diario.instance.ocurre_evento("El jugador #{@nombre} hipoteca la propiedad #{ip}")
        end
      end
      result
    end

    def mover_a_casilla(num_casilla)
      if encarcelado
        false
      else
        @num_casilla_actual = num_casilla
        @puede_comprar = false
        Diario.instance.ocurre_evento("#{@nombre} se mueve a casilla #{num_casilla_actual}")
        true
      end
    end

    def obtener_salvoconducto(sorpresa)
      if encarcelado
        false
      else
        @salvoconducto = sorpresa
        true
      end
    end

    def paga(cantidad)
      @saldo -= cantidad
    end

    def paga_alquiler(cantidad)
      if encarcelado
        false
      else
        paga(cantidad)
      end
    end

    def paga_impuesto(cantidad)
      if encarcelado
        false
      else
        paga(cantidad)
      end
    end

    def pasa_por_salida
      @saldo += @@Paso_por_salida
      Diario.instance.ocurre_evento("#{@jugador} pasó por salida y cobró #{@@Paso_por_salida}")
      true
    end

    def puede_comprar_casilla
      if encarcelado
        @puede_comprar = false
      else
        @puede_comprar = true
      end
    end

    def recibe(cantidad)
      if encarcelado
        false
      else
        @saldo += cantidad
      end
    end

    def salir_carcel_pagando
      puts "Dejame pagar coño"
      if encarcelado && puede_salir_carcel_pagando
        paga(@@Precio_libertad)
        @encarcelado = false
        Diario.instance.ocurre_evento("#{@jugador} sale de la cárcel pagando")
        true
      else
        false
      end
    end

    def salir_carcel_tirando
      if Dado.instance.salgo_de_la_carcel
        @encarcelado = false
        Diario.instance.ocurre_evento("#{@jugador} sale de la cárcel tirando")
      end
      puts "#{Dado.instance.ultimo_resultado} != 5"
      @encarcelado
    end

    def tiene_algo_que_gestionar
      !@propiedades.empty?
    end

    def tiene_salvoconducto
      @salvoconducto != nil
    end

    def to_s
      s = "Nombre: #{@nombre}
      Casilla actual: #{@num_casilla_actual}
      Saldo: #{@saldo}
      Propiedades: #{@propiedades.map { |p| p.to_s }}
      #{@salvoconducto != nil ? "T" : "No t"}iene salvoconducto
      #{@puede_comprar ? "P" : "No p"}uede comprar
      #{@encarcelado ? "E" : "No e"}stá encarcelado"
    end

    def vender(ip)
      if encarcelado
        false
      else
        if existe_la_propiedad(ip)
          if propiedades[ip].vender(self)
            Diario.instance.ocurre_evento("#{@jugador} vende la propiedad #{@propiedades[ip].nombre}")
            propiedades.delete_at(ip)
            true
          else
            false
          end
        else
          false
        end
      end
    end

    def <=>(otro)
      otro.saldo <=> @saldo
    end

    private

    def existe_la_propiedad(ip)
      ip < @propiedades.size
    end

    def self.Casas_max
      @@Casas_max
    end

    def self.Hoteles_max
      @@Hoteles_max
    end

    def self.Precio_libertad
      @@Precio_libertad
    end

    def self.Paso_por_salida
      @@Paso_por_salida
    end

    def perder_salvoconducto
      @salvoconducto.usada
      @salvoconducto = nil
    end

    def puede_salir_carcel_pagando
      @saldo >= @@Precio_libertad
    end

    def puedo_edificar_casa(propiedad)
      puedo_casa = false
      precio = propiedad.precio_edificar
      if puedo_gastar(precio) && propiedad.num_casas < @@Casas_max
        puedo_casa = true
      end
      puedo_casa
    end

    def puedo_edificar_hotel(propiedad)
      puedo_hotel = false
      precio = propiedad.precio_edificar
      if puedo_gastar(precio) && propiedad.num_hoteles < @@Hoteles_max && propiedad.num_casas >= @@Casas_por_hotel
        puedo_hotel = true
      end
      puedo_hotel
    end

    def puedo_gastar(precio)
      if encarcelado
        false
      else
        @saldo >= precio
      end
    end

    protected 

    def debe_ser_encarcelado
      if @encarcelado
        false
      else
        if tiene_salvoconducto
          perder_salvoconducto
          Diario.instance.ocurre_evento("#{@nombre} utiliza salvoconducto")
          false
        else
          true
        end
      end
    end

    #attr_reader :propiedades, :saldo
    #attr_reader :nombre

    # Constructor de copia?
  end
end
