package top.yang.net.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "okhttp.pool"
)
public class ConnectionPoolProperties {

    private static final int MAX_IDLE_CONNECTIONS = 10;
    private static final long KEEP_ALIVE_SECOND = 5;

    private int maxIdleConnections;

    private long keepAliveSecond;

    public ConnectionPoolProperties() {
        this.maxIdleConnections = MAX_IDLE_CONNECTIONS;
        this.keepAliveSecond = KEEP_ALIVE_SECOND;
    }

    public int getMaxIdleConnections() {
        return maxIdleConnections;
    }

    public void setMaxIdleConnections(int maxIdleConnections) {
        this.maxIdleConnections = maxIdleConnections;
    }

    public long getKeepAliveSecond() {
        return keepAliveSecond;
    }

    public void setKeepAliveSecond(long keepAliveSecond) {
        this.keepAliveSecond = keepAliveSecond;
    }
}
