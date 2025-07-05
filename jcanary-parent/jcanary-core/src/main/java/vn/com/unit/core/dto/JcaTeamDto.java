/*******************************************************************************
 * Class        ：JcaTeamDto
 * Created date ：2020/12/08
 * Lasted date  ：2020/12/08
 * Author       ：MinhNV
 * Change log   ：2020/12/08：01-00 MinhNV create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;


/**
 * JcaTeamDto
 * 
 * @version 01-00
 * @since 01-00
 * @author MinhNV
 */
@Setter
@Getter
@NoArgsConstructor
public class JcaTeamDto extends AbstractTracking{
    
   
    private Long teamId;
    private String code;
    private String name;
    private String nameAbv;
    private String description;
    private Boolean actived;
    private Long companyId;
    private String companyName;
    private List<JcaAccountDto> listAccountOfTeam;
    
    private List<JcaAccountDto> data;
    
   

}
