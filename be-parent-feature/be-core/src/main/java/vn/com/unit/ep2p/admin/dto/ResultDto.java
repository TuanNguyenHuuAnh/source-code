/*******************************************************************************
 * Class        ：ResultDto
 * Created date ：2019/04/19
 * Lasted date  ：2019/04/19
 * Author       ：HungHT
 * Change log   ：2019/04/19：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto;


/**
 * ResultDto
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class ResultDto {

    private int status;
    private String message;
    private Long id;
    
    /**
     * Get status
     * @return int
     * @author HungHT
     */
    public int getStatus() {
        return status;
    }
    
    /**
     * Set status
     * @param   status
     *          type int
     * @return
     * @author  HungHT
     */
    public void setStatus(int status) {
        this.status = status;
    }
    
    /**
     * Get message
     * @return String
     * @author HungHT
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * Set message
     * @param   message
     *          type String
     * @return
     * @author  HungHT
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get id
     * @return Long
     * @author HungHT
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  HungHT
     */
    public void setId(Long id) {
        this.id = id;
    }
}
