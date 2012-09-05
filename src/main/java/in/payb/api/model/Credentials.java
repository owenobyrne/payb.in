package in.payb.api.model;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.codec.Hex;

@XmlRootElement
public class Credentials {
	private String credentialsBlob;

	// No-arg constructor for XML Binding
	public Credentials() {
		// nothing
	}
	
	public Credentials(String password, String sharedsecret,
			String refundpassword) {
		credentialsBlob = password + (char) 1 + sharedsecret + (char) 1
				+ refundpassword;
	}

	public String getCredentialBlob() {
		return credentialsBlob;
	}

	public void setCredentialBlob(String credentialBlob) {
		this.credentialsBlob = credentialBlob;
	}
	
	public String getPassword(String key) {
		return getField(0, key);
	}
	
	public String getSharedSecret(String key) {
		return getField(1, key);
	}
	
	public String getRefundPassword(String key) {
		return getField(2, key);
	}
	
	public String getField(int fieldNum, String key) {

		if (!credentialsBlob.startsWith("ENCRYPTED:")) {
			return (credentialsBlob.split("" + (char) 1))[fieldNum];
		} else {

			byte[] KeyData = Hex.decode(key);
			byte[] decrypted = null;
			SecretKeySpec KS = new SecretKeySpec(KeyData, "Blowfish");
			Cipher cipher;
			try {
				cipher = Cipher.getInstance("Blowfish");
				cipher.init(Cipher.DECRYPT_MODE, KS);
				byte[] tmp = Base64.decodeBase64(credentialsBlob.replaceFirst(
						"ENCRYPTED:", ""));
				decrypted = cipher.doFinal(tmp);
				return (new String(decrypted).split("" + (char) 1))[fieldNum];

			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";
	}

	public void encryptCredentialsBlob(String key) {
		byte[] KeyData = Hex.decode(key);
		byte[] encrypted = null;
		SecretKeySpec KS = new SecretKeySpec(KeyData, "Blowfish");
		Cipher cipher;
		
		System.out.println("Clear credentials: " + credentialsBlob);
		System.out.println("Key: " + key);
		
		try {
			cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, KS);
			encrypted = cipher.doFinal(credentialsBlob.getBytes());
			credentialsBlob = "ENCRYPTED:"
					+ Base64.encodeBase64URLSafeString(encrypted);

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Encrypted credentials: " + credentialsBlob);
	}

}
