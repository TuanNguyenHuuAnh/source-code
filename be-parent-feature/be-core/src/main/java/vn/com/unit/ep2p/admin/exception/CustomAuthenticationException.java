/*******************************************************************************
 * Class        ：CustomAuthenticationException
 * Created date ：2019/06/04
 * Lasted date  ：2019/06/04
 * Author       ：HungHT
 * Change log   ：2019/06/04：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * CustomAuthenticationException
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@SuppressWarnings("serial")
public class CustomAuthenticationException extends AuthenticationException {

    private String code;
    private String username;
    private Long companyId;
    private String systemCode;

    /**
     * @param msg
     * @author HungHT
     */
    public CustomAuthenticationException(String code, String username, Long companyId, String systemCode) {
        super(code);
        this.code = code;
        this.username = username;
        this.companyId = companyId;
        this.systemCode = systemCode;
    }

    /**
     * getCode
     * 
     * @return
     * @author HungHT
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Get username
     * 
     * @return String
     * @author HungHT
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get companyId
     * 
     * @return Long
     * @author HungHT
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * Get systemCode
     * 
     * @return String
     * @author HungHT
     */
    public String getSystemCode() {
        return systemCode;
    }
}
