package in.payb.api.resource;

import in.payb.api.data.TransactionDao;
import in.payb.api.model.Transaction;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

 
@Component
@Path("/transaction")
public class TransactionResource {
    private Transaction transaction;
     
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
     
    @Autowired
    TransactionDao transactionDao;
    
    // Basic "is the service running" test
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String respondAsReady() {
        return "Demo service is ready!";
    }
 
    @GET
    @Path("{orderid}/{guid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Transaction getSamplePerson(@PathParam("orderid") String orderid, @PathParam("guid") String guid) {

    	transaction = transactionDao.retrieve(orderid, guid);
        return transaction;
    }
         
    /*
    // Use data from the client source to create a new Person object, returned in JSON format.  
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Person postPerson(
            MultivaluedMap<String, String> personParams
            ) {
         
        String firstName = personParams.getFirst(FIRST_NAME);
        String lastName = personParams.getFirst(LAST_NAME);
        String email = personParams.getFirst(EMAIL);
         
        System.out.println("Storing posted " + firstName + " " + lastName + "  " + email);
        
        StringBuffer sb = new StringBuffer();
        sb.append("<request type='auth' timestamp='20120717014212'>");
        sb.append("<merchantid>ccentre</merchantid>");
        sb.append("<account>owentest234</account>");
        sb.append("<orderid>owentest20120717</orderid>");
        sb.append("<amount currency='EUR'>1000</amount>");
        sb.append("<autosettle flag='1'/>");
        sb.append("<card><number>4242424242424242</number><chname>Owen</chname><expdate>0813</expdate><type>VISA</type></card>");
        
        String tmp1 = DigestUtils.shaHex("20120717014212.ccentre.owentest20120717.1000.EUR.4242424242424242");
        String tmp2 = DigestUtils.shaHex(tmp1.toLowerCase() + ".secret");
        
        sb.append("<sha1hash>").append(tmp2.toLowerCase()).append("</sha1hash>");
        sb.append("</request>");
        
        realexHttpConnectionManager.postToRealex(sb.toString());
        
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setEmail(email);
         
        System.out.println("person info: " + person.getFirstName() + " " + person.getLastName() + " " + person.getEmail());
         
        return person;
                         
    }
    */
}