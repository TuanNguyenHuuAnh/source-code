package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.dto.JobTypeDetailDto;
import vn.com.unit.cms.admin.all.dto.JobTypeDetailSearchDto;
import vn.com.unit.cms.admin.all.entity.JobTypeDetail;
import vn.com.unit.cms.admin.all.entity.JobTypeDetailLanguage;
import vn.com.unit.db.repository.DbRepository;

public interface JobTypeDetailRepository extends DbRepository<JobTypeDetail, Long> {

	/**
	 * find List<JobTypeDetailDto>
	 * 
	 * @param jtdDto
	 * @return List<JobTypeDetailDto>
	 */
	public List<JobTypeDetailDto> findJtdByCondition(@Param("jtdSearchDto") JobTypeDetailSearchDto jtdSearchDto);

	/**
	 * find List<JobTypeDetailLanguage> by m_type_detail_id in
	 * m_job_type_detail_language
	 * 
	 * @param typeDetailId
	 * @return List<JobTypeDetailLanguage>
	 */
	public List<JobTypeDetailLanguage> findTypeDetailLanguageById(@Param("typeDetailId") Long typeDetailId);

	/**
	 * count Jtds
	 * 
	 * @param jtdSearchDto
	 * @return
	 */
	public int countJtd(@Param("jtdSearchDto") JobTypeDetailSearchDto jtdSearchDto);

	/**
	 * retrieve JobTypeDetailDto by id
	 * 
	 * @param jtdId
	 * @return
	 */
	public JobTypeDetailDto findJtdDtoById(@Param("jtdId") Long jtdId);

	/**
	 * retrieve all jtdDto
	 * 
	 * @return
	 */
	public List<JobTypeDetailDto> findAllJtdDto();

}
