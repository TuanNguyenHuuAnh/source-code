/*******************************************************************************
 * Class        ：JpmHiProcessService
 * Created date ：2020/12/14
 * Lasted date  ：2020/12/14
 * Author       ：KhuongTH
 * Change log   ：2020/12/14：01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import vn.com.unit.workflow.entity.JpmProcess;

/**
 * JpmHiProcessService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmHiProcessService {

    /**
     * <p>
     * Save jpm hi process.
     * </p>
     *
     * @param jpmProcess
     *            type {@link JpmProcess}
     * @author KhuongTH
     */
    void saveJpmHiProcess(JpmProcess jpmProcess);
}