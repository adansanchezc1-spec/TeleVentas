package com.model.model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


public class Producto {
    private String id;
    private String descripcion;
    private float  precio;
    private int    stock;

    public Producto(String id, String descripcion, float precio, int stock) {
        this.id          = id;
        this.descripcion = descripcion;
        this.precio      = precio;
        this.stock       = stock;
    }

    // ── Getters ──────────────────────────────────────────────
    public String getId()          { return id;          }
    public String getDescripcion() { return descripcion; }
    public float  getPrecio()      { return precio;      }
    public int    getStock()       { return stock;       }

    // ── Setters ──────────────────────────────────────────────
    public void setId(String id)                  { this.id          = id;          }
    public void setDescripcion(String descripcion){ this.descripcion = descripcion; }
    public void setPrecio(float precio)           { this.precio      = precio;      }
    public void setStock(int stock)               { this.stock       = stock;       }

    /**
     * Suma unidades al stock (reabastecimiento).
     */
    public void ingresarProducto(int cant) {
        this.stock += cant;
    }

    /**
     * Descuenta unidades. Lanza excepción si no hay suficiente stock.
     */
    public void reducirStock(int cant) {
        if (this.stock < cant) {
            throw new IllegalStateException(
                "Stock insuficiente para el producto: " + id +
                ". Disponible: " + stock + ", solicitado: " + cant);
        }
        this.stock -= cant;
    }

    /**
     * Retorna true si hay al menos 1 unidad disponible.
     */
    public boolean verificarDisponibilidad() {
        return this.stock > 0;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s — $%.2f | Stock: %d",
                id, descripcion, precio, stock);
    }
 

}
