/*******************************************************************************
 * Class        ：ActionDto
 * Created date ：2020/04/09
 * Lasted date  ：2020/04/09
 * Author       ：KhuongTH
 * Change log   ：2020/04/09：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.common.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.common.constant.CommonConstant;

/**
 * ConstantDeleteDto
 * 
 * @version 01-00
 * @since 01-00
 * @author LongDCH
 */
@Getter
@Setter
public class ConstantDeleteDto {

	@In
	public String groupCode;
	
	@In
	public String kind;

	@In
	public String code;
	
	@Out
	public int status;
	
	@Out
	public String message;
	


}
