package delivery;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class PedidosMayoresForkJoin extends RecursiveTask<List<String>> {
    private List<Pedido> pedidos;
    private int start;
    private int end;
    private static final int UMBRAL = 10;     // caso trivial: menos de 10 pedidos
    private static final double LIMITE = 12.0;  // precio mínimo para incluir el pedido

    public PedidosMayoresForkJoin(List<Pedido> pedidos, int start, int end) {
        this.pedidos = pedidos;
        this.start = start;
        this.end = end;
    }

    @Override
    protected List<String> compute() {
        if ((end - start) <= UMBRAL) { 
            List<String> resultado = new ArrayList<>();
            for (int i = start; i < end; i++) {
                Pedido p = pedidos.get(i);
                if (p.getPrecioPedido() > LIMITE) {
                    resultado.add(p.getId());
                }
            }
            return resultado;
        } else {
            int mid = (start + end) / 2;
            PedidosMayoresForkJoin leftTask = new PedidosMayoresForkJoin(pedidos, start, mid);
            PedidosMayoresForkJoin rightTask = new PedidosMayoresForkJoin(pedidos, mid, end);
            leftTask.fork();
            List<String> rightResult = rightTask.compute();
            List<String> leftResult = leftTask.join();
            leftResult.addAll(rightResult);
            return leftResult;
        }
    }
}