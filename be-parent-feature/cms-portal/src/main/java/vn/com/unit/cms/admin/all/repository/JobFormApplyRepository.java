package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.dto.JobFormApllySearchDto;
import vn.com.unit.cms.admin.all.entity.JobFormApply;
import vn.com.unit.db.repository.DbRepository;

public interface JobFormApplyRepository extends DbRepository<JobFormApply, Long> {

	public Integer countJobFormApply(@Param("searchCond") JobFormApllySearchDto condition);

	public List<JobFormApllySearchDto> findJobFormApplyList(@Param("offset") int startIndex,
			@Param("sizeOfPage") int sizeOfPage, @Param("searchCond") JobFormApllySearchDto condition);

	public JobFormApply findId(@Param("jobFormApplyId") Long jobFormApplyId);

}
