SELECT *
FROM m_interest_rate_language
WHERE delete_date is null
	AND interest_rate_id = /*interestRateId*/