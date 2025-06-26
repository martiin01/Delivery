# MyDelivery - Sistema de Gestión de Pedidos con gRPC

Este proyecto es una simulación de un sistema de gestión de pedidos para una cadena de restaurantes, utilizando gRPC para la comunicación entre los diferentes componentes del sistema. La aplicación gestiona el stock de productos, la tramitación de pedidos, la asignación de moteros y la interacción con los clientes.

## Estructura del Proyecto

El proyecto está organizado en los siguientes paquetes:

- **`bank`**: Contiene las clases relacionadas con la gestión de cuentas bancarias.
- **`delivery`**: Contiene el núcleo de la lógica de negocio de la aplicación, incluyendo la gestión de pedidos, productos, restaurantes, moteros y clientes.
- **`grpc`**: Contiene las clases generadas por gRPC a partir de los ficheros `.proto`, que definen los servicios y mensajes para la comunicación.
- **`pcd.util`**: Proporciona utilidades para la visualización en consola, como colores y trazas.
- **`server`**: Contiene la clase que inicia el servidor gRPC.
- **`service`**: Contiene la implementación de los servicios gRPC definidos en los ficheros `.proto`.

## Tecnologías Utilizadas

- **Java**: Lenguaje de programación principal del proyecto.
- **Maven**: Herramienta para la gestión de dependencias y la construcción del proyecto.
- **gRPC**: Framework de RPC (Remote Procedure Call) de alto rendimiento para la comunicación entre servicios.
- **Protocol Buffers**: Mecanismo de serialización de datos estructurados utilizado por gRPC.
- **RxJava**: Biblioteca para la programación reactiva.

## Cómo Ejecutar el Proyecto

1.  **Clonar el repositorio:**
    ```bash
    git clone <URL-del-repositorio>
    ```
2.  **Construir el proyecto:**
    ```bash
    mvn clean install
    ```
3.  **Ejecutar el servidor:**
    ```bash
    java -cp target/classes server.MyServer
    ```
4.  **Ejecutar el cliente:**
    ```bash
    java -cp target/classes delivery.MyDelivery
    ```

## Servicios gRPC

El proyecto define dos servicios gRPC principales:

### Servicio `StockService`

Este servicio se encarga de gestionar el stock de productos de los restaurantes.

- **`venta(VentaRequest)`**: Realiza una venta de un producto, decrementando el stock.
- **`dameStock(DameStockRequest)`**: Devuelve el stock actual de un producto.
- **`dameProductos(StockRequest)`**: Devuelve la lista de todos los productos.
- **`necesitaRecargar(StockRequest)`**: Comprueba si es necesario recargar el stock de algún producto.
- **`recargar(StockRequest)`**: Recarga el stock de los productos.

### Servicio `Tramitador`

Este servicio se encarga de tramitar los pedidos de los clientes.

- **`tramitarPedido(PedidoRequest)`**: Tramita un nuevo pedido.
- **`damePedido(PedidoRequest)`**: Devuelve la información de un pedido.

## Clases Principales

- **`MyDelivery`**: Clase principal que simula la interacción de los clientes con el sistema, generando pedidos y consultando el stock.
- **`MyServer`**: Clase que inicia el servidor gRPC y registra los servicios.
- **`MyService`**: Implementación del servicio `StockService`.
- **`TramitadorServiceImpl`**: Implementación del servicio `Tramitador`.
- **`Cocina`**: Simula la preparación de los pedidos en la cocina de un restaurante.
- **`ControlMoteros`**: Gestiona la asignación de moteros a los pedidos.
- **`Pedido`**: Representa un pedido realizado por un cliente.
- **`Producto`**: Representa un producto del restaurante.

## Arquitectura del Directorio `delivery`

El paquete `delivery` es el corazón de la aplicación y contiene toda la lógica de negocio relacionada con la gestión de pedidos. A continuación se detallan las clases más relevantes:

- **`MyDelivery`**: Es la clase principal que orquesta la simulación. Inicia los restaurantes, los moteros, y los clientes que realizan pedidos.
- **`Cliente`**: Simula el comportamiento de un cliente, realizando pedidos de forma periódica.
- **`TramitadorCliente`**: Se encarga de la comunicación con el servicio gRPC `Tramitador` para enviar las solicitudes de nuevos pedidos.
- **`DameStockCliente` y `BajoStockCliente`**: Son clientes del servicio gRPC `StockService`. `DameStockCliente` consulta el stock de forma síncrona, mientras que `BajoStockCliente` utiliza un stream para recibir notificaciones asíncronas cuando el stock de un producto es bajo.
- **`Restaurante`**: Representa un restaurante individual con su propia cocina y control de moteros.
- **`Cocina`**: Simula la cocina de un restaurante. Utiliza un `ExecutorService` para procesar los pedidos de forma concurrente.
- **`ControlMoteros`**: Gestiona la flota de moteros de un restaurante. Utiliza monitores (`synchronized`, `wait`, `notify`) para coordinar la asignación de pedidos a los moteros que se encuentran en espera.
- **`Motero`**: Representa a un repartidor que recoge los pedidos de la cocina y los entrega.
- **`CadenaRestaurantes`**: Gestiona la apertura y cierre de los restaurantes, utilizando un semáforo para limitar el número de restaurantes que pueden operar simultáneamente.
- **`MoterosEsperaCyclicBarrier`**: Utiliza una `CyclicBarrier` para sincronizar a un grupo de moteros, haciendo que todos esperen en un punto común antes de empezar su jornada.
- **`PedidosMayoresForkJoin`**: Utiliza el framework Fork/Join para buscar de forma paralela en una colección de pedidos aquellos que superan un importe determinado.
- **`PrecioMasAltoCallable`**: Es una tarea `Callable` que se puede ejecutar en un `ExecutorService` para encontrar el producto más caro de un pedido.

