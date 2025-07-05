SELECT
	id,
	m_currency_id,
	m_language_code,
	title
FROM
	 m_currency_language
WHERE
	m_currency_id = /*currencyId*/
	AND
	delete_date is NULL