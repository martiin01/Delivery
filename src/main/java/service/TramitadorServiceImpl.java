package service;

import java.util.ArrayList; // Necesario para la lista de productos
import java.util.List;
// import java.util.stream.Collectors; // Ya no es necesario para esta parte

import delivery.CadenaRestaurantes;
import delivery.Pedido;
import delivery.Restaurante;
import grpc.TramitadorOuterClass.PedidoReply;
import grpc.TramitadorOuterClass.PedidoRequest;
import grpc.TramitadorOuterClass.ProductoRequest; // Import necesario
import grpc.TramitadorGrpc;
import io.grpc.stub.StreamObserver;
import pcd.util.ColoresConsola;
import pcd.util.Traza;

public class TramitadorServiceImpl extends TramitadorGrpc.TramitadorImplBase {

    private final CadenaRestaurantes cadenaRestaurantes;

    public TramitadorServiceImpl(CadenaRestaurantes cadena) {
        this.cadenaRestaurantes = cadena;
    }

    @Override
    public void tramitar(PedidoRequest request, StreamObserver<PedidoReply> responseObserver) {
        Traza.traza(ColoresConsola.BLUE, 2, "[Servidor Tramitador] Recibido pedido gRPC: " + request.getIdPedido());

        try {
            Restaurante restaurante = cadenaRestaurantes.getRestaurantes().get(request.getRestaurante());

            if (restaurante == null) {
                throw new IllegalArgumentException("Restaurante no encontrado: " + request.getRestaurante());
            }

            // --- Inicio de la conversión con bucle for ---
            // 1. Convertir productos gRPC a productos de dominio usando un bucle
            List<delivery.Producto> productosDominio = new ArrayList<>(); // Crear una lista vacía
            // Iterar sobre la lista de ProductoRequest del PedidoRequest
            for (ProductoRequest pr : request.getProductosList()) {
                // Crear un nuevo objeto delivery.Producto y añadirlo a la lista
                productosDominio.add(new delivery.Producto(pr.getId(), pr.getPrecio(), pr.getCantidad()));
            }
            // --- Fin de la conversión con bucle for ---


            // 2. Crear un objeto Pedido (usando la lista productosDominio creada arriba)
            delivery.Pedido pedidoDominio = new delivery.Pedido(
                request.getIdPedido(),
                request.getDireccion(),
                request.getRestaurante(),
                null, // Canal no disponible en request
                null, // Fecha no disponible en request
                null, // Hora no disponible en request
                productosDominio // Usar la lista creada con el bucle
            );

            // 3. Llamar a la lógica de negocio existente
            restaurante.tramitarPedido(pedidoDominio); // ¡Asegúrate que esto funciona con el Pedido creado!

            // 4. Construir la respuesta
            PedidoReply reply = PedidoReply.newBuilder()
                    .setResultado("Pedido " + request.getIdPedido() + " tramitado correctamente por restaurante " + restaurante.getNombre())
                    .build();

            // 5. Enviar la respuesta
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
            Traza.traza(ColoresConsola.BLUE, 2, "[Servidor Tramitador] Respuesta enviada para: " + request.getIdPedido());

        } catch (Exception e) {
            Traza.traza(ColoresConsola.RED, 2, "[Servidor Tramitador] Error tramitando pedido " + request.getIdPedido() + ": " + e.getMessage());
            // Enviar error al cliente
            responseObserver.onError(io.grpc.Status.INTERNAL
                    .withDescription("Error procesando el pedido: " + e.getMessage())
                    .asRuntimeException());
        }
    }
}