SELECT 
	count(cal.ID) 
FROM  
	SLA_CALENDAR_TYPE cal
INNER JOIN JCA_COMPANY COM
    ON COM.ID = cal.COMPANY_ID
    AND COM.DELETED_ID = 0
WHERE
	cal.DELETED_ID = 0
	/*BEGIN*/
	AND 
		(
			/*IF searchDto.calendarTypeCode != null && searchDto.calendarTypeCode != ''*/
			OR UPPER(replace(cal.CODE,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*searchDto.calendarTypeCode*/), '%' ))
			/*END*/
			
			/*IF searchDto.calendarTypeName != null && searchDto.calendarTypeName != ''*/
			OR UPPER(replace(cal.NAME,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*searchDto.calendarTypeName*/), '%' ))
			/*END*/
			
			/*IF searchDto.description != null && searchDto.description != ''*/
			OR UPPER(replace(cal.DESCRIPTION,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*searchDto.description*/), '%' ))
			/*END*/
		)
	/*END*/
	/*IF searchDto.companyId != null && searchDto.companyId != 0*/
	AND cal.COMPANY_ID = /*searchDto.companyId*/
	/*END*/
