package top.yang.web.dao.repository;

import org.springframework.stereotype.Repository;
import top.yang.dao.repository.BaseJdbcRepository;
import top.yang.web.entity.Links;

@Repository
public interface TestRepository extends BaseJdbcRepository<Links, Long> {

}
