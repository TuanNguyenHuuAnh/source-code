/*******************************************************************************
 * Class        ：EfoFormRegisterDto
 * Created date ：2020/12/24
 * Lasted date  ：2020/12/24
 * Author       ：taitt
 * Change log   ：2020/12/24：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * EfoFormRegisterDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EfoFormRegisterDto{

    private String reportType;
    private String reportDesc;
    private String reportPath;
    private String reportName;
    private String formName;
    private String usedFlag;
    private String deviceType;
    private String formType;
    
    
}
