/*******************************************************************************
 * Class        ：TitleService
 * Created date ：2020/03/17
 * Lasted date  ：2020/03/17
 * Author       ：KhuongTH
 * Change log   ：2020/03/17：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.List;

/**
 * TitleService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public interface TitleService {

    /**
     * getHighestTitleByListTitleCode
     * @param titleCodes
     * @param companyId
     * @return
     * @author KhuongTH
     */
    String getHighestTitleByListTitleCode(List<String> titleCodes, Long companyId);
}
