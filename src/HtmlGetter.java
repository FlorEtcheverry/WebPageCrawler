import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;


public class HtmlGetter implements Runnable {
	
	private LinkedBlockingQueue<String> colaURLsNuevos;
	private LinkedBlockingQueue<String[]> colaHTMLsAnalizar;
	private LinkedBlockingQueue<String> busMonitor;
	
	public HtmlGetter(LinkedBlockingQueue<String> colaEntrante,LinkedBlockingQueue<String[]> colaSaliente,LinkedBlockingQueue<String> colaEventos){
		colaURLsNuevos = colaEntrante;
		colaHTMLsAnalizar = colaSaliente;
		busMonitor = colaEventos;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		//lee url de la cola
		String url = "http://materias.fi.uba.ar/7526/"; //TODO
		
		//obtener string html
		String html = "";
		try {
			URL urlObj = new URL(url);
			BufferedReader in = new BufferedReader(new InputStreamReader(urlObj.openStream()));

	        String inputLine;
	        while ((inputLine = in.readLine()) != null){
	            html = html + inputLine;
	        }
	        in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//meter String html a la otra cola

		System.out.println("resultado htmlgetter");
		System.out.println(html);
		
		try {
			String[] page = new String[2];
			page[0] = url;
			page[1] = html;
			colaHTMLsAnalizar.put(page);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
