import java.io.File;
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
	private LinkedBlockingQueue<String[]> busMonitor;
	MonitorMessager monitor;

	public Downloader(
		String path, LinkedBlockingQueue<String> colaEntrante,
		LinkedBlockingQueue<String[]> colaEventos) {
			colaRecursos = colaEntrante;
			busMonitor = colaEventos;
			savePath = path + File.separator;
			monitor = new MonitorMessager(busMonitor);
		}

	@Override
	public void run() {

		String recString = "";
		while (true) {
			// leer cosa a descargar de la cola
			try {
				recString = colaRecursos.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
				continue;
			}
			String fileName = recString.replaceAll("/", "_");

			// avisar a monitor descargando
			monitor.agregarDescargandoRec();

			// guardar en archivo
			URL website;
			try {
				website = new URL(recString);
				ReadableByteChannel rbc = 
					Channels.newChannel(website.openStream());
	
				MediaFinder media = new MediaFinder(
					null,
					"\"" + recString + "\""
					);
				String path = savePath;
				if (media.isImage()) {
					path = savePath+File.separator+"images"+File.separator;
					if (saveFile(path, fileName, rbc)) {
						monitor.agregarImagenDescargada();
					}
				} else if (media.isAudio()) {
					path = savePath+File.separator+"audio"+File.separator;
					if (saveFile(path, fileName, rbc)) {
						monitor.agregarAudioDescargado();
					}
				} else if (media.isJS()) {
					path = savePath+File.separator+"js"+File.separator;
					if (saveFile(path, fileName, rbc)) {
						monitor.agregarJSDescargado();
					}
				} else if (media.isCSS()) {
					path = savePath+File.separator+"css"+File.separator;
					if (saveFile(path, fileName, rbc)) {
						monitor.agregarCSSDescargado();
					}
				}
			} catch (MalformedURLException e) {
			} catch (IOException e) {
			}
			// avisar monitor
			monitor.restarDescargandoRec();
		}
	}

	private boolean saveFile(
		String path,
		String fileName,
		ReadableByteChannel rbc
		) throws IOException {
		
		File arch = new File(path, fileName);
		try {
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			//boolean exist = arch.createNewFile();
			FileOutputStream fos = new FileOutputStream(arch);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
			return true;
		} catch (IOException e){
			arch.delete();
			return false;
		}
		}
}
