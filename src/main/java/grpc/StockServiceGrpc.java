package grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.54.0)",
    comments = "Source: Stock.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class StockServiceGrpc {

  private StockServiceGrpc() {}

  public static final String SERVICE_NAME = "StockService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<grpc.StockServiceOuterClass.VentaRequest,
      grpc.StockServiceOuterClass.RestoProductoReply> getRegistrarVentaMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "registrarVenta",
      requestType = grpc.StockServiceOuterClass.VentaRequest.class,
      responseType = grpc.StockServiceOuterClass.RestoProductoReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.StockServiceOuterClass.VentaRequest,
      grpc.StockServiceOuterClass.RestoProductoReply> getRegistrarVentaMethod() {
    io.grpc.MethodDescriptor<grpc.StockServiceOuterClass.VentaRequest, grpc.StockServiceOuterClass.RestoProductoReply> getRegistrarVentaMethod;
    if ((getRegistrarVentaMethod = StockServiceGrpc.getRegistrarVentaMethod) == null) {
      synchronized (StockServiceGrpc.class) {
        if ((getRegistrarVentaMethod = StockServiceGrpc.getRegistrarVentaMethod) == null) {
          StockServiceGrpc.getRegistrarVentaMethod = getRegistrarVentaMethod =
              io.grpc.MethodDescriptor.<grpc.StockServiceOuterClass.VentaRequest, grpc.StockServiceOuterClass.RestoProductoReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "registrarVenta"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.StockServiceOuterClass.VentaRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.StockServiceOuterClass.RestoProductoReply.getDefaultInstance()))
              .setSchemaDescriptor(new StockServiceMethodDescriptorSupplier("registrarVenta"))
              .build();
        }
      }
    }
    return getRegistrarVentaMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.StockServiceOuterClass.StockRequest,
      grpc.StockServiceOuterClass.ProductosReply> getBajoStockMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "bajoStock",
      requestType = grpc.StockServiceOuterClass.StockRequest.class,
      responseType = grpc.StockServiceOuterClass.ProductosReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<grpc.StockServiceOuterClass.StockRequest,
      grpc.StockServiceOuterClass.ProductosReply> getBajoStockMethod() {
    io.grpc.MethodDescriptor<grpc.StockServiceOuterClass.StockRequest, grpc.StockServiceOuterClass.ProductosReply> getBajoStockMethod;
    if ((getBajoStockMethod = StockServiceGrpc.getBajoStockMethod) == null) {
      synchronized (StockServiceGrpc.class) {
        if ((getBajoStockMethod = StockServiceGrpc.getBajoStockMethod) == null) {
          StockServiceGrpc.getBajoStockMethod = getBajoStockMethod =
              io.grpc.MethodDescriptor.<grpc.StockServiceOuterClass.StockRequest, grpc.StockServiceOuterClass.ProductosReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "bajoStock"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.StockServiceOuterClass.StockRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.StockServiceOuterClass.ProductosReply.getDefaultInstance()))
              .setSchemaDescriptor(new StockServiceMethodDescriptorSupplier("bajoStock"))
              .build();
        }
      }
    }
    return getBajoStockMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.StockServiceOuterClass.DameStockRequest,
      grpc.StockServiceOuterClass.VentaRequest> getDameStockMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "dameStock",
      requestType = grpc.StockServiceOuterClass.DameStockRequest.class,
      responseType = grpc.StockServiceOuterClass.VentaRequest.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<grpc.StockServiceOuterClass.DameStockRequest,
      grpc.StockServiceOuterClass.VentaRequest> getDameStockMethod() {
    io.grpc.MethodDescriptor<grpc.StockServiceOuterClass.DameStockRequest, grpc.StockServiceOuterClass.VentaRequest> getDameStockMethod;
    if ((getDameStockMethod = StockServiceGrpc.getDameStockMethod) == null) {
      synchronized (StockServiceGrpc.class) {
        if ((getDameStockMethod = StockServiceGrpc.getDameStockMethod) == null) {
          StockServiceGrpc.getDameStockMethod = getDameStockMethod =
              io.grpc.MethodDescriptor.<grpc.StockServiceOuterClass.DameStockRequest, grpc.StockServiceOuterClass.VentaRequest>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "dameStock"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.StockServiceOuterClass.DameStockRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.StockServiceOuterClass.VentaRequest.getDefaultInstance()))
              .setSchemaDescriptor(new StockServiceMethodDescriptorSupplier("dameStock"))
              .build();
        }
      }
    }
    return getDameStockMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static StockServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StockServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StockServiceStub>() {
        @java.lang.Override
        public StockServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StockServiceStub(channel, callOptions);
        }
      };
    return StockServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static StockServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StockServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StockServiceBlockingStub>() {
        @java.lang.Override
        public StockServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StockServiceBlockingStub(channel, callOptions);
        }
      };
    return StockServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static StockServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StockServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StockServiceFutureStub>() {
        @java.lang.Override
        public StockServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StockServiceFutureStub(channel, callOptions);
        }
      };
    return StockServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void registrarVenta(grpc.StockServiceOuterClass.VentaRequest request,
        io.grpc.stub.StreamObserver<grpc.StockServiceOuterClass.RestoProductoReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegistrarVentaMethod(), responseObserver);
    }

    /**
     */
    default void bajoStock(grpc.StockServiceOuterClass.StockRequest request,
        io.grpc.stub.StreamObserver<grpc.StockServiceOuterClass.ProductosReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getBajoStockMethod(), responseObserver);
    }

    /**
     */
    default io.grpc.stub.StreamObserver<grpc.StockServiceOuterClass.DameStockRequest> dameStock(
        io.grpc.stub.StreamObserver<grpc.StockServiceOuterClass.VentaRequest> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getDameStockMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service StockService.
   */
  public static abstract class StockServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return StockServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service StockService.
   */
  public static final class StockServiceStub
      extends io.grpc.stub.AbstractAsyncStub<StockServiceStub> {
    private StockServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StockServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StockServiceStub(channel, callOptions);
    }

    /**
     */
    public void registrarVenta(grpc.StockServiceOuterClass.VentaRequest request,
        io.grpc.stub.StreamObserver<grpc.StockServiceOuterClass.RestoProductoReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegistrarVentaMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void bajoStock(grpc.StockServiceOuterClass.StockRequest request,
        io.grpc.stub.StreamObserver<grpc.StockServiceOuterClass.ProductosReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getBajoStockMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<grpc.StockServiceOuterClass.DameStockRequest> dameStock(
        io.grpc.stub.StreamObserver<grpc.StockServiceOuterClass.VentaRequest> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getDameStockMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service StockService.
   */
  public static final class StockServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<StockServiceBlockingStub> {
    private StockServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StockServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StockServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public grpc.StockServiceOuterClass.RestoProductoReply registrarVenta(grpc.StockServiceOuterClass.VentaRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegistrarVentaMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<grpc.StockServiceOuterClass.ProductosReply> bajoStock(
        grpc.StockServiceOuterClass.StockRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getBajoStockMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service StockService.
   */
  public static final class StockServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<StockServiceFutureStub> {
    private StockServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StockServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StockServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.StockServiceOuterClass.RestoProductoReply> registrarVenta(
        grpc.StockServiceOuterClass.VentaRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegistrarVentaMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTRAR_VENTA = 0;
  private static final int METHODID_BAJO_STOCK = 1;
  private static final int METHODID_DAME_STOCK = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REGISTRAR_VENTA:
          serviceImpl.registrarVenta((grpc.StockServiceOuterClass.VentaRequest) request,
              (io.grpc.stub.StreamObserver<grpc.StockServiceOuterClass.RestoProductoReply>) responseObserver);
          break;
        case METHODID_BAJO_STOCK:
          serviceImpl.bajoStock((grpc.StockServiceOuterClass.StockRequest) request,
              (io.grpc.stub.StreamObserver<grpc.StockServiceOuterClass.ProductosReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_DAME_STOCK:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.dameStock(
              (io.grpc.stub.StreamObserver<grpc.StockServiceOuterClass.VentaRequest>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getRegistrarVentaMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.StockServiceOuterClass.VentaRequest,
              grpc.StockServiceOuterClass.RestoProductoReply>(
                service, METHODID_REGISTRAR_VENTA)))
        .addMethod(
          getBajoStockMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              grpc.StockServiceOuterClass.StockRequest,
              grpc.StockServiceOuterClass.ProductosReply>(
                service, METHODID_BAJO_STOCK)))
        .addMethod(
          getDameStockMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              grpc.StockServiceOuterClass.DameStockRequest,
              grpc.StockServiceOuterClass.VentaRequest>(
                service, METHODID_DAME_STOCK)))
        .build();
  }

  private static abstract class StockServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    StockServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.StockServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("StockService");
    }
  }

  private static final class StockServiceFileDescriptorSupplier
      extends StockServiceBaseDescriptorSupplier {
    StockServiceFileDescriptorSupplier() {}
  }

  private static final class StockServiceMethodDescriptorSupplier
      extends StockServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    StockServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (StockServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new StockServiceFileDescriptorSupplier())
              .addMethod(getRegistrarVentaMethod())
              .addMethod(getBajoStockMethod())
              .addMethod(getDameStockMethod())
              .build();
        }
      }
    }
    return result;
  }
}
