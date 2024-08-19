package top.lytree;

import top.lytree.consul.Consul;
import top.lytree.consul.model.ConsulResponse;
import top.lytree.consul.model.acl.RoleListResponse;
import top.lytree.consul.model.acl.TokenListResponse;
import top.lytree.consul.model.agent.Registration;
import top.lytree.consul.model.catalog.CatalogDeregistration;
import top.lytree.consul.model.catalog.CatalogRegistration;
import top.lytree.consul.model.catalog.CatalogService;
import top.lytree.consul.model.catalog.ImmutableCatalogDeregistration;
import top.lytree.consul.model.health.Node;

import java.util.List;

public class ConsulService {
    private final Consul consul;


    public ConsulService(Consul consul) {
        this.consul = consul;
    }

    public void listRoles() {
        List<RoleListResponse> roleListResponses = consul.aclClient().listRoles();
        for (RoleListResponse roleListResponse : roleListResponses) {
            System.out.println(roleListResponse.toString());
        }
    }

    public void listTokens() {
        List<TokenListResponse> tokenListResponses = consul.aclClient().listTokens();
        for (TokenListResponse roleListResponse : tokenListResponses) {
            System.out.println(roleListResponse.toString());
        }
    }

    public void listNodes() {
        List<Node> response = consul.catalogClient().getNodes().getResponse();
        for (Node node : response) {
            System.out.println(node.toString());
        }
    }

    public void register(Registration registration) {
        consul.agentClient().register(registration);
    }


    public void listRegister(String serviceName) {
        List<CatalogService> response = consul.catalogClient().getService(serviceName).getResponse();
        for (CatalogService service : response) {
            System.out.println(service.toString());
        }
    }

    public void removeRegister(String serverName) {
        ImmutableCatalogDeregistration.Builder builder = ImmutableCatalogDeregistration.builder()
                .serviceId(serverName)
                .node("one");
        removeRegister(builder.build());
    }

    public void removeRegister(CatalogDeregistration deregistration) {
        consul.catalogClient().deregister(deregistration);
    }

    public void destroy() {
        consul.destroy();
    }
}
