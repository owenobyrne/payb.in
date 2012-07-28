package in.payb.api.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TransactionList {
	private String orderId;
	private String guid;
	private String amount;
	private String currency;
	private String cardholderName;
	private String cardType;
	private String custNum;
	private String varRef;
	private String prodId;
	private String transactionDate;
	
	public TransactionList() {
		
	}
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCardholderName() {
		return cardholderName;
	}
	public void setCardholderName(String cardholderName) {
		this.cardholderName = cardholderName;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCustNum() {
		return custNum;
	}
	public void setCustNum(String custNum) {
		this.custNum = custNum;
	}
	public String getVarRef() {
		return varRef;
	}
	public void setVarRef(String varRef) {
		this.varRef = varRef;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	
}
