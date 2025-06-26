package delivery;

public class tramitarPedido implements Runnable{
	
	private Pedido pedido;
    private Restaurante restaurante;

    public tramitarPedido(Pedido p, Restaurante r) {
        this.pedido = p;
        this.restaurante = r;
    }

    @Override
    public void run() {
        restaurante.tramitarPedido(pedido);
    }

}
