/*******************************************************************************
 * Class        ：JcaPositionDto
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：MinhNV
 * Change log   ：2020/12/01：01-00 MinhNV create a new
 ******************************************************************************/

package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.common.enumdef.PageMode;
import vn.com.unit.common.tree.TreeNode;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaPositionDto
 * 
 * @version 01-00
 * @since 01-00
 * @author MinhNV
 */
@Setter
@Getter
@NoArgsConstructor
public class JcaPositionDto extends AbstractTracking implements TreeNode {
    private Long positionId;
    private String code;
    private String name;
    private String nameAbv;
    private String description;
    private Boolean actived ;
    private Long companyId;
    private Long positionParentId;
    private Integer positionOrder;
    private Integer positionLevel;
    private String positionType;
    private String url;
    private String companyName;
    private PageMode mode;
    
    @Override
    public Long getNodeId() {
        return positionId;
    }
    @Override
    public Long getNodeParentId() {
        return positionParentId;
    }
    @Override
    public String getNodeName() {
        return name;
    }
    @Override
    public Integer getNodeOrder() {
        return positionOrder;
    }
    @Override
    public boolean getSelected() {
        return false;
    }
    public void setCode(String code) {
        this.code=code.toUpperCase();
       
    }
}