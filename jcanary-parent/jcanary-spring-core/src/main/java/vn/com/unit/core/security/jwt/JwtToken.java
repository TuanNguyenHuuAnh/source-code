/*******************************************************************************
 * Class        ：JwtToken
 * Created date ：2020/12/03
 * Lasted date  ：2020/12/03
 * Author       ：KhoaNA
 * Change log   ：2020/12/03：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.core.security.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JwtToken
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class JwtToken {

    private String idToken;

    public JwtToken(String idToken) {
        this.idToken = idToken;
    }

    @JsonProperty("id_token")
    public String getIdToken() {
        return idToken;
    }

    void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
