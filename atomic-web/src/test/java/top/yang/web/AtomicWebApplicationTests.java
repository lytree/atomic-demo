package top.yang.web;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.yang.web.dao.mapper.TestMapper;
import top.yang.web.dao.repository.TestRepository;
import top.yang.web.entity.Links;

@SpringBootTest
class AtomicWebApplicationTests {

    @Autowired
    private TestMapper testMapper;
    @Autowired
    private TestRepository repository;

    @Test
    void contextLoads() {
        List<Links> all = testMapper.findAll();
        Iterable<Links> all1 = repository.findAll();
        List<Links> collect = StreamSupport.stream(all1.spliterator(), false).collect(Collectors.toList());
        System.out.println("debug");

    }

}
