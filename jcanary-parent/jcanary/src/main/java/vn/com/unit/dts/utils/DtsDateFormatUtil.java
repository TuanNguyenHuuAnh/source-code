/*******************************************************************************
 * Class        ：DtsDateFormatUtil
 * Created date ：2020/11/10
 * Lasted date  ：2020/11/10
 * Author       ：taitt
 * Change log   ：2020/11/10：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.dts.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * DtsDateFormatUtil
 * 
 * @version 01-00
 * @since 01-00
 * @author tritv
 */
public class DtsDateFormatUtil extends DateFormatUtils{
	
	public static String fullTimeZone()
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		String timestamp = simpleDateFormat.format(new Date());
		return timestamp;
	}
}
