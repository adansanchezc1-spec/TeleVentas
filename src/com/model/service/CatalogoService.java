package com.model.service;

import com.model.model.Catalogo;
import com.model.model.OrdenDeCompra;
import com.model.model.Producto;
/**
 * Servicio de catálogo: poblado de datos y lógica de consulta.
 */
public class CatalogoService {

    /**
     * Pobla el catálogo con 5 productos de demostración.
     * En producción, esto vendría de una fuente de datos real.
     */
    public void poblarCatalogoDemo(Catalogo catalogo) {
        catalogo.agregarProducto(new Producto("P001", "Televisor 55 pulgadas 4K",  1_800_000f, 10));
        catalogo.agregarProducto(new Producto("P002", "Nevera No-Frost 400L",       2_500_000f,  5));
        catalogo.agregarProducto(new Producto("P003", "Lavadora 18kg carga frontal",1_200_000f,  8));
        catalogo.agregarProducto(new Producto("P004", "Laptop Core i7 16GB RAM",    3_400_000f,  6));
        catalogo.agregarProducto(new Producto("P005", "Auriculares Bluetooth ANC",    350_000f, 20));
        System.out.println("  [CatalogoService] 5 productos cargados en el catálogo.");
    }
}
