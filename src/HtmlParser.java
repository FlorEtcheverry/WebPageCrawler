import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

public class HtmlParser implements Runnable {

	private LinkedBlockingQueue<String[]> colaStringHtml;
	private LinkedBlockingQueue<String> colaURLsEntrantes;
	private LinkedBlockingQueue<String> colaRecursos;
	private LinkedBlockingQueue<String[]> busMonitor;

	public HtmlParser(
		LinkedBlockingQueue<String[]> colaEntrante,
		LinkedBlockingQueue<String> colaSalienteUrl,
		LinkedBlockingQueue<String> colaSalienteRec,
		LinkedBlockingQueue<String[]> colaEventos) {
		
		colaStringHtml = colaEntrante;
		colaURLsEntrantes = colaSalienteUrl;
		colaRecursos = colaSalienteRec;
		busMonitor = colaEventos;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		String[] pagString = new String[2];
		while (true) {
			try {
				// leer string html de la cola
				pagString = colaStringHtml.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
			// avisar al monitor parseando html
			MonitorMessager monitor = new MonitorMessager(busMonitor);
			monitor.agregarAnalizandoHTML();
	
			// buscar links para enviar
			LinkFinder links = new LinkFinder(pagString[0], pagString[1]);
			LinkedList<String> listaLinks = links.find();
	
			// poner los links en la cola
			Iterator<String> it;
			try {
				it = listaLinks.iterator();
				while (it.hasNext()) {
					colaURLsEntrantes.put(it.next());
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			// buscar recursos para bajar
			MediaFinder media = new MediaFinder(pagString[0], pagString[1]);
			LinkedList<String> listaMedia = media.find();
	
			// poner en la cola
			it = listaMedia.iterator();
			try {
				while (it.hasNext()) {
					colaRecursos.put(it.next());
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// avisar al monitor -1 parseando html
			monitor.restarAnalizandoHTML();
		}
	}
}
