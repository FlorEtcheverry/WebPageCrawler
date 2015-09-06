import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;


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
		
		
		LinkedBlockingQueue<String[]> cola1 = new LinkedBlockingQueue<String[]>();
		LinkedBlockingQueue<String> colaRec = new LinkedBlockingQueue<String>();
		Thread t1 = new Thread(new HtmlGetter(null,cola1,null));
		Thread t2 = new Thread(new HtmlParser(cola1,null,colaRec,null));
		Thread t3 = new Thread(new Downloader(colaRec,null));
		t1.start();
		t2.start();
		t3.start();
		try {
			t1.join();
			t2.join();
			t3.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
