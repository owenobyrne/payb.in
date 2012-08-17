package in.payb.api;

import in.payb.api.utils.RealexHttpConnectionManager;

import java.util.EnumSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class PaybinAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private RealexHttpConnectionManager realexHttpConnectionManager;
    
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		// Check authentication against RealControl 
		System.out.println("Authenticating " + authentication.toString());
		
		if (realexHttpConnectionManager.checkLoginToRealControl(authentication)) {
			Set<RealControlRole> roles = EnumSet.noneOf(RealControlRole.class);
			roles.add(RealControlRole.USER);
	       
			// by setting the roles we are implicitly setting authenticated to true (can't call setAuthenticated directly)
			UsernamePasswordAuthenticationToken upat = 
					new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), 
							authentication.getCredentials(), roles);
			
			return upat;
		} else {
			// TODO change the check method to throw exceptions and catch them in a try loop
			// throw the right exceptions for the different problems.
			throw new BadCredentialsException("Incorrect login to RealControl.");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// check does this authentication provider support the AuthenticationToken type.
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
