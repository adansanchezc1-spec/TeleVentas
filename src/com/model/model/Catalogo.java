package com.model.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class Catalogo {

 private List<Producto> productos;

    public Catalogo() {
        this.productos = new ArrayList<>();
    }

    /**
     * Retorna una copia de la lista de productos.
     */
    public List<Producto> crearListaProductos() {
        return new ArrayList<>(productos);
    }

    /**
     * Busca un producto por su ID. Retorna null si no existe.
     */
    public Producto obtenerProducto(String id) {
        for (Producto p : productos) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Imprime todos los productos en consola.
     */
    public void listarProductos() {
        if (productos.isEmpty()) {
            System.out.println("  (El catálogo está vacío)");
            return;
        }
        for (int i = 0; i < productos.size(); i++) {
            System.out.printf("  %d. %s%n", i + 1, productos.get(i));
        }
    }

    /**
     * Agrega un producto al catálogo (usado en la capa de servicio).
     */
    public void agregarProducto(Producto p) {
        productos.add(p);
    }

}
