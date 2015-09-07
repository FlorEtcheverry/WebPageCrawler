import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HtmlParser implements Runnable {
	
	private LinkedBlockingQueue<String[]> colaStringHtml;
	private LinkedBlockingQueue<String> colaURLsEntrantes;
	private LinkedBlockingQueue<String> colaRecursos;
	private LinkedBlockingQueue<String> busMonitor;
	
	public HtmlParser(LinkedBlockingQueue<String[]> colaEntrante,LinkedBlockingQueue<String> colaSalienteUrl,LinkedBlockingQueue<String> colaSalienteRec,LinkedBlockingQueue<String> colaEventos){
		colaStringHtml = colaEntrante;
		colaURLsEntrantes = colaSalienteUrl;
		colaRecursos = colaSalienteRec;
		busMonitor = colaEventos;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		String[] pagString;
		while (true){
			try {
				//leer string html de la cola
				pagString = colaStringHtml.take();
				System.out.println("a parsear url: "+pagString[0]);
				System.out.println("a parsear html: "+pagString[1]);
	
				String url = pagString[0]; //TODO para completar relativos
				if (!url.endsWith("/")) { //TODO no poner / ni analizar links q terminen en ".algo"
					url = url + "/";
					System.out.println("url cambiado: "+url);
				}
				String htmlString = pagString[1];
			
				//buscar imagenes y meterlas en la cola de multimedia
				Pattern pattern = Pattern.compile("[\"|']([\\w:/\\.?=\\-]*\\.(jpg|png|gif|bmp))[\"|']");
				Matcher matcher = pattern.matcher(htmlString);
				while (matcher.find()){
					//System.out.println("imagen:");
					//System.out.println(url + matcher.group(1));
					//encolar a cola rec
					String aEnc = cleanURL(url,matcher.group(1));
					colaRecursos.put(aEnc);
				}
				
				//buscar audio y meterlos en la cola de multimedia
				Pattern pattern2 = Pattern.compile("[\"|']([\\w:/\\.?=\\-]*\\.(mp3|ogg))[\"|']");
				Matcher matcher2 = pattern2.matcher(htmlString);
				while (matcher2.find()){
					//System.out.println("audio:");
					//System.out.println(matcher2.group(1));
					//encolar a cola de rec
					String aEnc = cleanURL(url,matcher2.group(1));
					colaRecursos.put(aEnc);
				}
				
				//buscar recursos web y meterlos en la cola de rec web
				Pattern pattern4 = Pattern.compile("[\"|']([\\w:/\\.?=\\-]*\\.(css|js))[\"|']");
				Matcher matcher4 = pattern4.matcher(htmlString);
				while (matcher4.find()){
					//System.out.println("web:");
					//System.out.println(matcher4.group(1));
					//encolar
					String aEnc = cleanURL(url,matcher4.group(1));
					colaRecursos.put(aEnc);
				}
				
				//buscar links y meterlos en la cola de urls
				Pattern pattern3 = Pattern.compile("href\\s*=\\s*[\"|']([\\w:/\\.?=\\-]*)[\"|']");
				Matcher matcher3 = pattern3.matcher(htmlString);
				while (matcher3.find()){
					//System.out.println("link:");
					//System.out.println(matcher3.group(1));
					//encolar a cola urls
					String aEnc = cleanURL(url,matcher3.group(1));
					colaURLsEntrantes.put(aEnc);
				}
				
				System.out.println("-----");
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private String cleanURL(String url,String resto){
		Pattern p = Pattern.compile("(http:\\/\\/)[^\\s]*");
		Matcher m = p.matcher(resto);
		while (m.find()){
			System.out.println("la url "+resto+" es absoluta, no agrego");
			return resto;
		}
		return url + resto;
	}
}
