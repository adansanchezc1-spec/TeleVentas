package com.model.model;
import java.util.ArrayList;
import java.util.List;

public class InventarioViejo {
    private static final String NO_ENCONTRADO = "No encontrado";
    private static final float  PRECIO_ERROR  = -1f;

    private List<Producto> catalogo;

    public InventarioViejo(List<Producto> catalogo) {
        this.catalogo = new ArrayList<>(catalogo);
    }

    /**
     * Retorna la descripción del producto con el id dado,
     * o "No encontrado" si no existe.
     */
    public String consultarDescripcion(String id) {
        Producto p = buscarPorId(id);
        return (p != null) ? p.getDescripcion() : NO_ENCONTRADO;
    }

    /**
     * Retorna el precio del producto o -1 si no existe.
     */
    public float consultarPrecio(String id) {
        Producto p = buscarPorId(id);
        return (p != null) ? p.getPrecio() : PRECIO_ERROR;
    }

    /**
     * Reduce el stock del producto en 'cant' unidades.
     * Delega la validación a Producto.reducirStock().
     */
    public void actualizarDisponibilidad(String id, int cant) {
        Producto p = buscarPorId(id);
        if (p == null) {
            System.out.println("  [InventarioViejo] Producto no encontrado: " + id);
            return;
        }
        p.reducirStock(cant);
    }

    // ── Método privado de búsqueda ────────────────────────────
    private Producto buscarPorId(String id) {
        for (Producto p : catalogo) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }
}
