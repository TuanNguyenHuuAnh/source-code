/*******************************************************************************
 * Class        ：roomClientDto
 * Created date ：2017/05/12
 * Lasted date  ：2017/05/12
 * Author       ：phunghn
 * Change log   ：2017/05/12：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;
import java.util.List;

/**
 * roomClientDto
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public class RoomClientDto {
    /** id */
    private int id;
    
    /** clientId */
    private String clientid;
    
    /** email */
    private String email;
    
    /** fullname */
    private String fullname;
    
    private String nickname;
    
    /** phone */
    private String phone;
    
    /** agent */
    private String agentName;
    
    /** agentGroup */
    private String agentGroup;    

    /** status */
    private int status;
    
    /** statusName */
    private String statusName;
    
    /** createDate */
    private Date createdDate;
    
    /** disconnected_date */
    private Date disconnectedDate;
    
    /** totalSupport */
    private String totalSupport;
    
    /** totalWait */
    private String totalWait;
    
    /** type_disconnected */
    private int typeDisconnected;
    
    /** typeDisconnectedName */
    private String typeDisconnectedName;
    
    /** assignName*/
    private String assignName;
    
    /**message */
    private String message;
    
    private String productName;
    
    private String customerTypeName;
    
    private String productCategoryName;
    
    private int rowNum;
    
    /** fieldValues */
    private List<String> fieldValues;
    
    /** fieldSearch */
    private String fieldSearch;
    
    private Date fromDate;
    
    private Date toDate;
    
    private String service;
    
    private String agent;
    
    private Integer stt;
    
    private Integer countMessage;
    
    /**
	 * Get message
	 * @return String
	 * @author phunghn
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Set message
	 * @param   message
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
     * Get id
     * @return int
     * @author phunghn
     */
    public int getId() {
        return id;
    }

    /**
     * Set id
     * @param   id
     *          type int
     * @return
     * @author  phunghn
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get clientid
     * @return String
     * @author phunghn
     */
    public String getClientid() {
        return clientid;
    }

    /**
     * Set clientid
     * @param   clientid
     *          type String
     * @return
     * @author  phunghn
     */
    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    /**
     * Get email
     * @return String
     * @author phunghn
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set email
     * @param   email
     *          type String
     * @return
     * @author  phunghn
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get fullname
     * @return String
     * @author phunghn
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * Set fullname
     * @param   fullname
     *          type String
     * @return
     * @author  phunghn
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * Get phone
     * @return String
     * @author phunghn
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set phone
     * @param   phone
     *          type String
     * @return
     * @author  phunghn
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Get agentName
     * @return String
     * @author phunghn
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     * Set agentName
     * @param   agentName
     *          type String
     * @return
     * @author  phunghn
     */
    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    /**
     * Get agentGroup
     * @return String
     * @author phunghn
     */
    public String getAgentGroup() {
        return agentGroup;
    }

    /**
     * Set agentGroup
     * @param   agentGroup
     *          type String
     * @return
     * @author  phunghn
     */
    public void setAgentGroup(String agentGroup) {
        this.agentGroup = agentGroup;
    }

    /**
     * Get status
     * @return int
     * @author phunghn
     */
    public int getStatus() {
        return status;
    }

    /**
     * Set status
     * @param   status
     *          type int
     * @return
     * @author  phunghn
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Get statusName
     * @return String
     * @author phunghn
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * Set statusName
     * @param   statusName
     *          type String
     * @return
     * @author  phunghn
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }


    /**
     * Get disconnectedDate
     * @return Date
     * @author phunghn
     */
    public Date getDisconnectedDate() {
        return disconnectedDate;
    }

    /**
     * Set disconnectedDate
     * @param   disconnectedDate
     *          type Date
     * @return
     * @author  phunghn
     */
    public void setDisconnectedDate(Date disconnectedDate) {
        this.disconnectedDate = disconnectedDate;
    }

    /**
     * Get totalSupport
     * @return String
     * @author phunghn
     */
    public String getTotalSupport() {
        return totalSupport;
    }

    /**
     * Set totalSupport
     * @param   totalSupport
     *          type String
     * @return
     * @author  phunghn
     */
    public void setTotalSupport(String totalSupport) {
        this.totalSupport = totalSupport;
    }

    /**
     * Get typeDisconnected
     * @return int
     * @author phunghn
     */
    public int getTypeDisconnected() {
        return typeDisconnected;
    }

    /**
     * Set typeDisconnected
     * @param   typeDisconnected
     *          type int
     * @return
     * @author  phunghn
     */
    public void setTypeDisconnected(int typeDisconnected) {
        this.typeDisconnected = typeDisconnected;
    }

    /**
     * Get typeDisconnectedName
     * @return String
     * @author phunghn
     */
    public String getTypeDisconnectedName() {
        return typeDisconnectedName;
    }

    /**
     * Set typeDisconnectedName
     * @param   typeDisconnectedName
     *          type String
     * @return
     * @author  phunghn
     */
    public void setTypeDisconnectedName(String typeDisconnectedName) {
        this.typeDisconnectedName = typeDisconnectedName;
    }

    /**
     * Get createdDate
     * @return Date
     * @author phunghn
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Set createdDate
     * @param   createdDate
     *          type Date
     * @return
     * @author  phunghn
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Get totalWait
     * @return String
     * @author phunghn
     */
    public String getTotalWait() {
        return totalWait;
    }

    /**
     * Set totalWait
     * @param   totalWait
     *          type String
     * @return
     * @author  phunghn
     */
    public void setTotalWait(String totalWait) {
        this.totalWait = totalWait;
    }

    /**
     * Get assignName
     * @return String
     * @author phunghn
     */
    public String getAssignName() {
        return assignName;
    }

    /**
     * Set assignName
     * @param   assignName
     *          type String
     * @return
     * @author  phunghn
     */
    public void setAssignName(String assignName) {
        this.assignName = assignName;
    }

	/**
	 * Get productName
	 * @return String
	 * @author phunghn
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * Set productName
	 * @param   productName
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * Get customerTypeName
	 * @return String
	 * @author phunghn
	 */
	public String getCustomerTypeName() {
		return customerTypeName;
	}

	/**
	 * Set customerTypeName
	 * @param   customerTypeName
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}

	/**
	 * Get productCategoryName
	 * @return String
	 * @author phunghn
	 */
	public String getProductCategoryName() {
		return productCategoryName;
	}

	/**
	 * Set productCategoryName
	 * @param   productCategoryName
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}

	/**
	 * Get rowNum
	 * @return Long
	 * @author phunghn
	 */
	public int getRowNum() {
		return rowNum;
	}

	/**
	 * Set rowNum
	 * @param   rowNum
	 *          type Long
	 * @return
	 * @author  phunghn
	 */
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public List<String> getFieldValues() {
		return fieldValues;
	}

	public void setFieldValues(List<String> fieldValues) {
		this.fieldValues = fieldValues;
	}

	public String getFieldSearch() {
		return fieldSearch;
	}

	public void setFieldSearch(String fieldSearch) {
		this.fieldSearch = fieldSearch;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the service
	 * @author taitm
	 */
	public String getService() {
		return service;
	}

	/**
	 * @param service
	 *            the service to set
	 * @author taitm
	 */
	public void setService(String service) {
		this.service = service;
	}

	/**
	 * @return the agent
	 * @author taitm
	 */
	public String getAgent() {
		return agent;
	}

	/**
	 * @param agent the agent to set
	 * @author taitm
	 */
	public void setAgent(String agent) {
		this.agent = agent;
	}

	public Integer getStt() {
		return stt;
	}

	public void setStt(Integer stt) {
		this.stt = stt;
	}

	public Integer getCountMessage() {
		return countMessage;
	}

	public void setCountMessage(Integer countMessage) {
		this.countMessage = countMessage;
	}

}
