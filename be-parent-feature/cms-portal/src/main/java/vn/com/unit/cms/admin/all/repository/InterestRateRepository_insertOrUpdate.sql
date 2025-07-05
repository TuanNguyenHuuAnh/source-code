INSERT INTO m_interest_rate 
	(
		id, 
		currency_id, 
		m_term_id, 
		m_city_id, 
		display_date,
		value
	)
VALUES 
	(
		/*interestRate.interestId*/, 
		/*interestRate.currencyId*/., 
		/*interestRate.termId*/,
		/*interestRate.cityId*/,
		/*interestRate.displayDate*/,
		/*interestRate.value*/
	)
ON DUPLICATE KEY UPDATE 
	value=/*interestRate.value*/;