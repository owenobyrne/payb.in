package in.payb.api.data;

import in.payb.api.CassandraConnection;
import in.payb.api.model.Account;
import in.payb.api.model.Transaction;
import in.payb.api.utils.RealexHttpConnectionManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.MutationBatch;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.Column;
import com.netflix.astyanax.model.ColumnList;

@Component
public class TransactionListDao {
	private final static String BASE = "https://emerchant.payandshop.com";
	private final static String URL = BASE + "/Transactions/advancedsearch_improved.asp?selPage=%s&q=%s";
	
    @Autowired
    RealexHttpConnectionManager realexHttpConnectionManager;
	
    @Autowired
    AccountListDao accountListDao;
    
    @Autowired
    CassandraConnection cassandra;
    
	public ArrayList<Transaction> retrieve(String from, String to) {
		Formatter f = new Formatter(new StringBuilder(), Locale.UK);
		ArrayList<Transaction> tl = new ArrayList<Transaction>();
		Pattern p;
		Matcher m;
		String page = "";
		String url = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		if (from == null || to == null) {
			from = sdf.format(new Date());
			to = sdf.format(new Date());
		}
		String qs;
		
		Keyspace keyspace = cassandra.getKeyspace();
	    
		OperationResult<ColumnList<String>> result = null;
		try {
			result = keyspace.prepareQuery(CassandraConnection.CF_USER_INFO)
			    .getKey("ccentre")
			    .execute();
		} catch (ConnectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Lookup columns in response by name 
		ColumnList<String> columns = result.getResult();
		Column<String> c = columns.getColumnByName("accountList");
		
		if (c != null) {
			qs = c.getStringValue();
				
		} else {
			ArrayList<Account> al = accountListDao.retrieve();
			qs = "accounts#=#";
			for (Object a : al.toArray()) {
				qs += ((Account)a).getId() + ", ";
			}
			qs = qs.substring(0, qs.length() - 2); // chop the last ", "		
		
			MutationBatch mb = keyspace.prepareMutationBatch();

	    	mb.withRow(CassandraConnection.CF_USER_INFO, "ccentre")
	    	  .putColumn("accountList", qs, null);

	    	try {
	    	  OperationResult<Void> result1 = mb.execute();
	    	} catch (ConnectionException e) {
	    		e.printStackTrace();
	    	}

		}
				
		qs += "&pasfromDate#=#" + from + "&pastoDate#=#" + to + "&start#=#0";
		
		System.out.println(qs);
		String query = new String(Base64.encodeBase64(qs.getBytes()));
		try {
			url = f.format(URL, URLEncoder.encode(query, "UTF-8"), URLEncoder.encode(query, "UTF-8")).toString();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		page = realexHttpConnectionManager.getFromServer(url);
		
		p = Pattern.compile("<tr bgcolor=\"([^\"]*)\" onmouseover=\"colorBack\\(this,'white'\\)\" onmouseout=\"clearBack\\(this\\);\" onClick=\"location.href='show-trans.asp\\?orderid=([^&]+)&uuid=([0-9a-zA-Z]+)'\">\\s*" +
				"<td class=\"listCell\" align=\"right\">&raquo;</td>\\s*<td class=\"listCell\" align=\"right\">\\s*" + 
				"<font color=[^>]+>\\s*(<font color=[^>]+>\\s*<span title=\"[^\"]+\">)?\\s*[^<]+</font>(</a>\\s*</span>\\s*</font>)?\\s*&nbsp;\\s*</td>\\s*" + 
				"<td class=\"listCell\" align=\"right\"><font color=[^>]+>\\s*([^&]+)(&nbsp;&nbsp;)?</font>\\s*" + 
				"<font color=[^>]+>\\s*(...)&nbsp;&nbsp;\\s*</td>\\s*<td class=\"listCell\" align=\"right\">\\s*" + 	
				"<font color=[^>]+>\\s*<img src=\"/images/([^\\.]+).gif\" border=0\\s*width=28 height=19 vspace=0 hspace=0>" + 
				"</font></td>\\s*<td class=\"listCell\" align=\"right\"><font color=[^>]+>\\s*([^&]*)(&nbsp;)+</font>\\s*" + 
				"</td>\\s*<td class=\"listCell\" align=\"right\"><font color=[^>]+>\\s*([^&]*?)\\s*&nbsp;</font>\\s*" + 
				"</td>\\s*<td class=\"listCell\" align=\"right\"><font color=[^>]+>\\s*([^&]*?)\\s*&nbsp;</font>\\s*" +
				"</td>\\s*<td class=\"listCell\" align=\"right\"><font color=[^>]+>\\s*([^&]*?)\\s*&nbsp;</font>\\s*" +
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
			t.setCurrency(m.group(8));
			t.setCardType(m.group(9));
			t.setCardholderName(m.group(10));
			t.setCustNum(m.group(12));
			t.setVarRef(m.group(13));
			t.setProdId(m.group(14));
			t.setTransactionDate(m.group(17) + m.group(16) + m.group(15) + m.group(18) + m.group(19) + m.group(20));
			
			tl.add(t);
			/*
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
			*/
		}
		
		// two empties to ensure we get an array in the response json.
		tl.add(new Transaction());
		tl.add(new Transaction());
		return tl;
	}
}
