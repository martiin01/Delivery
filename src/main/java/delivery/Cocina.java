package delivery;

import java.util.LinkedList;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import grpc.StockServiceGrpc;
import grpc.StockServiceOuterClass;
import grpc.StockServiceGrpc.StockServiceBlockingStub;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import pcd.util.ColoresConsola;
import pcd.util.Traza;

/*
 * Práctica 4: Contenedores de pollo y pan
 * Práctica 6: Contenedor lechuga
 */
public class Cocina {
    // Se crean los contenedores para pan y pollo.
    // El de pan tiene capacidad 3 y el de pollo, 1.
    ContenedorCocina contenedorPan = new ContenedorCocina(3);
    ContenedorCocina contenedorPollo = new ContenedorCocina(1);

    private final BlockingQueue<String> contenedorLechuga = new LinkedBlockingQueue<>(2);


    Restaurante r;
    
    public Cocina(Restaurante _r) {
        r = _r;
        //Lanza los threads de los robots
        Thread robotPan = new Thread(new RobotPan(contenedorPan));
        robotPan.setDaemon(true);
        robotPan.start();
        Thread robotPollo = new Thread(new RobotPollo(contenedorPollo));
        robotPollo.setDaemon(true);
        robotPollo.start();

        Thread robotLechuga = new Thread(new RobotLechuga(contenedorLechuga));
        robotLechuga.setDaemon(true);
        robotLechuga.start();
    }
    
    public static void executeUnaryCall(List<Producto> listaProductos, StockServiceBlockingStub stockStub) {
		for (int i=0;i<listaProductos.size();i++){
			StockServiceOuterClass.VentaRequest ventaRequest = StockServiceOuterClass.VentaRequest.newBuilder().setIdProducto(listaProductos.get(i).getId()).setCantidad(listaProductos.get(i).getCantidad()).build();
			StockServiceOuterClass.RestoProductoReply respuesta = stockStub.registrarVenta(ventaRequest);
			Traza.traza(ColoresConsola.BLACK, 2, "Quedan "+respuesta.getResto()+" del producto "+listaProductos.get(i).getId());
		}
	}
    
    // Pedido que contiene algún Producto con ID "0" (hamburguesa de pollo)
    public void cocinar(Pedido p) {
        List<Producto> Lproductos=new LinkedList<Producto>();
        Lproductos=p.getProductos();
        String target ="localhost:9093";
        ManagedChannel channel = null; // Inicializa el canal fuera del bloque try
        try {
            channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create()).build();
            StockServiceBlockingStub stockStub = StockServiceGrpc.newBlockingStub(channel);
            executeUnaryCall(Lproductos, stockStub);

            boolean esHamburguesa = false;
            for (Producto prod : p.getProductos()) {
                if ("0".equals(prod.getId())) {
                    esHamburguesa = true;
                    break;
                }
            }

            if (esHamburguesa) {
                try {
                    // Al llamar a get() se bloquea internamente si no hay piezas disponibles.
                    contenedorPan.get();
                    contenedorPollo.get();

                    String lechuga = contenedorLechuga.take();
                    System.out.println("Usando pieza de " + lechuga + " para la hamburguesa.");

                    System.out.println("Preparando hamburguesa de pollo para el pedido: " + p.getId());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                Traza.traza(ColoresConsola.GREEN, 2, "Cocinando pedido normal: " + p.printConRetorno());
            }
            try {
                // Simula el tiempo de cocina.
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            if (channel != null) {
                channel.shutdownNow(); // Intenta un cierre inmediato
                try {
                    channel.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS); // Espera un poco para que termine
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Error al cerrar el canal gRPC.");
                }
            }
        }
    }
}