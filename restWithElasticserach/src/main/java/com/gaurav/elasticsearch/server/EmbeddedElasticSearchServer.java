package com.gaurav.elasticsearch.server;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.Thread.sleep;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.elasticsearch.cluster.health.ClusterHealthStatus.RED;
import static org.elasticsearch.common.settings.Settings.builder;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;
import static org.slf4j.LoggerFactory.getLogger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.node.Node;
import org.slf4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
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
public class EmbeddedElasticSearchServer implements InitializingBean, DisposableBean {

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
    private final Map<String, String> transportClientConfig;
    private Node server;
    private String host;
    private int port;

    @Autowired
    public EmbeddedElasticSearchServer(final Map<String, String> esServerConfiguration,
                                       final Map<String, String> transportClientConfig) {
        this.esServerConfiguration = checkNotNull(esServerConfiguration);
        this.transportClientConfig = checkNotNull(transportClientConfig);
    }

    @Override
    public void destroy() throws Exception {
        LOG.info("SHUTTING DOWN ELASTIC SERACH SERVER");
        deleteAllIndices();
        getNodeClient().close();
        server.close();
        LOG.info("SHUTTING DOWN COMPLETED");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOG.info("Starting the Elastic Search server node ");

        final Builder settingBuilder = builder().put(esServerConfiguration);
        server = nodeBuilder().data(true).client(false).local(true).settings(settingBuilder).build();

        server.start();

        chechStatusAndDeleteOldData();

        LOG.info("Elastic Search server is started.");
    }

    private void chechStatusAndDeleteOldData() {
        newFixedThreadPool(1).execute(() -> checkServerStatus());
    }

    private void deleteAllIndices() {
        final DeleteIndexResponse response = getNodeClient().admin().indices().prepareDelete("_all").get();
        if (!response.isAcknowledged()) {
            throw new IllegalStateException("Fail to delete all indices");
        }
        LOG.info("Done deleting old data={}", response);
    }

    private Client getNodeClient() {
        return server.client();
    }

    @SuppressWarnings("unused")
    private TransportClient getTransportClient() throws UnknownHostException {
        final Settings settings = Settings.builder().put(transportClientConfig).build();
        final TransportClient client = TransportClient.builder().settings(settings).build();
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
        LOG.info("connectedNodes. = {}, transportAddresses", client.connectedNodes(), client.transportAddresses());
        return client;
    }

    private ClusterHealthStatus getHealthStatus() {
        return getNodeClient().admin().cluster().prepareHealth().execute().actionGet().getStatus();
    }

    private void checkServerStatus() {
        try {
            sleep(5000);
        } catch (final InterruptedException ex) {
            LOG.error("Exception = ", ex);
        }
        ClusterHealthStatus status = getHealthStatus();
        if (RED.equals(status)) {
            LOG.info("ES cluster status is {}. Waiting for ES recovery.", status);
            getNodeClient().admin().cluster().prepareHealth().setWaitForYellowStatus()
            .setTimeout(new TimeValue(TIME_OUT, SECONDS)).execute().actionGet();
        }

        status = getHealthStatus();

        LOG.info("ES Cluster status is {}, = {}", status, getNodeClient().admin().cluster().prepareNodesInfo());

        if (RED.equals(status)) {
            LOG.error("ES cluster health status is RED. Server is not able to start.");
            checkServerStatus();
        }
    }

    public void setHost(final String host) {
        this.host = host;
    }

    public void setPort(final int port) {
        this.port = port;
    }

}
