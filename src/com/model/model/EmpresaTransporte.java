package com.model.model;

public abstract class EmpresaTransporte {
    private String nombre;
    public EmpresaTransporte(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    /**
     * Cada empresa implementa su propia lógica de entrega.
     */
    public abstract void entregarPedido(OrdenDeCompra orden);

    @Override
    public String toString() {
        return nombre;
    }
}
