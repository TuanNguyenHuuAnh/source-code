/*******************************************************************************
 * Class        ：AccountDto
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：SonND
 * Change log   ：2020/12/01：01-00 SonND create a new
 ******************************************************************************/

package vn.com.unit.ep2p.core.efo.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * 
 * EfoCategoryDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TrongNV
 */
@Setter
@Getter
@NoArgsConstructor
public class EfoCategoryDto extends AbstractTracking {

    private Long categoryId;
    private String categoryName;
    private Long companyId;
    private String description;
    private Long displayOrder;
    private boolean actived;

    private String companyName;
    private boolean companyAdmin;
    private List<Long> companyIdList;

    private List<EfoCategoryLangDto> listCategoryLangDto;
}
