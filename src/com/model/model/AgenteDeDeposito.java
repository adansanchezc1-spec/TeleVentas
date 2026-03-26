package com.model.model;

import java.util.ArrayList;
import java.util.List;

public class AgenteDeDeposito extends Usuario {
    
    public AgenteDeDeposito(String nombre, String correo) {
        super(nombre, correo, "Agente");
    }

    /**
     * Filtra y retorna únicamente las órdenes en estado "comprado" (R7).
     */
    public List<OrdenDeCompra> consultarOrdenesConfirmadas(List<OrdenDeCompra> todas) {
        List<OrdenDeCompra> confirmadas = new ArrayList<>();
        for (OrdenDeCompra orden : todas) {
            if (OrdenDeCompra.ESTADO_COMPRADO.equals(orden.getEstado())) {
                confirmadas.add(orden);
            }
        }
        return confirmadas;
    }

    /**
     * Imprime la lista de empaque de todos los productos de la orden y actualiza el stock.
     */
    public void armarPedido(OrdenDeCompra orden) {
        System.out.println("  ┌── Lista de empaque — Orden #" + orden.getId() + " ──");
        List<Producto> productos = orden.getListaProductos();
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            System.out.printf("  │  %d. %s%n", i + 1, p);
            p.reducirStock(1);
            System.out.printf("  │     [Stock] Reducido en 1. Restante: %d%n", p.getStock());
        }
        System.out.println("  └── Pedido armado y listo para despacho. ──");
    }

    /**
     * Reduce el stock del producto en el inventario.
     */
    public void actualizarStock(Producto producto, int cant) {
        producto.reducirStock(cant);
        System.out.printf("  [Stock] Producto '%s' actualizado. Stock restante: %d.%n",
                producto.getId(), producto.getStock());
    }

    /**
     * Asigna una empresa de transporte y cambia el estado de la orden a "encamino" (R8).
     */
    public void asignarEmpresaTransporte(OrdenDeCompra orden, EmpresaTransporte empresa) {
        empresa.entregarPedido(orden);
        orden.cambiarEstado(OrdenDeCompra.ESTADO_ENCAMINO);
        System.out.printf("  [Despacho] Orden #%s asignada a '%s'. Estado: encamino.%n",
                orden.getId(), empresa.getNombre());
    }

    @Override
    public void mostrarMenu() {
        // Delegado a MenuAgenteDeposito.mostrar() desde Main
        System.out.println("  Redirigiendo al menú de Agente de Depósito...");
    }
    
}
