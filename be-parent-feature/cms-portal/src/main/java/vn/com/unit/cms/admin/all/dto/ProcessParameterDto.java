package vn.com.unit.cms.admin.all.dto;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ProcessParameterDto {
	
	/** Người thực hiện */
	private String userName;
	
	/** Link xử lý*/
	private String url;
	
	/** Bước xử lý*/
	private String action;
	
	/** Người thực hiện tiếp theo*/
	private String fullName;
	
	/** Tên màn hình chức năng*/
	private String actionName;
	
	/** Nội dung*/
	private LinkedHashMap<String, String> content;
	
	/** Nội dung*/
    private String comment;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getFullName() {
		return fullName;
	}

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the actionName
     * @author taitm
     */
    public String getActionName() {
        return actionName;
    }

    /**
     * @param actionName
     *            the actionName to set
     * @author taitm
     */
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    /**
     * @return the content
     * @author taitm
     */
    public HashMap<String, String> getContent() {
        return content;
    }

    /**
     * @param content
     *            the content to set
     * @author taitm
     */
    public void setContent(LinkedHashMap<String, String> content) {
        this.content = content;
    }

    /**
     * @return the comment
     * @author taitm
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment
     *            the comment to set
     * @author taitm
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

}
