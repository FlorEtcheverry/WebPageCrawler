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
		
		//leer string html de la cola
		//String htmlString = "<!DOCTYPE html><html><body><p>Click on the sun or on one of the planets to watch it closer:</p><img src=\"planets.gif\" alt=\"Planets\" usemap=\"#planetmap\" style=\"width:145px;height:126px;\"><map name=\"planetmap\"><area shape=\"rect\" coords=\"0,0,82,126\" alt=\"Sun\" href=\"sun.htm\"><area shape=\"circle\" coords=\"90,58,3\" alt=\"Mercury\" href=\"mercur.htm\"><area shape=\"circle\" coords=\"124,58,8\" alt=\"Venus\" href=\"venus.htm\"></map></body></html>";
		String[] pagString;
		try {
			pagString = colaStringHtml.take();
			String url = pagString[0]; //TODO para completar relativos
			String htmlString = pagString[1];
		
			//buscar imagenes y meterlas en la cola de multimedia
			//Pattern pattern = Pattern.compile("(?m)(?s)<img\\s+(.*)src\\s*=\\s*\"([^\"]+)\"(.*)");
			Pattern pattern = Pattern.compile("[\"|']([\\w:/\\.?=\\-]*\\.(jpg|png|gif|bmp))[\"|']");
			Matcher matcher = pattern.matcher(htmlString);
			while (matcher.find()){
				System.out.println("imagen:");
				System.out.println(matcher.group(1));
				//encolar a cola rec matcher.group(2)
			}
			
			//buscar audio y meterlos en la cola de multimedia
			
			//Pattern pattern2 = Pattern.compile("\\S+[\\.mp3]|[\\.ogg]");
			Pattern pattern2 = Pattern.compile("[\"|']([\\w:/\\.?=\\-]*\\.(mp3|ogg))[\"|']");
			Matcher matcher2 = pattern2.matcher(htmlString);
			while (matcher2.find()){
				System.out.println("audio:");
				System.out.println(matcher2.group(1));
				//encolar a cola matcher.group()
			}
			
			
			//buscar recursos web y meterlos en la cola de rec web
			Pattern pattern4 = Pattern.compile("[\"|']([\\w:/\\.?=\\-]*\\.(css|js))[\"|']");
			Matcher matcher4 = pattern4.matcher(htmlString);
			while (matcher4.find()){
				System.out.println("web:");
				System.out.println(matcher4.group(1));
				//encolar a cola matcher.group()
				colaRecursos.put(url + matcher4.group(1)); //TODO puede q sea necesario agregar una / despues de la url
			}
			
			//buscar links y meterlos en la cola de urls
			//Pattern pattern3 = Pattern.compile("/<a\\s[^>]*href\\s*=\\s*(\\\"??)([^\\\" >]*?)\\\\1[^>]*>(.*)<\\/a>/siU");
			Pattern pattern3 = Pattern.compile("href\\s*=\\s*[\"|']([\\w:/\\.?=\\-]*)[\"|']");
			Matcher matcher3 = pattern3.matcher(htmlString);
			while (matcher3.find()){
				System.out.println("link:");
				System.out.println(matcher3.group(1));
				//encolar a cola urls matcher.group()
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	//si el algun link es relativo completar con path actual INCLUIR HTTP://

}
