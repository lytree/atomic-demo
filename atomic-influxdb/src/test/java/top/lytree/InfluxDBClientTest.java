package top.lytree;

import com.google.gson.JsonObject;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.Bucket;
import com.influxdb.client.domain.InfluxQLQuery;
import com.influxdb.client.domain.Query;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxTable;
import com.influxdb.query.InfluxQLQueryResult;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import top.lytree.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfluxDBClientTest {

    private static InfluxDBManager influxDBManager;


    @BeforeAll
    public static void init(){
        InfluxDBClientConfig influxDBClientConfig = new InfluxDBClientConfig("http://127.0.0.1:8086","mTLU4-dtOJmNt7wbZR6vA6fzivwqpoIH1zj-UeGWkB3MJ7u49thA6kfa9WOBMiQOxSM9grlfhzgw3KB6jSuqpA==","test","telegrafs");
        influxDBManager = new InfluxDBManager(influxDBClientConfig);
    }



    @Test
    public void query(){

        try (InfluxDBClient influxDBClient = influxDBManager.getInfluxDbClient()){

            InfluxQLQuery influxQLQuery = new InfluxQLQuery("select * from win_system","telegrafs");

            InfluxQLQueryResult query = influxDBClient.getInfluxQLQueryApi().query(influxQLQuery);
            // 处理查询结果
            query.getResults().forEach(resultObj -> {

                resultObj.getSeries().forEach(series -> {
                    System.out.println(JSONObject.toJSONString(series.getColumns()));
                     System.out.println( JSONObject.toJSONString(series.getValues()));
                });
            });
        }
    }

//    @Test
//    public void createWriteMeasurement() {
//        try (InfluxDBClient influxDBClient = influxDBManager.getInfluxDbClient()) {
//            WriteApiBlocking writeApiBlocking = influxDBClient.getWriteApiBlocking();
//            for (int i = 0; i < 10000000; i++) {
//                int finalI = i;
//                writeApiBlocking.writeMeasurement("write", "test_write_measurement", WritePrecision.NS, new HashMap<>() {{
//                    put("value", finalI);
//                }});
//            }
//
//        }
//    }
    @Test
    public void createWritePoint(){
        try (InfluxDBClient influxDBClient = influxDBManager.getInfluxDbClient()){
            WriteApiBlocking writeApiBlocking = influxDBClient.getWriteApiBlocking();
            for (int i = 0;i<10000000;i++){
                Point point = new Point("test_write_point");

                point.time(System.currentTimeMillis(), WritePrecision.MS);
                point.addField("test", RandomUtils.nextDouble(10,16));
                point.addField("test1", RandomUtils.nextDouble(10,16));
                point.addField("test2", RandomUtils.nextDouble(10,16));
                writeApiBlocking.writePoint(point);
            }

        }
    }
    @Test
    public void query1(){

        try (InfluxDBClient influxDBClient = influxDBManager.getInfluxDbClient()){

            InfluxQLQuery influxQLQuery = new InfluxQLQuery("select * from test_write_point","telegrafs");

            InfluxQLQueryResult query = influxDBClient.getInfluxQLQueryApi().query(influxQLQuery);
            // 处理查询结果
            query.getResults().forEach(resultObj -> {

                resultObj.getSeries().forEach(series -> {
                    System.out.println(JSONObject.toJSONString(series.getColumns()));
                    System.out.println( JSONObject.toJSONString(series.getValues()));
                });
            });
        }
    }
}
