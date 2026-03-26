package com.model.menu;
import com.model.model.AgenteDeDeposito;
import com.model.model.Cliente; 
import com.model.model.EmpresaTransporte;
import com.model.model.GerenteDeRelaciones;
import com.model.model.Usuario;
import com.model.service.OrdenService;
import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {

/**
 * Menú de inicio de sesión. Responsabilidad única: autenticar y crear el usuario.
 */

    private static final int ROL_CLIENTE  = 1;
    private static final int ROL_AGENTE   = 2;
    private static final int ROL_GERENTE  = 3;

    /**
     * Solicita nombre, correo y rol. Retorna el objeto Usuario correspondiente.
     */
    public static Usuario iniciarSesion(Scanner sc) {
        System.out.println("\n  ╔══════════════════════════════════╗");
        System.out.println("  ║     BIENVENIDO A TELEVENTAS      ║");
        System.out.println("  ╚══════════════════════════════════╝");

        String nombre = leerNombreNoVacio(sc);
        String correo = leerCorreo(sc);
        int    opcion = leerOpcionRol(sc);

        return crearUsuario(opcion, nombre, correo);
    }

    // ── Helpers privados ─────────────────────────────────────

    private static String leerNombreNoVacio(Scanner sc) {
        String nombre;
        do {
            System.out.print("  Ingresa tu nombre: ");
            nombre = sc.nextLine().trim();
            if (nombre.isEmpty()) {
                System.out.println("  ⚠ El nombre no puede estar vacío. Intenta de nuevo.");
            }
        } while (nombre.isEmpty());
        return nombre;
    }

    private static String leerCorreo(Scanner sc) {
        System.out.print("  Ingresa tu correo electrónico: ");
        return sc.nextLine().trim();
    }

    private static int leerOpcionRol(Scanner sc) {
        int opcion = 0;
        do {
            System.out.println("\n  Selecciona tu rol:");
            System.out.println("    [1] Cliente");
            System.out.println("    [2] Agente de Depósito");
            System.out.println("    [3] Gerente de Relaciones");
            System.out.print("  Opción: ");
            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                opcion = 0;
            }
            if (opcion < ROL_CLIENTE || opcion > ROL_GERENTE) {
                System.out.println("  ⚠ Opción inválida. Ingresa 1, 2 o 3.");
            }
        } while (opcion < ROL_CLIENTE || opcion > ROL_GERENTE);
        return opcion;
    }

    private static Usuario crearUsuario(int opcion, String nombre, String correo) {
        switch (opcion) {
            case ROL_CLIENTE:  return new Cliente(nombre, correo);
            case ROL_AGENTE:   return new AgenteDeDeposito(nombre, correo);
            case ROL_GERENTE:  return new GerenteDeRelaciones(nombre, correo);
            default:           throw new IllegalArgumentException("Rol no reconocido: " + opcion);
        }
    }
}

