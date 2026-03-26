package com.model.menu;
import com.model.model.AgenteDeDeposito;
import com.model.model.Catalogo;
import com.model.model.EmpresaTransporte;
import com.model.model.OrdenDeCompra;
import com.model.model.Producto;
import java.util.List;
import java.util.Scanner;

public class MenuAgenteDeposito {
      private static final int OPCION_VER_ORDENES        = 1;
    private static final int OPCION_ARMAR_DESPACHAR    = 2;
    private static final int OPCION_ACTUALIZAR_STOCK   = 3;
    private static final int OPCION_ASIGNAR_TRANSPORTE = 4;
    private static final int OPCION_SALIR              = 5;

    public static void mostrar(AgenteDeDeposito agente,
                               List<OrdenDeCompra> todasLasOrdenes,
                               Catalogo catalogo,
                               List<EmpresaTransporte> empresas,
                               Scanner sc) {
        int opcion;
        do {
            imprimirMenu(agente.getNombre());
            opcion = leerOpcion(sc, OPCION_SALIR);

            switch (opcion) {
                case OPCION_VER_ORDENES -> flujoVerOrdenes(agente, todasLasOrdenes);
                case OPCION_ARMAR_DESPACHAR -> flujoArmarDespachar(agente, todasLasOrdenes, empresas, sc);
                case OPCION_ACTUALIZAR_STOCK -> flujoActualizarStock(agente, catalogo, sc);
                case OPCION_ASIGNAR_TRANSPORTE -> flujoAsignarTransporte(agente, todasLasOrdenes, empresas, sc);
                case OPCION_SALIR -> {
                }
                default -> System.out.println("  ⚠ Opción no válida.");
            }
        } while (opcion != OPCION_SALIR);
    }

    // ── Flujo: ver órdenes confirmadas ───────────────────────

    private static void flujoVerOrdenes(AgenteDeDeposito agente,
                                        List<OrdenDeCompra> todas) {
        List<OrdenDeCompra> confirmadas = agente.consultarOrdenesConfirmadas(todas);
        if (confirmadas.isEmpty()) {
            System.out.println("  No hay órdenes confirmadas en este momento.");
            return;
        }
        System.out.println("\n  ── Órdenes Confirmadas ───────────────");
        for (OrdenDeCompra o : confirmadas) {
            o.consultarOrden();
        }
    }

    // ── Flujo: armar y despachar pedido ──────────────────────

    private static void flujoArmarDespachar(AgenteDeDeposito agente,
                                            List<OrdenDeCompra> todas,
                                            List<EmpresaTransporte> empresas,
                                            Scanner sc) {
        List<OrdenDeCompra> confirmadas = agente.consultarOrdenesConfirmadas(todas);
        if (confirmadas.isEmpty()) {
            System.out.println("  No hay órdenes listas para despachar.");
            return;
        }

        OrdenDeCompra orden = seleccionarOrden(confirmadas, sc);
        if (orden == null) return;

        agente.armarPedido(orden);

        EmpresaTransporte empresa = seleccionarEmpresa(empresas, sc);
        if (empresa == null) return;

        agente.asignarEmpresaTransporte(orden, empresa);
    }

    // ── Flujo: actualizar stock ──────────────────────────────

    private static void flujoActualizarStock(AgenteDeDeposito agente,
                                             Catalogo catalogo, Scanner sc) {
        System.out.print("  ID del producto a actualizar: ");
        String id = sc.nextLine().trim();
        Producto producto = catalogo.obtenerProducto(id);

        if (producto == null) {
            System.out.println("  ⚠ Producto no encontrado: " + id);
            return;
        }

        System.out.print("  Cantidad a reducir: ");
        try {
            int cant = Integer.parseInt(sc.nextLine().trim());
            agente.actualizarStock(producto, cant);
        } catch (NumberFormatException e) {
            System.out.println("  ⚠ Cantidad inválida.");
        } catch (IllegalStateException e) {
            System.out.println("  ⚠ " + e.getMessage());
        }
    }

    // ── Flujo: asignar empresa de transporte ─────────────────

    private static void flujoAsignarTransporte(AgenteDeDeposito agente,
                                               List<OrdenDeCompra> todas,
                                               List<EmpresaTransporte> empresas,
                                               Scanner sc) {
        List<OrdenDeCompra> confirmadas = agente.consultarOrdenesConfirmadas(todas);
        if (confirmadas.isEmpty()) {
            System.out.println("  No hay órdenes disponibles para asignar transporte.");
            return;
        }

        OrdenDeCompra orden   = seleccionarOrden(confirmadas, sc);
        if (orden == null) return;

        EmpresaTransporte empresa = seleccionarEmpresa(empresas, sc);
        if (empresa == null) return;

        agente.asignarEmpresaTransporte(orden, empresa);
    }

    // ── Helpers de selección ─────────────────────────────────

    private static OrdenDeCompra seleccionarOrden(List<OrdenDeCompra> lista, Scanner sc) {
        System.out.println("\n  Órdenes disponibles:");
        for (int i = 0; i < lista.size(); i++) {
            System.out.printf("    [%d] Orden #%s%n", i + 1, lista.get(i).getId());
        }
        System.out.print("  Selecciona el número de orden: ");
        try {
            int idx = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (idx >= 0 && idx < lista.size()) return lista.get(idx);
        } catch (NumberFormatException ignored) {}
        System.out.println("  ⚠ Selección inválida.");
        return null;
    }

    private static EmpresaTransporte seleccionarEmpresa(List<EmpresaTransporte> empresas,
                                                        Scanner sc) {
        System.out.println("\n  Empresas de transporte disponibles:");
        for (int i = 0; i < empresas.size(); i++) {
            System.out.printf("    [%d] %s%n", i + 1, empresas.get(i).getNombre());
        }
        System.out.print("  Selecciona empresa: ");
        try {
            int idx = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (idx >= 0 && idx < empresas.size()) return empresas.get(idx);
        } catch (NumberFormatException ignored) {}
        System.out.println("  ⚠ Selección inválida.");
        return null;
    }

    // ── Helper UI ────────────────────────────────────────────

    private static void imprimirMenu(String nombre) {
        System.out.println("\n  ╔══════════════════════════════════════╗");
        System.out.println("  ║  MENÚ AGENTE DE DEPÓSITO — " + nombre);
        System.out.println("  ╠══════════════════════════════════════╣");
        System.out.println("  ║  1. Ver órdenes confirmadas          ║");
        System.out.println("  ║  2. Armar y despachar pedido         ║");
        System.out.println("  ║  3. Actualizar stock de producto      ║");
        System.out.println("  ║  4. Asignar empresa de transporte     ║");
        System.out.println("  ║  5. Salir                             ║");
        System.out.println("  ╚══════════════════════════════════════╝");
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
}

