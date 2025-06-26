// filepath: c:\Users\marti\eclipse-workspace\GrpcMydelivery\src\main\java\server\MyServer.java
package server;

import java.io.IOException;
import java.util.Map; // Added import
import java.util.concurrent.ConcurrentHashMap; // Added import

import delivery.Config;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import pcd.util.Ventana;
import service.MyService;

public class MyServer {

     public static void main(String[] args) throws IOException, InterruptedException {
            Ventana v = new Ventana ("Servidor", 30, 30);

            Map<String, Integer> stock = new ConcurrentHashMap<>();
            // Initialize stock for potential product IDs 
            // Adjust this if product IDs have a different format
            for (int i = 0; i < Config.maximoIdProducto; i++) {
                stock.put(String.valueOf(i), 100);
            }
            v.addText("Initial stock set to " + Config.numeroProductos + " for " + Config.maximoIdProducto + " products.");

            // Configurar el servidor en el puerto 9093 y registrar la implementación del servicio.
            Server server = ServerBuilder
                    .forPort(9093)
                    .addService(new MyService(v, stock)) // Pass stock map to MyService
                    .build();

            // Iniciar el servidor para comenzar a escuchar peticiones.
            server.start();
            v.addText("Servidor gRPC en puerto 9093...");

            // Bloquear el hilo principal para mantener el servidor en ejecución.
            server.awaitTermination();
        }
}