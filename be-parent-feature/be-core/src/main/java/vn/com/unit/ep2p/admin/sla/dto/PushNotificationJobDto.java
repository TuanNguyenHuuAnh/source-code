/*******************************************************************************
 * Class        ：PushNotificationJobDto
 * Created date ：2019/08/30
 * Lasted date  ：2019/08/30
 * Author       ：KhuongTH
 * Change log   ：2019/08/30：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.sla.dto;

import java.util.Date;
import java.util.Map;

/**
 * PushNotificationJobDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public class PushNotificationJobDto {
    
    private Long id;
    private Long accountId; 
    private String title;
    private String data;
    private String description;
    private Date pushTime;
    private Long taskId;
    private Long companyId;
    private Long docId;
    
    private Map<String, String> titles;
    private Map<String, String> descriptions;
    private String langCode;
    
    /** only handle */
    private String firebaseUrl;
    private String authenKey;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getAccountId() {
        return accountId;
    }
    
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getData() {
        return data;
    }
    
    public void setData(String data) {
        this.data = data;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Date getPushTime() {
        return pushTime;
    }
    
    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getCompanyId() {
        return companyId;
    }
    
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
	 * @return the titles
	 */
	public Map<String, String> getTitles() {
		return titles;
	}

	/**
	 * @param titles the titles to set
	 */
	public void setTitles(Map<String, String> titles) {
		this.titles = titles;
	}

	/**
	 * @return the descriptions
	 */
	public Map<String, String> getDescriptions() {
		return descriptions;
	}

	/**
	 * @param descriptions the descriptions to set
	 */
	public void setDescriptions(Map<String, String> descriptions) {
		this.descriptions = descriptions;
	}

	/**
	 * @return the langCode
	 */
	public String getLangCode() {
		return langCode;
	}

	/**
	 * @param langCode the langCode to set
	 */
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public String getFirebaseUrl() {
        return firebaseUrl;
    }

    
    public void setFirebaseUrl(String firebaseUrl) {
        this.firebaseUrl = firebaseUrl;
    }
    
    public String getAuthenKey() {
        return authenKey;
    }

    public void setAuthenKey(String authenKey) {
        this.authenKey = authenKey;
    }

    /**
     * Get docId
     * 
     * @return Long
     * @author KhuongTH
     */
    public Long getDocId() {
        return docId;
    }

    /**
     * Set docId
     * 
     * @param docId
     *            type Long
     * @return
     * @author KhuongTH
     */
    public void setDocId(Long docId) {
        this.docId = docId;
    }

}
