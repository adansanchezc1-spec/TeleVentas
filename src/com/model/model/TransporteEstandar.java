package com.model.model;

public class TransporteEstandar extends EmpresaTransporte {
        public TransporteEstandar(String nombre) {
        super(nombre);
    }

    @Override
    public void entregarPedido(OrdenDeCompra orden) {
        System.out.printf("  [TransporteEstándar - %s] Pedido #%s en camino — entrega estimada: 72h.%n",
                getNombre(), orden.getId());
    }
}
