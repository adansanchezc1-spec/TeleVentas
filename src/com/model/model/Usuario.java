package com.model.model;
public abstract class Usuario {
   private String nombre;
    private String correo;
    private String rol;

    public Usuario(String nombre, String correo, String rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.rol    = rol;
    }

    // ── Getters ──────────────────────────────────────────────
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getRol()    { return rol;    }

    // ── Setters ──────────────────────────────────────────────
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setRol(String rol)       { this.rol    = rol;    }

    /**
     * Compara el rol almacenado con el parámetro (sin distinguir mayúsculas).
     */
    public boolean verificarRol(String rol) {
        return this.rol.equalsIgnoreCase(rol);
    }

    /**
     * Cada subclase muestra su propio menú.
     */
    public abstract void mostrarMenu();

    @Override
    public String toString() {
        return "Usuario{nombre='" + nombre + "', rol='" + rol + "'}";
    }
}