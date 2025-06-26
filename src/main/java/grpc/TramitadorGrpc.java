package grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.54.0)",
    comments = "Source: sesion10.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class TramitadorGrpc {

  private TramitadorGrpc() {}

  public static final String SERVICE_NAME = "Tramitador";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<grpc.TramitadorOuterClass.PedidoRequest,
      grpc.TramitadorOuterClass.PedidoReply> getTramitarMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "tramitar",
      requestType = grpc.TramitadorOuterClass.PedidoRequest.class,
      responseType = grpc.TramitadorOuterClass.PedidoReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.TramitadorOuterClass.PedidoRequest,
      grpc.TramitadorOuterClass.PedidoReply> getTramitarMethod() {
    io.grpc.MethodDescriptor<grpc.TramitadorOuterClass.PedidoRequest, grpc.TramitadorOuterClass.PedidoReply> getTramitarMethod;
    if ((getTramitarMethod = TramitadorGrpc.getTramitarMethod) == null) {
      synchronized (TramitadorGrpc.class) {
        if ((getTramitarMethod = TramitadorGrpc.getTramitarMethod) == null) {
          TramitadorGrpc.getTramitarMethod = getTramitarMethod =
              io.grpc.MethodDescriptor.<grpc.TramitadorOuterClass.PedidoRequest, grpc.TramitadorOuterClass.PedidoReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "tramitar"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.TramitadorOuterClass.PedidoRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.TramitadorOuterClass.PedidoReply.getDefaultInstance()))
              .setSchemaDescriptor(new TramitadorMethodDescriptorSupplier("tramitar"))
              .build();
        }
      }
    }
    return getTramitarMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TramitadorStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TramitadorStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TramitadorStub>() {
        @java.lang.Override
        public TramitadorStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TramitadorStub(channel, callOptions);
        }
      };
    return TramitadorStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TramitadorBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TramitadorBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TramitadorBlockingStub>() {
        @java.lang.Override
        public TramitadorBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TramitadorBlockingStub(channel, callOptions);
        }
      };
    return TramitadorBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TramitadorFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TramitadorFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TramitadorFutureStub>() {
        @java.lang.Override
        public TramitadorFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TramitadorFutureStub(channel, callOptions);
        }
      };
    return TramitadorFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void tramitar(grpc.TramitadorOuterClass.PedidoRequest request,
        io.grpc.stub.StreamObserver<grpc.TramitadorOuterClass.PedidoReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getTramitarMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Tramitador.
   */
  public static abstract class TramitadorImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return TramitadorGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Tramitador.
   */
  public static final class TramitadorStub
      extends io.grpc.stub.AbstractAsyncStub<TramitadorStub> {
    private TramitadorStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TramitadorStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TramitadorStub(channel, callOptions);
    }

    /**
     */
    public void tramitar(grpc.TramitadorOuterClass.PedidoRequest request,
        io.grpc.stub.StreamObserver<grpc.TramitadorOuterClass.PedidoReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getTramitarMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Tramitador.
   */
  public static final class TramitadorBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<TramitadorBlockingStub> {
    private TramitadorBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TramitadorBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TramitadorBlockingStub(channel, callOptions);
    }

    /**
     */
    public grpc.TramitadorOuterClass.PedidoReply tramitar(grpc.TramitadorOuterClass.PedidoRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getTramitarMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Tramitador.
   */
  public static final class TramitadorFutureStub
      extends io.grpc.stub.AbstractFutureStub<TramitadorFutureStub> {
    private TramitadorFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TramitadorFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TramitadorFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.TramitadorOuterClass.PedidoReply> tramitar(
        grpc.TramitadorOuterClass.PedidoRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getTramitarMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_TRAMITAR = 0;

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
        case METHODID_TRAMITAR:
          serviceImpl.tramitar((grpc.TramitadorOuterClass.PedidoRequest) request,
              (io.grpc.stub.StreamObserver<grpc.TramitadorOuterClass.PedidoReply>) responseObserver);
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
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getTramitarMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.TramitadorOuterClass.PedidoRequest,
              grpc.TramitadorOuterClass.PedidoReply>(
                service, METHODID_TRAMITAR)))
        .build();
  }

  private static abstract class TramitadorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TramitadorBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.TramitadorOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Tramitador");
    }
  }

  private static final class TramitadorFileDescriptorSupplier
      extends TramitadorBaseDescriptorSupplier {
    TramitadorFileDescriptorSupplier() {}
  }

  private static final class TramitadorMethodDescriptorSupplier
      extends TramitadorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    TramitadorMethodDescriptorSupplier(String methodName) {
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
      synchronized (TramitadorGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TramitadorFileDescriptorSupplier())
              .addMethod(getTramitarMethod())
              .build();
        }
      }
    }
    return result;
  }
}
