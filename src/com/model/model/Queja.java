package com.model.model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


public class Queja {
 private String titulo;
    private String descripcion;
    private String respuesta;

    public Queja(String titulo, String descripcion) {
        this.titulo      = titulo;
        this.descripcion = descripcion;
        this.respuesta   = null;
    }

    // ── Getters ──────────────────────────────────────────────
    public String getTitulo()      { return titulo;      }
    public String getDescripcion() { return descripcion; }
    public String getRespuesta()   { return respuesta;   }

    // ── Setter ───────────────────────────────────────────────
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }

    /**
     * Envía la queja directamente al gerente de relaciones (R5: sin cola ni demora).
     */
    public void enviarAGerente(GerenteDeRelaciones gerente) {
        gerente.recibirQueja(this);
        System.out.println("  [Queja] Enviada inmediatamente al Gerente de Relaciones.");
    }

    @Override
    public String toString() {
        return "Queja{título='" + titulo + "', descripción='" + descripcion + "'" +
               (respuesta != null ? ", respuesta='" + respuesta + "'" : "") + "}";
    }
}
