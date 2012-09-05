package in.payb.api.mvc;

import in.payb.api.RealControlUserDetails;

import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetailsService;
import org.springframework.security.oauth.provider.token.OAuthProviderToken;
import org.springframework.security.oauth.provider.token.OAuthProviderTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for retrieving the model for and displaying the confirmation page
 * for access to a protected resource.
 * 
 * @author Ryan Heaton
 */
@Controller
@RequestMapping("/confirm_secrets")
public class SecretsConfirmationController {

	@Autowired
	private OAuthProviderTokenServices tokenServices;

	@Autowired
	private ConsumerDetailsService consumerDetailsService;

	@RequestMapping(method = RequestMethod.POST)
	protected ModelAndView accessConfirmation(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String token = request.getParameter("oauth_token");
		String callback = request.getParameter("oauth_callback");
		String sharedsecret = request.getParameter("j_sharedsecret");
		String refundpassword = request.getParameter("j_refundpassword");

		System.out.println("In confirm_secrets - secrets are " + sharedsecret
				+ "/" + refundpassword);
		if (token == null) {
			throw new IllegalArgumentException(
					"A request token to authorize must be provided.");
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UsernamePasswordAuthenticationToken upat = (UsernamePasswordAuthenticationToken)authentication;
		RealControlUserDetails rcud = (RealControlUserDetails)upat.getPrincipal();
		rcud.setRefundpassword(refundpassword);
		rcud.setSharedsecret(sharedsecret);
		upat.setDetails(rcud);
		SecurityContextHolder.getContext().setAuthentication(upat);
		
		OAuthProviderToken providerToken = tokenServices.getToken(token);
		System.out.println("Got providerToken " + providerToken.toString());
		
		ConsumerDetails consumer = consumerDetailsService.loadConsumerByConsumerKey(providerToken.getConsumerKey());
		System.out.println("Got a consumer " + consumer.toString());
		
		TreeMap<String, Object> model = new TreeMap<String, Object>();
		model.put("oauth_token", token);
		
		if (callback != null) {
			model.put("oauth_callback", callback);
		}
		
		model.put("consumer", consumer);
		return new ModelAndView("secrets_confirmation", model);
	}
}
