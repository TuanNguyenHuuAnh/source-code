/*******************************************************************************
 * Class        CommonServiceImpl
 * Created date 2019/06/24
 * Lasted date  2019/06/24
 * Author       BaoHG
 * Change log   2019/06/2501-00 BaoHG create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.service.impl.JCommonServiceImpl;
import vn.com.unit.common.utils.CommonDateUtil
;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.enumdef.NotificationTimeEnum;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.core.service.JcaConstantService;

/**
 * CommonServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author BaoHG
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
@Primary
public class CommonServiceImpl extends JCommonServiceImpl implements CommonService {
	
	
	@Autowired
	JcaConstantService jcaConstantService;
	
	@Override
	public Date getSystemDate() {
		return CommonDateUtil.getSystemDate();
	}
	
	@Override
	public Date getSystemDateTime() {
		return CommonDateUtil.getSystemDateTime();
	}
	
	@Override
	public Date convertLongToDateTime(Long milis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milis);
		return calendar.getTime();
	}
	
	@Override
	public  String caculatorDateNotifi(Date date,Locale locale) throws ParseException{ //TODO
		Date dateCurrent  = getSystemDateTime();
		String res =null;
		NotificationTimeEnum notifi;
		String timeLeft ;
		
		long diffDate = date.getTime();
		long diffDateCurent = dateCurrent.getTime()	;
		BigDecimal totalTimeLeft = new BigDecimal( diffDateCurent - diffDate);
		BigDecimal month =new BigDecimal(  1000d * 60d * 60d * 24d);
		BigDecimal hh =new BigDecimal(  1000d * 60d * 60d);
		BigDecimal mm =new BigDecimal(  1000d * 60d);
		Long diff = totalTimeLeft.longValue()/month.longValue();
		
		List<JcaConstantDto> listNotifi = jcaConstantService.getListJcaConstantDtoByKind("SLA_NOTI", locale.getLanguage());
		if (diff > 0){
			 notifi = NotificationTimeEnum.NOTIFI_TIME_DAY;
			 timeLeft = diff.toString();
		}else {
			Long diffTime = totalTimeLeft.longValue()/hh.longValue();
			if(diffTime > 1){
				notifi = NotificationTimeEnum.NOTIFI_TIME_HOURS;
				timeLeft = diffTime.toString();
			}
			else {
				Long diffHours =totalTimeLeft.longValue()/mm.longValue();
				notifi = NotificationTimeEnum.NOTIFI_TIME_MINUTES;
				timeLeft = diffHours.toString();
			}
		}
		String notifyType = null;
		String notifiName =null;
		for (JcaConstantDto notify: listNotifi){
			String type = notify.getCode();
			if (type.equals(notifi.toString())){
				notifyType = type;
				notifiName = notify.getName();
			}
		}
		
		
		NotificationTimeEnum actionEnum = Enum.valueOf(NotificationTimeEnum.class, notifyType);
		switch(actionEnum){
			case NOTIFI_TIME_DAY: {
				res = notifiName.concat(":").concat(timeLeft);
			}
			case NOTIFI_TIME_HOURS: {
				res = notifiName.concat(":").concat(timeLeft);
			}
			case NOTIFI_TIME_MINUTES: {
				res = notifiName.concat(":").concat(timeLeft);
			}
		}
			
		return res;
	}
}

