package in.payb.api.utils;

import java.io.IOException;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RealexHttpConnectionManager {
	@Autowired
	private HttpConnectionManager connectionManager;

	public RealexHttpConnectionManager() {
		super();
	}

	public void postToRealex(String xml) {
		HttpClient client = new HttpClient(connectionManager);

		// and then from inside some thread executing a method
		PostMethod post = new PostMethod("https://epage.payandshop.com/epage-remote.cgi");
		try {
			post.setRequestEntity(new StringRequestEntity(xml, "text/xml", "ISO-8859-1"));
			client.executeMethod(post);
			System.out.println(post.getResponseBodyAsString());
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// be sure the connection is released back to the connection
			// manager
			post.releaseConnection();
		}

	}
	
	public String getFromServer(String url) {
		HttpClient client = new HttpClient(connectionManager);
		String p = null;
		
		Credentials defaultcreds = new UsernamePasswordCredentials("ccentre!owen", "OeOyn616");
		client.getState().setCredentials(AuthScope.ANY, defaultcreds);
		
		System.out.println("Getting " + url);
		// and then from inside some thread executing a method
		GetMethod g = new GetMethod(url);
		try {
			client.executeMethod(g);
			p = g.getResponseBodyAsString();
			System.out.println(p);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// be sure the connection is released back to the connection
			// manager
			g.releaseConnection();
		}
		return p;
	}
	
}
