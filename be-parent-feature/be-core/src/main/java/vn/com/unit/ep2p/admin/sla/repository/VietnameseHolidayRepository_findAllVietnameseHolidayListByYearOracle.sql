WITH DEPT_COUNT AS (
SELECT TO_DATE(/*vietnameseHolidaySearchDto.firstDate*/'','DD-MM-YYYY') + ROWNUM -1 AS vietnamese_holiday_date
 FROM ALL_OBJECTS 
 WHERE ROWNUM <= TO_DATE(/*vietnameseHolidaySearchDto.lastDate*/'','DD-MM-YYYY') - TO_DATE(/*vietnameseHolidaySearchDto.firstDate*/'','DD-MM-YYYY')+1 )
 
 SELECT  D.*,
 Extract( Month from vietnamese_holiday_date) as month,
 Extract( DAY from vietnamese_holiday_date) as day, NVL2(V.DAY_OFF,'true', 'false') AS is_holiday ,
 V.description
 FROM DEPT_COUNT D
 LEFT JOIN 
 (SELECT * FROM SLA_DAY_OFF jca 
 	WHERE jca.calendar_type_id = /*vietnameseHolidaySearchDto.calendarType*/'') V
 ON D.vietnamese_holiday_date = V.DAY_OFF
 ;