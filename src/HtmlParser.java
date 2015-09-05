import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HtmlParser implements Runnable {
	
	private LinkedBlockingQueue<String> colaStringHtml;
	private LinkedBlockingQueue<String> colaURLsEntrantes;
	private LinkedBlockingQueue<String> colaRecursos;
	private LinkedBlockingQueue<String> busMonitor;
	
	public HtmlParser(LinkedBlockingQueue<String> colaEntrante,LinkedBlockingQueue<String> colaSalienteUrl,LinkedBlockingQueue<String> colaSalienteRec,LinkedBlockingQueue<String> colaEventos){
		colaStringHtml = colaEntrante;
		colaURLsEntrantes = colaSalienteUrl;
		colaRecursos = colaSalienteRec;
		busMonitor = colaEventos;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		//leer string html de la cola
		String htmlString = "";
		
		//buscar imagenes y meterlas en la cola de imagenes
		Pattern pattern = Pattern.compile("(?m)(?s)<img\\s+(.*)src\\s*=\\s*\"([^\"]+)\"(.*)");
		Matcher matcher = pattern.matcher(htmlString);
		while (matcher.find()){
			//encolar a cola rec matcher.group(2)
		}
		
		//buscar audio y meterlos en la cola de audios
		Pattern.compile("\\S[\\.mp3]|[\\.ogg]");
		
		//buscar links y meterlos en la cola de urls
		Pattern patter = Pattern.compile("/<a\\s[^>]*href\\s*=\\s*(\\\"??)([^\\\" >]*?)\\\\1[^>]*>(.*)<\\/a>/siU");
		Matcher matche = pattern.matcher(htmlString);
		while (matcher.find()){
			//encolar a cola urls matcher.group()
		}

	}

	
	//si el algun link es relativo completar con path actual

}
