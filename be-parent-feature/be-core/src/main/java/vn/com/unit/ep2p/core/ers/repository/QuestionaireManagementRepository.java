// 2021-04-12 LocLT Task #41067

package vn.com.unit.ep2p.core.ers.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.core.ers.dto.QuestionaireManagementDto;
import vn.com.unit.ep2p.core.ers.dto.QuestionaireManagementSearchDto;
import vn.com.unit.ep2p.core.ers.entity.ErsQuestionInterview;

public interface QuestionaireManagementRepository extends DbRepository<ErsQuestionInterview, Long> {

	List<QuestionaireManagementDto> searchByCondition(@Param("searchDto") QuestionaireManagementSearchDto searchDto, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

	int countByCondition(@Param("searchDto") QuestionaireManagementSearchDto searchDto);

}
