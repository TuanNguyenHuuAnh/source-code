/*******************************************************************************
 * Class        ：EfoFormRegisterSvcService
 * Created date ：2020/12/31
 * Lasted date  ：2020/12/31
 * Author       ：taitt
 * Change log   ：2020/12/31：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.service;

import java.util.List;

import vn.com.unit.common.api.dto.ResRESTApi;
import vn.com.unit.core.storage.dto.AbstractResultUpload;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.efo.dto.EfoFormRegisterSearchDto;
import vn.com.unit.ep2p.core.efo.entity.EfoComponent;

/**
 * EfoFormRegisterSvcService
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface EfoFormRegisterSvcService {

    public <T extends AbstractResultUpload> T uploadFileImageFormRegister(byte[] base64Image,Long companyId
            ,String businessCode,String formName) throws DetailException;
    
    public List<EfoComponent> getComponentListfromOZRepository(Long formId, String formName, String formFileName, String formOzCategory, Long companyId) throws DetailException;
    
    public <T extends ResRESTApi> T getEfoFormRegisterDtoListOZRepository(EfoFormRegisterSearchDto search) throws DetailException ;
}
