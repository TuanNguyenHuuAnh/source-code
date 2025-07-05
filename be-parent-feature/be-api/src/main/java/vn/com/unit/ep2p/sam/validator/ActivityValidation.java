package vn.com.unit.ep2p.sam.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;

import vn.com.unit.cms.core.module.sam.dto.ActErrMessageDto;
import vn.com.unit.cms.core.module.sam.dto.ActivitityRequest;

/**
 * @author ntr.bang
 * @create date 2023/10/23
 */
public class ActivityValidation {
	private static final String DATE_FORMAT = "MM/dd/yyyy";
	/**
	 * Validate for activity creation
	 * @param messageSource
	 * @param locale
	 * @param dto
	 * @return
	 */
	public static ActErrMessageDto validate(MessageSource messageSource, Locale locale, ActivitityRequest dto) {
		ActErrMessageDto errMsg = new ActErrMessageDto();
		Date planDate = convertToDate(dto.getPlanDate(), DATE_FORMAT);
		// Partner
		if(StringUtils.isBlank(dto.getPartner())) {
			errMsg.setMessage(messageSource.getMessage("activity.require.input.information", null, locale));
			return errMsg;
		}
		// Zone
		else if (StringUtils.isBlank(dto.getZone())) {
			errMsg.setMessage(messageSource.getMessage("activity.require.input.information", null, locale));
			return errMsg;
		}
		// BU
		else if (StringUtils.isBlank(dto.getBu())) {
			errMsg.setMessage(messageSource.getMessage("activity.require.input.information", null, locale));
			return errMsg;
		}
		// CATEGORY
		else if (dto.getCategoryId() <= 0) {
			errMsg.setMessage(messageSource.getMessage("activity.require.input.information", null, locale));
			return errMsg;
		}
		// Subject
		else if (StringUtils.isBlank(dto.getSubject())) {
			errMsg.setMessage(messageSource.getMessage("activity.require.input.information", null, locale));
			return errMsg;
		}
		// Content
		else if (StringUtils.isBlank(dto.getContent())) {
			errMsg.setMessage(messageSource.getMessage("activity.require.input.information", null, locale));
			return errMsg;
		}
		// Type
		else if (StringUtils.isBlank(dto.getType())) {
			errMsg.setMessage(messageSource.getMessage("activity.require.input.information", null, locale));
			return errMsg;
		}
		// Form
		else if (StringUtils.isBlank(dto.getForm())) {
			errMsg.setMessage(messageSource.getMessage("activity.require.input.information", null, locale));
			return errMsg;
		}
		// Plan date
		else if (StringUtils.isBlank(dto.getPlanDate())) {
			errMsg.setMessage(messageSource.getMessage("activity.require.input.information", null, locale));
			return errMsg;
		}
		else if (planDate.compareTo(convertDateToDate(new Date(), DATE_FORMAT)) < 0) {
			errMsg.setMessage(messageSource.getMessage("activity.require.plan.date.after.current", null, locale));
			return errMsg;
		}
		else if (dto.getPersonNumberPlan() <= 0) {
			errMsg.setMessage(messageSource.getMessage("activity.require.input.information", null, locale));
			return errMsg;
		}
		else if (dto.getCostAmtPlan() < 0) {
			errMsg.setMessage(messageSource.getMessage("activity.require.input.information", null, locale));
			return errMsg;
		}
		else if (dto.getSalesAmtPlan() < 0) {
			errMsg.setMessage(messageSource.getMessage("activity.require.input.information", null, locale));
			return errMsg;
		}
		else if (StringUtils.isBlank(dto.getParticipants())) {
			errMsg.setMessage(messageSource.getMessage("activity.require.input.information", null, locale));
			return errMsg;
		}
		return errMsg;
	}
	
	/**
	 * validateChangeStatus
	 * @param messageSource
	 * @param locale
	 * @param dto
	 * @return
	 */
	public static ActErrMessageDto validateChangeStatus(MessageSource messageSource, Locale locale, ActivitityRequest dto) {
		ActErrMessageDto errMsg = new ActErrMessageDto();
		if (dto.getNewStatusId() != 4) {
			return errMsg;
		}
		Date actualDate = convertToDate(dto.getActualDate(), DATE_FORMAT);
		
		// Plan date
		if (StringUtils.isBlank(dto.getActualDate())) {
			errMsg.setMessage(messageSource.getMessage("activity.require.input.information", null, locale));
			return errMsg;
		}
		else if (actualDate.after(new Date())) {
			errMsg.setMessage(messageSource.getMessage("activity.require.actual.date.before.current", null, locale));
			return errMsg;
		}
		else if (dto.getPersonNumberActual() <= 0) {
			errMsg.setMessage(messageSource.getMessage("activity.require.input.information", null, locale));
			return errMsg;
		}
		else if (dto.getCostAmtActual() < 0) {
			errMsg.setMessage(messageSource.getMessage("activity.require.input.information", null, locale));
			return errMsg;
		}
		else if (dto.getSalesAmtActual() < 0) {
			errMsg.setMessage(messageSource.getMessage("activity.require.input.information", null, locale));
			return errMsg;
		}
		return errMsg;
	}
	
	/**
	 * Convert date
	 * @param dateVal
	 * @param format
	 * @return
	 */
	private static Date convertToDate(String dateVal, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date;
        try{
        	date = sdf.parse(dateVal);
        } catch (ParseException e){
        	date = null;
        }
        return date;
	}
	
	/**
	 * convertDateToDate
	 * @param dateVal
	 * @param format
	 * @return
	 */
	private static Date convertDateToDate(Date dateVal, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date;
        try{
        	String dateF = sdf.format(dateVal);
        	date = sdf.parse(dateF);
        } catch (ParseException e){
        	date = null;
        }
        return date;
	}
}
