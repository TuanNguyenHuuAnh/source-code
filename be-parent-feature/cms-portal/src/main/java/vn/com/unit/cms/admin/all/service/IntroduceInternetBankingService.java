/*******************************************************************************
 * Class        ：IntroduceInternetBankingService
 * Created date ：2017/08/23
 * Lasted date  ：2017/08/23
 * Author       ：hoangnp
 * Change log   ：2017/08/23：01-00 ：hoangnp create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.dto.IntroduceInternetBankingEditDto;
import vn.com.unit.cms.admin.all.dto.IntroduceInternetBankingLanguageSearchDto;
import vn.com.unit.cms.admin.all.dto.IntroduceInternetBankingSearchDto;
import vn.com.unit.cms.admin.all.entity.IntroduceInternetBanking;
import vn.com.unit.common.dto.PageWrapper;

/**
 * IntroduceInternetBankingService
 * 
 * @version 01-00
 * @since 01-00
 * @author hoangnp
 */
public interface IntroduceInternetBankingService {
    
    /**
     * initIntroduceInternetBankingEdit
     *
     * @param mav
     * @param string
     * @author hoangnp
     */
    public void initIntroduceInternetBankingEdit(ModelAndView mav,
            String string);

    /**
     * getIntroduceInternetBanking
     *
     * @param introduceInternetBankingId
     * @param string
     * @return IntroduceInternetBankingEditDto
     * @author hoangnp
     */
    public IntroduceInternetBankingEditDto getIntroduceInternetBanking(
            Long introduceInternetBankingId, String string);

    /**
     * addOrEdit
     *
     * @param introduceInternetBankingEdit
     * @author hoangnp
     */
    public void addOrEdit(
            IntroduceInternetBankingEditDto introduceInternetBankingEdit);

    /**
     * findByCode
     *
     * @param code
     * @return IntroduceInternetBanking
     * @author hoangnp
     */
    public IntroduceInternetBanking findByCode(String code);

    /**
     * search
     *
     * @param page
     * @param introduceInternetBankingSearchDto
     * @return PageWrapper<IntroduceInternetBankingLanguageSearchDto>
     * @author hoangnp
     */
    PageWrapper<IntroduceInternetBankingLanguageSearchDto> search(int page,
            IntroduceInternetBankingSearchDto introduceInternetBankingSearchDto);

    /**
     * deleteIntroduce
     *
     * @param id
     * @author hoangnp
     */
    public void deleteIntroduce(Long id);

}
