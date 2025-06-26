// filepath: c:\Users\marti\eclipse-workspace\GrpcMydelivery\src\main\java\service\MyService.java
package service;

import java.util.Map; // Added import
import java.util.concurrent.atomic.AtomicInteger; // Added import

import grpc.StockServiceGrpc.StockServiceImplBase;
import grpc.StockServiceOuterClass.DameStockRequest; // Added import
import grpc.StockServiceOuterClass.ProductosReply; // Added import
import grpc.StockServiceOuterClass.RestoProductoReply;
import grpc.StockServiceOuterClass.StockRequest; // Added import
import grpc.StockServiceOuterClass.VentaRequest;
import io.grpc.stub.StreamObserver;
import pcd.util.*;

public class MyService extends StockServiceImplBase {
    private final Ventana v;
    private final Map<String, Integer> stock; // Central stock data

    public MyService(Ventana v_, Map<String, Integer> stock_) {
        v = v_;
        stock = stock_; // Store the stock map reference
    }

    // Service 1: registrarVenta
    @Override
    public void registrarVenta(VentaRequest request, StreamObserver<RestoProductoReply> responseObserver) {
        String id = request.getIdProducto();
        int cantidadVendida = request.getCantidad(); // Use the quantity from the request

        // Atomically update stock and get the new value
        AtomicInteger stockActual = new AtomicInteger();
        stock.compute(id, (productId, currentStock) -> {
            if (currentStock == null) {
                v.addText("[Servidor] Error: Producto '" + productId + "' no encontrado en stock.");
                stockActual.set(-1); // Indicate error or non-existence
                return null; // Or handle as needed, maybe initialize?
            }
            int nuevoStock = Math.max(0, currentStock - cantidadVendida); // Ensure stock doesn't go below 0
            stockActual.set(nuevoStock);
            return nuevoStock;
        });

        int stockRestante = stockActual.get();

        if (stockRestante != -1) {
             v.addText("[Servidor] Petición 'registrarVenta' para: " + id + ", Cantidad: " + cantidadVendida);
             v.addText("[Servidor] Venta registrada -> Producto: " + id + " quedan " + stockRestante);
        } else {
             v.addText("[Servidor] Petición 'registrarVenta' fallida para producto no existente: " + id);
        }


        // Build and send response
        RestoProductoReply resp = RestoProductoReply.newBuilder().setResto(stockRestante).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
        if (stockRestante != -1) {
             v.addText("[Servidor] Respuesta 'registrarVenta' enviada.");
        }
    }

    // Service 2: bajoStock (Server Streaming)
    @Override
    public void bajoStock(StockRequest request, StreamObserver<ProductosReply> responseObserver) {
        int umbral = request.getUmbral();
        v.addText("[Servidor] Petición 'bajoStock' con umbral: " + umbral);

        stock.forEach((idProducto, cantidad) -> {
            if (cantidad < umbral) {
                ProductosReply reply = ProductosReply.newBuilder().setIdProducto(idProducto).build();
                responseObserver.onNext(reply);
                v.addText("[Servidor] Enviando producto bajo stock: " + idProducto + " (" + cantidad + ")");
            }
        });

        responseObserver.onCompleted();
        v.addText("[Servidor] Finalizada respuesta 'bajoStock'.");
    }

    // Service 3: dameStock (Bidirectional Streaming)
    @Override
    public StreamObserver<DameStockRequest> dameStock(StreamObserver<VentaRequest> responseObserver) {
         v.addText("[Servidor] Petición 'dameStock' iniciada.");

         return new StreamObserver<DameStockRequest>() {
            @Override
            public void onNext(DameStockRequest request) {
                String idProducto = request.getIdProducto();
                int cantidadActual = stock.getOrDefault(idProducto, -1); // Get stock, default to -1 if not found

                if (cantidadActual != -1) {
                     v.addText("[Servidor] 'dameStock' -> Consultando stock para: " + idProducto);
                     VentaRequest reply = VentaRequest.newBuilder()
                            .setIdProducto(idProducto)
                            .setCantidad(cantidadActual)
                            .build();
                     responseObserver.onNext(reply);
                     v.addText("[Servidor] 'dameStock' -> Enviando stock para: " + idProducto + " (" + cantidadActual + ")");
                } else {
                     v.addText("[Servidor] 'dameStock' -> Producto no encontrado: " + idProducto);
                     // Optionally send an error or a specific value like -1
                     VentaRequest reply = VentaRequest.newBuilder()
                            .setIdProducto(idProducto)
                            .setCantidad(-1) // Indicate product not found
                            .build();
                     responseObserver.onNext(reply);
                }
            }

            @Override
            public void onError(Throwable t) {
                v.addText("[Servidor] Error en 'dameStock': " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted(); // Signal the client that the server is done sending
                v.addText("[Servidor] Finalizada petición 'dameStock'.");
            }
         };
    }
}