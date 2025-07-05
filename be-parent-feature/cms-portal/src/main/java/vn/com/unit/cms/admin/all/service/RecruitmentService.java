/*******************************************************************************
 * Class        ：RecruitmentService
 * Created date ：2017/04/13
 * Lasted date  ：2017/04/26
 * Author       ：phunghn
 * Change log   ：2017/04/26：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import vn.com.unit.cms.admin.all.dto.RecruimentSearchDto;
import vn.com.unit.cms.admin.all.dto.RecruitmentDto;
import vn.com.unit.cms.admin.all.entity.Recruitment;
import vn.com.unit.common.dto.PageWrapper;

/**
 * RecruitmentService
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public interface RecruitmentService {
    /**
     * findAllActive
     *
     * @param page
     * @param recDto
     * @return PageWrapper<RecruitmentDto>
     * @author phunghn
     */
    public PageWrapper<RecruitmentDto> findAllActive(int page, RecruimentSearchDto recDto);

    /**
     * getRecruitmentEditdto
     *
     * @param id
     * @return Recruitment
     * @author phunghn
     */
    public Recruitment getRecruitmentEditdto(Long id);

    /**
     * addOrEdit
     *
     * @param recDto
     * @author phunghn
     */
    public void addOrEdit(Recruitment recDto);

    /**
     * delete
     *
     * @param id
     * @author phunghn
     */
    public void delete(Long id);
}
