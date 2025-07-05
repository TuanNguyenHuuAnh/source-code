/***************************************************************
 * @author vunt					
 * @date May 4, 2021	
 * @project mbal-core
 * @version 1.0 
 * @description 
 ***************************************************************/
package vn.com.unit.ep2p.core.ers.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;

public class SpProcessAgencyParamDto {
    @In
    public String agentCode;

    @In
    public String agentType;

    @In
    public Long userId;

    @In
    public Long id;

    @In
    public int submit;
    
    @Out
    public int results;
}
