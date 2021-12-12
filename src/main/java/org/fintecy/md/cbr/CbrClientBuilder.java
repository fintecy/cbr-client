package org.fintecy.md.cbr;

import dev.failsafe.Policy;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

class CbrClientBuilder {
    private final List<Policy<Object>> policies = new ArrayList<>();
    private HttpClient client = HttpClient.newHttpClient();
    private String rootPath = CbrApi.ROOT_PATH;

    public CbrClientBuilder useClient(HttpClient client) {
        this.client = client;
        return this;
    }

    public CbrClientBuilder rootPath(String rootPath) {
        this.rootPath = rootPath;
        return this;
    }

    public CbrClientBuilder with(Policy<Object> policy) {
        this.policies.add(policy);
        return this;
    }

    public CbrApi build() {
        return new CbrClient(rootPath, client, policies);
    }
}
