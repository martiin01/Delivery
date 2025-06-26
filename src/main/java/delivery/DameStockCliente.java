package delivery;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import grpc.StockServiceGrpc;
import grpc.StockServiceOuterClass.DameStockRequest;
import grpc.StockServiceOuterClass.VentaRequest;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;

public class DameStockCliente {

    public static void main(String[] args) throws InterruptedException {
        String target = "localhost:9093"; // Server address
        List<String> productIdsToQuery = Arrays.asList("0", "5", "10", "99", "nonexistent"); // Example product IDs

        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create()).build();
        System.out.println("Cliente conectado a " + target);

        StockServiceGrpc.StockServiceStub asyncStub = StockServiceGrpc.newStub(channel);
        CountDownLatch finishLatch = new CountDownLatch(1); // To wait for server response stream to finish

        StreamObserver<VentaRequest> responseObserver = new StreamObserver<VentaRequest>() {
            @Override
            public void onNext(VentaRequest response) {
                System.out.println("Stock recibido -> Producto: " + response.getIdProducto() + ", Cantidad: " + response.getCantidad());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error en 'dameStock': " + t.getMessage());
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("Servidor ha terminado de enviar stocks.");
                finishLatch.countDown();
            }
        };

        // Start the bidirectional call and get the request observer
        StreamObserver<DameStockRequest> requestObserver = asyncStub.dameStock(responseObserver);

        try {
            System.out.println("Enviando solicitudes de stock para: " + productIdsToQuery);
            for (String productId : productIdsToQuery) {
                DameStockRequest request = DameStockRequest.newBuilder().setIdProducto(productId).build();
                requestObserver.onNext(request);
                // Optional: Add a small delay between requests if needed
                // Thread.sleep(100);
            }
        } catch (RuntimeException e) {
            // Cancel RPC
            requestObserver.onError(e);
            throw e;
        }

        // Mark the end of requests
        requestObserver.onCompleted();
        System.out.println("Todas las solicitudes de stock enviadas.");

        // Wait for the server to finish sending responses
        if (!finishLatch.await(1, TimeUnit.MINUTES)) {
             System.err.println("'dameStock' did not finish within 1 minute.");
        }

        // Shutdown channel
        channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Cliente desconectado.");
    }
}