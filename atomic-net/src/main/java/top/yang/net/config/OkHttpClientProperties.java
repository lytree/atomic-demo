package top.yang.net.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.env.Environment;
import top.yang.net.Level;
import top.yang.net.logger.DefaultLoggingInterceptor;
import top.yang.net.logger.LoggingFactory;
import top.yang.net.logger.LoggingInterceptor;

@ConfigurationProperties(
        prefix = "okhttp"
)
public class OkHttpClientProperties {

    private static final long CALL_TIMEOUT = 30;
    private static final long READ_TIMEOUT = 5;
    private static final long WRITE_TIMEOUT = 10;
    private static final long CONNECT_TIMEOUT = 30;
    private static final long PING_INTERVAL = 1;
    private static final boolean RETRY_ON_CONNECTION_FAILURE = true;
    private static final boolean FOLLOW_REDIRECTS = true;
    private static final boolean FOLLOW_SSL_REDIRECTS = true;
    private Long callTimeout;
    private Long readTimeout;
    private Long writeTimeout;
    private Long connectTimeout;
    private Long pingInterval;
    private Boolean retryOnConnectionFailure;
    private Boolean followRedirects;
    private Boolean followSslRedirects;
    private Class<? extends LoggingInterceptor> logImpl;
    private Level level = Level.BASIC;
    @NestedConfigurationProperty
    private ConnectionPoolProperties connectionPool = new ConnectionPoolProperties();

    public OkHttpClientProperties() {
        callTimeout = CALL_TIMEOUT;
        readTimeout = READ_TIMEOUT;
        writeTimeout = WRITE_TIMEOUT;
        connectTimeout = CONNECT_TIMEOUT;
        pingInterval = PING_INTERVAL;
        retryOnConnectionFailure = RETRY_ON_CONNECTION_FAILURE;
        followRedirects = FOLLOW_REDIRECTS;
        followSslRedirects = FOLLOW_SSL_REDIRECTS;
    }


    public Long getPingInterval() {
        return pingInterval;
    }

    public void setPingInterval(Long pingInterval) {
        this.pingInterval = pingInterval;
    }

    public Long getCallTimeout() {
        return callTimeout;
    }

    public void setCallTimeout(Long callTimeout) {
        this.callTimeout = callTimeout;
    }

    public Long getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Long readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Long getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(Long writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public Long getConnectTimeout() {
        return connectTimeout;
    }

    public Boolean getRetryOnConnectionFailure() {
        return retryOnConnectionFailure;
    }

    public void setRetryOnConnectionFailure(Boolean retryOnConnectionFailure) {
        this.retryOnConnectionFailure = retryOnConnectionFailure;
    }

    public Boolean getFollowRedirects() {
        return followRedirects;
    }

    public void setFollowRedirects(Boolean followRedirects) {
        this.followRedirects = followRedirects;
    }

    public Boolean getFollowSslRedirects() {
        return followSslRedirects;
    }

    public void setFollowSslRedirects(Boolean followSslRedirects) {
        this.followSslRedirects = followSslRedirects;
    }

    public Class<? extends LoggingInterceptor> getLogImpl() {
        return logImpl;
    }

    public void setLogImpl(Class<? extends LoggingInterceptor> logImpl) {
        if (logImpl != null) {
            this.logImpl = logImpl;
        }
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setConnectTimeout(Long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public ConnectionPoolProperties getConnectionPool() {
        return this.connectionPool;
    }

    public void setConnectionPool(ConnectionPoolProperties connectionPool) {
        this.connectionPool = connectionPool;
    }

}
