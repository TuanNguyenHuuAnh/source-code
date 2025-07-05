SELECT VIETNAMESEHOLIDAYDATE VIETNAMESE_HOLIDAY_DATE , DESCRIPTION
 FROM 
	(SELECT ROWNUM rnum, a.*
	FROM
		(select
			VH.*
		FROM
			SLA_DAY_OFF VH
		WHERE	
				VH.VIETNAMESEHOLIDAYDATE IS NOT NULL
				/*IF vietnameseHolidaySearchDto.vietnameseHolidayDate != null */ 	
				AND VH.VIETNAMESEHOLIDAYDATE = /*vietnameseHolidaySearchDto.vietnameseHolidayDate*/
				/*END*/				
				/*IF vietnameseHolidaySearchDto.description != null */
				AND VH.Description like ('%' || /*vietnameseHolidaySearchDto.description*/ || '%') 
				/*END*/ 
				) a  )
  WHERE rnum BETWEEN /*startIndex*/ AND /*endIndex*/ ;