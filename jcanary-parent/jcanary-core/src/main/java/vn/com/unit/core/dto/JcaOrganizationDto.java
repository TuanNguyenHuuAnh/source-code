/*******************************************************************************
 * Class        ：JcaOrganizationDto
 * Created date ：2020/12/14
 * Lasted date  ：2020/12/14
 * Author       ：SonND
 * Change log   ：2020/12/14：01-00 SonND create a new
 ******************************************************************************/

package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.common.enumdef.PageMode;
import vn.com.unit.common.tree.TreeNode;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * JcaOrganizationDto
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Setter
@Getter
@NoArgsConstructor
public class JcaOrganizationDto extends AbstractTracking implements TreeNode {

    private Long orgId;
    private String code;
    private String name;
    private String nameAbv;
    private Integer displayOrder;
    private String description;
    private String orgType;
    private String email;
    private String phone;
    private String address;
    private boolean actived;
    private Long companyId;
    private String companyName;
    private Long orgParentId;
    private Long depth;
    private Boolean isLeaf;
    private PageMode mode;

    @Override
    public Long getNodeId() {
        return orgId;
    }

    @Override
    public Long getNodeParentId() {
        return orgParentId;
    }

    @Override
    public String getNodeName() {
        return name;
    }

    @Override
    public Integer getNodeOrder() {
        return displayOrder;
    }

    @Override
    public boolean getSelected() {
        return false;
    }
}
