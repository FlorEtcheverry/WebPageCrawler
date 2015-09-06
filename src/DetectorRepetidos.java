import java.util.concurrent.LinkedBlockingQueue;


public class DetectorRepetidos implements Runnable {

	private LinkedBlockingQueue<String> colaURLsEntrantes;
	private LinkedBlockingQueue<String> colaURLsNuevos;
	
	public DetectorRepetidos(LinkedBlockingQueue<String> colaEntrante,LinkedBlockingQueue<String> colaSaliente){
		colaURLsEntrantes = colaEntrante;
		colaURLsNuevos = colaSaliente;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		String url;
		while (true){
			try {
				//lee de la cola un url
				url = colaURLsEntrantes.take();
				
				//se fija en el archivo si ese url ya fue analizado
				
				//si no lo fue, lo agrega y lo pone en la otra cola de urls no repetidos
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
