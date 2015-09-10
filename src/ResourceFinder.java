import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class ResourceFinder {
	
	protected String url;
	protected String html;
	
	public ResourceFinder(String url, String html){
		this.url = url;
		this.html = html;
	}
	
	public LinkedList<String> find(){
		return new LinkedList<String>();
	}
	
	//Deja la url con protocolo. (Para links relativos)
	protected String cleanURL(String urlActual,String resto){
		String urlFinal = urlActual;
		//Pattern p = Pattern.compile("(http(s?):\\/\\/)[^\\s]*");
		Pattern p = Pattern.compile("(https?):\\/\\/[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
		Matcher m = p.matcher(resto);
		if (m.find()){
			return resto;
		}
		p = Pattern.compile("^((.*))\\/[^\\/]+\\.[^\\/\\.]+$");
		m = p.matcher(urlActual);
		if (m.find() && !m.group(1).endsWith("/")){
			urlFinal = m.group(1);
		}
		if (urlFinal.endsWith("/") || resto.startsWith("/")) {
			return (urlFinal + resto);
		}
		return (urlFinal + "/" + resto);
	}

}
