package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.dto.JobTypeSearchDto;
import vn.com.unit.cms.admin.all.entity.JobType;
import vn.com.unit.db.repository.DbRepository;

public interface JobTypeRepository extends DbRepository<JobType, Long> {

	Long findMaxSort();

	public Integer countJobType(@Param("searchCond") JobTypeSearchDto condition);

	public List<JobTypeSearchDto> findJobTypeList(@Param("offset") int startIndex, @Param("sizeOfPage") int sizeOfPage,
			@Param("searchCond") JobTypeSearchDto condition);

	public JobType findJobTypeByCode(@Param("code") String code);

	Long countDelete();

}
