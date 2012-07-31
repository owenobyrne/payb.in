package in.payb.api;

import org.springframework.stereotype.Component;

import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.NodeDiscoveryType;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolConfigurationImpl;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolType;
import com.netflix.astyanax.connectionpool.impl.CountingConnectionPoolMonitor;
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.thrift.ThriftFamilyFactory;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Component
public class CassandraConnection {
	private Keyspace keyspace;
	public static ColumnFamily<String, String> CF_USER_INFO = new ColumnFamily<String, String>(
		    "Movies",              // Column Family Name
		    StringSerializer.get(),   // Key Serializer
		    StringSerializer.get());  // Column Serializer
	
    public CassandraConnection() {
    	AstyanaxContext<Keyspace> context = new AstyanaxContext.Builder()
        .forCluster("Test Cluster")
        .forKeyspace("OwenTest")
        .withAstyanaxConfiguration(new AstyanaxConfigurationImpl()      
            .setDiscoveryType(NodeDiscoveryType.RING_DESCRIBE)
            .setConnectionPoolType(ConnectionPoolType.TOKEN_AWARE)
        )
        .withConnectionPoolConfiguration(new ConnectionPoolConfigurationImpl("MyConnectionPool")
            .setPort(9160)
            .setMaxConnsPerHost(3)
            .setSeeds("10.10.1.2:9160")
         )
        .withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
        .buildKeyspace(ThriftFamilyFactory.getInstance());

    context.start();
    keyspace = context.getEntity();
    
    }

	public Keyspace getKeyspace() {
		return keyspace;
	}

	public void setKeyspace(Keyspace keyspace) {
		this.keyspace = keyspace;
	}
    
    
    
}
