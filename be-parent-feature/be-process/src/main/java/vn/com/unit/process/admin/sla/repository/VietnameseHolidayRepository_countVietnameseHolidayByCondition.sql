select
	count(*)
FROM
	SLA_DAY_OFF VH

WHERE
  VH.VIETNAMESEHOLIDAYDATE IS NOT NULL
  	/*IF vietnameseHolidaySearchDto != null && vietnameseHolidaySearchDto.vietnameseHolidayDate != null */ 	
		AND VH.VIETNAMESEHOLIDAYDATE = /*vietnameseHolidaySearchDto.vietnameseHolidayDate*/
	/*END*/
		
	/*IF vietnameseHolidaySearchDto.description != null */
	AND VH.DESCRIPTION like ('%' || /*vietnameseHolidaySearchDto.description*/ || '%') 
	/*END*/ 
;
