package com.model.service;

import java.util.ArrayList;
import java.util.List;

import com.model.model.OrdenDeCompra;

public class OrdenService {
    
    // Lista global compartida en memoria durante la ejecución
    private static final List<OrdenDeCompra> TODAS_LAS_ORDENES = new ArrayList<>();

    /**
     * Retorna la lista global de órdenes (referencia directa para filtrado).
     */
    public List<OrdenDeCompra> getTodasLasOrdenes() {
        return TODAS_LAS_ORDENES;
    }

    /**
     * Registra una nueva orden en el repositorio global.
     */
    public void registrarOrden(OrdenDeCompra orden) {
        TODAS_LAS_ORDENES.add(orden);
        System.out.println("  [OrdenService] Orden #" + orden.getId() + " registrada.");
    }

    /**
     * Elimina una orden del repositorio global.
     */
    public void cancelarOrden(OrdenDeCompra orden) {
        boolean eliminada = TODAS_LAS_ORDENES.remove(orden);
        if (eliminada) {
            System.out.println("  [OrdenService] Orden #" + orden.getId() + " eliminada del sistema.");
        }
    }
}
