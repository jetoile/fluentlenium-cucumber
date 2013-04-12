package harstorage;


import org.browsermob.core.har.Har;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * https://code.google.com/p/harstorage/wiki/JavaTutorial
 */

public class HttpRequest {
	public String host;
	public String port;
	public String path;
	public String method;

	private String response;

	public void setRequestHostname(String host) {
		this.host = host;
	}

	public void setRequestPort(String port) {
		this.port = port;
	}

	public void setRequestPath(String path) {
		this.path = path;
	}

	public void setRequestMethod(String method) {
		this.method = method;
	}

	public String send() throws Exception {
		// Connection
		URL url = new URL("http://" + host + ":" + port + path);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(method);

		// Get the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		response = "";
		while ((line = rd.readLine()) != null) {
			response += line;
		}

		rd.close();
		connection.disconnect();
		return response;
	}


	public String send(String data) throws Exception {
		// Connection
		URL url = new URL("http://" + host + ":" + port + path);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(method);
		connection.setDoOutput(true);

		// HTTP headers
		connection.setRequestProperty("Automated", "true");

		// Send data to server
		OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
		data = URLEncoder.encode(data, "utf-8");
		wr.write("file=" + data);
		wr.flush();
		wr.close();

		// Get the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		response = "";
		while ((line = rd.readLine()) != null) {
			response += line;
		}

		rd.close();
		connection.disconnect();

		return response;
	}

}

