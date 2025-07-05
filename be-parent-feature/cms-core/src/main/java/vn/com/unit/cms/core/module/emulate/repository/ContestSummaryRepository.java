package vn.com.unit.cms.core.module.emulate.repository;

import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.core.module.emulate.entity.ContestSummary;
import vn.com.unit.db.repository.DbRepository;

public interface ContestSummaryRepository extends DbRepository<ContestSummary, Long> {

	
    public ContestSummary findByContestCode(@Param("code") String code);
    public ContestSummary findByMemo(@Param("memo") String memo);

}
