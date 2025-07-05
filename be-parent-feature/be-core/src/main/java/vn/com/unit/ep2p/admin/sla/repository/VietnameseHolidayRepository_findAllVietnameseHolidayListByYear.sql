WITH cte AS
(
  SELECT ROW_NUMBER() OVER (ORDER BY (SELECT 1)) - 1 AS [Incrementor]
  FROM   [master].[sys].[columns] sc1
  CROSS JOIN [master].[sys].[columns] sc2
),
temp as 
(
	SELECT DATEADD(DAY, cte.[Incrementor], /*vietnameseHolidaySearchDto.firstDate*/) as numberDay
	FROM   cte
	WHERE  DATEADD(DAY, cte.[Incrementor], /*vietnameseHolidaySearchDto.firstDate*/) <= CONVERT(datetime, /*vietnameseHolidaySearchDto.lastDate*/, 105)
)
select 
 numberDay as vietnamese_holiday_date,
 Month(numberDay) as month,
 Day(numberDay) as day,
 CASE 
 WHEN V.VIETNAMESEHOLIDAYDATE IS NULL THEN 'false' 
 ELSE 'true' 
 END AS is_holiday,
 V.description
 from temp
LEFT JOIN (SELECT * FROM SLA_DAY_OFF jca 
			WHERE jca.calendar_type_id = /*vietnameseHolidaySearchDto.calendarType*/
			/*IF vietnameseHolidaySearchDto.companyId != null*/
			AND jca.company_id = /*vietnameseHolidaySearchDto.companyId*/
			/*END*/
			/*IF vietnameseHolidaySearchDto.companyId == null*/
			AND jca.company_id IS NULL
			/*END*/
			) V
ON temp.numberDay = V.VIETNAMESEHOLIDAYDATE
