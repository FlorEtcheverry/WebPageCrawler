import java.util.concurrent.LinkedBlockingQueue;


public class MonitorMessager {
	
	private LinkedBlockingQueue<String[]> busMonitor;

	public MonitorMessager(LinkedBlockingQueue<String[]> colaEventos) {
		busMonitor = colaEventos;
	}
	
	private void enviarMsj(String titulo, String numero){
		String[] msj = new String[2];
		msj[0] = titulo;
		msj[1] = numero;
		try {
			busMonitor.put(msj);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void agregarLinkAnalizado(){
		enviarMsj("URLs Analizadas","1");
	}
	
	public void agregarObteniendoHTML(){
		enviarMsj("Obteniendo HTML","1");
	}
	
	public void restarObtenienoHTML(){
		enviarMsj("Obteniendo HTML","-1");
	}
	
	public void agregarAnalizandoHTML(){
		enviarMsj("Analizando HTML","1");
	}
	
	public void restarAnalizandoHTML(){
		enviarMsj("Analizando HTML","-1");
	}
	
	public void agregarDescargandoRec(){
		enviarMsj("Descargando Recurso","1");
	}
	
	public void restarDescargandoRec(){
		enviarMsj("Descargando Recurso","-1");
	}
	
	public void agregarImagenDescargada(){
		enviarMsj("Imágenes Descargadas","1");
	}
	
	public void agregarAudioDescargado(){
		enviarMsj("Audios Descargados","1");
	}
	
	public void agregarJSDescargado(){
		enviarMsj("JS Descargados","1");
	}
	
	public void agregarCSSDescargado(){
		enviarMsj("CSS Descargados","1");
	}

}
