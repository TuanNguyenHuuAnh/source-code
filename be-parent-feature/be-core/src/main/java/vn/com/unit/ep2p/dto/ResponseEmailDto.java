/*******************************************************************************
 * Class        ResponseEmailDto
 * Created date 2018/08/23
 * Lasted date  2018/08/23
 * Author       phatvt
 * Change log   2018/08/2301-00 phatvt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

/**
 * ResponseEmailDto
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
public class ResponseEmailDto {

    private String statusCode;
    
    private String status;
    
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
    
    
}
