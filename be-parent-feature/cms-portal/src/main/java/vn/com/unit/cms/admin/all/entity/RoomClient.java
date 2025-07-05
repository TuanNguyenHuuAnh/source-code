/*******************************************************************************
 * Class        ：RoomClient
 * Created date ：2017/05/12
 * Lasted date  ：2017/05/12
 * Author       ：phunghn
 * Change log   ：2017/05/12：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;

/**
 * RoomClient
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
@Table( name = "m_room_client")
public class RoomClient {
    
    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_ROOM_CLIENT")
    private Long id;
        
    @Column(name = "clientid")
    private String clientid;
    
    @Column(name = "fullname")
    private String fullname;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "agent")
    private String agent;
    
    @Column(name = "agent_group")
    private String agentGroup;
    
    @Column(name = "room")
    private String room;
    
    @Column(name = "status")
    private int status;
    
    @Column(name = "created_date")
    private Date createdDate;
    
    @Column(name = "disconnected_date")
    private Date disconnectedDate;
    
    @Column(name = "type_disconnected")
    private int typeDisconnected;

    /**
     * Get id
     * @return Long
     * @author phunghn
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  phunghn
     */
    public void setId(Long id) {
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
     * Get agent
     * @return String
     * @author phunghn
     */
    public String getAgent() {
        return agent;
    }

    /**
     * Set agent
     * @param   agent
     *          type String
     * @return
     * @author  phunghn
     */
    public void setAgent(String agent) {
        this.agent = agent;
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
     * Get room
     * @return String
     * @author phunghn
     */
    public String getRoom() {
        return room;
    }

    /**
     * Set room
     * @param   room
     *          type String
     * @return
     * @author  phunghn
     */
    public void setRoom(String room) {
        this.room = room;
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
}
