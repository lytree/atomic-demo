package top.yang.net.autoconfigure;

import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import top.yang.net.config.ConnectionPoolProperties;
import top.yang.net.config.OkHttpClientProperties;
import top.yang.net.logger.DefaultLoggingInterceptor;
import top.yang.net.logger.LoggingFactory;

@EnableConfigurationProperties(value = {OkHttpClientProperties.class,ConnectionPoolProperties.class})
public class OkHttpClientAutoConfiguration {

    private final Logger logger = LoggerFactory.getLogger(OkHttpClientAutoConfiguration.class);

    /**
     * 配置OkHttpClient
     *
     * @return {@link OkHttpClient}
     */
    @Bean
    @ConditionalOnMissingBean
    public OkHttpClient okHttpClient() {
        Builder builder = new Builder();
        builder.callTimeout(properties.getCallTimeout(), TimeUnit.SECONDS);
        builder.writeTimeout(properties.getCallTimeout(), TimeUnit.SECONDS);
        builder.readTimeout(properties.getCallTimeout(), TimeUnit.SECONDS);
        builder.connectTimeout(properties.getCallTimeout(), TimeUnit.SECONDS);
        builder.pingInterval(properties.getPingInterval(), TimeUnit.SECONDS);
        ConnectionPoolProperties connectionPool = properties.getConnectionPool();
        builder.connectionPool(new ConnectionPool(connectionPool.getMaxIdleConnections(), connectionPool.getKeepAliveSecond(), TimeUnit.SECONDS));
        builder.followRedirects(properties.getFollowRedirects());
        builder.followSslRedirects(properties.getFollowSslRedirects());
        if (properties.getLogImpl()==null){

            builder.addNetworkInterceptor(LoggingFactory.getLog(DefaultLoggingInterceptor.class,properties.getLevel()));
        }else {
            builder.addNetworkInterceptor(LoggingFactory.getLog(properties.getLogImpl(),properties.getLevel()));
        }

        builder.retryOnConnectionFailure(properties.getRetryOnConnectionFailure());
        return builder.build();
    }

    /**
     * 注入ExampleProperties
     */
    private final OkHttpClientProperties properties;

    public OkHttpClientAutoConfiguration(OkHttpClientProperties properties) {
        this.properties = properties;
    }
}
