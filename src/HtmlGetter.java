import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;


public class HtmlGetter implements Runnable {
	
	private LinkedBlockingQueue<String> colaURLsNuevos;
	private LinkedBlockingQueue<String[]> colaHTMLAnalizar;
	private LinkedBlockingQueue<String[]> busMonitor;
	
	public HtmlGetter(LinkedBlockingQueue<String> colaEntrante,LinkedBlockingQueue<String[]> colaSaliente,LinkedBlockingQueue<String[]> colaEventos){
		colaURLsNuevos = colaEntrante;
		colaHTMLAnalizar = colaSaliente;
		busMonitor = colaEventos;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true){
			try {
				
				//lee url de la cola
				String url = colaURLsNuevos.take();
				
				//avisar al monitor
				MonitorMessager monitor = new MonitorMessager(busMonitor);
				monitor.agregarObteniendoHTML();
				
				//obtener string html
				String html = "";
				URL urlObj = new URL(url);
				BufferedReader in = new BufferedReader(
						new InputStreamReader(urlObj.openStream()));
		        String inputLine;
		        while ((inputLine = in.readLine()) != null){
		            html = html + inputLine;
		        }
		        in.close();
			
				//meter String html a la otra cola		
				String[] page = new String[2];
				page[0] = url;
				page[1] = html;
				colaHTMLAnalizar.put(page);
				
				//avisar al monitor
				monitor.restarObtenienoHTML();
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
