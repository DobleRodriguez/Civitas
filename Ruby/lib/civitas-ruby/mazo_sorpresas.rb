module Civitas
  class MazoSorpresas
    
    def initialize(debug = false)
      @sorpresas = []
      @barajada = false
      @usadas = 0
      @debug = debug
      Diario.instance.ocurre_evento("MazoSorpresas puesto en modo debug") if debug
      @cartas_especiales = []
      @ultima_sorpresa
    end

    def al_mazo(s)
      @sorpresas << s if !@barajada
    end

    def siguiente
      if !@barajada || (@usadas == @sorpresas.size)
        @sorpresas.shuffle! if !@debug
        @usadas = 0
        @barajada = true
      end
      @usadas += 1
      @ultima_sorpresa = @sorpresas.shift
      @sorpresas << @ultima_sorpresa
      @ultima_sorpresa
    end

    def inhabilitar_carta_especial(sorpresa)
      inhabilitada = @sorpresas.delete(sorpresa)
      if inhabilitada != nil
        @cartas_especiales << inhabilitada
        Diario.instance.ocurre_evento("Inhabilitada sorpresa #{sorpresa}")
      end
    end
    
    def habilitar_carta_especial(sorpresa)
      habilitada = @cartas_especiales.delete(sorpresa)
      if habilitada != nil
        @sorpresas << habilitada
        Diario.instance.ocurre_evento("Habilitada sorpresa #{sorpresa}")
      end
    end

  end
end
