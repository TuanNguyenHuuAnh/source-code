/*******************************************************************************
 * Class        ：AccountSearchDto
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

/**
 * 
 * EfoCategorySearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TrongNV
 */
@Setter
@Getter
@NoArgsConstructor
public class EfoCategorySearchDto {
    private String name;
    private String description;
    private Long companyId;
    private Boolean actived;
    
    //Search
    private String fieldSearch;
    private List<String> fieldValues;
    private String companyName;
    private boolean companyAdmin;
    private List<Long> companyIdList;
}
