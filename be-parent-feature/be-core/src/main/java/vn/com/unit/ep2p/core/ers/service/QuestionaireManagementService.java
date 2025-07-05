// 2021-04-12 LocLT Task #41067

package vn.com.unit.ep2p.core.ers.service;

import java.util.List;

import vn.com.unit.ep2p.core.ers.dto.QuestionaireManagementDto;
import vn.com.unit.ep2p.core.ers.dto.QuestionaireManagementSearchDto;

public interface QuestionaireManagementService {

	List<QuestionaireManagementDto> searchByCondition(QuestionaireManagementSearchDto searchDto, Integer pageNumber, Integer pageSize);

	QuestionaireManagementDto findById(Long id);

	QuestionaireManagementDto save(QuestionaireManagementDto dto);

	void delele(Long id);

	int countByCondition(QuestionaireManagementSearchDto searchDto);

}
