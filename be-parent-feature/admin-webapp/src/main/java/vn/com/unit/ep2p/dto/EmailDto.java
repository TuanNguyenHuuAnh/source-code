/*******************************************************************************
 * Class        EmailDto
 * Created date 2017/05/19
 * Lasted date  2017/05/19
 * Author       phunghn
 * Change log   2017/05/1901-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import vn.com.unit.common.dto.AttachFileEmailDto;
import vn.com.unit.core.entity.JcaAttachFileEmail;
import vn.com.unit.ep2p.admin.dto.ConstantDisplayDto;

/**
 * EmailDto
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public class EmailDto {

    private String senderAddress;

    private String senderName;

    private String receiveAddress;

    /**
     * @author vunt
     */
    private String toString;

    private String ccString;

    private String bccString;

    private String[] recipientAddress;

    private String[] ccAddress;

    private String[] bccAddress;

    private String additionalData001;

    private String additionalData002;

    private String subject;

    private String textTemplate;

    private String htmlTemplate;

    private Boolean textOnly;

    private Locale locale;

    private List<AttachFileEmailDto> attachments;

    private Map<String, Object> data;

    private String templateFile;

    private String templateCode;

    private String createdBy;

    private String pswEmail;

    private String statusSendMail;

    private int typeContent;

    private String emailContent;

    private boolean flagSendImmediately;

    private Integer sendImmediately;

    private String uuidEmail;

    private List<Long> attachFileId;

    private int numberAttachFile;

    private Long templateId;

    private Long emailId;

    private String statusEmail;

    private Date sendDate;

    private Long businessId;

    private Long senderAccountId;

    private Long companyId;

    private Long branchId;

    private Long departmentId;

    private Long sectionId;

    private Integer deletedBy;

    private Integer deletedDate;

    private List<ConstantDisplayDto> listOption;

    private String sendEmailType;

    private List<JcaAttachFileEmail> listAttach;

    private String mobileNotification;

    private Boolean isUseEmail;

    private String langCode;

    private Map<String, String> contentNotificationMap;
    private Map<String, String> subjectNotificationMap;

    public List<JcaAttachFileEmail> getListAttach() {
        return listAttach;
    }

    public void setListAttach(List<JcaAttachFileEmail> listAttach) {
        this.listAttach = listAttach;
    }

    public boolean isFlagSendImmediately() {
        return flagSendImmediately;
    }

    public void setFlagSendImmediately(boolean flagSendImmediately) {
        this.flagSendImmediately = flagSendImmediately;
    }

    /**
     * Get senderAddress
     * 
     * @return String
     * @author phunghn
     */
    public String getSenderAddress() {
        return senderAddress;
    }

    /**
     * Set senderAddress
     * 
     * @param senderAddress type String
     * @return
     * @author phunghn
     */
    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    /**
     * Get senderName
     * 
     * @return String
     * @author phunghn
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * Set senderName
     * 
     * @param senderName type String
     * @return
     * @author phunghn
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * Get recipientAddress
     * 
     * @return String[]
     * @author phunghn
     */
    public String[] getRecipientAddress() {
        return recipientAddress;
    }

    /**
     * Set recipientAddress
     * 
     * @param recipientAddress type String[]
     * @return
     * @author phunghn
     */
    public void setRecipientAddress(String[] recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    /**
     * Get ccAddress
     * 
     * @return String[]
     * @author phunghn
     */
    public String[] getCcAddress() {
        return ccAddress;
    }

    /**
     * Set ccAddress
     * 
     * @param ccAddress type String[]
     * @return
     * @author phunghn
     */
    public void setCcAddress(String[] ccAddress) {
        this.ccAddress = ccAddress;
    }

    /**
     * Get bccAddress
     * 
     * @return String[]
     * @author phunghn
     */
    public String[] getBccAddress() {
        return bccAddress;
    }

    /**
     * Set bccAddress
     * 
     * @param bccAddress type String[]
     * @return
     * @author phunghn
     */
    public void setBccAddress(String[] bccAddress) {
        this.bccAddress = bccAddress;
    }

    /**
     * Get subject
     * 
     * @return String
     * @author phunghn
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Set subject
     * 
     * @param subject type String
     * @return
     * @author phunghn
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Get textTemplate
     * 
     * @return String
     * @author phunghn
     */
    public String getTextTemplate() {
        return textTemplate;
    }

    /**
     * Set textTemplate
     * 
     * @param textTemplate type String
     * @return
     * @author phunghn
     */
    public void setTextTemplate(String textTemplate) {
        this.textTemplate = textTemplate;
    }

    /**
     * Get htmlTemplate
     * 
     * @return String
     * @author phunghn
     */
    public String getHtmlTemplate() {
        return htmlTemplate;
    }

    /**
     * Set htmlTemplate
     * 
     * @param htmlTemplate type String
     * @return
     * @author phunghn
     */
    public void setHtmlTemplate(String htmlTemplate) {
        this.htmlTemplate = htmlTemplate;
    }

    /**
     * Get textOnly
     * 
     * @return Boolean
     * @author phunghn
     */
    public Boolean getTextOnly() {
        return textOnly;
    }

    /**
     * Set textOnly
     * 
     * @param textOnly type Boolean
     * @return
     * @author phunghn
     */
    public void setTextOnly(Boolean textOnly) {
        this.textOnly = textOnly;
    }

    /**
     * Get locale
     * 
     * @return Locale
     * @author phunghn
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Set locale
     * 
     * @param locale type Locale
     * @return
     * @author phunghn
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * getAttachments
     * 
     * @return
     * @author trieuvd
     */
    public List<AttachFileEmailDto> getAttachments() {
        return attachments;
    }

    /**
     * setAttachments
     * 
     * @param attachments
     * @author trieuvd
     */
    public void setAttachments(List<AttachFileEmailDto> attachments) {
        this.attachments = attachments;
    }

    /**
     * Get data
     * 
     * @return Map<String,Object>
     * @author phunghn
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * Set data
     * 
     * @param data type Map<String,Object>
     * @return
     * @author phunghn
     */
    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    /**
     * Get templateFile
     * 
     * @return String
     * @author phunghn
     */
    public String getTemplateFile() {
        return templateFile;
    }

    /**
     * Set templateFile
     * 
     * @param templateFile type String
     * @return
     * @author phunghn
     */
    public void setTemplateFile(String templateFile) {
        this.templateFile = templateFile;
    }

    /**
     * Get receiveAddress
     * 
     * @return String
     * @author phunghn
     */
    public String getReceiveAddress() {
        return receiveAddress;
    }

    /**
     * Set receiveAddress
     * 
     * @param receiveAddress type String
     * @return
     * @author phunghn
     */
    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getAdditionalData001() {
        return additionalData001;
    }

    public void setAdditionalData001(String additionalData001) {
        this.additionalData001 = additionalData001;
    }

    public String getAdditionalData002() {
        return additionalData002;
    }

    public void setAdditionalData002(String additionalData002) {
        this.additionalData002 = additionalData002;
    }

    public String getCcString() {
        return ccString;
    }

    public void setCcString(String ccString) {
        this.ccString = ccString;
    }

    public String getBccString() {
        return bccString;
    }

    public void setBccString(String bccString) {
        this.bccString = bccString;
    }

    /**
     * Get templateCode
     * 
     * @return String
     * @author HungHT
     */
    public String getTemplateCode() {
        return templateCode;
    }

    /**
     * Set templateCode
     * 
     * @param templateCode type String
     * @return
     * @author HungHT
     */
    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    /**
     * Get createdBy
     * 
     * @return String
     * @author HungHT
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Set createdBy
     * 
     * @param createdBy type String
     * @return
     * @author HungHT
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getPswEmail() {
        return pswEmail;
    }

    public void setPswEmail(String pswEmail) {
        this.pswEmail = pswEmail;
    }

    public String getStatusSendMail() {
        return statusSendMail;
    }

    public void setStatusSendMail(String statusSendMail) {
        this.statusSendMail = statusSendMail;
    }

    public int getTypeContent() {
        return typeContent;
    }

    public void setTypeContent(int typeContent) {
        this.typeContent = typeContent;
    }

    public Integer getSendImmediately() {
        return sendImmediately;
    }

    public void setSendImmediately(Integer sendImmediately) {
        this.sendImmediately = sendImmediately;
    }

    public String getUuidEmail() {
        return uuidEmail;
    }

    public void setUuidEmail(String uuidEmail) {
        this.uuidEmail = uuidEmail;
    }

    public List<Long> getAttachFileId() {
        return attachFileId;
    }

    public void setAttachFileId(List<Long> attachFileId) {
        this.attachFileId = attachFileId;
    }

    public int getNumberAttachFile() {
        return numberAttachFile;
    }

    public void setNumberAttachFile(int numberAttachFile) {
        this.numberAttachFile = numberAttachFile;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getEmailId() {
        return emailId;
    }

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }

//  public String getStatusEmail() {
//      if (statusSendMail.equals(EmailStatusEnum.SUCCESS.toString())) {
//          statusEmail = EmailStatusEnum.SUCCESS.name();
//      } else if (statusSendMail.equals(EmailStatusEnum.SENDING.toString())) {
//          statusEmail = EmailStatusEnum.SENDING.name();
//      } else if (statusSendMail.equals(EmailStatusEnum.SAVED.toString())) {
//          statusEmail = EmailStatusEnum.SAVED.name();
//      } else if (statusSendMail.equals(EmailStatusEnum.FAIL.toString())) {
//          statusEmail = EmailStatusEnum.FAIL.name();
//      } else if (statusSendMail.equals(EmailStatusEnum.CANCEL.toString())) {
//          statusEmail = EmailStatusEnum.CANCEL.name();
//      } else if (statusSendMail.equals(EmailStatusEnum.RESEND.toString())) {
//          statusEmail = EmailStatusEnum.RESEND.name();
//      } else {
//          statusEmail = EmailStatusEnum.ERROR.name();
//      }
//      return statusEmail;
//  }

    public void setStatusEmail(String statusEmail) {
        this.statusEmail = statusEmail;
    }

    /*
     * public String getStatusEmail() { return statusEmail; }
     */

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Long getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(Long senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Integer getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Integer deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Integer getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Integer deletedDate) {
        this.deletedDate = deletedDate;
    }

    public List<ConstantDisplayDto> getListOption() {
        return listOption;
    }

    public void setListOption(List<ConstantDisplayDto> listOption) {
        this.listOption = listOption;
    }

    public String getSendEmailType() {
        return sendEmailType;
    }

    public void setSendEmailType(String sendEmailType) {
        this.sendEmailType = sendEmailType;
    }

    public String getMobileNotification() {
        return mobileNotification;
    }

    public void setMobileNotification(String mobileNotification) {
        this.mobileNotification = mobileNotification;
    }

    public Boolean getIsUseEmail() {
        return isUseEmail;
    }

    public void setIsUseEmail(Boolean isUseEmail) {
        this.isUseEmail = isUseEmail;
    }

    @Override
    public String toString() {
        return "EmailDto [senderAddress=" + senderAddress + ", senderName=" + senderName + ", receiveAddress="
                + receiveAddress + ", ccString=" + ccString + ", bccString=" + bccString + ", subject=" + subject
                + ", emailContent=" + emailContent + ", emailId=" + emailId + ", sendDate=" + sendDate + ", companyId="
                + companyId + ", sendEmailType=" + sendEmailType + ", mobileNotification=" + mobileNotification + "]";
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

    /**
     * Get contentNotificationMap
     * 
     * @return Map<String,String>
     * @author taitt
     */
    public Map<String, String> getContentNotificationMap() {
        return contentNotificationMap;
    }

    /**
     * Set contentNotificationMap
     * 
     * @param contentNotificationMap type Map<String,String>
     * @return
     * @author taitt
     */
    public void setContentNotificationMap(Map<String, String> contentNotificationMap) {
        this.contentNotificationMap = contentNotificationMap;
    }

    /**
     * Get subjectNotificationMap
     * 
     * @return Map<String,String>
     * @author taitt
     */
    public Map<String, String> getSubjectNotificationMap() {
        return subjectNotificationMap;
    }

    /**
     * Set subjectNotificationMap
     * 
     * @param subjectNotificationMap type Map<String,String>
     * @return
     * @author taitt
     */
    public void setSubjectNotificationMap(Map<String, String> subjectNotificationMap) {
        this.subjectNotificationMap = subjectNotificationMap;
    }

    /**
     * @return the toString
     */
    public String getToString() {
        return toString;
    }

    /**
     * @param toString the toString to set
     */
    public void setToString(String toString) {
        this.toString = toString;
    }

    /**
     * @return the statusEmail
     */
    public String getStatusEmail() {
        return statusEmail;
    }
}
