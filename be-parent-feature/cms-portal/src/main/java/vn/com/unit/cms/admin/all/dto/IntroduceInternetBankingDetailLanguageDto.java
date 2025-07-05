/*******************************************************************************
 * Class        ：IntroduceInternetBankingDetailLanguageDto
 * Created date ：2017/08/23
 * Lasted date  ：2017/08/23
 * Author       ：hoangnp
 * Change log   ：2017/08/23：01-00 hoangnp create a new
 ******************************************************************************/

package vn.com.unit.cms.admin.all.dto;

import java.util.List;

import javax.validation.Valid;

/**
 * IntroduceInternetBankingDetailLanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hoangnp
 */
public class IntroduceInternetBankingDetailLanguageDto {
	/** language code */
    private String languageCode;

    /** introduce Internet banking detail list */
    @Valid
    private List<IntroduceInternetBankingDetailDto> introduceInternetBankingDetailList;

    /**
     * getLanguageCode
     *
     * @return String
     * @author hoangnp
     */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * setLanguageCode
	 *
	 * @param languageCode
	 * @author hoangnp
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * getIntroduceInternetBankingDetailList
	 *
	 * @return List<IntroduceInternetBankingDetailDto>
	 * @author hoangnp
	 */
	public List<IntroduceInternetBankingDetailDto> getIntroduceInternetBankingDetailList() {
		return introduceInternetBankingDetailList;
	}

	/**
	 * setIntroduceInternetBankingDetailList
	 *
	 * @param introduceInternetBankingDetailList
	 * @author hoangnp
	 */
	public void setIntroduceInternetBankingDetailList(
			List<IntroduceInternetBankingDetailDto> introduceInternetBankingDetailList) {
		this.introduceInternetBankingDetailList = introduceInternetBankingDetailList;
	}
    
    
	
}
