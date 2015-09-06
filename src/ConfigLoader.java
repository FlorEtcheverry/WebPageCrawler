import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ConfigLoader {
	
	private int cantDetectoresRep;
	private int cantGetters;
	private int cantParsers;
	private int cantDownloaders;
	
	public ConfigLoader(){
		leerArch();
	}
	
	private void leerArch(){
		try {
			List<String> lines = Files.readAllLines(Paths.get("/home/knoppix/wc/config.ini"),Charset.defaultCharset());
			String detectores = lines.get(0);
			String getters = lines.get(1);
			String parsers = lines.get(2);
			String downloaders = lines.get(3);
			cantDetectoresRep = Integer.parseInt(detectores);
			cantGetters = Integer.parseInt(getters);
			cantParsers = Integer.parseInt(parsers);
			cantDownloaders = Integer.parseInt(downloaders);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("No se pudo cargar el archivo de configuracion.");
			System.exit(1);
		}
	}
	
	public int getCantGetters(){
		return cantGetters;
	}
	
	public int getCantDetectoresRep(){
		return cantDetectoresRep;
	}
	
	public int getCantParsers(){
		return cantParsers;
	}
	
	public int getCantDownloaders(){
		return cantDownloaders;
	}

}
