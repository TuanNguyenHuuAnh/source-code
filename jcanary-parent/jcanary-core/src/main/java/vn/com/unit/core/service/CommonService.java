/*******************************************************************************
 * Class        CommonService
 * Created date 2019/06/24
 * Lasted date  2019/06/24
 * Author       BaoHG
 * Change log   2019/06/2501-00 BaoHG create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import vn.com.unit.common.service.JCommonService;

/**
 * CommonService
 * 
 * @version 01-00
 * @since 01-00
 * @author BaoHG
 */
public interface CommonService extends JCommonService{
	
	/**
	 * Get system date without time 
	 * 
	 * @return Date
	 * @author KhoaNA
	 */
	public Date getSystemDate();
	
	/**
	 * Get system date time 
	 * 
	 * @return Date
	 * @author KhoaNA
	 */
	public Date getSystemDateTime();
	
	/**
	 * Convert long to date time 
	 * 
	 * @param  milis
     * 			type Long
	 * @return Date
	 * @author BaoHG
	 */
	public Date convertLongToDateTime(Long milis);
	
	public  String caculatorDateNotifi(Date date,Locale locale) throws ParseException;
}
