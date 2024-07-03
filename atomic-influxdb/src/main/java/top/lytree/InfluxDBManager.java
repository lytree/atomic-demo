package top.lytree;

import com.influxdb.client.*;
import com.influxdb.client.domain.Bucket;
import com.influxdb.client.domain.Organization;
import com.influxdb.client.write.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.util.List;


public class InfluxDBManager {
    private final static Logger logger = LoggerFactory.getLogger(InfluxDBManager.class);

    private final InfluxDBClientConfig influxDBClientConfig;

    public InfluxDBManager(InfluxDBClientConfig influxDBClientConfig) {
        this.influxDBClientConfig = influxDBClientConfig;
    }

    public InfluxDBClient getInfluxDbClient(){
        InfluxDBClient client = InfluxDBClientFactory.create(influxDBClientConfig.getUrl(),
                influxDBClientConfig.getToken().toCharArray(), influxDBClientConfig.getOrg(), influxDBClientConfig.getBucket());
        Bucket bucket = client.getBucketsApi().findBucketByName(influxDBClientConfig.getBucket());
        if (bucket == null) {
            Organization organization;
            OrganizationsQuery query = new OrganizationsQuery();
            query.setOrg(influxDBClientConfig.getOrg());
            List<Organization> organizationList = client.getOrganizationsApi().findOrganizations(query);
            if (organizationList.isEmpty()) {
                organization = client.getOrganizationsApi().createOrganization(influxDBClientConfig.getOrg());
            } else {
                organization = organizationList.get(0);
            }
            client.getBucketsApi().createBucket(influxDBClientConfig.getBucket(), organization);
        }
        return client;
    }

    public void write(Point point) {
        try (InfluxDBClient client = getInfluxDbClient()) {
            WriteApiBlocking api = client.getWriteApiBlocking();
            api.writePoint(point);
        } catch (Exception e) {
            logger.error("InfluxDB write[Point point] has exception: ", e);
        }
    }
    public void write(List<Point> list){
        try (InfluxDBClient client = getInfluxDbClient()) {
            WriteApiBlocking api = client.getWriteApiBlocking();
            api.writePoints(list);
        } catch (Exception e) {
            logger.error("InfluxDB write[Point point] has exception: ", e);
        }
    }
    public <M> List<M> query(String query, Class<M> measurementType) {
        try (InfluxDBClient client = getInfluxDbClient()) {
            QueryApi api = client.getQueryApi();
            return api.query(query, measurementType);
        } catch (Exception e) {
            logger.error("InfluxDB query has exception: ", e);
        }
        return null;
    }

    public void deleteBucket(String bucketName){
        try (InfluxDBClient client = getInfluxDbClient()){
            Bucket bucket = client.getBucketsApi().findBucketByName(bucketName);
            if (bucket != null) {
                client.getBucketsApi().deleteBucket(bucket);
            }
        }
    }
    public void delete(OffsetDateTime start, OffsetDateTime end, String predicate, String bucket, String org) {
        try (InfluxDBClient client = getInfluxDbClient()) {
            DeleteApi api = client.getDeleteApi();
            api.delete(start, end, predicate, bucket, org);
        } catch (Exception e) {
            logger.error("InfluxDB delete has exception: ", e);
        }
    }
}
