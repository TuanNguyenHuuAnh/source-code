/*******************************************************************************
 * Class        ：IntroduceInternetBankingEditDto
 * Created date ：2017/08/23
 * Lasted date  ：2017/08/23
 * Author       ：hoangnp
 * Change log   ：2017/08/23：01-00 hoangnp create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.List;

/**
 * IntroduceInternetBankingEditDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hoangnp
 */
public class IntroduceInternetBankingEditDto {

	/** id */
	private Long id;

	/** code */
	private String code;

	/** name */
	private String name;

	/** enabled */
	private boolean enabled;

	/** languageCode */
	private String languageCode;

	/** introduceInternetBankingLanguageList */
	private List<IntroduceInternetBankingLanguageDto> introduceInternetBankingLanguageList;

	/** introduceInternetBankingDetailList */
	private List<IntroduceInternetBankingDetailLanguageDto> introduceInternetBankingDetailLanguageList;

	/** url */
	private String url;

	/** requestToken */
	private String requestToken;

	/** introductionType */
	private String introductionType;
	
	/** introductionTypeName */
	private String introductionTypeName;
	
	/**
	 * getId
	 *
	 * @return Long
	 * @author hoangnp
	 */
	public Long getId() {
		return id;
	}

	/**
	 * setId
	 *
	 * @param id
	 * @author hoangnp
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * getCode
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getCode() {
		return code;
	}

	/**
	 * setCode
	 *
	 * @param code
	 * @author hoangnp
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * getName
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getName() {
		return name;
	}

	/**
	 * setName
	 *
	 * @param name
	 * @author hoangnp
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * isEnabled
	 *
	 * @return boolean
	 * @author hoangnp
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * setEnabled
	 *
	 * @param enabled
	 * @author hoangnp
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

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
	 * getUrl
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * setUrl
	 *
	 * @param url
	 * @author hoangnp
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * getIntroduceInternetBankingLanguageList
	 *
	 * @return List<IntroduceInternetBankingLanguageDto>
	 * @author hoangnp
	 */
	public List<IntroduceInternetBankingLanguageDto> getIntroduceInternetBankingLanguageList() {
		return introduceInternetBankingLanguageList;
	}

	/**
	 * setIntroduceInternetBankingLanguageList
	 *
	 * @param introduceInternetBankingLanguageList
	 * @author hoangnp
	 */
	public void setIntroduceInternetBankingLanguageList(
			List<IntroduceInternetBankingLanguageDto> introduceInternetBankingLanguageList) {
		this.introduceInternetBankingLanguageList = introduceInternetBankingLanguageList;
	}

	/**
	 * getIntroduceInternetBankingDetailLanguageList
	 *
	 * @return List<IntroduceInternetBankingDetailLanguageDto>
	 * @author hoangnp
	 */
	public List<IntroduceInternetBankingDetailLanguageDto> getIntroduceInternetBankingDetailLanguageList() {
		return introduceInternetBankingDetailLanguageList;
	}

	/**
	 * setIntroduceInternetBankingDetailLanguageList
	 *
	 * @param introduceInternetBankingDetailLanguageList
	 * @author hoangnp
	 */
	public void setIntroduceInternetBankingDetailLanguageList(
			List<IntroduceInternetBankingDetailLanguageDto> introduceInternetBankingDetailLanguageList) {
		this.introduceInternetBankingDetailLanguageList = introduceInternetBankingDetailLanguageList;
	}

	/**
	 * getRequestToken
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getRequestToken() {
		return requestToken;
	}

	/**
	 * setRequestToken
	 *
	 * @param requestToken
	 * @author hoangnp
	 */
	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}

	public String getIntroductionType() {
		return introductionType;
	}

	public void setIntroductionType(String introductionType) {
		this.introductionType = introductionType;
	}

	public String getIntroductionTypeName() {
		return introductionTypeName;
	}

	public void setIntroductionTypeName(String introductionTypeName) {
		this.introductionTypeName = introductionTypeName;
	}
}
