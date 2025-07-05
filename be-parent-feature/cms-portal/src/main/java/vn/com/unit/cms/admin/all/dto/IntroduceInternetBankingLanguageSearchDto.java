/*******************************************************************************
 * Class        ：IntroduceInternetBankingLanguageSearchDto
 * Created date ：2017/08/23
 * Lasted date  ：2017/08/23
 * Author       ：hoangnp
 * Change log   ：2017/08/23：01-00 ：hoangnp create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import vn.com.unit.cms.admin.all.enumdef.IntroduceInternetBankingTypeEnum;

/**
 * IntroduceInternetBankingLanguageSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author hoangnp
 */
public class IntroduceInternetBankingLanguageSearchDto {
	/** id */
	private Long id;

	/** code */
	private String code;

	/** name */
	private String name;

	/** subTitle */
	private String subTitle;

	/** title */
	private String title;

	/** title detail */
	private String titleDetail;

	/** enabled */
	private Long enabled;

	/** description */
	private String description;

	/** status */
	private String status;

	/** createDate */
	private Date createDate;

	/** introductionType */
	private String introductionType;

	/** introductionTypeName */
	private String introductionTypeName;

	/**
	 * getStatus
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * setStatus
	 *
	 * @param status
	 * @author hoangnp
	 */
	public void setStatus(String status) {
		this.status = status;
	}

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
	 * getSubTitle
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getSubTitle() {
		return subTitle;
	}

	/**
	 * setSubTitle
	 *
	 * @param subTitle
	 * @author hoangnp
	 */
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
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
	 * getEnabled
	 *
	 * @return Long
	 * @author hoangnp
	 */
	public Long getEnabled() {
		return enabled;
	}

	/**
	 * setEnabled
	 *
	 * @param enabled
	 * @author hoangnp
	 */
	public void setEnabled(Long enabled) {
		this.enabled = enabled;
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

	/**
	 * getCreateDate
	 *
	 * @return Date
	 * @author hoangnp
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * setCreateDate
	 *
	 * @param createDate
	 * @author hoangnp
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * getTitleDetail
	 *
	 * @return String
	 * @author hoangnp
	 */
	public String getTitleDetail() {
		return titleDetail;
	}

	/**
	 * setTitleDetail
	 *
	 * @param titleDetail
	 * @author hoangnp
	 */
	public void setTitleDetail(String titleDetail) {
		this.titleDetail = titleDetail;
	}

	public String getIntroductionType() {
		return introductionType;
	}

	public void setIntroductionType(String introductionType) {
		this.introductionType = introductionType;
	}

	public String getIntroductionTypeName() {
		for (IntroduceInternetBankingTypeEnum en : IntroduceInternetBankingTypeEnum.values()) {
			if (StringUtils.equals(this.introductionType, en.getName())) {
				introductionTypeName = en.getValue();
				break;
			}
		}
		return introductionTypeName;
	}

	public void setIntroductionTypeName(String introductionTypeName) {
		this.introductionTypeName = introductionTypeName;
	}

}
