package vn.com.unit.ep2p.dto;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Required;

import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;

//import vn.com.unit.constant.Message;
//import vn.com.unit.constant.MessageList;
//import vn.com.unit.jcanary.entity.AbstractTempEntity;

/**
 * AbstractDTO
 * 
 * @version 01-00
 * @since 01-00
 * @author CongDT
 */
@SuppressWarnings("deprecation")
public class AbstractDTO {

    private Boolean isAllowSave;

    private Boolean isAllowSendForApproval;

    private Boolean isAllowApprove;

    private String action;

    private Integer page;

    private Integer currentPage;

    private Integer sizeOfPage;

    private MessageList messageList;

    private String importNote;

    private String importResultCheck;

    private String url;

    private String uuidString;

    private Boolean isViewAll = false;

    private Boolean isViewDept = false;

    @Required
    public AbstractTempEntity createTempEntity() {
        return null;
    }

    public void addMessage(String status, String content) {

        if (messageList == null) {
            messageList = new MessageList();
        }
        messageList.add(status, String.valueOf(content));

    }

    public void addMessageSuccess(String content) {

        if (messageList == null) {
            messageList = new MessageList();
        }
        messageList.add(Message.SUCCESS, String.valueOf(content));

    }

    public void addMessageError(String content) {

        if (messageList == null) {
            messageList = new MessageList();
        }
        messageList.add(Message.ERROR, String.valueOf(content));

    }

    public void addMessageWarning(String content) {

        if (messageList == null) {
            messageList = new MessageList();
        }
        messageList.add(Message.WARNING, String.valueOf(content));

    }

    public void clearMessageList() {
        if (messageList != null) {
            messageList.setMessages(new ArrayList<>());
        }
    }

    public MessageList getMessageList() {
        return messageList;
    }

    public void setMessageList(MessageList messageList) {
        this.messageList = messageList;
    }

    /**
     * @return the importNote
     */
    public String getImportNote() {
        return importNote;
    }

    /**
     * @param importNote the importNote to set
     */
    public void setImportNote(String importNote) {
        this.importNote = importNote;
    }

    /**
     * @return the importResultCheck
     */
    public String getImportResultCheck() {
        return importResultCheck;
    }

    /**
     * @param importResultCheck the importResultCheck to set
     */
    public void setImportResultCheck(String importResultCheck) {
        this.importResultCheck = importResultCheck;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getSizeOfPage() {
        return sizeOfPage;
    }

    public void setSizeOfPage(Integer sizeOfPage) {
        this.sizeOfPage = sizeOfPage;
    }

    public Boolean getIsAllowSave() {
        return isAllowSave;
    }

    public void setIsAllowSave(Boolean isAllowSave) {
        this.isAllowSave = isAllowSave;
    }

    public Boolean getIsAllowSendForApproval() {
        return isAllowSendForApproval;
    }

    public void setIsAllowSendForApproval(Boolean isAllowSendForApproval) {
        this.isAllowSendForApproval = isAllowSendForApproval;
    }

    public Boolean getIsAllowApprove() {
        return isAllowApprove;
    }

    public void setIsAllowApprove(Boolean isAllowApprove) {
        this.isAllowApprove = isAllowApprove;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getUuidString() {
        return uuidString;
    }

    public void setUuidString(String uuidString) {
        this.uuidString = uuidString;
    }

    /**
     * Get isViewAll
     * 
     * @return Boolean
     * @author VinhLT
     */
    public Boolean getIsViewAll() {
        return isViewAll;
    }

    /**
     * Set isViewAll
     * 
     * @param isViewAll type Boolean
     * @return
     * @author VinhLT
     */
    public void setIsViewAll(Boolean isViewAll) {
        this.isViewAll = isViewAll;
    }

    /**
     * Get isViewDept
     * 
     * @return Boolean
     * @author VinhLT
     */
    public Boolean getIsViewDept() {
        return isViewDept;
    }

    /**
     * Set isViewDept
     * 
     * @param isViewDept type Boolean
     * @return
     * @author VinhLT
     */
    public void setIsViewDept(Boolean isViewDept) {
        this.isViewDept = isViewDept;
    }

}
