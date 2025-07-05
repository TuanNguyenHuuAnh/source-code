package vn.com.unit.cms.core.module.emulate.repository;

import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.core.module.emulate.dto.ContestDto;
import vn.com.unit.cms.core.module.emulate.entity.ContestApplicableDetail;
import vn.com.unit.db.repository.DbRepository;

public interface ContestApplicableDetailRepository extends DbRepository<ContestApplicableDetail, Long> {
	
    public ContestApplicableDetail findAplicaById(@Param("id") Long id);

}
