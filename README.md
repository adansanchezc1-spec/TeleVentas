# TeleVentas - Sistema de Compras en Línea

## Descripción

TeleVentas es un sistema de consola desarrollado en Java que simula un e-commerce para compras a distancia. Permite a los clientes consultar catálogos, realizar pedidos, presentar quejas y cancelar órdenes. Los agentes de depósito gestionan el armado y despacho de pedidos, mientras que el gerente de relaciones maneja las quejas de los clientes.

El sistema integra con un inventario legado y soporta múltiples roles con autenticación básica.

## Características Principales

- **Clientes**: Consulta de catálogo, suscripción a envíos periódicos, ingreso de órdenes de compra con pago por tarjeta de crédito, presentación de quejas y cancelación de órdenes.
- **Agentes de Depósito**: Visualización de órdenes confirmadas, armado de pedidos (actualización automática de stock), asignación de empresas de transporte y despacho.
- **Gerente de Relaciones**: Visualización y respuesta a quejas de clientes.
- **Integración con Inventario**: Consulta y actualización de stock de productos.
- **Autenticación**: Verificación de credenciales para el gerente de relaciones.

## Arquitectura del Sistema

El proyecto sigue una arquitectura en capas:

- **Capa de Modelo (model)**: Contiene las clases de dominio que representan entidades del negocio.
- **Capa de Servicio (service)**: Maneja la lógica de negocio y la integración con sistemas externos.
- **Capa de Menú (menu)**: Gestiona la interacción con el usuario a través de menús de consola.
- **Capa Principal (App)**: Punto de entrada que coordina la inicialización y el flujo principal.

## Explicación de Clases

### Clases de Modelo

#### Usuario (Usuario.java)
Clase base abstracta para todos los usuarios del sistema.
- Atributos: nombre, correo, rol.
- Métodos: mostrarMenu() (delegado a las subclases).

#### Cliente (Cliente.java)
Representa a un cliente del sistema.
- Atributos: recibirCatalogoCada15Dias, ordenes, quejas.
- Métodos:
  - consultarCatalogo(): Muestra el catálogo de productos.
  - solicitarEnvioCatalogo(): Activa suscripción a catálogo periódico.
  - ingresarOrdenDeCompra(): Crea y registra una orden con pago.
  - cancelarOrden(): Cancela una orden existente.
  - presentarQueja(): Crea y envía una queja al gerente.

#### AgenteDeDeposito (AgenteDeDeposito.java)
Representa a un agente de depósito.
- Métodos:
  - consultarOrdenesConfirmadas(): Filtra órdenes en estado "comprado".
  - armarPedido(): Lista productos de la orden y reduce stock automáticamente.
  - actualizarStock(): Reduce stock de un producto específico.
  - asignarEmpresaTransporte(): Asigna transporte y cambia estado a "encamino".

#### GerenteDeRelaciones (GerenteDeRelaciones.java)
Representa al gerente de relaciones.
- Atributos: quejasRecibidas.
- Métodos:
  - recibirQueja(): Agrega una queja a la lista.
  - responderQueja(): Establece respuesta a una queja.

#### Producto (Producto.java)
Representa un producto en el catálogo.
- Atributos: id, descripcion, precio, stock.
- Métodos:
  - verificarDisponibilidad(): Verifica si stock > 0.
  - reducirStock(): Reduce stock en cantidad especificada.

#### OrdenDeCompra (OrdenDeCompra.java)
Representa una orden de compra.
- Estados: "nocomprado", "comprado", "encamino".
- Atributos: id, estado, listaProductos, metodoPago.
- Métodos:
  - agregarProducto(): Agrega producto a la orden.
  - consultarOrden(): Muestra detalles de la orden.
  - cambiarEstado(): Cambia estado de la orden.
  - calcularTotal(): Suma precios de productos.

#### Queja (Queja.java)
Representa una queja de cliente.
- Atributos: titulo, descripcion, respuesta.
- Métodos:
  - enviarAGerente(): Envía la queja al gerente.

#### MetodoPago (MetodoPago.java)
Representa un método de pago (solo tarjeta de crédito).
- Atributos: numeroTarjeta.
- Métodos: validar() (simula validación básica).

#### Catalogo (Catalogo.java)
Gestiona la colección de productos.
- Atributos: productos (lista).
- Métodos:
  - obtenerProducto(): Busca producto por ID.
  - listarProductos(): Imprime todos los productos.
  - agregarProducto(): Agrega producto al catálogo.

