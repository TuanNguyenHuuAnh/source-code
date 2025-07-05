SELECT 
	cal.ID                  		AS ID
	, cal.CODE						AS CODE
	, cal.CODE						AS ALERT_TYPE
	, cal.NAME						AS NAME
	, cal.DESCRIPTION				AS DESCRIPTION
	, cal.ACTIVED					AS ACTIVED
	, cal.COMPANY_ID				AS COMPANY_ID	
	, cal.WORKING_HOURS				AS WORKING_HOURS	
	, COMPANY.NAME					AS COMPANY_NAME	
	, cal.WORKING_HOURS				AS WORKINGHOURS	
	, COMPANY.NAME					AS COMPANYNAME	
FROM  
	SLA_CALENDAR_TYPE cal
INNER JOIN JCA_COMPANY COMPANY 
    ON COMPANY.ID = cal.COMPANY_ID
    AND COMPANY.DELETED_ID = 0
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


/*IF orders != null*/
ORDER BY /*$orders*/cal.ID
-- ELSE ORDER BY cal.SORT ASC
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/