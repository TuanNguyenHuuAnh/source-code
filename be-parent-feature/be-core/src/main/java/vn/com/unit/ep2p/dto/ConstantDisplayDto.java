/*******************************************************************************
 * Class        ConstantDisplayDto
 * Created date 2017/02/17
 * Lasted date  2017/02/17
 * Author       trieunh <trieunh@unit.com.vn>
 * Change log   2017/02/1701-00 trieunh <trieunh@unit.com.vn> create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.Date;
import java.util.List;

/**
 * ConstantDisplay
 * 
 * @version 01-00
 * @since 01-00
 * @author trieunh
 */
public class ConstantDisplayDto {
    private Long id;
    private String type;
    private String kind;
    private String cat;
    private String code;
    private String catOfficialName;
    private String catOfficialNameVi;
    private String catAbbrName;
    private String catAbbrNameVi;
    private Integer displayOrder;
    private Boolean delFlg;

    private Date deletedDate;

    private String deletedBy;

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    private List<ConstantDisplayDto> datas;

    private int strDelFlg;

    public List<ConstantDisplayDto> getDatas() {
        return datas;
    }

    public void setDatas(List<ConstantDisplayDto> datas) {
        this.datas = datas;
    }

    public int getStrDelFlg() {
        return strDelFlg;
    }

    public void setStrDelFlg(int strDelFlg) {
        this.strDelFlg = strDelFlg;
    }

    public ConstantDisplayDto () {
        
    }
    
    public ConstantDisplayDto (long id, String cat, String code) {
        this.id = id;
        this.cat = cat;
        this.code = code;
    }

    /**
     * Get id
     * @return Long
     * @author trieunh
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  trieunh
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get type
     * @return String
     * @author trieunh
     */
    public String getType() {
        return type;
    }

    /**
     * Set type
     * @param   type
     *          type String
     * @return
     * @author  trieunh
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get kind
     * @return String
     * @author trieunh
     */
    public String getKind() {
        return kind;
    }

    /**
     * Set kind
     * @param   kind
     *          type String
     * @return
     * @author  trieunh
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     * Get cat
     * @return String
     * @author trieunh
     */
    public String getCat() {
        return cat;
    }

    /**
     * Set cat
     * @param   cat
     *          type String
     * @return
     * @author  trieunh
     */
    public void setCat(String cat) {
        this.cat = cat;
    }

    /**
     * Get code
     * @return String
     * @author trieunh
     */
    public String getCode() {
        return code;
    }

    /**
     * Set code
     * @param   code
     *          type String
     * @return
     * @author  trieunh
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Get catOfficialName
     * @return String
     * @author trieunh
     */
    public String getCatOfficialName() {
        return catOfficialName;
    }

    /**
     * Set catOfficialName
     * @param   catOfficialName
     *          type String
     * @return
     * @author  trieunh
     */
    public void setCatOfficialName(String catOfficialName) {
        this.catOfficialName = catOfficialName;
    }

    /**
     * Get catAbbrName
     * @return String
     * @author trieunh
     */
    public String getCatAbbrName() {
        return catAbbrName;
    }

    /**
     * Set catAbbrName
     * @param   catAbbrName
     *          type String
     * @return
     * @author  trieunh
     */
    public void setCatAbbrName(String catAbbrName) {
        this.catAbbrName = catAbbrName;
    }

    /**
     * Get displayOrder
     * @return Integer
     * @author trieunh
     */
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    /**
     * Set displayOrder
     * @param   displayOrder
     *          type Integer
     * @return
     * @author  trieunh
     */
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    /**
     * Get delFlg
     * @return Boolean
     * @author trieunh
     */
    public Boolean getDelFlg() {
        return delFlg;
    }

    /**
     * Set delFlg
     * @param   delFlg
     *          type Boolean
     * @return
     * @author  trieunh
     */
    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    
    public String getCatOfficialNameVi() {
        return catOfficialNameVi;
    }

    
    public void setCatOfficialNameVi(String catOfficialNameVi) {
        this.catOfficialNameVi = catOfficialNameVi;
    }

    
    public String getCatAbbrNameVi() {
        return catAbbrNameVi;
    }

    
    public void setCatAbbrNameVi(String catAbbrNameVi) {
        this.catAbbrNameVi = catAbbrNameVi;
    }
    
    
}
