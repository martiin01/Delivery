package delivery;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import grpc.StockServiceGrpc;
import grpc.StockServiceOuterClass.ProductosReply;
import grpc.StockServiceOuterClass.StockRequest;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;

public class BajoStockCliente {

    public static void main(String[] args) throws InterruptedException {
        String target = "localhost:9093"; // Server address
        int umbral = 20; // Example threshold

        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create()).build();
        System.out.println("Cliente conectado a " + target);

        try {
            StockServiceGrpc.StockServiceBlockingStub blockingStub = StockServiceGrpc.newBlockingStub(channel);

            StockRequest request = StockRequest.newBuilder().setUmbral(umbral).build();
            System.out.println("Solicitando productos con stock por debajo de " + umbral + "...");

            Iterator<ProductosReply> responseIterator;
            try {
                responseIterator = blockingStub.bajoStock(request);
                System.out.println("Productos con bajo stock:");
                while (responseIterator.hasNext()) {
                    ProductosReply reply = responseIterator.next();
                    System.out.println("- ID Producto: " + reply.getIdProducto());
                }
                System.out.println("Fin de la lista.");
            } catch (StatusRuntimeException e) {
                System.err.println("RPC fallido: " + e.getStatus());
                return;
            }

        } finally {
            // Shutdown channel
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
            System.out.println("Cliente desconectado.");
        }
    }
}