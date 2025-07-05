/*******************************************************************************
 * Class        ：JobService
 * Created date ：2017/03/07
 * Lasted date  ：2017/03/07
 * Author       ：TranLTH
 * Change log   ：2017/03/07：01-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.dto.JobDto;
import vn.com.unit.cms.admin.all.dto.JobSearchDto;
import vn.com.unit.cms.admin.all.dto.SortPageDto;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;

/**
 * JobService
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public interface JobService extends DocumentWorkflowCommonService<JobDto, JobDto> {
    /**
     * initScreenJobList
     *
     * @param mav
     * @author TranLTH
     */
    public void initScreenJobList(ModelAndView mav, String languageCode);
    /**
     * searchJobByCondition
     *
     * @param jobDto
     * @return List<JobDto>
     * @author TranLTH
     */
    public JobDto searchJobByCondition(JobDto jobDto);
    /**
     * updateJob
     *
     * @param jobDto
     * @throws Exception
     * @author TranLTH
     */
//    public void addOrEditJob(JobDto jobDto) throws Exception;
    /**
     * deleteJob
     *
     * @param jobId
     * @throws Exception
     * @author TranLTH
     */
    public void delete(Long jobId);
    /**
     * getJobDto
     *
     * @param JobId
     * @return JobDto
     * @author TranLTH
     */
    public JobDto getJobDto(Long JobId, String languageCode);
    /**
     * search
     *
     * @param page
     * @param jobSearchDto
     * @return PageWrapper<JobDto>
     * @author TranLTH
     * @throws ParseException 
     */
    public PageWrapper<JobDto> search(int page, JobSearchDto jobSearchDto) throws ParseException;
    /**
     * approver
     *
     * @param jobDto
     * @author TranLTH
     */
//    public void approver(JobDto jobDto);
    /**
     * getAllActive
     *
     * @return
     * @author TranLTH
     */
    public List<JobDto> getAllActive(String languageCode);
    /**
     * updateModelsSort
     *
     * @param sortPageModel
     * @author TranLTH
     */
    public void updateModelsSort(SortPageDto sortPageModel);
}