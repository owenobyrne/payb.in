package in.payb.api.resource;

import in.payb.api.RealControlUserDetails;
import in.payb.api.model.Credentials;

import java.security.SecureRandom;
import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.codec.binary.Hex;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

 
@Component
@Path("/credentials")
public class CredentialsResource {
     
    // The @Context annotation allows us to have certain contextual objects
    // injected into this class.
    // UriInfo object allows us to get URI information (no kidding).
    @Context
    UriInfo uriInfo;
 
    // Another "injected" object. This allows us to use the information that's
    // part of any incoming request.
    // We could, for example, get header information, or the requestor's address.
    @Context
    Request request;
    
    private SecureRandom random = new SecureRandom();
    
    // Basic "is the service running" test
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Credentials getEncryptedCredentialsBlob() {
    	// retrieve the credentials from the session authentication
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UsernamePasswordAuthenticationToken upat = (UsernamePasswordAuthenticationToken)authentication;
		RealControlUserDetails rcud = (RealControlUserDetails)upat.getPrincipal();
		
		// create a new credentials object
		Credentials cred = new Credentials(rcud.getPassword(), rcud.getSharedsecret(), rcud.getRefundpassword());
		// create a new random key
		byte[] keyBytes = new byte[56];
		new Random().nextBytes(keyBytes);
		String key = new String(Hex.encodeHexString(keyBytes));
		
		// replace the credentials in the authentication object with the key 
		cred.encryptCredentialsBlob(key);
		rcud.setKey(key);
		rcud.scrubSensitiveDetails();
		
		// update the object in the session
		upat.setDetails(rcud);
		SecurityContextHolder.getContext().setAuthentication(upat);
		
        return cred;
    }
 

}