import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.LinkedBlockingQueue;


public class Downloader implements Runnable {
	
	String savePath;
	private LinkedBlockingQueue<String> colaRecursos;
	private LinkedBlockingQueue<String> busMonitor;
	
	public Downloader(LinkedBlockingQueue<String> colaEntrante,LinkedBlockingQueue<String> colaEventos){
		colaRecursos = colaEntrante;
		busMonitor = colaEventos;
		savePath = "/home/downloaded/"; //TODO String path = "C:"+File.separator+"hello"+File.separator+"hi.txt";
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		//leer cosa a descargar de la cola
		String recString;
		try {
			recString = colaRecursos.take();
			String filePath = "";
		
			//guardar en archivo
			URL website;
			try {
				website = new URL(recString);
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				
				FileOutputStream fos = new FileOutputStream(new File(filePath));
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				fos.close();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
