/*******************************************************************************
 * Class        ：OZDocMainFileValidObjectDto
 * Created date ：2020/03/18
 * Lasted date  ：2020/03/18
 * Author       ：taitt
 * Change log   ：2020/03/18：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.List;

/**
 * OZDocMainFileValidObjectDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public class OZDocMainFileValidObjectDto {

    private List<OZDocMainFileValidDto> data;

    
    public List<OZDocMainFileValidDto> getData() {
        return data;
    }

    
    public void setDatas(List<OZDocMainFileValidDto> data) {
        this.data = data;
    }
}
