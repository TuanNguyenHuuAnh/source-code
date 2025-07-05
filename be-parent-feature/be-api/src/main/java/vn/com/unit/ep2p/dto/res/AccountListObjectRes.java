/*******************************************************************************
 * Class        ：AccountListObjectRes.java
 * Created date ：2020/12/02
 * Lasted date  ：2020/12/02
 * Author       ：SonND
 * Change log   ：2020/12/02：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.res;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.dto.JcaAccountDto;
import vn.com.unit.core.res.ObjectDataRes;

/**
 * AccountListObjectRes.java
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@SuppressWarnings("serial")
@Getter
@Setter
public class AccountListObjectRes extends ObjectDataRes<JcaAccountDto>{

//	private static final long serialVersionUID = -7716642225725364342L;

	private String moreData;
    
	public AccountListObjectRes(String moreData, int totalDatas,List<JcaAccountDto> jcaAccountDto) {
	    super(totalDatas,jcaAccountDto);
	    this.moreData = moreData;
	}
}
