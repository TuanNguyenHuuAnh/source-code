
package vn.com.unit.cms.admin.all.service;

import java.util.List;

import vn.com.unit.cms.admin.all.dto.InterestRateTitleDto;

public interface InterestRateTitleService {

    /** findInterestRateTitleByType
     *
     * @param type
     * @author nhutnn
     */
    List<InterestRateTitleDto> findInterestRateTitleByType(String type, Long customerTypeId);

    /** deleteByListId
     *
     * @param lstId
     * @author nhutnn
     */
    void deleteByListId(List<Long> lstId);

    /** saveInterestRateTitle
     *
     * @param interestRateTitleDto
     * @throws Exception
     * @author nhutnn
     */
    void saveInterestRateTitle(InterestRateTitleDto interestRateTitleDto) throws Exception;

    /** findInterestRateTitleByDto
     *
     * @param interestRateTitleDto
     * @author nhutnn
     */
    List<InterestRateTitleDto> findInterestRateTitleByDto(InterestRateTitleDto interestRateTitleDto);  
}
