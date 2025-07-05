package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.dto.JobTypeSubDto;
import vn.com.unit.cms.admin.all.dto.JobTypeSubSearchDto;
import vn.com.unit.cms.admin.all.entity.JobTypeSub;
import vn.com.unit.db.repository.DbRepository;

public interface JobTypeSubRepository extends DbRepository<JobTypeSub, Long> {

	Long findMaxSort();

	public List<JobTypeSubDto> findAllTypeSub(@Param("lang") String lang);

	public Integer countJobTypeSub(@Param("searchCond") JobTypeSubSearchDto condition);

	public List<JobTypeSubSearchDto> findJobTypeSubList(@Param("offset") int startIndex,
			@Param("sizeOfPage") int sizeOfPage, @Param("searchCond") JobTypeSubSearchDto typeSubDto);

	public JobTypeSub findJobTypeSubByCode(@Param("code") String code);

}
