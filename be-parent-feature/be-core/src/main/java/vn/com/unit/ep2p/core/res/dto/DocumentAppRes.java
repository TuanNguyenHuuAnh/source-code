/*******************************************************************************
 * Class        ：ConstantInfoRes
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：tantm
 * Change log   ：2020/12/23：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.res.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.dto.DocumentDataResultDto;
import vn.com.unit.ep2p.core.efo.dto.EfoOzDocMainFileVersionDto;
import vn.com.unit.workflow.dto.JpmButtonForDocDto;
import vn.com.unit.workflow.dto.JpmButtonWrapper;
import vn.com.unit.workflow.dto.JpmHiTaskDto;

/**
 * ConstantInfoRes
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Getter
@Setter
public class DocumentAppRes<DTO> {
    DTO dto;
    
    DocumentDataResultDto efoDocDto;
    List<JpmHiTaskDto> listProcessHistory;
    List<EfoOzDocMainFileVersionDto> listOzDocVersion;
    JpmButtonWrapper<JpmButtonForDocDto> jpmButtons;
    


}
