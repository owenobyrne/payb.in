package in.payb.api.resource;

import in.payb.api.data.TransactionListDao;
import in.payb.api.model.Transaction;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

 
@Component
@Path("/transactions")
public class TransactionListResource {
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
    TransactionListDao transactionListDao;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Transaction> getTransactionList(
    		@QueryParam("from") String from, 
    		@QueryParam("to") String to
    	) {

    	ArrayList<Transaction> transactions = transactionListDao.retrieve(from, to);
        return transactions;
    }
}