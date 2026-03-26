// ══════════════════════════════════════
//  — televentas/model/Cliente.java
// ══════════════════════════════════════
package com.model.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cliente extends Usuario {

    private boolean          recibirCatalogoCada15Dias;
    private final List<OrdenDeCompra> ordenes;
    private final List<Queja>         quejas;

    public Cliente(String nombre, String correo) {
        super(nombre, correo, "Cliente");
        this.recibirCatalogoCada15Dias = false;
        this.ordenes = new ArrayList<>();
        this.quejas  = new ArrayList<>();
    }

    // ── Getter de suscripción ────────────────────────────────
    public boolean isRecibirCatalogoCada15Dias() {
        return recibirCatalogoCada15Dias;
    }

    /**
     * Muestra todos los productos del catálogo en consola.
     */
    public void consultarCatalogo(Catalogo catalogo) {
        System.out.println("  ── Catálogo de Productos ──────────────");
        catalogo.listarProductos();
        System.out.println("  ────────────────────────────────────────");
    }

    /**
     * Activa el envío periódico del catálogo por correo (R1).
     */
    public void solicitarEnvioCatalogo() {
        this.recibirCatalogoCada15Dias = true;
        System.out.println("  [Catálogo] Suscripción activada. Recibirás el catálogo cada 15 días en: "
                + getCorreo());
    }

    /**
     * Crea una orden de compra, la paga y la registra (R3, R4).
     * Retorna la orden creada (puede estar en estado "nocomprado" si el pago falla).
     */
    public OrdenDeCompra ingresarOrdenDeCompra(List<Producto> productos, MetodoPago pago) {
        String       idOrden = generarIdOrden();
        OrdenDeCompra orden  = new OrdenDeCompra(idOrden, pago);

        for (Producto p : productos) {
            orden.agregarProducto(p);
        }

        float total = orden.calcularTotal();
        boolean pagoExitoso = pago.procesarPago(total);

        if (pagoExitoso) {
            reducirStockDeProductos(productos);
            orden.cambiarEstado(OrdenDeCompra.ESTADO_COMPRADO);
            ordenes.add(orden);
        }
        return orden;
    }

    /**
     * Cancela una orden existente del cliente.
     */
    public void cancelarOrden(OrdenDeCompra orden) {
        boolean eliminada = ordenes.remove(orden);
        if (eliminada) {
            System.out.println("  [Orden] Orden #" + orden.getId() + " cancelada exitosamente.");
        } else {
            System.out.println("  [Orden] No se encontró la orden en tu historial.");
        }
    }

    /**
     * Crea una queja y la envía inmediatamente al gerente (R5).
     */
    public void presentarQueja(String titulo, String desc, GerenteDeRelaciones gerente) {
        Queja queja = new Queja(titulo, desc);
        queja.enviarAGerente(gerente);
        quejas.add(queja);
    }

    /**
     * Retorna una copia de la lista de órdenes del cliente.
     */
    public List<OrdenDeCompra> getOrdenes() {
        return new ArrayList<>(ordenes);
    }

    @Override
    public void mostrarMenu() {
        // Delegado a MenuCliente.mostrar() desde Main
        System.out.println("  Redirigiendo al menú de Cliente...");
    }

    // ── Métodos privados ─────────────────────────────────────

    private String generarIdOrden() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private void reducirStockDeProductos(List<Producto> productos) {
        for (Producto p : productos) {
            p.reducirStock(1);
        }
    }
}
