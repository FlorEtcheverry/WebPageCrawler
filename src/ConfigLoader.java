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
	private String downloadsPath;
	private String statsPath;
	
	public ConfigLoader(){
		leerArch();
		flush();
	}
	
	private void leerArch(){
		try {
			List<String> lines = Files.readAllLines(
				Paths.get("/home/knoppix/wc/config.ini"),
				Charset.defaultCharset()
				);
			cantDetectoresRep = Integer.parseInt(lines.get(0));
			cantGetters = Integer.parseInt(lines.get(1));
			cantParsers = Integer.parseInt(lines.get(2));
			cantDownloaders = Integer.parseInt(lines.get(3));
			downloadsPath = lines.get(4);
			statsPath = lines.get(5);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(
				"No se pudo cargar el archivo de configuracion.");
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
	
	public String getDownloadsPath(){
		return downloadsPath;
	}
	
	public String getStatsPath(){
		return statsPath;
	}
	
	private void flush(){
		//TODO borrar directorios y files creados
	}

}
