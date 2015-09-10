import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkFinder extends ResourceFinder {

	public LinkFinder(String url, String html) {
		super(url, html);
	}

	@Override
	public LinkedList<String> find() {
		LinkedList<String> found = new LinkedList<String>();
		Pattern pattern = Pattern
			.compile("((href)|(src))\\s*=\\s*[\"|']([\\w:/\\.?=\\-]*)[\"|']");
		Matcher matcher = pattern.matcher(html);
		while (matcher.find()) {
			// agregar a lista de enconctrados
			String cleaned = cleanURL(url, matcher.group(4));
			found.add(cleaned);
		}
		return found;
	}

}
