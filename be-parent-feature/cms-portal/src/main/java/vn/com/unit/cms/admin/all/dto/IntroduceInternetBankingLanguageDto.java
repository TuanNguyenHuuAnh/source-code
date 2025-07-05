/*******************************************************************************
 * Class        ：IntroduceInternetBankingLanguageDto
 * Created date ：2017/08/23
 * Lasted date  ：2017/08/23
 * Author       ：hoangnp
 * Change log   ：2017/08/23：01-00 ：hoangnp create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

/**
 * IntroduceInternetBankingLanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hoangnp
 */
public class IntroduceInternetBankingLanguageDto {
	
    /** id*/
	private Long id;
	
	/** introduceInternetBankingId*/
	private Long introduceInternetBankingId;
	
	/** languageCode*/
	private String languageCode;
	
	/** title*/
	private String title;
	
	/** description*/
	private String description;

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
	 * getIntroduceInternetBankingId
	 *
	 * @return Long
	 * @author hoangnp
	 */
	public Long getIntroduceInternetBankingId() {
		return introduceInternetBankingId;
	}

	/**
	 * setIntroduceInternetBankingId
	 *
	 * @param introduceInternetBankingId
	 * @author hoangnp
	 */
	public void setIntroduceInternetBankingId(Long introduceInternetBankingId) {
		this.introduceInternetBankingId = introduceInternetBankingId;
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
	 * getTitle
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * setTitle
	 *
	 * @param title
	 * @author hoangnp
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * getDescription
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * setDescription
	 *
	 * @param description
	 * @author hoangnp
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
}
