SELECT
	*
FROM m_term
WHERE
	delete_date is NULL
	AND is_loan_term = 0
ORDER BY sort_order ASC