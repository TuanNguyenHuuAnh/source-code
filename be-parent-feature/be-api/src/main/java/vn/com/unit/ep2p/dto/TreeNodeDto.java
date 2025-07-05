/*******************************************************************************
 * Class        ：TreeNodeDto
 * Created date ：2021/02/05
 * Lasted date  ：2021/02/05
 * Author       ：TrieuVD
 * Change log   ：2021/02/05：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * TreeNodeDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TreeNodeDto {

    private Long key;
    private String title;
    private Boolean isLeaf;
}
