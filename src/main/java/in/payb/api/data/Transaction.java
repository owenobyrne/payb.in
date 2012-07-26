package in.payb.api.data;

import in.payb.api.utils.RealexHttpConnectionManager;

import java.util.Formatter;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Transaction {
	private final static String BASE = "https://emerchant.payandshop.com";
	private final static String URL = BASE + "/Transactions/show-trans-full.asp?orderid=%s&uuid=%s";
	
    @Autowired
    RealexHttpConnectionManager realexHttpConnectionManager;
	
	public void retrieve(String orderid, String uuid) {
		Formatter f = new Formatter(new StringBuilder(), Locale.UK);
		String url = f.format(URL, orderid, uuid).toString();
		realexHttpConnectionManager.getFromServer(url);
	       
	}
}
