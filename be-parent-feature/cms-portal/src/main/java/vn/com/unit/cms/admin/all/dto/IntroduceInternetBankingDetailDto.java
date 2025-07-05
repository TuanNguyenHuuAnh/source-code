/*******************************************************************************
 * Class        ：IntroduceInternetBankingDetailDto
 * Created date ：2017/08/23
 * Lasted date  ：2017/08/23
 * Author       ：hoangnp
 * Change log   ：2017/08/23：01-00 hoangnp create a new
 ******************************************************************************/

package vn.com.unit.cms.admin.all.dto;

/**
 * IntroduceInternetBankingDetailDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hoangnp
 */
public class IntroduceInternetBankingDetailDto {
	/** id*/
	private Long id;
	
	/** introduceInternetBankingId*/
	private Long introduceInternetBankingId;
	
	/** languageCode*/
	private String languageCode;
	
	/** title*/
	private String title;
	
	/** content*/
	private String content;
	
	/** groupContent*/
	private String groupContent;

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
	 * getContent
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getContent() {
		return content;
	}

	/**
	 * setContent
	 *
	 * @param content
	 * @author hoangnp
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * getGroupContent
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getGroupContent() {
		return groupContent;
	}

	/**
	 * setGroupContent
	 *
	 * @param groupContent
	 * @author hoangnp
	 */
	public void setGroupContent(String groupContent) {
		this.groupContent = groupContent;
	}
	
	

}
