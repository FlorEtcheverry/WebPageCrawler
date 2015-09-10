import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;


public class DetectorRepetidos implements Runnable {

	private LinkedBlockingQueue<String> colaURLsEntrantes;
	private LinkedBlockingQueue<String> colaURLsNuevos;
	private LinkedBlockingQueue<String[]> busMonitor;
	private String fileNameURLs;
	
	public DetectorRepetidos(LinkedBlockingQueue<String> colaEntrante,LinkedBlockingQueue<String> colaSaliente,LinkedBlockingQueue<String[]> colaStatus){
		colaURLsEntrantes = colaEntrante;
		colaURLsNuevos = colaSaliente;
		busMonitor = colaStatus;
		fileNameURLs = "urls";
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		String url;
		while (true){
			try {
				//lee de la cola un url
				url = colaURLsEntrantes.take();
				
				boolean rep = analizarRepetido(url);
				
				if (!rep) {
					//avisar monitor
					MonitorMessager monitor = new MonitorMessager(busMonitor);
					monitor.agregarLinkAnalizado();
					
					//poner en la cola
					colaURLsNuevos.put(url);
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/*Devuelve false si no se encontro*/
	private synchronized boolean analizarRepetido(String url){
		
		boolean res = true;
		
		//se fija en el archivo si ese url ya fue analizado
		try {
			File arch = new File(fileNameURLs);
			if (!arch.exists()) {
				arch.createNewFile();
			}
			BufferedReader br = new BufferedReader(new FileReader(arch));
			String line;
			boolean found = false;
			while (((line = br.readLine()) != null) && !found) {
				if (url.equals(line)) {
					found = true;
				}
			}
			
			//si no lo fue, lo agrega y devuelve false
			if (!found) {
				BufferedWriter bw = new BufferedWriter(new FileWriter(arch,true));
				bw.write(url);
				bw.newLine();
				bw.close();
				res = false;
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
}
