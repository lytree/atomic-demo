package top.yang.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.yang.utils.jdbc.MySQLMapper;

@SpringBootTest
class AtomicUtilsApplicationTests {

    @Autowired
    private MySQLMapper mySQLMapper;

    @Test
    void contextLoads() {
        mySQLMapper.resetsTheAutoincrementPrimaryKey();
    }

}
