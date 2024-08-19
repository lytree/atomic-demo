package top.lytree;

import com.google.common.net.HostAndPort;
import okhttp3.ConnectionPool;
import top.lytree.consul.Consul;
import top.lytree.consul.NotRegisteredException;
import top.lytree.consul.config.ClientConfig;
import top.lytree.consul.model.State;
import top.lytree.consul.model.agent.Check;
import top.lytree.consul.model.agent.ImmutableCheck;
import top.lytree.consul.model.agent.ImmutableRegistration;
import top.lytree.consul.model.agent.Registration;
import top.lytree.consul.model.catalog.CatalogRegistration;
import top.lytree.consul.model.catalog.ImmutableCatalogDeregistration;
import top.lytree.consul.model.catalog.ImmutableCatalogRegistration;
import top.lytree.consul.model.health.HealthCheck;
import top.lytree.consul.model.health.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Application {
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);


    private static final String APP_NAME = "Application";
    private static final String NODE_NAME = "one";

    public static void main(String[] args) throws NotRegisteredException {
        Properties properties = new Properties();
        try (OutputStream output = new FileOutputStream("F:\\Code\\Java\\atomic-demo\\atomic-consul\\src\\main\\resources\\consul.properties");) {
            properties.setProperty("consul.host", "127.0.0.1:8500");
            properties.setProperty("consul.token", "b2e80734-6620-0fe0-ecc0-8b9b4489a88a");

            // 保存键值对到文件中
            properties.store(output, "Thinkingcao modify");

        } catch (IOException io) {
            io.printStackTrace();
        }
        Consul build = Consul.builder().withConnectionPool(new ConnectionPool())
                .withHostAndPort(HostAndPort.fromParts("127.0.0.1", 8500))
                .withAclToken("b2e80734-6620-0fe0-ecc0-8b9b4489a88a")
                .build();

        Map<String, Service> services = build.agentClient().getServices();
        Map<String, HealthCheck> checks = build.agentClient().getChecks();

        executor.scheduleAtFixedRate(
                () -> {
                    for (Map.Entry<String, HealthCheck> entry : checks.entrySet()) {
                        HealthCheck check = entry.getValue();
                        try {
                            build.agentClient().passCheck(check.getCheckId(), "");
                        } catch (NotRegisteredException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(LocalDateTime.now());
                },
                0,
                1000,
                TimeUnit.MILLISECONDS);

    }
}
