import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;


public class Monitor implements Runnable {
	
	private String fileName;
	LinkedBlockingQueue<String[]> colaStatus;
	private int cantUrls;
	private HashMap<String,Integer> cantMedia;
	private int cantGetting;
	private int cantParsing;
	private int cantDownloading;
	
	public Monitor(LinkedBlockingQueue<String[]> cola){
		fileName = "wc_status";
		colaStatus = cola;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			BufferedWriter bw;
			File arch = new File(fileName);
			if (!arch.exists()) {
				arch.createNewFile();
			}
			bw = new BufferedWriter(new FileWriter(arch,true));
		

			while (true){
				
				//leer data de la cola
				String[] msg = colaStatus.take();
				
			}
		

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
