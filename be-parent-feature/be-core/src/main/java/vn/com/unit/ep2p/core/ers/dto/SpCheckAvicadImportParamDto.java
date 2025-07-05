/***************************************************************
 * @author vunt					
 * @date Apr 19, 2021	
 * @project mbal-core
 * @version 1.0 
 * @description 
 ***************************************************************/
package vn.com.unit.ep2p.core.ers.dto;

import jp.sf.amateras.mirage.annotation.In;

public class SpCheckAvicadImportParamDto {
	@In
    public String sessionKey;
	@In
	public String channel;
}
