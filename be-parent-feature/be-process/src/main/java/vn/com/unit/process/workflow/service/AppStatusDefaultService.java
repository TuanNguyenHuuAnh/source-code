/*******************************************************************************
 * Class        ：AppStatusDefaultService
 * Created date ：2019/08/28
 * Lasted date  ：2019/08/28
 * Author       ：KhuongTH
 * Change log   ：2019/08/28：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.service;

import java.util.List;

import vn.com.unit.workflow.entity.JpmStatusDefault;

/**
 * AppStatusDefaultService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public interface AppStatusDefaultService {

    /**
     * getListDefault
     * @return List<JpmStatusDefault>
     * @author KhuongTH
     */
    public List<JpmStatusDefault> getListDefault();
}
