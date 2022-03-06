package top.yang.utils.jdbc;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class MySQLMapper {

    private static final Logger logger = LoggerFactory.getLogger(MySQLMapper.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @param tableNames 重置mysql数据库逐渐自增值
     */
    public void resetsTheAutoincrementPrimaryKey(List<String> tableNames) {
        for (String tableName : tableNames) {
            jdbcTemplate.execute("ALTER TABLE " + String.valueOf(tableName) + " AUTO_INCREMENT = 1");
        }

    }

    /**
     * 重置所有数据库表自增id
     */
    public void resetsTheAutoincrementPrimaryKey() {
        List<String> tableNames = jdbcTemplate
                .queryForList("select CONCAT(TABLE_SCHEMA,\".\",TABLE_NAME) from information_schema.tables WHERE `AUTO_INCREMENT` is NOT NULL", String.class);
        resetsTheAutoincrementPrimaryKey(tableNames);
    }
}
