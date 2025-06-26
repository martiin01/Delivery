package delivery;
public class Config {
	public final static int modoTraza = 2;				// nivel de profundidad de la traza
	public final static int numeroRestaurantes = 7;		// n�mero de restaurantes a crear
	public final static int numeroPedidos = 5;			// n�mero de pedidos por canal a crear
	public final static int numeroMoteros = 2;			// n�mero de moteros por restaurante a crear
	public final static int numeroProductos = 100; 		// l�mite de cantidad de productos a crear en pedido
	public final static int maximoIdProducto = 100; 		// n�mero m�ximo de id de producto
	public final static double maximoPrecioProducto = 5;	// precio m�ximo de cada producto. 
	public static int modoLanzador = 0;   				// 0 -> Antiguo, 1 -> Executor, 2 -> Stream , 3 -> Observable, 4 -> server Tramitador Cliente
	public static int modoEsperaMoteros = 1;				// 0 -> Semaforo, 1 -> CycliBarrier
	// Puerto para el nuevo servicio Tramitador
    public final static int PUERTO_TRAMITADOR_GRPC = 9094;
    // Puerto para el servicio de Stock existente (si lo mantienes separado)
    public final static int PUERTO_STOCK_GRPC = 9093;
}
