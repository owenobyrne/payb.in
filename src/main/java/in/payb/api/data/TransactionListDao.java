package in.payb.api.data;

import in.payb.api.model.Transaction;
import in.payb.api.utils.RealexHttpConnectionManager;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionListDao {
	private final static String BASE = "https://emerchant.payandshop.com";
	private final static String URL = BASE + "/Transactions/zoombatch.asp?delayedbatch=0&batchId=%s&pascurrency=%s&type=%s";
	
    @Autowired
    RealexHttpConnectionManager realexHttpConnectionManager;
	
	public ArrayList<Transaction> retrieve(String batchId) {
		Formatter f = new Formatter(new StringBuilder(), Locale.UK);
		ArrayList<Transaction> tl = new ArrayList<Transaction>();
		Pattern p;
		Matcher m;
		String page;
		
		String url = f.format(URL, batchId, "EUR", "VISA/MC").toString();
		page = realexHttpConnectionManager.getFromServer(url);
		
		p = Pattern.compile("<tr bgcolor=\"([^\"]*)\" onClick=\"location.href='show-trans.asp\\?delayedbatch=0&orderid=([^&]+)&uuid=([0-9a-zA-Z]+)'\" onmouseover=\"colorBack\\(this,'white'\\)\" onmouseout=\"clearBack\\(this\\);\">\\s*" +
				"<td class=\"listCell\" align=\"right\">&raquo;</td>\\s*<td class=\"listCell\" align=\"right\">\\s*" + 
				"<font color=[^>]+>\\s*(<span title=\"[^\"]+\">)?\\s*[^<]+</font>(</a>\\s*</span>)?\\s*&nbsp;</td>\\s*" + 
				"<td class=\"listCell\" align=\"right\"><font color=[^>]+>\\s*([^&]+)&nbsp;&nbsp;</font>\\s*" + 
				"<font color=[^>]+>\\s*(...)&nbsp;&nbsp;\\s*</td>\\s*<td class=\"listCell\" align=\"right\">\\s*" + 	
				"<font color=[^>]+><img src=\"/images/([^\\.]+).gif\" border=0 width=28 height=19 vspace=0 hspace=0>" + 
				"</font></td>\\s*<td class=\"listCell\" align=\"right\"><font color=[^>]+>\\s*([^&]*)&nbsp;&nbsp;</font>\\s*" + 
				"</td>\\s*<td class=\"listCell\" align=\"right\"><font color=[^>]+>\\s*&nbsp;\\s*([^<]*?)\\s*</font>\\s*" + 
				"</td>\\s*<td class=\"listCell\" align=\"right\"><font color=[^>]+>\\s*&nbsp;\\s*([^<]*?)\\s*</font>\\s*" +
				"</td>\\s*<td class=\"listCell\" align=\"right\"><font color=[^>]+>\\s*&nbsp;\\s*([^<]*?)\\s*</font>\\s*" +
				"</td>\\s*<td class=\"listCell\" align=\"right\"><font color=[^>]+>\\s*" + 
				"(\\d\\d)\\.(\\d\\d)\\.(\\d\\d\\d\\d)&nbsp;&nbsp;(\\d\\d):(\\d\\d):(\\d\\d)&nbsp;&nbsp;</font>\\s*" +
				"</td>\\s*</tr>");

		m = p.matcher(page);
		while (m.find()) { 
			Transaction t = new Transaction();
			if (m.group(1).equals("#cc9999")) {
				t.setFailed(true);
			} else {
				t.setFailed(false);
			}
			
			t.setOrderId(m.group(2));
			t.setGuid(m.group(3));
			t.setAmount(m.group(6));
			t.setCurrency(m.group(7));
			t.setCardType(m.group(8));
			t.setCardholderName(m.group(9));
			t.setCustNum(m.group(10));
			t.setVarRef(m.group(11));
			t.setProdId(m.group(12));
			t.setTransactionDate(m.group(15) + m.group(14) + m.group(13) + m.group(16) + m.group(17) + m.group(18));
			
			tl.add(t);
			
			 System.out.println("OID was " + m.group(1));
			 System.out.println("GUID was " + m.group(2));
			 System.out.println("Amount was " + m.group(3));
			 System.out.println("Currency was " + m.group(4));
			 System.out.println("Card type was " + m.group(5));
			 System.out.println("Cardholder name was " + m.group(6));
			 System.out.println("CustNum was " + m.group(7));
			 System.out.println("VarRef was " + m.group(8));
			 System.out.println("ProdId was " + m.group(9));
			 System.out.println("TransactionDate was " + m.group(12) + m.group(11) + m.group(10) + m.group(13) + m.group(14) + m.group(15));
		}
		
		return tl;
	}
}
