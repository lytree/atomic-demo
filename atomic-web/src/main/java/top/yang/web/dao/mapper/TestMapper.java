package top.yang.web.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import top.yang.web.entity.Links;

@Mapper
public interface TestMapper {

    List<Links> findAll();
}
