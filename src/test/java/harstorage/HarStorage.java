package harstorage;

import org.browsermob.core.har.Har;

/**
 * https://code.google.com/p/harstorage/wiki/JavaTutorial
 */

public class HarStorage {
	private HttpRequest httpRequest;

	public HarStorage(String host, String port) {
		httpRequest = new HttpRequest();
		httpRequest.setRequestHostname(host);
		httpRequest.setRequestPort(port);
	}

	public String save(String har) throws Exception {
		httpRequest.setRequestMethod("POST");
		httpRequest.setRequestPath("/results/upload");

		return httpRequest.send(har);
	}

}
