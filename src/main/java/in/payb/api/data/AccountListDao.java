package in.payb.api.data;

import in.payb.api.CassandraConnection;
import in.payb.api.model.Account;
import in.payb.api.utils.RealexHttpConnectionManager;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.MutationBatch;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.Column;
import com.netflix.astyanax.model.ColumnList;

@Component
public class AccountListDao {
	private final static String URL = "/Transactions/index-left-s.asp";
	
    @Autowired
    RealexHttpConnectionManager realexHttpConnectionManager;

  
	public ArrayList<Account> retrieve() {
		ArrayList<Account> al = new ArrayList<Account>();
		Pattern p;
		Matcher m;
		String page;
	
		page = realexHttpConnectionManager.getFromRealControl(URL);
		
		p = Pattern.compile("account_id=(\\d+)\" target=\"main\">([^<]+)</a>");

		m = p.matcher(page);
		while (m.find()) { 
			Account a = new Account(Integer.parseInt(m.group(1)), m.group(2));
			al.add(a);
		}
		
		return al;
	}
}