#### EmpresaTransporte (EmpresaTransporte.java)
Clase abstracta para empresas de transporte.
- Subclases: TransporteEstandar, TransporteRapido.
- Métodos: entregarPedido() (cambia estado de orden a "encamino").

#### InventarioViejo (InventarioViejo.java)
Simula integración con sistema de inventario legado.
- Atributos: productos (lista compartida con catálogo).
- Métodos: Consultar stock (a través de productos).

### Clases de Servicio

#### CatalogoService (CatalogoService.java)
Servicio para poblar el catálogo con datos de demo.
- Métodos: poblarCatalogoDemo().

#### OrdenService (OrdenService.java)
Gestiona el repositorio global de órdenes.
- Atributos: todasLasOrdenes.
- Métodos:
  - registrarOrden(): Agrega orden a la lista.
  - cancelarOrden(): Remueve orden de la lista.
  - getTodasLasOrdenes(): Retorna copia de la lista.

### Clases de Menú

#### MenuPrincipal (MenuPrincipal.java)
Maneja el inicio de sesión.
- Métodos:
  - iniciarSesion(): Solicita nombre, correo y rol; valida credenciales para gerente.
  - crearUsuario(): Crea instancia de usuario según rol.

#### MenuCliente (MenuCliente.java)
Menú interactivo para clientes.
- Opciones: Consultar catálogo, solicitar envío, ingresar orden, cancelar orden, presentar queja.
- Flujos: Permite ingresar IDs de productos directamente en el prompt de opción.

#### MenuAgenteDeposito (MenuAgenteDeposito.java)
Menú para agentes de depósito.
- Opciones: Ver órdenes, armar/despachar, actualizar stock, asignar transporte.

#### MenuGerenteRelaciones (MenuGerenteRelaciones.java)
Menú para gerente de relaciones.
- Opciones: Ver quejas, responder queja.

### Clase Principal

#### App (App.java)
Punto de entrada del sistema.
- Inicializa catálogo, inventario, gerente, órdenes, transportes.
- Bucle principal: Inicio de sesión, despacho de menú por rol, opción de continuar con otro usuario.

## Interacción del Usuario

1. **Inicio**: El usuario ejecuta la aplicación y ve el menú de bienvenida.
2. **Inicio de Sesión**: Ingresa nombre, correo y selecciona rol (1: Cliente, 2: Agente, 3: Gerente).
   - Para gerente, debe usar "Carlos Mendoza" y "gerente@televentas.com".
3. **Menú por Rol**:
   - **Cliente**:
     - Consultar catálogo: Muestra lista de productos.
     - Solicitar envío: Activa suscripción.
     - Ingresar orden: Muestra catálogo, permite ingresar IDs separados por coma (ej: P001,P003), solicita tarjeta (16 dígitos), registra orden si válida.
     - Cancelar orden: Lista órdenes del cliente, permite cancelar.
     - Presentar queja: Solicita título y descripción, envía al gerente.
   - **Agente de Depósito**:
     - Ver órdenes: Muestra órdenes confirmadas.
     - Armar y despachar: Selecciona orden, arma (reduce stock), selecciona transporte, despacha.
     - Actualizar stock: Reduce stock de producto específico.
     - Asignar transporte: Asigna transporte a orden.
   - **Gerente de Relaciones**:
     - Ver quejas: Lista quejas con respuestas si existen.
     - Responder queja: Selecciona queja, ingresa respuesta.
4. **Salida**: Después de usar un menú, opción de iniciar sesión como otro usuario o salir.

## Cómo Ejecutar

1. Asegúrate de tener Java instalado (JDK 8 o superior).
2. Compila el proyecto: `javac -d bin src/com/model/*.java src/com/model/menu/*.java src/com/model/model/*.java src/com/model/service/*.java`
3. Ejecuta: `java -cp bin com.model.App`
4. Interactúa a través de la consola.

## Notas Técnicas

- El sistema usa un Scanner compartido para entrada.
- Datos persistentes solo durante la ejecución (no hay base de datos).
- Validación de tarjeta: Simula aceptación si termina en dígitos pares.
- Stock se actualiza automáticamente al armar pedidos.
- Quejas se envían inmediatamente al gerente global.
- Órdenes pasan por estados: nocomprado -> comprado -> armado (implícito) -> encamino.
