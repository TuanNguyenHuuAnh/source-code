
package vn.com.unit.cms.admin.all.service;

//import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import vn.com.unit.cms.admin.all.dto.InterestRateValueDto;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;

public interface InterestRateValueService
        extends DocumentWorkflowCommonService<InterestRateValueDto, InterestRateValueDto> {

    /** saveInterestRateValue
     *
     * @param interestRateValueDto
     * @throws Exception
     * @author nhutnn
     */
//    void saveInterestRateValue(InterestRateValueDto interestRateValueDto) throws Exception;

    /** deleteById
     *
     * @param lstId
     * @author nhutnn
     */
    void deleteByLstId(List<Long> lstId);

    /** findInterestRateValueByDto
     *
     * @param interestRateValueDto
     * @author nhutnn
     */
    List<InterestRateValueDto> findInterestRateValueByDto(InterestRateValueDto interestRateValueDto,Locale locale);

    /** countTotalTitleHaveValue
     *
     * @param interestRateType
     * @return
     * @author nhutnn
     */
    Integer countTotalTitleHaveValue(String interestRateType);

    
    boolean doEdit(InterestRateValueDto interestRateValueDto,Locale locale, HttpServletRequest request) throws Exception;
}
