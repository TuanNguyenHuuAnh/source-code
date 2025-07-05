SELECT  ID                  					AS ID
		,WORKING_DAY							AS CALENDAR_DATE
		,CALENDAR_TYPE_ID						AS CALENDAR_TYPE_ID
		,START_TIME								AS START_TIME
		,END_TIME								AS END_TIME
		,DESCRIPTION							AS DESCRIPTION
		,CREATED_ID								AS CREATED_ID
		,CREATED_DATE							AS CREATED_DATE
		,UPDATED_ID								AS UPDATED_ID
		,UPDATED_DATE							AS UPDATED_DATE

FROM SLA_WORKING_DAY 
WHERE  WORKING_DAY >= /*search.fromDate*/ AND WORKING_DAY <= /*search.toDate*/
/*IF search.calendarTypeId != null && search.calendarTypeId != 0*/
	AND CALENDAR_TYPE_ID = /*search.calendarTypeId*/
	/*END*/
	


	