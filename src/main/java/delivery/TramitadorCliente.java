// filepath: src/main/java/delivery/TramitadorCliente.java
package delivery;

import java.util.List;
import java.util.concurrent.TimeUnit;

//Corrige los imports de los mensajes gRPC
import grpc.TramitadorOuterClass.PedidoReply;
import grpc.TramitadorOuterClass.PedidoRequest;
import grpc.TramitadorOuterClass.ProductoRequest;
import grpc.TramitadorGrpc; // Este import está bien
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import pcd.util.ColoresConsola;
import pcd.util.Traza;

public class TramitadorCliente {

    private final ManagedChannel channel;
    private final TramitadorGrpc.TramitadorBlockingStub blockingStub; // Usaremos el stub bloqueante para simplicidad

    public TramitadorCliente(String host, int port) {
        // Crear el canal de comunicación gRPC
        this.channel = Grpc.newChannelBuilder(host + ":" + port, InsecureChannelCredentials.create())
                .build();
        // Crear un stub bloqueante a partir del canal
        this.blockingStub = TramitadorGrpc.newBlockingStub(channel);
        Traza.traza(ColoresConsola.GREEN, 1, "Cliente Tramitador Conectado a " + host + ":" + port);
    }

    /** Cierra el canal de comunicación. */
    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        Traza.traza(ColoresConsola.RED, 1, "Cliente Tramitador Canal cerrado.");
    }

    /** Envía un pedido al servidor para ser tramitado. */
    public void enviarPedidoParaTramitar(Pedido pedidoDominio) {
        Traza.traza(ColoresConsola.YELLOW, 1, "Cliente Tramitador Enviando pedido: " + pedidoDominio.getId());

        // 1. Convertir Pedido de dominio a PedidoRequest gRPC
        PedidoRequest.Builder requestBuilder = PedidoRequest.newBuilder()
                .setIdPedido(pedidoDominio.getId())
                .setDireccion(pedidoDominio.getDireccion())
                .setRestaurante(pedidoDominio.getRestaurante()); 

        // Convertir la lista de productos
        if (pedidoDominio.getProductos() != null) {
            for (Producto productoDominio : pedidoDominio.getProductos()) {
                ProductoRequest productoGrpc = ProductoRequest.newBuilder()
                        .setId(productoDominio.getId())
                        .setPrecio(productoDominio.getPrecio())
                        .build();
                requestBuilder.addProductos(productoGrpc); // Añadir producto al request
            }
        }

        PedidoRequest request = requestBuilder.build();

        try {
            // 2. Llamar al método remoto 'tramitar'
            PedidoReply response = blockingStub.tramitar(request);
            Traza.traza(ColoresConsola.GREEN, 1, "[Cliente Tramitador] Respuesta recibida: " + response.getResultado());
        } catch (StatusRuntimeException e) {
            Traza.traza(ColoresConsola.RED, 1, "[Cliente Tramitador] RPC fallido para pedido " + pedidoDominio.getId() + ": " + e.getStatus());
            // Puedes decidir si continuar con el siguiente pedido o detenerte
        } catch (Exception e) {
            Traza.traza(ColoresConsola.RED, 1, "[Cliente Tramitador] Error inesperado para pedido " + pedidoDominio.getId() + ": " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Configuración: Dirección y puerto del servidor (Máquina A)
        String hostServidor = "localhost"; 
        int puertoServidor = Config.PUERTO_TRAMITADOR_GRPC; // Puerto definido en Config.java
        String archivoPedidos = "fichero/pedidos7.bin"; // Archivo de donde leer los pedidos

        TramitadorCliente cliente = null;
        try {
            // Crear el cliente
            cliente = new TramitadorCliente(hostServidor, puertoServidor);

            // Leer los pedidos del fichero
            List<Pedido> pedidos = Pedido.pedidosDesdeFichero(archivoPedidos);
            Traza.traza(ColoresConsola.CYAN, 1, "[Cliente Tramitador] Leídos " + pedidos.size() + " pedidos de " + archivoPedidos);

            // Enviar cada pedido al servidor
            for (Pedido p : pedidos) {
                cliente.enviarPedidoParaTramitar(p);
            }

        } catch (Exception e) {
            Traza.traza(ColoresConsola.RED_BOLD, 0, "[Cliente Tramitador] Error general: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar el canal al finalizar
            if (cliente != null) {
                try {
                    cliente.shutdown();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    Traza.traza(ColoresConsola.RED, 1, "[Cliente Tramitador] Interrupción al cerrar el canal.");
                }
            }
        }
        Traza.traza(ColoresConsola.CYAN, 0, "[Cliente Tramitador] Proceso finalizado.");
    }
}