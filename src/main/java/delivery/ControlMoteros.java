package delivery;

import java.util.LinkedList;

import pcd.util.Ventana;

public class ControlMoteros {
	int numeroMoteros;
	Restaurante r;
	Ventana v;
	int moterosLibres;
	static int posicionVentana = 10;
	LinkedList<Pedido> listaPedidosEnviados; 
	Object o1;
	Object o2;
	MoterosEspera moterosEspera;
	MoterosEsperaCyclicBarrier moterosEsperaBarrier;
	Recarga recarga;
	ArranqueCargador arranque;
	
	public ControlMoteros (Restaurante _r, int _numeroMoteros) {
		r=_r;
		numeroMoteros = _numeroMoteros;
		moterosLibres = numeroMoteros;
		o1 = new Object();
		o2 = new Object();
		listaPedidosEnviados = new LinkedList<Pedido>();
        arranque = new ArranqueCargador(numeroMoteros);
		recarga = new Recarga(this);
        Thread hiloCargador = new Thread(new Cargador(recarga, arranque));
        hiloCargador.start();
		// Creamos una ventana para los mensajes de este objeto.
		v =  new Ventana ("Control Moteros - "+r.getNombre(), posicionVentana,10);
		posicionVentana+=250;
		if(Config.modoEsperaMoteros == 0){
			moterosEspera = new MoterosEspera(numeroMoteros);
		}else 
		if(Config.modoEsperaMoteros == 1){
			moterosEsperaBarrier = new MoterosEsperaCyclicBarrier(numeroMoteros);
		}
		else{
			System.out.println("Modo de espera moteros no soportado");
			System.exit(0);
		}
		
		for(int i=0;i<numeroMoteros;i++){
			
			Thread m = new Thread( new Motero(this,i, v));
			m.start();
		}
	}
		
	public void moteroLibre() {
		synchronized (o1) {
		    while(moterosLibres == 0) {
		        try {
		            o1.wait();
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
		    }
	    moterosLibres--;
		}
	}

	public synchronized void enviarPedido(Pedido p) {
		synchronized (o2) {
	    v.addText("REPARTIENDO PEDIDO : "+p.getId());
	    try {
	        listaPedidosEnviados.add(p);
	        o2.notify();
	        Thread.sleep(500);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
		}
	}

	public synchronized Pedido getPedido() {
		synchronized (o2) {
	    while(listaPedidosEnviados.get(0) == null) {
	        try {
	            o2.wait();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	    Pedido pedido = listaPedidosEnviados.removeFirst();
	    return pedido;
		}
	}

	public synchronized void regresa() {
		synchronized (o1) {
			moterosLibres++;
		    o1.notify();
		}
	    
	}
	
}
