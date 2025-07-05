SELECT 
	id,
	m_interest_rate_id AS interest_id,
	m_city_id AS city_id,
	update_date_time AS update_date_time
FROM t_interest_rate_history
WHERE m_city_id = /*cityId*/
ORDER BY update_date_time DESC
LIMIT 
    /*sizeOfPage*/
OFFSET 
    /*offset*/