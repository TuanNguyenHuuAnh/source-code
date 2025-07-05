/*******************************************************************************
 * Class        ：TreeNodeRes
 * Created date ：2021/02/05
 * Lasted date  ：2021/02/05
 * Author       ：TrieuVD
 * Change log   ：2021/02/05：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.res;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.dto.TreeNodeDto;

/**
 * TreeNodeRes
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public class TreeNodeRes {

    private List<TreeNodeDto> treeData;
    private List<Long> checkedKeys;
}
