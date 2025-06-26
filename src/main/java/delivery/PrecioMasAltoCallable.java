package delivery;

import java.util.List;
import java.util.concurrent.Callable;

public class PrecioMasAltoCallable implements Callable<Integer> {
    private List<Pedido> pedidos;

    public PrecioMasAltoCallable(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
    
    @Override
    public Integer call() {
        int max = 0;
        for (Pedido p : pedidos) {
            int precio = (int) p.getPrecioPedido();
            if (precio > max) {
                max = precio;
            }
        }
        return max;
    }

    
}
