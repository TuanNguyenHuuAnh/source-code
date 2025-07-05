/*******************************************************************************
 * Class        ：EfoFormRegisterRes
 * Created date ：2020/12/24
 * Lasted date  ：2020/12/24
 * Author       ：taitt
 * Change log   ：2020/12/24：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.common.api.dto.ResRESTApi;
import vn.com.unit.ep2p.core.efo.dto.EfoFormRegisterDto;

/**
 * EfoFormRegisterRes
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EfoFormRegisterRes extends ResRESTApi{

    private List<EfoFormRegisterDto> resultObj;
}
