/*******************************************************************************
 * Class        ：MessageDto
 * Created date ：2017/05/12
 * Lasted date  ：2017/05/12
 * Author       ：phunghn
 * Change log   ：2017/05/12：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.Date;

/**
 * MessageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public class MessageDto {
	private String messageid;
	private String clientid;
	private String agentName;
	private String agent;
	private String message;
	private int type;
	private String fullname;
	private String timeSend;
	private String typeName;
	private Date createdDate;
	private int typeMessage;
	private String oldAgent;
	private String oldAgentName;

	/**
	 * Get clientid
	 * 
	 * @return String
	 * @author phunghn
	 */
	public String getClientid() {
		return clientid;
	}

	/**
	 * Set clientid
	 * 
	 * @param clientid type String
	 * @return
	 * @author phunghn
	 */
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	/**
	 * Get agentName
	 * 
	 * @return String
	 * @author phunghn
	 */
	public String getAgentName() {
		return agentName;
	}

	/**
	 * Set agentName
	 * 
	 * @param agentName type String
	 * @return
	 * @author phunghn
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	/**
	 * Get message
	 * 
	 * @return String
	 * @author phunghn
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Set message
	 * 
	 * @param message type String
	 * @return
	 * @author phunghn
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Get type
	 * 
	 * @return int
	 * @author phunghn
	 */
	public int getType() {
		return type;
	}

	/**
	 * Set type
	 * 
	 * @param type type int
	 * @return
	 * @author phunghn
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Get typeName
	 * 
	 * @return String
	 * @author phunghn
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * Set typeName
	 * 
	 * @param typeName type String
	 * @return
	 * @author phunghn
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * Get createdDate
	 * 
	 * @return Date
	 * @author phunghn
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * Set createdDate
	 * 
	 * @param createdDate type Date
	 * @return
	 * @author phunghn
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Get typeMessage
	 * 
	 * @return int
	 * @author phunghn
	 */
	public int getTypeMessage() {
		return typeMessage;
	}

	/**
	 * Set typeMessage
	 * 
	 * @param typeMessage type int
	 * @return
	 * @author phunghn
	 */
	public void setTypeMessage(int typeMessage) {
		this.typeMessage = typeMessage;
	}

	/**
	 * @return the messageid
	 * @author taitm
	 */
	public String getMessageid() {
		return messageid;
	}

	/**
	 * @param messageid the messageid to set
	 * @author taitm
	 */
	public void setMessageid(String messageid) {
		this.messageid = messageid;
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

	/**
	 * @return the fullname
	 * @author taitm
	 */
	public String getFullname() {
		return fullname;
	}

	/**
	 * @param fullname the fullname to set
	 * @author taitm
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * @return the timeSend
	 * @author taitm
	 */
	public String getTimeSend() {
		return timeSend;
	}

	/**
	 * @param timeSend the timeSend to set
	 * @author taitm
	 */
	public void setTimeSend(String timeSend) {
		this.timeSend = timeSend;
	}

	public String getOldAgent() {
		return oldAgent;
	}

	public void setOldAgent(String oldAgent) {
		this.oldAgent = oldAgent;
	}

	public String getOldAgentName() {
		return oldAgentName;
	}

	public void setOldAgentName(String oldAgentName) {
		this.oldAgentName = oldAgentName;
	}

}
