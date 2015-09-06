import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;


public class Main {

	/**
	 * @param args: url
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//por parámetro viene la primer URL
		String url = cargarParametroURL(args);
		
		//lee de arch de config cantidad de threads por pool
		ConfigLoader conf = new ConfigLoader();
		
		//crea colas (5)
		LinkedBlockingQueue<String> colaURLsARevisar = new LinkedBlockingQueue<String>();
		LinkedBlockingQueue<String> colaURLsRevisados = new LinkedBlockingQueue<String>();
		LinkedBlockingQueue<String[]> colaHTML = new LinkedBlockingQueue<String[]>();
		LinkedBlockingQueue<String> colaRecursos = new LinkedBlockingQueue<String>();
		LinkedBlockingQueue<String> colaMonitor = new LinkedBlockingQueue<String>();
		
		//crea pools con cant leidas		
		//pool para detector repetidos
		ExecutorService poolDetectores = Executors.newFixedThreadPool(conf.getCantDetectoresRep());
		
		//pool para html getter
		ExecutorService poolHtmlGetters = Executors.newFixedThreadPool(conf.getCantGetters());
		
		//pool html parser
		ExecutorService poolParsers = Executors.newFixedThreadPool(conf.getCantParsers());
		
		//pool resource downloader
		ExecutorService poolDownloaders = Executors.newFixedThreadPool(conf.getCantDownloaders());
		
		//pool monitor
		ExecutorService poolMonitor = Executors.newSingleThreadExecutor();
		
		//ejecutar threads
		//poolDetectores.execute(new DetectorRepetidos(colaURLsARevisar, colaURLsRevisados));
		poolHtmlGetters.execute(new HtmlGetter(colaURLsRevisados, colaHTML, colaMonitor));
		poolParsers.execute(new HtmlParser(colaHTML, colaURLsARevisar, colaRecursos, colaMonitor));
		poolDownloaders.execute(new Downloader(colaRecursos, colaMonitor));
		//poolMonitor.execute(new Monitor());

	}
	
	private static String cargarParametroURL(String[] args){
		System.out.println("Parametro: URL.");
		if (args.length != 1) {
			System.out.println("Error, el unico parametro es la URL.");
		}
		return args[0];
	}

}
