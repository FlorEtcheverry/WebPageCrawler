import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//por parámetro viene la primer URL
		
		//lee de arch de config cantidad de threads por pool
		int cantDetectores;
		int cantGetters;
		int cantParsers;
		
		//crea colas (5)
		
		//crea pools con cant leidas		
		//pool para detector repetidos
		//ExecutorService poolDetectores = Executors.newFixedThreadPool(cantDetectores);
		
		
		//pool para html getter
		
		//pool html parser
		
		//pool resource downloader (uno por tipo?)
		
		//monitor (pool?)

	}

}