## Mecanismos de Concurrencia

El proyecto hace un uso extensivo de diferentes mecanismos de concurrencia de Java para gestionar de forma eficiente las operaciones del sistema.

### Monitores (`synchronized`, `wait`, `notify`)

Los monitores se utilizan para garantizar la exclusión mutua y la coordinación entre hilos. Un claro ejemplo es la clase `ControlMoteros`:

- El método `asignarPedido(Pedido p)` es `synchronized` para asegurar que solo un hilo (la cocina) pueda añadir un pedido a la vez.
- Si no hay moteros disponibles, el hilo de la cocina invoca a `wait()`, quedando en espera y liberando el monitor.
- Cuando un motero termina un reparto y queda libre, invoca a `notify()` sobre el mismo monitor para despertar a un posible hilo de cocina que esté esperando.

### Semáforos (`Semaphore`)

Los semáforos se utilizan para controlar el acceso a un número limitado de recursos. En `CadenaRestaurantes`, un `Semaphore` limita el número de restaurantes que pueden estar abiertos simultáneamente, simulando una licencia de apertura limitada.

### Barreras (`CyclicBarrier`)

Las barreras de sincronización se utilizan para hacer que un grupo de hilos se esperen los unos a los otros en un punto determinado antes de continuar. La clase `MoterosEsperaCyclicBarrier` utiliza una `CyclicBarrier` para que todos los moteros estén listos en un punto de encuentro antes de empezar a repartir pedidos.

### Patrón Observador (con RxJava y gRPC Streams)

El patrón observador se implementa para notificar a los clientes sobre cambios en el estado del stock. La clase `BajoStockCliente` se suscribe a un *stream* de notificaciones del servidor gRPC. Cuando el `MyService` detecta que el stock de un producto ha bajado de un umbral, emite un evento por el *stream* y todos los clientes suscritos reciben la notificación en tiempo real.

### Framework Fork/Join

Para procesar grandes volúmenes de datos de forma paralela, se utiliza el framework Fork/Join. La clase `PedidosMayoresForkJoin` es una `RecursiveTask` que divide la tarea de buscar pedidos grandes en subtareas más pequeñas que se pueden ejecutar en paralelo en un `ForkJoinPool`, mejorando significativamente el rendimiento.

### Executors y Callables

El `ExecutorService` se utiliza para gestionar un pool de hilos y desacoplar la creación de tareas de su ejecución. La `Cocina` utiliza un `ExecutorService` para procesar cada pedido en un hilo separado. Además, se usan `Callable` y `Future` para ejecutar tareas que devuelven un resultado, como en `PrecioMasAltoCallable`, que calcula el producto más caro de un pedido de forma asíncrona.

## Uso de gRPC

gRPC es la tecnología central para la comunicación entre los distintos componentes distribuidos del sistema.

### 1. Definición de Servicios (`.proto`)

La estructura de los servicios y los mensajes se define en archivos `.proto`.

- **`Stock.proto`**: Define el `StockService` con los métodos para gestionar el inventario (`venta`, `dameStock`, `dameProductos`, etc.) y los mensajes correspondientes (`VentaRequest`, `DameStockRequest`, `ProductosReply`, etc.).
- **`sesion10.proto`**: Define el servicio `Tramitador` con el método `tramitarPedido` para gestionar la creación de nuevos pedidos.

### 2. Generación de Código

El plugin `protoc-jar-maven-plugin` de Maven se encarga de procesar estos archivos `.proto` y generar automáticamente las clases de Java necesarias para la comunicación gRPC, incluyendo:
- Los **mensajes** de solicitud y respuesta.
- Los **stubs** del cliente (bloqueantes y no bloqueantes) que permiten invocar los RPCs desde el cliente.
- La **clase base** del servicio que se debe extender en el servidor para implementar la lógica del servicio.

### 3. Implementación del Servidor

- **`MyServer`**: Esta clase crea e inicia el servidor gRPC en un puerto específico.
- **`MyService`**: Implementa la lógica del `StockService`. Extiende de `StockServiceGrpc.StockServiceImplBase` y sobreescribe los métodos definidos en el `.proto`. Aquí es donde se gestiona el acceso al stock y se envían las notificaciones a los clientes.
- **`TramitadorServiceImpl`**: Implementa la lógica del servicio `Tramitador`.

### 4. Comunicación Cliente-Servidor

Los clientes utilizan los *stubs* generados por gRPC para comunicarse con el servidor de forma transparente.

- **Llamadas Unary**: Es el tipo de comunicación más simple (una solicitud, una respuesta). Clases como `DameStockCliente` y `TramitadorCliente` crean un *stub* bloqueante (`blockingStub`) y llaman a los métodos del servicio como si fueran métodos locales.
- **Server Streaming**: Para notificaciones en tiempo real, se utiliza un *stream* del servidor al cliente. En `BajoStockCliente`, se crea un *stub* no bloqueante (`asyncStub`) y se implementa un `StreamObserver` para manejar los mensajes que llegan desde el servidor de forma asíncrona a través del método `onNext`.
