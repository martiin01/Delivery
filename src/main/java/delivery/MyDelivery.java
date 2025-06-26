package delivery;

import java.util.Date;




import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.io.IOException; // Necesario para el servidor gRPC
import io.grpc.Server;      // Necesario para el servidor gRPC
import io.grpc.ServerBuilder;// Necesario para el servidor gRPC
import service.TramitadorServiceImpl; // Importa tu nueva implementación
import server.MyServer; // Si quieres iniciar también el servidor de Stock
import service.MyService; // Si quieres iniciar también el servidor de Stock
import java.util.Map; // Para el stock si inicias MyService
import java.util.concurrent.ConcurrentHashMap; // Para el stock si inicias MyService
import pcd.util.Ventana; // Para la ventana si inicias MyService


import pcd.util.ColoresConsola;
import pcd.util.Traza;

public class MyDelivery {
	// Variable para mantener el servidor gRPC corriendo
    private static Server tramitadorServer;
    // Podrías necesitar una variable similar si inicias el servidor de Stock aquí también
    // private static Server stockServer;
    
    public MyDelivery () {
        Traza.setNivel(Config.modoTraza);

        CadenaRestaurantes cadenaRestaurantes = new CadenaRestaurantes(Config.numeroRestaurantes);
        cadenaRestaurantes.crearRestaurantes();

        List<Pedido> lp = Pedido.pedidosDesdeFichero("fichero\\pedidos7.bin");
        LinkedList<Restaurante> listaRestaurantes = cadenaRestaurantes.getRestaurantes();
        
        long initialTime = new Date().getTime();
        /*
         * Se tiene puesto en Config el modoLanzador a 0 para ejecución inicial.
         * Cambiar para probar las otras ejecuciones.
         */

        switch (Config.modoLanzador) {
            case 0:
                // Lanzamiento “antiguo” (hilos manuales)
                List<Thread> listaThreadPedidos = new LinkedList<>();
                for (Pedido p: lp) {
                    Restaurante r = listaRestaurantes.get(p.getRestaurante());
                    Thread threadPedido = new Thread(new tramitarPedido(p, r));
                    threadPedido.start();
                    listaThreadPedidos.add(threadPedido);
                }
                // Join
                for (Thread t : listaThreadPedidos) {
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 1:
                // Lanzamiento con Executor
                int numHilos = Runtime.getRuntime().availableProcessors();
                ExecutorService pool = Executors.newFixedThreadPool(numHilos);
                
                for (Pedido p: lp) {
                    Restaurante r = listaRestaurantes.get(p.getRestaurante());
                    pool.submit(new tramitarPedido(p, r));
                }
                
                pool.shutdown(); // Se cierra el envío de nuevas tareas
                // Esperar a que finalicen todas las tareas del pool (opcional)
                try {
                    if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                        pool.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    pool.shutdownNow();
                }
                break;
            case 2:
                // Lanzamiento con Stream
                Traza.traza(ColoresConsola.PURPLE, 2, "Lanzamiento con Stream ");
                lp.stream().parallel().forEach(p -> {
                    Restaurante r = listaRestaurantes.get(p.getRestaurante());
                    tramitarPedido tp = new tramitarPedido(p, r);
                    Traza.traza(ColoresConsola.PURPLE, 2, "Lanzamiento con Stream " + p.getId() + " del restaurante  " + r.getNombre());
                    tp.run();
                });
                break;
            case 3:
            	String ruta = "ficheros\\pedidos7.bin";
                Observable<Pedido> observablePedidos = Pedido.pedidosDesdeFicheroObservable("fichero\\pedidos7.bin");
                ExecutorService executorService = Executors.newFixedThreadPool(20);
                Scheduler scheduler = Schedulers.from(executorService);

                observablePedidos
                    .flatMap(p -> Observable.just(p).subscribeOn(scheduler))
                    .subscribe(
                        p -> listaRestaurantes.get(p.getRestaurante()).tramitarPedido(p),
                        error -> System.err.println("Error procesando pedido: " + error.getMessage()),
                        () -> System.out.println("Todos los pedidos han sido procesados.")
                    );

                executorService.shutdown();
				try {
					executorService.awaitTermination(100, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	break;
            	// NUEVO CASO PARA EL SERVIDOR GRPC
            case 4:
                Traza.traza(ColoresConsola.GREEN_BOLD, 1, "Modo Servidor gRPC activado.");
                try {
                    // Iniciar el servidor Tramitador
                    tramitadorServer = ServerBuilder.forPort(Config.PUERTO_TRAMITADOR_GRPC)
                            .addService(new TramitadorServiceImpl(cadenaRestaurantes)) // Pasar la cadena de restaurantes
                            .build()
                            .start();
                    Traza.traza(ColoresConsola.GREEN_BOLD, 1, "Servidor Tramitador gRPC iniciado en el puerto " + Config.PUERTO_TRAMITADOR_GRPC);
                    
                    // Mantener el servidor principal corriendo hasta que se interrumpa
                    System.out.println("Presiona Ctrl+C para detener los servidores.");
                    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                        System.err.println("Apagar servidor gRPC");
                         
                        try {
                            if (tramitadorServer != null) {
                            	Traza.traza(ColoresConsola.YELLOW_BOLD, 1, "--- Auditoría Final ---");
                                listaRestaurantes.forEach(r->
                                    Traza.traza(ColoresConsola.CYAN, 2, "Auditoría Restaurante " + r.getNombre() + " " + r.getBalance()));
                                System.out.println("\nAuditoria Cadena Final: " + cadenaRestaurantes.getBank().audit(0, Config.numeroRestaurantes));
                                Traza.traza(ColoresConsola.YELLOW_BOLD, 1, "-----------------------");
                                
                            	tramitadorServer.shutdown().awaitTermination(30, TimeUnit.SECONDS);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace(System.err);
                        }
                        System.err.println("Servidor apagado");
                        /**
                         *  No se muestran las auditorías. No ha sido posible mostrarlas ya que el servidor se cierra antes y  
                         *  se han terminado obviando mostrarlas. Se comprueba que se muestran cambiando la forma de tramitar los
                         *  pedidos con las otras opciones de Config.java.
                         *  Para comprobar que la sesión 10 se hace correctamente se ha de mirar las ventanas de moteros
                         *  y la termminal de MyDelivery.
                         *  
                         */
                        
                    }));

                    // Bloquear el hilo principal para mantener el servidor activo
                    tramitadorServer.awaitTermination();
                } catch (IOException e) {
                    Traza.traza(ColoresConsola.RED_BOLD, 1, "Error al iniciar el servidor Tramitador gRPC: " + e.getMessage());
                    e.printStackTrace();
                    return; // Salir si hay error
                } catch (InterruptedException e) {
                    Traza.traza(ColoresConsola.RED_BOLD, 1, "Servidor Tramitador gRPC interrumpido.");
                    e.printStackTrace();
                }
                // El código después del switch no se ejecutará en modo servidor hasta que se detenga
                break;
            default:
                System.err.println("Valor de Config.modoLanzador no reconocido.");
                break;
        }

        // Auditorías
        listaRestaurantes.forEach(r->
            Traza.traza(ColoresConsola.CYAN, 2, "Auditoría Restaurante " + r.getNombre() + " " + r.getBalance()));

        System.out.println("\nAuditoria Cadena: " + cadenaRestaurantes.getBank().audit(0, Config.numeroRestaurantes));

        // Comprobaciones

        System.out.println("Comprobamos si hay pedidos a la dirección Avenida de la Universidad ->");

        if (lp.stream().anyMatch(d -> d.getDireccion().equals("Avenida de la Universidad")))
            Traza.traza(ColoresConsola.BLUE_UNDERLINED, 2, " Encontrado ");
        else 
        Traza.traza(ColoresConsola.BLUE_UNDERLINED, 2, "No Encontrado ");
        
        System.out.println("Comprobamos si hay pedidos a la dirección Berna, 11 ->");

        if (lp.stream().anyMatch(d -> d.getDireccion().equals("Berna, 11")))
            Traza.traza(ColoresConsola.BLUE_UNDERLINED, 2, "Encontrado ");
        else 
            Traza.traza(ColoresConsola.BLUE_UNDERLINED, 2, "No Encontrado ");
        
        // Lanzamos la tarea Callable para obtener el precio máximo entre los pedidos
        ExecutorService executor2 = Executors.newSingleThreadExecutor();
        PrecioMasAltoCallable pedidoMasCaro = new PrecioMasAltoCallable(lp);
        try {
            Future<Integer> future = executor2.submit(pedidoMasCaro);
            int precioMasCaro = future.get();
            Traza.traza(ColoresConsola.GREEN, 2, "El precio máximo de un pedido es: " + precioMasCaro);
            //System.out.println("El precio máximo de un pedido es: " + precioMasCaro);
        } catch (Exception e) {
            e.printStackTrace();
        }
        executor2.shutdown();

        ForkJoinPool pedidoMayorDe = new ForkJoinPool();
        try {
            PedidosMayoresForkJoin tareaPedidos = new PedidosMayoresForkJoin(lp, 0, lp.size());
            List<String> pedidosPorEncimaDe12 = pedidoMayorDe.invoke(tareaPedidos);
            Traza.traza(ColoresConsola.YELLOW_BACKGROUND, 2,"Pedidos por encima de 12: " + pedidosPorEncimaDe12);
        } finally {
            pedidoMayorDe.shutdown();
        }
        
       	// Filtrar los pedidos con precio menor que 7€
        LinkedList<Pedido> pedidosMenos7 = new LinkedList<>();
        pedidosMenos7.addAll(lp);
        List<String> pedidosFiltrados = pedidosMenos7.stream()
                .parallel()
                .filter(p -> p.getPrecioPedido() < 7)
                .map(p -> p.getId() + ":  " + p.getPrecioPedido()) 
                        .toList();
        Traza.traza(ColoresConsola.YELLOW, 2,"Pedidos con precio menor de 7: " + pedidosFiltrados);


        Optional<Double> mayorPrecio = lp.stream().
                parallel()
                .map(Pedido :: getPrecioPedido)
                .max((a, b) -> Double.compare(a, b));

        System.out.println("El precio más alto es: " + mayorPrecio.get());
        
        Observable<Pedido> pedidosObservable = Observable.fromIterable(lp);
        
        pedidosObservable
        .subscribeOn(Schedulers.computation())
        .map(Pedido::getPrecioPedido)
        .reduce((total, precio) -> total + precio)
        .subscribe(
            suma -> Traza.traza(ColoresConsola.GREEN, 2, "La suma de todos los pedidos es: " + suma)
        );

    // Segundo observador: Imprime los pedidos con un precio mayor que 12
        pedidosObservable
        .subscribeOn(Schedulers.computation())
        .filter(p -> p.getPrecioPedido() > 12)
        .map(p -> "Pedido ID: " + p.getId() + ", Precio: " + p.getPrecioPedido())
        .toList()
        .subscribe(
            pedidos -> Traza.traza(ColoresConsola.YELLOW, 2, "Pedidos con precio mayor de 12: " + pedidos)
           
        		);

        
        System.out.println("Tiempo total invertido en la tramitación: " + (new Date().getTime() - initialTime));

        System.exit(0);
    }

    public static void main(String[] args) {
        new MyDelivery();
    }
}