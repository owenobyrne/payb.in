package in.payb.api.mvc;

import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/confirm_access")
public class AccessConfirmationController {

  @Autowired
  private OAuthProviderTokenServices tokenServices;
  
  @Autowired
  private ConsumerDetailsService consumerDetailsService;

  @RequestMapping(method = RequestMethod.GET)
  protected ModelAndView accessConfirmation(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String token = request.getParameter("oauth_token");
    System.out.println("In confirm_access - token is " + token);
    if (token == null) {
      throw new IllegalArgumentException("A request token to authorize must be provided.");
    }

    System.out.println("Got an oauth_token " + token);
    
    OAuthProviderToken providerToken = tokenServices.getToken(token);
    System.out.println("Got providerToken " + providerToken.toString());
    ConsumerDetails consumer = consumerDetailsService.loadConsumerByConsumerKey(providerToken.getConsumerKey());
    System.out.println("Got a consumer " + consumer.toString());
    String callback = request.getParameter("oauth_callback");
    TreeMap<String, Object> model = new TreeMap<String, Object>();
    model.put("oauth_token", token);
    if (callback != null) {
      model.put("oauth_callback", callback);
    }
    model.put("consumer", consumer);
    return new ModelAndView("access_confirmation", model);
  }
}
