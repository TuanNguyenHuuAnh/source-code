select
	count(*)
FROM
	SLA_DAY_OFF VH
WHERE 	
	VH.VIETNAMESEHOLIDAYDATE = /*vietnameseHolidayDate*/'01-Jan-2017'
	AND VH.COMPANY_ID = /*companyId*/
	AND VH.CALENDAR_TYPE_ID = /*calendarType*/
	;