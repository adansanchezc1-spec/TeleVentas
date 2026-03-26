package com.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.model.menu.MenuAgenteDeposito;
import com.model.menu.MenuCliente;
import com.model.menu.MenuGerenteRelaciones;
import com.model.menu.MenuPrincipal;
import com.model.model.AgenteDeDeposito;
import com.model.model.Catalogo;
import com.model.model.Cliente;
import com.model.model.EmpresaTransporte;
import com.model.model.GerenteDeRelaciones;
import com.model.model.InventarioViejo;
import com.model.model.TransporteEstandar;
import com.model.model.TransporteRapido;
import com.model.model.Usuario;
import com.model.service.CatalogoService;
import com.model.service.OrdenService;

public class App {


    
    public static void main(String[] args) {

        // ── 1. Scanner compartido ─────────────────────────────
        Scanner sc = new Scanner(System.in);

        // ── 2. Gerente global (receptor de quejas) ────────────
        GerenteDeRelaciones gerente = new GerenteDeRelaciones(
                "Carlos Mendoza", "gerente@televentas.com");

        // ── 3. Catálogo poblado con productos de demo ─────────
        Catalogo catalogo = new Catalogo();
        CatalogoService catalogoService = new CatalogoService();
        catalogoService.poblarCatalogoDemo(catalogo);

        // ── 4. InventarioViejo recibe los mismos productos ────
        //       (integración con sistema legado — R6)
        InventarioViejo inventarioViejo = new InventarioViejo(
                catalogo.crearListaProductos());

        // ── 5. Empresas de transporte disponibles ────────────
        List<EmpresaTransporte> empresas = new ArrayList<>();
        empresas.add(new TransporteRapido("DHL Express"));
        empresas.add(new TransporteEstandar("Servientrega"));

        // ── 6. Repositorio global de órdenes ─────────────────
        OrdenService ordenService = new OrdenService();

        // ── 7. Inicio de sesión ───────────────────────────────
        Usuario usuario = MenuPrincipal.iniciarSesion(sc);

        System.out.println("\n  Bienvenido/a, " + usuario.getNombre()
                + " [" + usuario.getRol() + "]");

        // ── 8. Redirigir al menú según el rol ─────────────────
        despacharMenuPorRol(usuario, catalogo, gerente,
                ordenService, empresas, sc);

        // ── 9. Cierre de sesión ───────────────────────────────
        System.out.println("\n  Sesión cerrada. ¡Hasta pronto!");
        sc.close();
    }

    // ── Despacho de menú por rol ──────────────────────────────

    private static void despacharMenuPorRol(Usuario usuario,
                                            Catalogo catalogo,
                                            GerenteDeRelaciones gerente,
                                            OrdenService ordenService,
                                            List<EmpresaTransporte> empresas,
                                            Scanner sc) {
        if (usuario instanceof Cliente) {
            MenuCliente.mostrar(
                    (Cliente) usuario,
                    catalogo,
                    gerente,
                    ordenService,
                    sc);

        } else if (usuario instanceof AgenteDeDeposito) {
            MenuAgenteDeposito.mostrar(
                    (AgenteDeDeposito) usuario,
                    ordenService.getTodasLasOrdenes(),
                    catalogo,
                    empresas,
                    sc);

        } else if (usuario instanceof GerenteDeRelaciones) {
            // Si el usuario que inició sesión ES el gerente,
            // sus quejas son las del objeto autenticado.
            MenuGerenteRelaciones.mostrar(
                    (GerenteDeRelaciones) usuario,
                    sc);
        } else {
            System.out.println("  ⚠ Rol no reconocido. Contacta al administrador.");
        }
    }
   
}
