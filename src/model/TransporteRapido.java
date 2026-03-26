package model;

public class TransporteRapido extends EmpresaTransporte {
        public TransporteRapido(String nombre) {
        super(nombre);
    }

    @Override
    public void entregarPedido(OrdenDeCompra orden) {
        System.out.printf("  [TransporteRápido - %s] Pedido #%s en camino — entrega estimada: 24h.%n",
                getNombre(), orden.getId());
    }
}


