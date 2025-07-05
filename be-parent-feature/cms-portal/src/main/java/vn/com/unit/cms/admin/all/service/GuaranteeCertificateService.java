/*******************************************************************************
 * Class        ：GuaranteeCertificateService
 * Created date ：2017/08/24
 * Lasted date  ：2017/08/24
 * Author       ：hoangnp
 * Change log   ：2017/08/24：01-00 hoangnp create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.dto.GuaranteeCertificateEditDto;
import vn.com.unit.cms.admin.all.dto.GuaranteeCertificateSearchDto;
import vn.com.unit.cms.admin.all.entity.GuaranteeCertificate;
import vn.com.unit.common.dto.PageWrapper;

/**
 * GuaranteeCertificateService
 * 
 * @version 01-00
 * @since 01-00
 * @author hoangnp
 */

public interface GuaranteeCertificateService {

    /**
     * getEdit
     *
     * @param id
     * @return GuaranteeCertificateEditDto
     * @author hoangnp
     */
	public GuaranteeCertificateEditDto getEdit(Long id);

	/**
	 * initGuaranteeCertificateDurationType
	 *
	 * @param modelAndView
	 * @author hoangnp
	 */
	void initGuaranteeCertificateDurationType(ModelAndView modelAndView);

    /** createOrEdit
     *
     * @param editDto
     * @author hoangnp
     */
    public void createOrEdit(GuaranteeCertificateEditDto editDto);

    /** findCertificateNumber
     *
     * @param certificateNumber
     * @return GuaranteeCertificate
     * @author hoangnp
     */
    public GuaranteeCertificate findCertificateNumber(String certificateNumber);
    
    
    /**
     * search
     *
     * @param page
     * @param guaranteeCertificateSearchDto
     * @return
     * @author toannt
     */
    PageWrapper<GuaranteeCertificateSearchDto> search(int page,GuaranteeCertificateSearchDto guaranteeCertificateSearchDto);

    /** deleteGuaranteeCertificate
     *
     * @param id
     * @author toannt
     */
    void deleteGuaranteeCertificate(Long id);

}
