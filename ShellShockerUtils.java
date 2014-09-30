import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class ShellShockerUtils {
	
	private static HostnameVerifier hostnameverifier;
	private static TrustManager[] trustmanager;
	
	public static void trustAllHostnames() {
		
		hostnameverifier = new FakeHostnameVerifier();
		HttpsURLConnection.setDefaultHostnameVerifier(hostnameverifier);
	}
	
	public static void trustAllHttpsCertificates() {
		SSLContext context;
		 
		if (trustmanager == null) {
			trustmanager = new TrustManager[] { new FakeX509TrustManager() };
		} 
		
		try {
			context = SSLContext.getInstance("SSL");
			context.init(null, trustmanager, new SecureRandom());
		} catch (GeneralSecurityException e) {
			throw new IllegalStateException(e.getMessage());
		} 
		
		HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
		
	}
	
	
	public static class FakeHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
	
	public static class FakeX509TrustManager implements X509TrustManager {
		private static final X509Certificate[] acceptedissuers = new X509Certificate[] {};
	
		public void checkClientTrusted(X509Certificate[] chain, String authType) {
		}
	
		public void checkServerTrusted(X509Certificate[] chain, String authType) {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return (acceptedissuers);
		}
	}
}
