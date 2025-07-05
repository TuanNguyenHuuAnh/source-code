package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.quartz.job.dto.QrtzMJobDto;
import vn.com.unit.quartz.job.dto.QrtzMJobSearchDto;
import vn.com.unit.quartz.job.entity.QrtzMJob;

@Repository
public interface AppQrtzMJobRepository extends DbRepository<QrtzMJob, Long> {
	
	/**
	 * @param jobSearch
	 * @return
	 */
	int countJobByCondition(@Param("jobSearch") QrtzMJobSearchDto jobSearch);

	/**
	 * @param jobCode
	 * @return
	 */
	QrtzMJob getByJobId(@Param("jobId") Long jobId);

	/**
	 * @param jobCode
	 * @return
	 */
	Object getJobInUse(@Param("jobId") Long jobId);

	/**
	 * @param jobSearch
	 * @param offset
	 * @param sizeOfPage
	 * @return
	 */
	List<QrtzMJobDto> getJobs(@Param("jobSearch") QrtzMJobSearchDto jobSearch, @Param("offset") int offset,
                                     @Param("sizeOfPage") int sizeOfPage);

	/**
	 * @param term
	 * @param companyIdList
	 * @return
	 */
	List<Select2Dto> getListForCombobox(@Param("term") String term, @Param("companyIdList") List<Long> companyIdList);

	/**
	 * @param jobCode
	 * @param id
	 * @return
	 */
	Select2Dto findSelect2ByJobIdAndCompanyId(@Param("jobId") Long jobCode, @Param("id") Long id);
	
	/**
	 * getByJobGroup
	 * @param jobGroup
	 * @return
	 * @author HungHT
	 */
	QrtzMJob getByJobGroup(@Param("companyId") Long companyId, @Param("jobGroup") String jobGroup);
	
	/**
	 * @param companyId
	 * @param code
	 * @param id
	 * @return
	 */
	int hasCode(@Param("companyId") Long companyId, @Param("code") String code, @Param("id") Long id);
	
	/**
	 * getListSelect2Dto
	 * @param term
	 * @param companyId
	 * @param isPaging
	 * @return
	 * @author trieuvd
	 */
	List<Select2Dto> getListSelect2DtoByCompanyId(@Param("term")String term, @Param("companyId")Long companyId, @Param("isPaging")boolean isPaging);
}
