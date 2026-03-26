/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class OrdenDeCompra {
 public static final String ESTADO_NO_COMPRADO = "nocomprado";
    public static final String ESTADO_COMPRADO    = "comprado";
    public static final String ESTADO_ENCAMINO    = "encamino";

    private static final List<String> ESTADOS_VALIDOS =
            Arrays.asList(ESTADO_NO_COMPRADO, ESTADO_COMPRADO, ESTADO_ENCAMINO);

    private String          id;
    private String          estado;
    private List<Producto>  listaProductos;
    private MetodoPago      metodoPago;

    public OrdenDeCompra(String id, MetodoPago metodoPago) {
        this.id             = id;
        this.metodoPago     = metodoPago;
        this.estado         = ESTADO_NO_COMPRADO;
        this.listaProductos = new ArrayList<>();
    }

    // ── Getters ──────────────────────────────────────────────
    public String         getId()              { return id;             }
    public String         getEstado()          { return estado;         }
    public List<Producto> getListaProductos()  { return listaProductos; }
    public MetodoPago     getMetodoPago()      { return metodoPago;     }

    /**
     * Agrega un producto a la lista de la orden.
     */
    public void agregarProducto(Producto p) {
        listaProductos.add(p);
    }

    /**
     * Imprime el detalle completo de la orden.
     */
    public void consultarOrden() {
        System.out.println("  ┌── Orden #" + id + " ──────────────────────");
        System.out.println("  │  Estado : " + estado);
        System.out.println("  │  Productos:");
        for (Producto p : listaProductos) {
            System.out.println("  │    • " + p);
        }
        System.out.printf("  │  Total  : $%.2f%n", calcularTotal());
        System.out.println("  └──────────────────────────────────────");
    }

    /**
     * Cambia el estado de la orden.
     * Lanza IllegalArgumentException si el valor no es uno de los 3 permitidos.
     */
    public void cambiarEstado(String nuevoEstado) {
        if (!ESTADOS_VALIDOS.contains(nuevoEstado)) {
            throw new IllegalArgumentException(
                "Estado inválido: '" + nuevoEstado + "'. Permitidos: " + ESTADOS_VALIDOS);
        }
        this.estado = nuevoEstado;
    }

    /**
     * Calcula la suma de precios de todos los productos en la orden.
     */
    public float calcularTotal() {
        float total = 0f;
        for (Producto p : listaProductos) {
            total += p.getPrecio();
        }
        return total;
    }

}
