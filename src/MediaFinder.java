import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MediaFinder extends ResourceFinder {

	public MediaFinder(String url, String html) {
		super(url, html);
	}

	@Override
	public LinkedList<String> find() {
		LinkedList<String> found = new LinkedList<String>();
		Matcher matcherImg = matchImage();
		while (matcherImg.find()) {
			String cleaned = cleanURL(url, matcherImg.group(1));
			found.add(cleaned);
		}
		Matcher matcherAudio = matchAudio();
		while (matcherAudio.find()) {
			String cleaned = cleanURL(url, matcherAudio.group(1));
			found.add(cleaned);
		}
		Matcher matcherJS = matchJS();
		while (matcherJS.find()) {
			String cleaned = cleanURL(url, matcherJS.group(1));
			found.add(cleaned);
		}
		Matcher matcherCSS = matchCSS();
		while (matcherCSS.find()) {
			String cleaned = cleanURL(url, matcherCSS.group(1));
			found.add(cleaned);
		}
		return found;
	}

	private Matcher matchImage() {
		Pattern pattern = Pattern
			.compile(
		"[\"|']([\\w:/\\.?=\\-]*\\.(jpg|png|gif|bmp))\\??[a-zA-Z\\-0-9=]*[\"|']"
				);
		return pattern.matcher(html);
	}

	private Matcher matchAudio() {
		Pattern pattern = Pattern
			.compile(
				"[\"|']([\\w:/\\.?=\\-]*\\.(mp3|ogg))\\??[a-zA-Z\\-0-9=]*[\"|']"
				);
		return pattern.matcher(html);
	}

	private Matcher matchJS() {
		Pattern pattern = Pattern
			.compile(
				"[\"|']([\\w:/\\.?=\\-]*\\.(js))\\??[a-zA-Z\\-0-9=]*[\"|']"
				);
		return pattern.matcher(html);
	}

	private Matcher matchCSS() {
		Pattern pattern = Pattern
			.compile(
				"[\"|']([\\w:/\\.?=\\-]*\\.(css))\\??[a-zA-Z\\-0-9=]*[\"|']"
				);
		return pattern.matcher(html);
	}

	public boolean isImage() {
		return matchImage().find();
	}

	public boolean isAudio() {
		return matchAudio().find();
	}

	public boolean isJS() {
		return matchJS().find();
	}

	public Boolean isCSS() {
		return matchCSS().find();
	}

}
