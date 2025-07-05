package vn.com.unit.cms.admin.all.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;

@Table(name = "M_SOCKET_SERVER")
public class SocketServer {
	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_TERM")
	private Long id;

	@Column(name = "agent")
	private String agent;

	@Column(name = "socketid")
	private String socketId;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "disconnected_date")
	private Date disconnectedDate;

	/**
	 * @return the id
	 * @author taitm
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 * @author taitm
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the socketId
	 * @author taitm
	 */
	public String getSocketId() {
		return socketId;
	}

	/**
	 * @param socketId the socketId to set
	 * @author taitm
	 */
	public void setSocketId(String socketId) {
		this.socketId = socketId;
	}

	/**
	 * @return the createdDate
	 * @author taitm
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 * @author taitm
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the disconnectedDate
	 * @author taitm
	 */
	public Date getDisconnectedDate() {
		return disconnectedDate;
	}

	/**
	 * @param disconnectedDate the disconnectedDate to set
	 * @author taitm
	 */
	public void setDisconnectedDate(Date disconnectedDate) {
		this.disconnectedDate = disconnectedDate;
	}

}
