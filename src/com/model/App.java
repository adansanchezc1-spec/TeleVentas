package com.model;

import com.model.menu.MenuAgenteDeposito;
import com.model.menu.MenuCliente;
import com.model.menu.MenuGerenteRelaciones;
import com.model.menu.MenuPrincipal;
import com.model.model.AgenteDeDeposito;
import com.model.model.Catalogo;
import com.model.model.Cliente;
import com.model.model.EmpresaTransporte;
import com.model.model.GerenteDeRelaciones;
import com.model.model.TransporteEstandar;
import com.model.model.TransporteRapido;
import com.model.model.Usuario;
import com.model.service.CatalogoService;
import com.model.service.OrdenService;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {


    
    public static void main(String[] args) {

        // ── 2. Gerente global (receptor de quejas) ────────────
        try ( // ── 1. Scanner compartido ─────────────────────────────
                Scanner sc = new Scanner(System.in)) {
            // ── 2. Gerente global (receptor de quejas) ────────────
            GerenteDeRelaciones gerente = new GerenteDeRelaciones(
                    "Carlos Mendoza", "gerente@televentas.com");
            
            // ── 3. Catálogo poblado con productos de demo ─────────
            Catalogo catalogo = new Catalogo();
            CatalogoService catalogoService = new CatalogoService();
            catalogoService.poblarCatalogoDemo(catalogo);
            // ── 4. InventarioViejo recibe los mismos productos ────
            //       (integración con sistema legado — R6)

            
            // ── 5. Empresas de transporte disponibles ────────────
            List<EmpresaTransporte> empresas = new ArrayList<>();
            empresas.add(new TransporteRapido("DHL Express"));
            empresas.add(new TransporteEstandar("Servientrega"));
            
            // ── 6. Repositorio global de órdenes ─────────────────
            OrdenService ordenService = new OrdenService();
            
            // ── 7. Inicio de sesión ───────────────────────────────
            boolean continuar = true;
            while (continuar) {
                Usuario usuario = MenuPrincipal.iniciarSesion(sc);
                
                System.out.println("\n  Bienvenido/a, " + usuario.getNombre()
                        + " [" + usuario.getRol() + "]");
                
                // ── 8. Redirigir al menú según el rol ─────────────────
                despacharMenuPorRol(usuario, catalogo, gerente,
                        ordenService, empresas, sc);
                
                System.out.print("\n¿Desea iniciar sesión como otro usuario? (s/n): ");
                String resp = sc.nextLine().trim().toLowerCase();
                if (!"s".equals(resp)) {
                    continuar = false;
                }
            }
            
            // ── 9. Cierre de sesión ───────────────────────────────
            System.out.println("\n  Sesión cerrada. ¡Hasta pronto!");
        }
    }

    // ── Despacho de menú por rol ──────────────────────────────

    private static void despacharMenuPorRol(Usuario usuario,
                                            Catalogo catalogo,
                                            GerenteDeRelaciones gerente,
                                            OrdenService ordenService,
                                            List<EmpresaTransporte> empresas,
                                            Scanner sc) {
        if (usuario instanceof Cliente cliente) {
            MenuCliente.mostrar(cliente,
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
            // Usar el gerente global para que las quejas estén disponibles
            MenuGerenteRelaciones.mostrar(
                    gerente,
                    sc);
        } else {
            System.out.println("   Rol no reconocido. Contacta al administrador.");
        }
    }
   
}
