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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class RealexHttpConnectionManager {
	private final static String RCBASE = "https://emerchant.payandshop.com";

	
	@Autowired
	private HttpConnectionManager connectionManager;

	public RealexHttpConnectionManager() {
		super();
	}

	public void postToRealex(String xml) {
		HttpClient client = new HttpClient(connectionManager);

		// and then from inside some thread executing a method
		PostMethod post = new PostMethod(
				"https://epage.payandshop.com/epage-remote.cgi");
		try {
			post.setRequestEntity(new StringRequestEntity(xml, "text/xml",
					"ISO-8859-1"));
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

	public String getFromRealControl(String url) {
		HttpClient client = new HttpClient(connectionManager);
		String p = null;

		// pull the credentials out of the session
		UsernamePasswordAuthenticationToken upat = 
				(UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

		Credentials defaultcreds = new UsernamePasswordCredentials(
				upat.getPrincipal().toString(),
				upat.getCredentials().toString());
		
		client.getState().setCredentials(AuthScope.ANY, defaultcreds);

		System.out.println("Getting " + url);
		// and then from inside some thread executing a method
		GetMethod g = new GetMethod(RCBASE + url);
		try {
			client.executeMethod(g);
			p = g.getResponseBodyAsString();
			//System.out.println(p);
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

	public boolean checkLoginToRealControl(Authentication upat) {
		HttpClient client = new HttpClient(connectionManager);
		boolean loggedIn = false;

		Credentials defaultcreds = new UsernamePasswordCredentials(
				upat.getPrincipal().toString(),
				upat.getCredentials().toString());
		
		client.getState().setCredentials(AuthScope.ANY, defaultcreds);

		System.out.println("Checking login to RealControl... " + RCBASE);
		// and then from inside some thread executing a method
		GetMethod g = new GetMethod(RCBASE);
		try {
			client.executeMethod(g);
			
			if (200 == g.getStatusCode()) { 
				loggedIn = true;
				System.out.println("Logged in successfully!");
			} else {
				System.out.println("Incorrect Login details");
			}
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
		return loggedIn;
	}

}
