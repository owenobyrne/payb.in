package in.payb.api.utils;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
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
}
