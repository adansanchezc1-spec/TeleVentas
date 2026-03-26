package com.model.service;

import java.util.List;

import com.model.model.Catalogo;
import com.model.model.Cliente;
import com.model.model.Producto;

public class NotificacionService {
    
    /**
     * Simula el envío del catálogo por correo al cliente (R1).
     * Solo actúa si el cliente tiene la suscripción activada.
     */
    public void enviarCatalogoPorCorreo(Cliente cliente, Catalogo catalogo) {
        if (!cliente.isRecibirCatalogoCada15Dias()) {
            System.out.println("  [Notificación] El cliente no está suscrito al catálogo por correo.");
            return;
        }
        System.out.println("  ╔══ Catálogo enviado a: " + cliente.getCorreo() + " ══");
        List<Producto> productos = catalogo.crearListaProductos();
        for (Producto p : productos) {
            System.out.println("  ║  " + p);
        }
        System.out.println("  ╚═══════════════════════════════════════");
    }
}
