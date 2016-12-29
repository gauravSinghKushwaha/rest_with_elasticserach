package com.embedded.elasticsearch;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeValidationException;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Class runs elastic server, we use transport client to connect to it.http is disabled could be enabled by setting
 * properties appropriately
 * 
 * @author gkushwaha
 *
 */
@Repository
public class EmbeddedElasticSearchServer {

    private static final Logger LOG = getLogger(EmbeddedElasticSearchServer.class);
    private static final int TIME_OUT = 30;

    /**
     * keeping server settings like
     * 
     * <code>
     *  # only in embedded mode set them true
        node.data.enabled=true
        node.master.enabled=true
        node.ingest.enabled=true
        path.data=/target/data/
        cluster.name=test_cluster
        node.name=test_node
        http.port=9300
        #set to false if using java interface
        http.enabled=false
        #if using java interface and not using rest for elastic searching
        transport.tcp.port=9300
        # if using node client or transport client. node or transport
        client.type=transport
        </code>
     */
    private final Map<String, String> esServerConfiguration;
    private final Map<String, String> transportClientConfiguration;
    private Node server;
    private TransportClient transportClient;

    @Autowired
    public EmbeddedElasticSearchServer(final Map<String, String> esServerConfiguration,
                                       final Map<String, String> transportClientConfiguration) {
        this.esServerConfiguration = checkNotNull(esServerConfiguration);
        this.transportClientConfiguration = checkNotNull(transportClientConfiguration);
    }

    @SuppressWarnings("unused")
    private void start() throws NodeValidationException {
        LOG.info("Starting the Elastic Search server node ");

        final Settings settings = Settings.builder().put(esServerConfiguration).build();
        server = new Node(settings);

        LOG.info("Starting the Elastic Search server node with {} /n settings:", settings);

        server.start();
        checkServerStatus();

        LOG.info("Elastic Search server is started.");
    }

    @SuppressWarnings("unused")
    private void stop() throws IOException {
        server.close();
        getClient().close();
    }

    private Client getClient() {
        return server.client();
    }

    private ClusterHealthStatus getHealthStatus() {
        return getClient().admin().cluster().prepareHealth().execute().actionGet().getStatus();
    }

    private void checkServerStatus() {
        ClusterHealthStatus status = getHealthStatus();
        if (ClusterHealthStatus.RED.equals(status)) {
            LOG.info("ES cluster status is {}. Waiting for ES recovery.", status);
            getClient().admin().cluster().prepareHealth().setWaitForYellowStatus()
            .setTimeout(new TimeValue(TIME_OUT, SECONDS)).execute().actionGet();
        }

        status = getHealthStatus();
        LOG.info("ES cluster status is {}", status);

        if (ClusterHealthStatus.RED.equals(status)) {
            throw new RuntimeException("ES cluster health status is RED. Server is not able to start.");
        }
    }

    @SuppressWarnings({ "resource", "unused" })
    private TransportClient getInstance() throws Exception {
        final Settings settings = Settings.builder().put(transportClientConfiguration).build();
        final InetAddress localHost = InetAddress.getLocalHost();
        transportClient =
                new PreBuiltTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(localHost,
                        9300));
        LOG.info("transportClient created. = {}", transportClient);
        return transportClient;
    }

}
