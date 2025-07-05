/*******************************************************************************
 * Class        ：JobRepository
 * Created date ：2017/03/06
 * Lasted date  ：2017/03/06
 * Author       ：TranLTH
 * Change log   ：2017/03/06：01-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.Date;
import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.dto.JobDto;
import vn.com.unit.cms.admin.all.dto.JobSearchDto;
import vn.com.unit.cms.admin.all.entity.Job;
import vn.com.unit.cms.admin.all.entity.JobLanguage;
import vn.com.unit.cms.admin.all.jcanary.dto.BranchDto;
import vn.com.unit.cms.admin.all.jcanary.entity.OrgInfo;
import vn.com.unit.db.repository.DbRepository;
//import vn.com.unit.jcanary.dto.BranchDto;
//import vn.com.unit.jcanary.entity.OrgInfo;

/**
 * JobRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public interface JobRepository extends DbRepository<Job, Long> {
    /**
     * find Location by OrgType in jca_m_org table
     *
     * @param orgType
     * @param expiry_date
     * @return List<OrgInfo>
     * @author TranLTH
     */
    public List<OrgInfo> findLocationOrgType(@Param("orgType") String orgType, @Param("expired_date") Date expiry_date);
    /**
     * findJobListByCondition
     *
     * @param jobDto
     * @return List<JobDto>
     * @author TranLTH
     */
    public JobDto findJobListByCondition(@Param("jobDto") JobDto jobDto); 
    /**
     * findJobListByCondition
     *
     * @param jobId
     * @return
     * @author TranLTH
     */
    public List<JobLanguage> findJobIdLanguage(@Param("jobId") String jobId);
    /**
     * countJobByCondition
     *
     * @param jobSearchDto
     * @return int
     * @author TranLTH
     */
    public int countJobByCondition( @Param("jobSearchDto")JobSearchDto jobSearchDto);
    /**
     * getJobDtoById
     *
     * @param jobId
     * @return
     * @author TranLTH
     */
    public JobDto findJobDtoById(@Param("jobId") Long jobId, @Param("languageCode") String languageCode);
    /**
     * findJobLimitByCondition
     *
     * @param startIndex
     * @param sizeOfPage
     * @param jobSearchDto
     * @return List<JobDto>
     * @author TranLTH
     */
    public List<JobDto> findJobLimitByCondition(@Param("startIndex") int startIndex, @Param("sizeOfPage") int sizeOfPage,
            @Param("jobSearchDto") JobSearchDto jobSearchDto);
    /**
     * findMaxSortJob
     *
     * @return int
     * @author TranLTH
     */
    public int findMaxSortJob();
    /**
     * findLocationByBranch
     *
     * @return
     * @author TranLTH
     */
    public List<BranchDto> findLocationByBranch();
    /**
     * findAllActiveNonPaging
     *
     * @return
     * @author TranLTH
     */
    public List<JobDto> findAllActiveNonPaging(@Param("currentDate") Date currentDate, @Param("languageCode") String languageCode);
    /**
     * findMaxIdJob
     *
     * @return
     * @author TranLTH
     */
    public int findMaxIdJob();
}