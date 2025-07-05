DELETE FROM SLA_DAY_OFF
WHERE vietnameseholidaydate = /*vietnameseHolidayDate*/''
	AND COMPANY_ID = /*companyId*/
	AND CALENDAR_TYPE_ID = /*calendarType*/
;
	