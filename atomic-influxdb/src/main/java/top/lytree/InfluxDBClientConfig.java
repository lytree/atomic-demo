package top.lytree;

public class InfluxDBClientConfig {
    private final String url;

    private final String token;

    /**
     * 默认组织
     */
    private final String org;

    /**
     * 默认 bucket
     */
    private final String bucket;

    public InfluxDBClientConfig(String url, String token, String org, String bucket) {
        this.url = url;
        this.token = token;
        this.org = org;
        this.bucket = bucket;
    }

    public String getUrl() {
        return url;
    }

    public String getToken() {
        return token;
    }

    public String getOrg() {
        return org;
    }

    public String getBucket() {
        return bucket;
    }
}
