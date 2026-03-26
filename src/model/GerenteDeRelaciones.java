package model;
import java.util.ArrayList;
import java.util.List;

public class GerenteDeRelaciones {
  private List<Queja> quejasRecibidas;
  private String nombre;
  private String correo;
  private String rol;

    public GerenteDeRelaciones(String nombre, String correo) {
        this.nombre = nombre;
        this.correo = correo;
        this.rol = "Gerente";
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
     * Atiende y elimina una queja de la lista.
     */
    public void gestionarQueja(Queja queja) {
        boolean eliminada = quejasRecibidas.remove(queja);
        if (eliminada) {
            System.out.println("  [Gerente] Queja atendida: '" + queja.getTitulo() + "'");
        } else {
            System.out.println("  [Gerente] La queja no se encontró en la lista.");
        }
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
