package in.payb.api.mvc.filter;

import in.payb.api.RealControlUserDetails;

import java.beans.ConstructorProperties;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
public class PaybinAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
    @Autowired
    private AuthenticationManager authenticationManager;
    
    // We'll use this to populate some environment details into the authentication - ip, sessionid etc.
    private AuthenticationDetailsSource<HttpServletRequest, ?> ads = new WebAuthenticationDetailsSource();
    
    // This is needed so that the value can be passed in from the bean definition.
    @ConstructorProperties(value = { "filterProcessesUrl" })
	public PaybinAuthenticationFilter(String filterProcessesUrl) {
		super(filterProcessesUrl);
	}

	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException,
			IOException, ServletException {
		
		Authentication authentication = null;
		
		// initial implementation - hard code the credentials - should get them from request.
		RealControlUserDetails rcud = new RealControlUserDetails("ccentre", "owen", "OeOyn616");
		UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(rcud, rcud.getPassword());
		upat.setDetails(ads.buildDetails(request));

		try {
			// try to authenticate these credentials using the authenticationManager. This is a basic
			// manager that will just iterate through a list of authenticationproviders as specified
			// in security-config.xml.
			// IMPORTANT - set erase-credentials to false on the manager in the security-config.xml
			// or else the password will be removed by the call to authenticate. 
			authentication = authenticationManager.authenticate(upat);
			
			// Set the authentication details in the ThreadLocal context so we can reuse the from now on.
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		} catch (AuthenticationException ae) {
			failureHandler.onAuthenticationFailure((HttpServletRequest)request, (HttpServletResponse)response, ae);
	        return null;
	    }

		return authentication;
	}

}
