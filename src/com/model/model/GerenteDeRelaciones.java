package com.model.model;
import java.util.ArrayList;
import java.util.List;

public class GerenteDeRelaciones extends Usuario {
    private final List<Queja> quejasRecibidas;

    public GerenteDeRelaciones(String nombre, String correo) {
        super(nombre, correo, "Gerente");
        this.quejasRecibidas = new ArrayList<>();
    }

    /**
     * Recibe una queja y la registra en la lista interna.
     */
    public void recibirQueja(Queja queja) {
        quejasRecibidas.add(queja);
        System.out.println("  [Gerente] Queja recibida: '" + queja.getTitulo() + "'");
    }

    /**
     * Responde a una queja y la marca como atendida (la mantiene en la lista).
     */
    public void responderQueja(Queja queja, String respuesta) {
        queja.setRespuesta(respuesta);
        System.out.println("  [Gerente] Queja respondida: '" + queja.getTitulo() + "'");
    }

    /**
     * Retorna una copia de la lista de quejas para no exponer el estado interno.
     */
    public List<Queja> getQuejasRecibidas() {
        return new ArrayList<>(quejasRecibidas);
    }

    /**
     * Delega la presentación del menú a la capa de menús.
     * La implementación completa se conecta desde Main.
     */
    @Override
    public void mostrarMenu() {
        // Delegado a MenuGerenteRelaciones.mostrar() desde Main
        System.out.println("  Redirigiendo al menú de Gerente de Relaciones...");
    }
}
