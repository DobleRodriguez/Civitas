require_relative 'gestiones_inmobiliarias'

module Civitas
  class OperacionInmobiliaria

    def initialize(gest, ip)
      @num_propiedad = ip
      @gestion = gest
    end
    attr_reader :num_propiedad, :gestion
      
  end
end
