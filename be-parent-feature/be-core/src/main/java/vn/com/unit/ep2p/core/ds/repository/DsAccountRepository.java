package vn.com.unit.ep2p.core.ds.repository;

import java.util.List;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.core.ds.dto.DsAccountQuestionDto;
import vn.com.unit.ep2p.core.ds.entity.DsAccountQuestion;

/**
 * @author TaiTM
 **/
public interface DsAccountRepository extends DbRepository<DsAccountQuestion, Long> {
    public List<DsAccountQuestionDto> findDsAccountQuestionDtoByUsername(@Param("username") String username,
            @Param("companyId") Long companyId);

    public List<DsAccountQuestionDto> findDsAccountQuestionDtoByUserId(@Param("userId") Long userId);
    
    
    public List<DsAccountQuestion> findDsAccountQuestion(@Param("userId") String userId);
    @Modifying
    void deleteByUsername(@Param("username") String username);

    int countAgentQuestion(@Param("agentCode") String agentCode, @Param("questionCode") String questionCode, @Param("answer") String answer);
}
