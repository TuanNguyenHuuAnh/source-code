/*******************************************************************************
 * Class        ：BatchRecruitingService
 * Created date ：2020/02/05
 * Lasted date  ：2020/02/05
 * Author       ：KhuongTH
 * Change log   ：2020/02/05：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import org.activiti.engine.delegate.DelegateExecution;

/**
 * BatchRecruitingService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public interface RecruitmentService {

    void getValueStepByTitle(DelegateExecution execution) throws Exception;
    
    void setHighestTitle(DelegateExecution execution) throws Exception;
}
