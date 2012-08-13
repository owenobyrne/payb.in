package in.payb.api.data;

import in.payb.api.model.Transaction;
import in.payb.api.utils.RealexHttpConnectionManager;

import java.util.Formatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionDao {
	private final static String URL = "/Transactions/show-trans-full.asp?orderid=%s&uuid=%s";
	
    @Autowired
    RealexHttpConnectionManager realexHttpConnectionManager;
	
	public Transaction retrieve(String orderid, String uuid) {
		Formatter f = new Formatter(new StringBuilder(), Locale.UK);
		Transaction t = new Transaction();
		Pattern p;
		Matcher m;
		String page;
		
		String url = f.format(URL, orderid, uuid).toString();
		page = realexHttpConnectionManager.getFromRealControl(url);
		
		t.setOrderId(orderid);
		
		p = Pattern.compile("<b>Result:&nbsp;</b></td>\\s*<td bgcolor=\"#bbbbbb\">&nbsp;(\\d+)</td>");
		m = p.matcher(page);
		if (m.find()) { 
			t.setResult(m.group(1));
			 System.out.println("Result was " + m.group(1));
		}

		p = Pattern.compile("<b>Authcode:&nbsp;</b></td>\\s*<td bgcolor=\"#bbbbbb\">&nbsp;([^<]+)</td>");
		m = p.matcher(page);
		if (m.find()) { 
			t.setAuthcode(m.group(1));
			 System.out.println("Authcode was " + m.group(1));
		}

		p = Pattern.compile("<b>Message:&nbsp;</b></td>\\s*<td bgcolor=\"#bbbbbb\">&nbsp;([^<]+)</td>");
		m = p.matcher(page);
		if (m.find()) { 
			t.setMessage(m.group(1));
			 System.out.println("Message was " + m.group(1));
		}

		p = Pattern.compile("<b>Pas Ref:&nbsp;</b></td>\\s*<td bgcolor=\"#bbbbbb\">&nbsp;([^<]+)</td>");
		m = p.matcher(page);
		if (m.find()) { 
			t.setPasref(m.group(1));
			 System.out.println("Pas Ref was " + m.group(1));
		}
		
		p = Pattern.compile("<b>Transaction ID:&nbsp;</b></td>\\s*<td bgcolor=\"#bbbbbb\">&nbsp;([^<]+)</a></td>");
		m = p.matcher(page);
		if (m.find()) { 
			t.setTransactionId(m.group(1));
			 System.out.println("Transaction ID was " + m.group(1));
		}
		
		p = Pattern.compile("<b>In Batch:&nbsp;</b></td>\\s*<td bgcolor=\"#bbbbbb\">&nbsp;<a href=\"showbatch.asp.*?(\\d+)</a></td>");
		m = p.matcher(page);
		if (m.find()) { 
			try {
				System.out.println("Batch was " + m.group(1));
				t.setBatchId(Integer.parseInt(m.group(1)));
			 
			} catch (NumberFormatException nfe) {
				System.out.println("Can't parse batchid " + m.group(1));
			}
		}

		p = Pattern.compile("<b>Security Code Result:&nbsp;</b></td>\\s*<td bgcolor=\"#bbbbbb\">&nbsp;([^<]+)</td>");
		m = p.matcher(page);
		if (m.find()) {
			t.setCvvResult(m.group(1));
			 System.out.println("CVV Result was " + m.group(1));
		}
		
		p = Pattern.compile("<b>AVS Postcode Result:&nbsp;</b></td>\\s*<td bgcolor=\"#bbbbbb\">&nbsp;([^<]+)</td>");
		m = p.matcher(page);
		if (m.find()) { 
			t.setAvsPostcodeResult(m.group(1));
			 System.out.println("AVS Postcode result was " + m.group(1));
		}
		
		p = Pattern.compile("<b>AVS Address Result:&nbsp;</b></td>\\s*<td bgcolor=\"#bbbbbb\">&nbsp;([^<]+)</td>");
		m = p.matcher(page);
		if (m.find()) { 
			t.setAvsAddressResult(m.group(1));
			 System.out.println("AVS Address result was " + m.group(1));
		}
		
		p = Pattern.compile("<b>Cardholder Name:&nbsp;</b></td>\\s*<td bgcolor=\"#bbbbbb\">&nbsp;<a href=\"advancedsearch.asp\\?cardname=([^\"]*)\">");
		m = p.matcher(page);
		if (m.find()) { 
			t.setCardholderName(m.group(1));
			 System.out.println("Card holder was " + m.group(1));
		}
		
		p = Pattern.compile("/images/(\\S*).gif\" width=\"28\" height=\"19\" vspace=\"0\" hspace=\"0\" border=\"0\" align=\"absmiddle\"></td>\\s*<td bgcolor=\"#bbbbbb\">&nbsp;<a href=\"advancedsearch.asp\\?cardnumref=(\\d*)\">\\s*(\\S*)\\s*</a>");
		m = p.matcher(page);
		if (m.find()) { 
			t.setCardType(m.group(1));
			t.setCardRefId(m.group(2));
			t.setCardNumber(m.group(3));
			
			System.out.println("Card Type was " + m.group(1));
			System.out.println("Card Ref ID was " + m.group(2));
			System.out.println("Card Num was " + m.group(3));			 
		}
		
		p = Pattern.compile("<b>Card Issuer:&nbsp;</b></td>\\s*<td bgcolor=\"#bbbbbb\">&nbsp;([^<]+)</td>");
		m = p.matcher(page);
		if (m.find()) {
			t.setCardIssuer(m.group(1));
			 System.out.println("Card Issuer was " + m.group(1));
		}
		
		p = Pattern.compile("<b>Card Issuer Country:&nbsp;</b></td>\\s*<td bgcolor=\"#bbbbbb\">&nbsp;(.*) \\((\\S*)\\)</td>");
		m = p.matcher(page);
		if (m.find()) { 
			t.setCardIssuerCountryName(m.group(1));
			t.setCardIssuerCountryCode(m.group(2));
			
			 System.out.println("Card Issuer Country name was " + m.group(1));
			 System.out.println("Card Issuer Country code was " + m.group(2));
		}
		
		p = Pattern.compile("<b>Transaction Originating IP:&nbsp;</b></td>\\s*<td bgcolor=\"#bbbbbb\">&nbsp;<a href=\"advancedsearch.asp\\?ip=([^\"]+)\">");
		m = p.matcher(page);
		if (m.find()) {
			t.setTransactionIP(m.group(1));
			 System.out.println("Transaction IP was " + m.group(1));
		}
		
		p = Pattern.compile("<b>Customer IP Address:&nbsp;</b></td>\\s*<td bgcolor=\"#bbbbbb\">&nbsp;<a href=\"advancedsearch.asp\\?customerip=([^\"]*)\">");
		m = p.matcher(page);
		if (m.find()) { 
			t.setCustomerIP(m.group(1));
			 System.out.println("Customer IP was " + m.group(1));
		}
		
		p = Pattern.compile("<tr onClick=location.href='javascript:opentrans\\(\"(\\S*)\"\\)' onmouseover=\"colorBack\\(this,'white'\\)\" onmouseout=\"clearBack\\(this\\);\">\\s*" +  
              "<td class=\"listCell\">&raquo;</td>\\s*<td align=\"right\" class=\"listCell\"><font color=black>Click [^<]*</font>&nbsp;&nbsp;\\s*</td>\\s*" + 
              "<td align=\"right\" class=\"listCell\"><font color=black>([^&]*)&nbsp;&nbsp;</font>\\s*</td>\\s*" + 
              "<td align=\"right\" class=\"listCell\"><font color=black>([^&]*)&nbsp;&nbsp;</font> </td>\\s*" + 
			  "<td align=\"right\" class=\"listCell\"><font color=black>\\s*(\\S*)&nbsp;&nbsp; </font></td>\\s*" + 
			  "<td align=\"right\" class=\"listCell\"><font color=black>(\\d\\d)\\.(\\d\\d)\\.(\\d\\d\\d\\d)&nbsp;&nbsp;(\\d\\d):(\\d\\d):(\\d\\d)&nbsp;&nbsp;</font>\\s*" +  
			  "</td>\\s*<td align=\"right\" class=\"listCell\"><font color=black>\\s*&nbsp;&nbsp;</font>\\s*</td>\\s*</tr>\\s*<tr id=\\S* style=\"display: none;\">\\s*" + 
			  "<td colspan=6>\\s*<table width=\"98%\" border=\"0\" cellspacing=\"1\" cellpadding=\"0\">\\s*<tr>\\s*<td width=\"50%\" valign=\"top\">\\s*<center>\\s*"  +
			  "<table width=\"95%\" border=\"0\" cellspacing=\"1\" cellpadding=\"0\">\\s*<tr>\\s*<td align=\"right\" width=\"23%\"><b>.*? Result:&nbsp;</b></td>\\s*" + 
			  "<td bgcolor=\"#bbbbbb\">[^<]*</td>\\s*<td bgcolor=\"#bbbbbb\" width=\"22\"> </td>\\s*</tr>\\s*<tr>\\s*<td align=\"right\" width=\"23%\"><b>Customer Number:&nbsp;</b></td>\\s*" + 
			  "<td bgcolor=\"#bbbbbb\">&nbsp;<a href=\"advancedsearch.asp\\?cust_no=[^\"]*\">([^<]*)</a>\\s*&nbsp;\\s*</td>\\s*" + 
			  "<td bgcolor=\"#bbbbbb\" width=\"22\"><a href=\"advancedsearch.asp\\?cust_no=[^\"]*\">" + 
			  "<img src=\"/images/searchon.gif\" width=\"22\" height=\"19\" vspace=\"0\" hspace=\"0\" border=\"0\" alt=\"show more with this customer number\"></a></td>\\s*" + 
			  "</tr>\\s*<tr>\\s*<td align=\"right\"><b>Product ID:&nbsp;</b></td>\\s*<td bgcolor=\"#bbbbbb\">&nbsp;<a href=\"advancedsearch.asp\\?prod_id=[^\"]*\">([^<]*)</a>\\s*" + 
			  "&nbsp;\\s*</td>\\s*<td bgcolor=\"#bbbbbb\"><a href=\"advancedsearch.asp\\?prod_id=[^\"]*\">" + 
			  "<img src=\"/images/searchon.gif\" width=\"22\" height=\"19\" vspace=\"0\" hspace=\"0\" border=\"0\" alt=\"show more with this product id\"></a></td>\\s*" + 
			  "</tr>\\s*<tr>\\s*<td align=\"right\"><b>Variable Reference:&nbsp;</b></td>\\s*" + 
			  "<td bgcolor=\"#bbbbbb\">&nbsp;<a href=\"advancedsearch.asp\\?var_ref=[^\"]*\">([^<]*)</a>\\s*&nbsp;\\s*</td>\\s*" + 
			  "<td bgcolor=\"#bbbbbb\"><a href=\"advancedsearch.asp\\?var_ref=[^\"]*\">" + 
			  "<img src=\"/images/searchon.gif\" width=\"22\" height=\"19\" vspace=\"0\" hspace=\"0\" border=\"0\" alt=\"show more with this reference\"></a></td>\\s*" + 
			  "</tr>\\s*<tr>\\s*<td align=\"right\" rowspan=2><b>Comments:&nbsp;</b></td>\\s*<td bgcolor=\"#bbbbbb\">&nbsp;</td>\\s*" + 
			  "<form action=editcomments-new.cgi name=manualcomment1form method=post onSubmit=\"return getComment\\('\\S*comment1form'\\)\">\\s*" + 
			  "<td width=22 bgcolor=\"#bbbbbb\"><input type=image src=/images/editcomment.gif width=21 height=19 border=0 vspace=0 hspace=0 alt=\"edit this comment\"></td>\\s*" + 
			  "<input type=hidden name=\"GUID\" value=\"(\\S+)\">");

		m = p.matcher(page);
		if (m.find()) { 
			// type, account, amount, currency, day, month, year, hour, min, sec, custnum, prodid, varref, guid
			t.setType(m.group(1));
			t.setAccount(m.group(2));
			t.setAmount(m.group(3));
			t.setCurrency(m.group(4));
			t.setTransactionDate(m.group(7) + m.group(6) + m.group(5) + m.group(8) + m.group(9) + m.group(10));
			t.setCustNum(m.group(11));
			t.setProdId(m.group(12));
			t.setVarRef(m.group(13));
			t.setGuid(m.group(14));
			
			System.out.println("Type was " + m.group(1));
			System.out.println("Account was " + m.group(2));
			System.out.println("Amount was " + m.group(3) + m.group(4));
			System.out.println("Date was " + m.group(7) + m.group(6) + m.group(5) + m.group(8) + m.group(9) + m.group(10));
			System.out.println("Cust num was " + m.group(11));
			System.out.println("Prod ID was " + m.group(12));
			System.out.println("Var Ref was " + m.group(13));
			System.out.println("GUID was " + m.group(14));
		}
			
		return t;
	}
}
