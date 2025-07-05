/*******************************************************************************
 * Class        ：IntroduceInternetBankingDetail
 * Created date ：2017/08/23
 * Lasted date  ：2017/08/23
 * Author       ：hoangnp
 * Change log   ：2017/08/23：01-00 hoangnp create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;

/**
 * IntroduceInternetBankingDetail
 * 
 * @version 01-00
 * @since 01-00
 * @author hoangnp
 */
@Table(name="m_introduce_internet_banking_detail")
public class IntroduceInternetBankingDetail extends AbstractTracking {
    
	@Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_INTRODUCE_INTERNET_BANKING_DETAIL")
    private Long id;
	
	@Column(name = "introduce_internet_banking_id")
    private Long introduceInternetBankingId;
	
	@Column(name = "m_language_code")
    private String languageCode;
	
	@Column(name="title")
	private String title;
	
	@Column(name = "content")
    private String content;
	
	@Column(name = "group_content")
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
	
	
}
