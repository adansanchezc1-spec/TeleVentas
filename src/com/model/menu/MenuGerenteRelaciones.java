package com.model.menu;
import com.model.model.GerenteDeRelaciones;
import com.model.model.Queja;
import java.util.List;
import java.util.Scanner;

/**
 * Menú interactivo para el rol Gerente de Relaciones.
 * Responsabilidad única: capturar entradas y delegar al modelo.
 */
public class MenuGerenteRelaciones {

    private static final int OPCION_VER_QUEJAS      = 1;
    private static final int OPCION_RESPONDER_QUEJA = 2;
    private static final int OPCION_SALIR           = 3;

    public static void mostrar(GerenteDeRelaciones gerente, Scanner sc) {
        int opcion;
        do {
            imprimirMenu(gerente.getNombre());
            opcion = leerOpcion(sc, OPCION_SALIR);

            switch (opcion) {
                case OPCION_VER_QUEJAS -> flujoVerQuejas(gerente);
                case OPCION_RESPONDER_QUEJA -> flujoResponderQueja(gerente, sc);
                case OPCION_SALIR -> {
                }
                default -> System.out.println("  Opción no válida.");
            }
        } while (opcion != OPCION_SALIR);
    }

    // ── Flujo: ver quejas recibidas ──────────────────────────

    private static void flujoVerQuejas(GerenteDeRelaciones gerente) {
        List<Queja> quejas = gerente.getQuejasRecibidas();
        if (quejas.isEmpty()) {
            System.out.println("  No hay quejas registradas en este momento.");
            return;
        }
        System.out.println("\n  ── Quejas Recibidas ──────────────────");
        for (int i = 0; i < quejas.size(); i++) {
            Queja q = quejas.get(i);
            System.out.printf("  [%d] Título     : %s%n", i + 1, q.getTitulo());
            System.out.printf("       Descripción: %s%n", q.getDescripcion());
            if (q.getRespuesta() != null) {
                System.out.printf("       Respuesta  : %s%n", q.getRespuesta());
            }
            System.out.println("       ─────────────────────────────────");
        }
    }

    // ── Flujo: responder una queja ────────────────────────

    private static void flujoResponderQueja(GerenteDeRelaciones gerente, Scanner sc) {
        List<Queja> quejas = gerente.getQuejasRecibidas();
        if (quejas.isEmpty()) {
            System.out.println("  No hay quejas pendientes para responder.");
            return;
        }

        flujoVerQuejas(gerente);

        System.out.print("  Número de queja a responder: ");
        int indice = leerIndice(sc, quejas.size());
        if (indice < 0) {
            System.out.println("  ⚠ Selección inválida.");
            return;
        }

        Queja queja = quejas.get(indice);
        System.out.print("  Ingresa la respuesta: ");
        String respuesta = sc.nextLine().trim();
        gerente.responderQueja(queja, respuesta);
        System.out.printf("  ✔ Queja '%s' respondida.%n", queja.getTitulo());
    }

    // ── Helpers de UI ────────────────────────────────────────

    private static void imprimirMenu(String nombre) {
        System.out.println("\n  ╔════════════════════════════════════════╗");
        System.out.println("  ║  MENÚ GERENTE DE RELACIONES — " + nombre);
        System.out.println("  ╠════════════════════════════════════════╣");
        System.out.println("  ║  1. Ver quejas recibidas               ║");
        System.out.println("  ║  2. Responder una queja                ║");
        System.out.println("  ║  3. Salir                              ║");
        System.out.println("  ╚════════════════════════════════════════╝");
    }

    private static int leerOpcion(Scanner sc, int max) {
        System.out.print("  Opción: ");
        try {
            int op = Integer.parseInt(sc.nextLine().trim());
            return (op >= 1 && op <= max) ? op : -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static int leerIndice(Scanner sc, int max) {
        try {
            int idx = Integer.parseInt(sc.nextLine().trim()) - 1;
            return (idx >= 0 && idx < max) ? idx : -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}