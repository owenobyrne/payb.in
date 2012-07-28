package in.payb.api.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Transaction {
	private String orderId;
	private String result;
	private String authcode;
	private String message;
	private String pasref;
	private String transactionId;
	private int batchId;
	private String cvvResult;
	private String avsPostcodeResult;
	private String avsAddressResult;
	private String cardholderName;
	private String cardType;
	private String cardRefId;
	private String cardNumber;
	private String cardIssuer;
	private String cardIssuerCountryName;
	private String cardIssuerCountryCode;
	private String transactionIP;
	private String customerIP;
	private String type;
	private String account;
	private String amount;
	private String currency;
	private String transactionDate;
	private String custNum;
	private String prodId;
	private String varRef;
	private String guid;
	private boolean failed;
	
	public boolean isFailed() {
		return failed;
	}

	public void setFailed(boolean failed) {
		this.failed = failed;
	}

	public Transaction() {
		
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getAuthcode() {
		return authcode;
	}
	public void setAuthcode(String authcode) {
		this.authcode = authcode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPasref() {
		return pasref;
	}
	public void setPasref(String pasref) {
		this.pasref = pasref;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public int getBatchId() {
		return batchId;
	}
	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}
	public String getCvvResult() {
		return cvvResult;
	}
	public void setCvvResult(String cvvResult) {
		this.cvvResult = cvvResult;
	}
	public String getAvsPostcodeResult() {
		return avsPostcodeResult;
	}
	public void setAvsPostcodeResult(String avsPostcodeResult) {
		this.avsPostcodeResult = avsPostcodeResult;
	}
	public String getAvsAddressResult() {
		return avsAddressResult;
	}
	public void setAvsAddressResult(String avsAddressResult) {
		this.avsAddressResult = avsAddressResult;
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
	public String getCardRefId() {
		return cardRefId;
	}
	public void setCardRefId(String cardRefId) {
		this.cardRefId = cardRefId;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardIssuer() {
		return cardIssuer;
	}
	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}
	public String getCardIssuerCountryName() {
		return cardIssuerCountryName;
	}
	public void setCardIssuerCountryName(String cardIssuerCountryName) {
		this.cardIssuerCountryName = cardIssuerCountryName;
	}
	public String getCardIssuerCountryCode() {
		return cardIssuerCountryCode;
	}
	public void setCardIssuerCountryCode(String cardIssuerCountryCode) {
		this.cardIssuerCountryCode = cardIssuerCountryCode;
	}
	public String getTransactionIP() {
		return transactionIP;
	}
	public void setTransactionIP(String transactionIP) {
		this.transactionIP = transactionIP;
	}
	public String getCustomerIP() {
		return customerIP;
	}
	public void setCustomerIP(String customerIP) {
		this.customerIP = customerIP;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
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
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getCustNum() {
		return custNum;
	}
	public void setCustNum(String custNum) {
		this.custNum = custNum;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getVarRef() {
		return varRef;
	}
	public void setVarRef(String varRef) {
		this.varRef = varRef;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}

}
