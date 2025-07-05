/*******************************************************************************
 * Class        :StyleService
 * Created date :2019/06/10
 * Lasted date  :2019/06/10
 * Author       :HungHT
 * Change log   :2019/06/10:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import vn.com.unit.core.dto.JcaStyleDto;

/**
 * StyleService
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public interface StyleService {   

    /**
     * getStyleList
     * 
     * @return
     * @author HungHT
     */
    public List<JcaStyleDto> getStyleList();
}