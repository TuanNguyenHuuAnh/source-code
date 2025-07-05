/*******************************************************************************
 * Class        ：ScanResignToPdfService
 * Created date ：2020/08/28
 * Lasted date  ：2020/08/28
 * Author       ：KhuongTH
 * Change log   ：2020/08/28：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;


/**
 * ScanResignToPdfService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public interface ScanResignToPdfService {

    /**
     * scanAndProcessPdfSignFail
     * @param companyId
     * @return
     * @author KhuongTH
     */
    int scanAndProcessPdfSignFail(Long companyId);

}
