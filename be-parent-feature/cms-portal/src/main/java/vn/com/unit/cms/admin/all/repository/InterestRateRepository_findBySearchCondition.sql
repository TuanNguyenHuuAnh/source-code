SELECT *
FROM m_interest_rate
WHERE m_city_id = /*interestRateSearchDto.cityId*/
		/*IF interestRateSearchDto.displayDate == null*/
		AND display_date = (SELECT max(display_date)
		FROM m_interest_rate
		WHERE m_city_id = /*interestRateSearchDto.cityId*/)
		--ELSE AND display_date = /*interestRateSearchDto.displayDate*/
		/*END*/
ORDER BY m_term_id