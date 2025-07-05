package vn.com.unit.cms.core.module.customer.repository;

import org.springframework.data.repository.query.Param;
import vn.com.unit.cms.core.module.customer.dto.ClientNickNameDto;
import vn.com.unit.cms.core.module.customer.entity.ClientNickName;
import vn.com.unit.db.repository.DbRepository;

public interface ClientNickNameRepository extends DbRepository<ClientNickName, Long>{

    String getNickNameByCondition(@Param("agentCode") String agentCode, @Param("customerNo") String customerNo);

    ClientNickName findByCondition(@Param("dto") ClientNickNameDto dto);
    
    String findUsername(@Param("agentCode") String agentCode,@Param("customerNo") String customerNo);
}
