/*******************************************************************************
 * Class        ：ExchangeRateService
 * Created date ：2017/04/19
 * Lasted date  ：2017/04/26
 * Author       ：phunghn
 * Change log   ：2017/04/26：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.util.List;

import vn.com.unit.cms.admin.all.dto.ExchangeRateDto;
import vn.com.unit.cms.admin.all.dto.ExchangeRateListDto;
import vn.com.unit.cms.admin.all.dto.ExchangeRateSearchDto;
import vn.com.unit.cms.admin.all.dto.ExchnageRateHistoryDto;
import vn.com.unit.common.dto.PageWrapper;

/**
 * ExchangeRateService
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public interface ExchangeRateService {
    /**
     * findAllActive
     *
     * @param page
     * @param exRateDto
     * @return PageWrapper<ExchangeRateDto>
     * @author phunghn
     */
    public PageWrapper<ExchangeRateDto> findAllActive(int page, ExchangeRateSearchDto exRateDto);

    /**
     * getListExchangeRateById
     *
     * @param id
     * @param langeCode
     * @return List<ExchangeRateDto>
     * @author phunghn
     */
    public List<ExchangeRateDto> getListExchangeRateById(Long id, String langeCode);

    /**
     * CreateOrEditExchangeRate
     *
     * @param exchangeRateListDto
     * @author phunghn
     */
    public void CreateOrEditExchangeRate(ExchangeRateListDto exchangeRateListDto);

    /**
     * findAllActiveHistory
     *
     * @param page
     * @param exRateDto
     * @return PageWrapper<ExchnageRateHistoryDto>
     * @author phunghn
     */
    public PageWrapper<ExchnageRateHistoryDto> findAllActiveHistory(int page, ExchangeRateSearchDto exRateDto);
}
