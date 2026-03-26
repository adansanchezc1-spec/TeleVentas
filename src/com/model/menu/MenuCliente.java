package com.model.menu;
import com.model.model.Catalogo;
import com.model.model.Cliente;
import com.model.model.GerenteDeRelaciones;
import com.model.model.MetodoPago;
import com.model.model.OrdenDeCompra;
import com.model.model.Producto;
import com.model.service.OrdenService;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuCliente {
      private static final int OPCION_CONSULTAR_CATALOGO   = 1;
    private static final int OPCION_SOLICITAR_ENVIO      = 2;
    private static final int OPCION_INGRESAR_ORDEN       = 3;
    private static final int OPCION_CANCELAR_ORDEN       = 4;
    private static final int OPCION_PRESENTAR_QUEJA      = 5;
    private static final int OPCION_SALIR                = 6;

    private static String lastEntrada;

    public static void mostrar(Cliente cliente, Catalogo catalogo,
                               GerenteDeRelaciones gerente,
                               OrdenService ordenService, Scanner sc) {
        int opcion;
        do {
            imprimirMenu(cliente.getNombre());
            String entrada = leerEntrada(sc);
            try {
                opcion = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                if (entrada.contains(",") && entrada.toUpperCase().contains("P")) {
                    opcion = OPCION_INGRESAR_ORDEN;
                } else {
                    opcion = -1;
                }
            }

            switch (opcion) {
                case OPCION_CONSULTAR_CATALOGO -> cliente.consultarCatalogo(catalogo);
                case OPCION_SOLICITAR_ENVIO -> cliente.solicitarEnvioCatalogo();
                case OPCION_INGRESAR_ORDEN -> flujoIngresarOrden(cliente, catalogo, ordenService, sc, (opcion == OPCION_INGRESAR_ORDEN && lastEntrada != null && !lastEntrada.matches("\\d+")) ? lastEntrada : null);
                case OPCION_CANCELAR_ORDEN -> flujoGestionarCancelacion(cliente, ordenService, sc);
                case OPCION_PRESENTAR_QUEJA -> flujoPresentarQueja(cliente, gerente, sc);
                case OPCION_SALIR -> {
                }
                default -> System.out.println("  ⚠ Opción no válida.");
            }
        } while (opcion != OPCION_SALIR);
    }

    // ── Flujo: ingresar orden de compra ──────────────────────

    private static void flujoIngresarOrden(Cliente cliente, Catalogo catalogo,
                                           OrdenService ordenService, Scanner sc, String preInput) {
        System.out.println("\n  ── Ingresar Orden de Compra ──────────");
        cliente.consultarCatalogo(catalogo);

        List<Producto> seleccionados = seleccionarProductos(catalogo, sc, preInput);
        if (seleccionados.isEmpty()) {
            System.out.println("  ⚠ No se seleccionó ningún producto válido. Orden cancelada.");
            return;
        }

        MetodoPago pago = leerMetodoPago(sc);
        OrdenDeCompra orden = cliente.ingresarOrdenDeCompra(seleccionados, pago);

        if (OrdenDeCompra.ESTADO_COMPRADO.equals(orden.getEstado())) {
            ordenService.registrarOrden(orden);
            System.out.printf("  ✔ Orden #%s confirmada. Total: $%.2f%n",
                    orden.getId(), orden.calcularTotal());
        } else {
            System.out.println("  ✖ Tarjeta inválida. Orden cancelada.");
        }
    }

    private static List<Producto> seleccionarProductos(Catalogo catalogo, Scanner sc, String preInput) {
        String idsStr;
        if (preInput != null) {
            idsStr = preInput;
        } else {
            System.out.print("  Ingresa los IDs de productos separados por coma (ej: P001,P003): ");
            idsStr = sc.nextLine().trim();
        }
        String[] ids = idsStr.split(",");
        List<Producto> seleccionados = new ArrayList<>();

        for (String idRaw : ids) {
            String id = idRaw.trim();
            Producto p = catalogo.obtenerProducto(id);
            if (p == null) {
                System.out.println("  ⚠ Producto no encontrado: " + id);
            } else if (!p.verificarDisponibilidad()) {
                System.out.println("  ⚠ Sin stock: " + id);
            } else {
                seleccionados.add(p);
            }
        }
        return seleccionados;
    }

    private static MetodoPago leerMetodoPago(Scanner sc) {
        System.out.print("  Número de tarjeta de crédito (16 dígitos): ");
        String numeroTarjeta = sc.nextLine().trim();
        return new MetodoPago(numeroTarjeta);
    }

    // ── Flujo: cancelar orden ────────────────────────────────

    private static void flujoGestionarCancelacion(Cliente cliente,
                                                  OrdenService ordenService, Scanner sc) {
        List<OrdenDeCompra> ordenes = cliente.getOrdenes();
        if (ordenes.isEmpty()) {
            System.out.println("  No tienes órdenes registradas.");
            return;
        }

        System.out.println("\n  ── Tus Órdenes ───────────────────────");
        for (int i = 0; i < ordenes.size(); i++) {
            OrdenDeCompra o = ordenes.get(i);
            System.out.printf("    [%d] Orden #%s — Estado: %s%n",
                    i + 1, o.getId(), o.getEstado());
        }

        int indice = leerIndice(sc, ordenes.size(), "  Índice de orden a cancelar: ");
        if (indice < 0) return;

        OrdenDeCompra ordenACancelar = ordenes.get(indice);
        if (confirmar(sc, "¿Cancelar orden #" + ordenACancelar.getId() + "?")) {
            cliente.cancelarOrden(ordenACancelar);
            ordenService.cancelarOrden(ordenACancelar);
        } else {
            System.out.println("  Cancelación abortada.");
        }
    }

    // ── Flujo: presentar queja ───────────────────────────────

    private static void flujoPresentarQueja(Cliente cliente,
                                            GerenteDeRelaciones gerente, Scanner sc) {
        System.out.println("\n  ── Presentar Queja ───────────────────");
        System.out.print("  Título de la queja: ");
        String titulo = sc.nextLine().trim();
        System.out.print("  Descripción: ");
        String descripcion = sc.nextLine().trim();

        cliente.presentarQueja(titulo, descripcion, gerente);
        System.out.println("  ✔ Queja enviada al Gerente de Relaciones.");
    }

    // ── Helpers de UI ────────────────────────────────────────

    private static void imprimirMenu(String nombre) {
        System.out.println("\n  ╔═══════════════════════════════════╗");
        System.out.println("  ║   MENÚ CLIENTE — " + nombre);
        System.out.println("  ╠═══════════════════════════════════╣");
        System.out.println("  ║  1. Consultar catálogo            ║");
        System.out.println("  ║  2. Solicitar catálogo por correo ║");
        System.out.println("  ║  3. Ingresar orden de compra      ║");
        System.out.println("  ║  4. Cancelar una orden            ║");
        System.out.println("  ║  5. Presentar queja               ║");
        System.out.println("  ║  6. Salir                         ║");
        System.out.println("  ╚═══════════════════════════════════╝");
    }

    private static String leerEntrada(Scanner sc) {
        System.out.print("  Opción: ");
        String entrada = sc.nextLine().trim();
        lastEntrada = entrada;
        return entrada;
    }

    private static int leerIndice(Scanner sc, int max, String mensaje) {
        System.out.print(mensaje);
        try {
            int idx = Integer.parseInt(sc.nextLine().trim()) - 1;
            return (idx >= 0 && idx < max) ? idx : -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static boolean confirmar(Scanner sc, String pregunta) {
        System.out.print("  " + pregunta + " (s/n): ");
        return sc.nextLine().trim().equalsIgnoreCase("s");
    }
}
