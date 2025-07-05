/*******************************************************************************
 * Class        AccountTeamSearchDto
 * Created date 2017/09/12
 * Lasted date  2017/09/12
 * Author       phatvt
 * Change log   2017/09/1201-00 phatvt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto;

import java.util.Date;

import vn.com.unit.ep2p.dto.CommonSearchDto;

/**
 * AccountTeamSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
public class TeamSearchDto extends CommonSearchDto {
    private String name;

    private String nameAbv;

    private Date createdDate;

    private String code;

    /**
     * Get createdDate
     * 
     * @return Date
     * @author phatvt
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Set createdDate
     * 
     * @param createdDate type Date
     * @return
     * @author phatvt
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Get name
     * 
     * @return String
     * @author phatvt
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * 
     * @param name type String
     * @return
     * @author phatvt
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get nameAbv
     * 
     * @return String
     * @author phatvt
     */
    public String getNameAbv() {
        return nameAbv;
    }

    /**
     * Set nameAbv
     * 
     * @param nameAbv type String
     * @return
     * @author phatvt
     */
    public void setNameAbv(String nameAbv) {
        this.nameAbv = nameAbv;
    }

    /**
     * Get code
     * 
     * @return String
     * @author HungHT
     */
    public String getCode() {
        return code;
    }

    /**
     * Set code
     * 
     * @param code type String
     * @return
     * @author HungHT
     */
    public void setCode(String code) {
        this.code = code;
    }
}
