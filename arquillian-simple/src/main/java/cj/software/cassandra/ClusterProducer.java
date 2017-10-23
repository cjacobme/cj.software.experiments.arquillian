package cj.software.cassandra;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datastax.driver.core.Cluster;

@ApplicationScoped
@Singleton
@Startup
public class ClusterProducer
{
	private Cluster cluster;

	private Logger logger = LogManager.getFormatterLogger();

	public ClusterProducer()
	{
	}

	@PostConstruct
	public void setup()
	{
		getCluster();
	}

	public Cluster getCluster()
	{
		if (this.cluster == null)
		{
			String lHostname = System.getProperty("host");
			if (lHostname == null)
			{
				lHostname = "localhost";
			}
			this.cluster = Cluster.builder().addContactPoint(lHostname).build();
			this.logger.info(
					"connected to cluster \"%s\" via host \"%s\"",
					this.cluster.getClusterName(),
					lHostname);
		}
		return this.cluster;
	}
}
