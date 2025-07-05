/*******************************************************************************
 * Class        ：AccountInfoRes
 * Created date ：2020/12/08
 * Lasted date  ：2020/12/08
 * Author       ：taitt
 * Change log   ：2020/12/08：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.res;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.dto.JcaAccountDto;

/**
 * AccountInfoRes
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class AccountInfoRes extends JcaAccountDto{
 
	private String imgBase64;
	
}
