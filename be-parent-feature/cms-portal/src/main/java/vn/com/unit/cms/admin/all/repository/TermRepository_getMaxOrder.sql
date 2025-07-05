
SELECT
	max(m_term.sort_order)
FROM
	 m_term
WHERE
	m_term.delete_date is NULL