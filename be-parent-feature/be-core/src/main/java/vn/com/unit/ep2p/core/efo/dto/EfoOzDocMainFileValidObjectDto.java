/*******************************************************************************
 * Class        ：OZDocMainFileValidObjectDto
 * Created date ：2020/03/18
 * Lasted date  ：2020/03/18
 * Author       ：NhanNV
 * Change log   ：2020/03/18：01-00 NhanNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * OZDocMainFileValidObjectDto
 * 
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */
@Getter
@Setter
public class EfoOzDocMainFileValidObjectDto {

    private List<EfoOzDocMainFileValidDto> datas;
}
